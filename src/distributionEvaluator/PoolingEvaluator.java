package distributionEvaluator;

import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class PoolingEvaluator extends DistributionEvaluator implements IEvaluator {

	public PoolingEvaluator(AllocationMap map, ArrayList<Hardware> nodes) {
		super(map, nodes);
		// TODO 自動生成されたコンストラクター・スタブ
		stride_ = 2;
		filter_size_ = 2;
	}

	public PoolingEvaluator(AllocationMap map, ArrayList<Hardware> nodes, int stride, int filter_size) {
		super(map, nodes);
		stride_ = stride;
		filter_size_ = filter_size;
	}

	@Override
	public float evaluateTranslatedDataAmount() {
		// TODO 自動生成されたメソッド・スタブ
		return evaluateTranslatedDataAmount_B();


	}

	public void evaluateTranslatedDataAmount_C() {
		for (int num_node = 0; num_node < nodes_.size(); num_node++) {
			Hardware node = nodes_.get(num_node);
			AllocationMap white_map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);

			//フィルタサイズが奇数か偶数かで場合分けした方がよさそう
			//xy初期位置要チェック
			//奇数の場合
			//randset
			int loop_to_x;
			int loop_to_y;

			if (filter_size_ % 2 == 1) {
				int radius = filter_size_ / 2;
				for (int x = radius; x < allocation_map_.w_; x += stride_) {
					for (int y = radius; y < allocation_map_.h_; y += stride_) {
						int current_unit = allocation_map_.get(x, y) - 1;

						//8近傍を見る
						for (int j = y - radius; j <= y + radius; j++) {
							for (int i = x - radius; i <= x + radius; i++) {

								//						Todo : ijが範囲内にあることを保証する
								if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
									//									System.out.println(x + "," + y + "  " + i + "," + j + "  " + allocation_map_.get(x, y)
									//									+ "  " + allocation_map_.get(i, j));
									int comparasive_unit = allocation_map_.get(i, j) - 1;
									if (current_unit == num_node && current_unit != comparasive_unit) {
										//隣接ノードのうち所属の違うノード
										white_map.set(i, j, comparasive_unit + 1);
										//breakを使って抜ける

									}
								}
							}
						}
					}
				}
			} else {
				//偶数の場合
				int radius = filter_size_ / 2 - 1;
				for (int x = radius; x < allocation_map_.w_; x += stride_) {
					for (int y = radius; y < allocation_map_.h_; y += stride_) {
						int current_unit = allocation_map_.get(x, y) - 1;
						//8近傍を見る
						for (int j = y - radius; j <= y + radius + 1; j++) {
							for (int i = x - radius; i <= x + radius + 1; i++) {
								//						Todo : ijが範囲内にあることを保証する
								if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
									//									System.out.println(x + "," + y + "  " + i + "," + j + "  " + allocation_map_.get(x, y)
									//									+ "  " + allocation_map_.get(i, j));
									int comparasive_unit = allocation_map_.get(i, j) - 1;
									if (current_unit == num_node && current_unit != comparasive_unit) {
										//隣接ノードのうち所属の違うノード
										white_map.set(i, j, comparasive_unit + 1);
										//										System.out.println("!");

									}
								}
							}
						}
					}
				}
			}
			//チェック
			System.out.println("node : " + (num_node + 1));
			System.out.println(white_map);
			ArrayList<Integer> cnt_node = new ArrayList<Integer>();
			for (int i = 0; i < nodes_.size(); i++) {
				cnt_node.add(0);
			}
			//
			for (int x = 0; x < white_map.w_; x++) {
				for (int y = 0; y < white_map.h_; y++) {

					int current_unit = white_map.get(x, y);
					if (current_unit != 0) {
						cnt_node.set(current_unit - 1, cnt_node.get(current_unit - 1) + 1);
					}
				}
			}
			for (int i = 0; i < cnt_node.size(); i++) {
				System.out.println("node " + (i + 1) + " count :" + cnt_node.get(i));
			}
		}
	}

	//現状畳み込みからコピーしただけ
	public float evaluateTranslatedDataAmount_B() {
		float ret = 0;
		for (int num_node = 0; num_node < nodes_.size(); num_node++) {
			Hardware h_s = nodes_.get(num_node);
			AllocationMap map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);
			map.setRandom(rand_);
			//			map.setRandom();
			int radius;
			if (filter_size_ % 2 == 1) {
				radius = filter_size_ / 2;
			} else {
				radius = filter_size_ / 2 - 1;
			}
			for (int x = radius; x < allocation_map_.w_; x += stride_) {
				for (int y = radius; y < allocation_map_.h_; y += stride_) {
					int current_unit = allocation_map_.get(x, y) - 1;
					//					Unit s = new Unit(x, y, current_unit+1);
					//					Unit u_s = new Unit(node.x_,node.y_,num_node+1);
					int loop_to_x;
					int loop_to_y;

					if (filter_size_ % 2 == 1) {
						loop_to_y = y + radius;
						loop_to_x = x + radius;
						//フィルタが奇数の時
					} else {
						//フィルタが偶数の時
						loop_to_y = y + radius + 1;
						loop_to_x = x + radius + 1;

					}

					filter:
					for (int j = y - radius; j <= loop_to_y; j++) {
						for (int i = x - radius; i <= loop_to_x; i++) {

							//						Todo : ijが範囲内にあることを保証する
							if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
								int comparasive_unit = allocation_map_.get(i, j) - 1;
								Hardware h_d = nodes_.get(comparasive_unit);
								//								Unit u_d = new Unit(h_d.x_, h_d.y_, comparasive_unit+1);

								//								Unit d = new Unit(i, j, comparasive_unit+1);
								if (current_unit == num_node && current_unit != comparasive_unit) {
									//隣接ノードのうち所属の違うノード
									try {
										//										map.set(s, d);
										if(map.set(h_s, h_d, i, j, comparasive_unit + 1)) {
											System.out.println("break "+i+","+j);
											break filter;
										}

										//										map.set(i, j, comparasive_unit + 1,true);
									} catch (Exception e) {
										// TODO 自動生成された catch ブロック
										e.printStackTrace();
									}
									//									map.set(i, j, comparasive_unit + 1);
								}
							}
						}
					}

				}
			}

			//チェック
			System.out.println("node : " + (num_node + 1));
			System.out.println(map);
			ArrayList<Integer> cnt_node = new ArrayList<Integer>();
			for (int i = 0; i < nodes_.size(); i++) {
				cnt_node.add(0);
			}
			//
			for (int x = 0; x < map.w_; x++) {
				for (int y = 0; y < map.h_; y++) {

					int current_unit = map.get(x, y);
					if (current_unit != 0) {
						cnt_node.set(current_unit - 1, cnt_node.get(current_unit - 1) + 1);
					}
				}
			}
			for (int i = 0; i < cnt_node.size(); i++) {
				System.out.println("node " + (i + 1) + " count :" + cnt_node.get(i));
				ret += cnt_node.get(i);
			}
		}
		return ret;
	}

	public AllocationMap getOutputMap() {

		int out_h = 1 + (allocation_map_.h_ - filter_size_) / stride_;
		int out_w = 1 + (allocation_map_.w_ - filter_size_) / stride_;

		AllocationMap output_map = new AllocationMap(out_w, out_h);
		//		for (int x = 0; x < allocation_map_.w_; x+=stride_) {
		//			for (int y = 0; y < allocation_map_.h_; y+=stride_) {
		//奇数の場合

		if (filter_size_ % 2 == 1) {
			int radius = filter_size_ / 2;
			for (int x = radius; x < allocation_map_.w_; x += stride_) {
				for (int y = radius; y < allocation_map_.h_; y += stride_) {
					//					int current_unit = allocation_map_.get(x, y) - 1;
					int out_x = 1 + (x - filter_size_) / stride_;
					int out_y = 1 + (y - filter_size_) / stride_;
					output_map.set(out_x, out_y, allocation_map_.get(x, y));

				}
			}
		} else {
			//偶数の場合
			int radius = filter_size_ / 2 - 1;
			for (int x = radius; x < allocation_map_.w_; x += stride_) {
				for (int y = radius; y < allocation_map_.h_; y += stride_) {
					//					int current_unit = allocation_map_.get(x, y) - 1;
					int out_x = 1 + (x - filter_size_) / stride_;
					int out_y = 1 + (y - filter_size_) / stride_;
					output_map.set(out_x, out_y, allocation_map_.get(x, y));
				}
			}
		}

		System.out.println(output_map);

		return output_map;
	}

}
