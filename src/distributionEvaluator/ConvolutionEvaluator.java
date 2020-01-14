package distributionEvaluator;

import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class ConvolutionEvaluator extends DistributionEvaluator implements IEvaluator {

	public ConvolutionEvaluator(AllocationMap map, ArrayList<Hardware> nodes) {
		super(map, nodes);
		// TODO 自動生成されたコンストラクター・ スタブ
	}

	@Override
	public float evaluateTranslatedDataAmount() {
		// TODO 自動生成されたメソッド・スタブ
		return this.evaluateTranslatedDataAmount_B();
	}

	public float[][] forward(float[][] input, float[][] filter) throws Exception {
		float[][] ret = new float[input.length][input.length];
		int count_translate = 0;
		//		for (int num_node = 0; num_node < nodes_.size(); num_node++) {

		AllocationMap map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);
		map.setRandom(rand_);
		for (int x = 0; x < allocation_map_.w_; x++) {
			for (int y = 0; y < allocation_map_.h_; y++) {
				int current_unit = allocation_map_.get(x, y) - 1;
				Hardware h_s = nodes_.get(current_unit);
				//					Unit s = new Unit(x, y, current_unit+1);
				//					Unit u_s = new Unit(node.x_,node.y_,num_node+1);

				int loop_to_x;
				int loop_to_y;

				int radius;
				if (filter_size_ % 2 == 1) {
					//フィルタが奇数の時
					radius = filter_size_ / 2;
					loop_to_y = y + radius;
					loop_to_x = x + radius;
				} else {
					//フィルタが偶数の時
					radius = filter_size_ / 2 - 1;
					loop_to_y = y + radius + 1;
					loop_to_x = x + radius + 1;
				}

				float sum = 0;
				int j_f = 0;
				for (int j = y - radius; j <= loop_to_y; j++) {
					int i_f = 0;
					for (int i = x - radius; i <= loop_to_x; i++) {

						//						Todo : ijが範囲内にあることを保証する
						if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
							int comparasive_unit = allocation_map_.get(i, j) - 1;
							Hardware h_d = nodes_.get(comparasive_unit);
							//								Unit u_d = new Unit(h_d.x_, h_d.y_, comparasive_unit+1);

							//								Unit d = new Unit(i, j, comparasive_unit+1);
							//							if (current_unit == comparasive_unit) {
							//								sum += input[i][j] * filter[i_f][j_f];
							//							} else {
//							System.out.println(allocation_map_.isConnectionSucceed(allocation_map_.distance(h_s, h_d)));
							if (allocation_map_.isConnectionSucceed(allocation_map_.distance(h_s, h_d))) {
								sum += input[i][j] * filter[i_f][j_f];
							} else {
								//TODO 線形補間を行う
								//										sum +=
								//										allocation_map_.get(i+1, j);
								//										float right = input[i+1][j];
								//										float left = input[i-1][j];
							}
							//							}
							//								if (current_unit == num_node && current_unit != comparasive_unit) {
							//									//隣接ノードのうち所属の違うノード
							//									try {
							//										//										map.set(s, d);
							//										count_translate = map.set(h_s, h_d, i, j, comparasive_unit + 1)
							//												? count_translate + 1
							//												: count_translate;
							//										//										map.set(i, j, comparasive_unit + 1,true);
							//									} catch (Exception e) {
							//										e.printStackTrace();
							//									}
							//									//									map.set(i, j, comparasive_unit + 1);
							//								}
						}
						i_f++;
					}
					j_f++;
				}
				ret[x][y] = sum;

			}
		}

		//チェック
		//			System.out.println("node : " + (num_node + 1));
		//		System.out.println(map);
		//		ArrayList<Integer> cnt_node = new ArrayList<Integer>();
		//		for (int i = 0; i < nodes_.size(); i++) {
		//			cnt_node.add(0);
		//		}
		//		//
		//		for (int x = 0; x < map.w_; x++) {
		//			for (int y = 0; y < map.h_; y++) {
		//
		//				int current_unit = map.get(x, y);
		//				if (current_unit != 0) {
		//					cnt_node.set(current_unit - 1, cnt_node.get(current_unit - 1) + 1);
		//				}
		//			}
		//		}
		//		for (int i = 0; i < cnt_node.size(); i++) {
		//			System.out.println("node " + (i + 1) + " count :" + cnt_node.get(i));
		//			//				ret += cnt_node.get(i);
		//		}
		//		}
		//		}

		return ret;

	}

	//送受信データ量
	//データの重複を考慮していない
	@Deprecated
	public void evaluateTranslatedDataAmount_A() {

		ArrayList<Integer> datas = new ArrayList<>();
		for (Hardware hardware : nodes_) {
			datas.add(0);
		}

		for (int y = 0; y < allocation_map_.h_; y++) {
			for (int x = 0; x < allocation_map_.w_; x++) {

				int current_unit = allocation_map_.get(x, y) - 1;
				int radius = filter_size_ / 2;
				//8近傍を見る
				for (int j = y - radius; j <= y + radius; j++) {
					for (int i = x - radius; i <= x + radius; i++) {
						//						Todo : ijが範囲内にあることを保証する
						if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
							int comparasive_unit = allocation_map_.get(i, j) - 1;
							if (current_unit != comparasive_unit) {
								datas.set(current_unit, datas.get(current_unit) + 1);
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < datas.size(); i++) {
			System.out.println("data translated amount[" + (i + 1) + "] : " + datas.get(i));
		}

	}

	@Deprecated
	public void evaluateTranslatedDataAmount_C() {
		for (int num_node = 0; num_node < nodes_.size(); num_node++) {
			Hardware node = nodes_.get(num_node);
			AllocationMap white_map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);
			white_map.setRandom(rand_);

			//フィルタサイズが奇数か偶数かで場合分けした方がよさそう
			//xy初期位置要チェック
			//奇数の場合

			if (filter_size_ % 2 == 1) {
				int radius = filter_size_ / 2;
				for (int x = 0; x < allocation_map_.w_; x += stride_) {
					for (int y = 0; y < allocation_map_.h_; y += stride_) {
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

									}
								}
							}
						}
					}
				}
			} else {
				//偶数の場合
				int radius = filter_size_ / 2 - 1;
				for (int x = 0; x < allocation_map_.w_; x += stride_) {
					for (int y = 0; y < allocation_map_.h_; y += stride_) {

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

	/*作り方
	 *	各ノードのallocmapを作る
	 *	allocmapからみてフィルタの届く範囲のデータを追加する
	 *	データの数をカウントする
	 *
	 * */
	//データの重複を考慮する送受信データ量
	public float evaluateTranslatedDataAmount_B() {
		int count_translate = 0;
		float ret = 0;
		for (int num_node = 0; num_node < nodes_.size(); num_node++) {
			Hardware h_s = nodes_.get(num_node);
			AllocationMap map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);
			map.setRandom(rand_);
			//			map.setRandom();

			for (int x = 0; x < allocation_map_.w_; x++) {
				for (int y = 0; y < allocation_map_.h_; y++) {
					int current_unit = allocation_map_.get(x, y) - 1;

					//					Unit s = new Unit(x, y, current_unit+1);
					//					Unit u_s = new Unit(node.x_,node.y_,num_node+1);

					int loop_to_x;
					int loop_to_y;

					int radius;
					if (filter_size_ % 2 == 1) {
						radius = filter_size_ / 2;
						loop_to_y = y + radius;
						loop_to_x = x + radius;
						//8近傍を見る
						//フィルタが奇数の時

						//						for (int j = y - radius; j <= y + radius; j++) {
						//							for (int i = x - radius; i <= x + radius; i++) {
						//								//						Todo : ijが範囲内にあることを保証する
						//								if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
						//									int comparasive_unit = allocation_map_.get(i, j) - 1;
						//									if (current_unit == num_node && current_unit != comparasive_unit) {
						//										//隣接ノードのうち所属の違うノード
						//										map.set(i, j, comparasive_unit + 1);
						//
						//									}
						//								}
						//							}
						//						}

					} else {
						//フィルタが偶数の時
						radius = filter_size_ / 2 - 1;
						loop_to_y = y + radius + 1;
						loop_to_x = x + radius + 1;
						//8近傍を見る
						//フィルタが奇数の時
						//						for (int j = y - radius; j <= y + radius + 1; j++) {
						//							for (int i = x - radius; i <= x + radius + 1; i++) {
						//								//						Todo : ijが範囲内にあることを保証する
						//								if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
						//									int comparasive_unit = allocation_map_.get(i, j) - 1;
						//									if (current_unit == num_node && current_unit != comparasive_unit) {
						//										//隣接ノードのうち所属の違うノード
						//										map.set(i, j, comparasive_unit + 1);
						//
						//									}
						//								}
						//							}
						//						}
					}

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
										count_translate = map.set(h_s, h_d, i, j, comparasive_unit + 1)
												? count_translate + 1
												: count_translate;
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

	@Override
	public AllocationMap getOutputMap() {
		// TODO 自動生成されたメソッド・スタブ
		return allocation_map_;
	}
}