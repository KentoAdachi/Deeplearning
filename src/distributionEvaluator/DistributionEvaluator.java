package distributionEvaluator;

import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class DistributionEvaluator {

	AllocationMap allocation_map_;
	ArrayList<Hardware> nodes_;

	int filter_size_;

	public boolean enable_zero_padding_ = false;

	public DistributionEvaluator(AllocationMap map, ArrayList<Hardware> nodes) {
		allocation_map_ = map;
		nodes_ = nodes;

		filter_size_ = 3;
	}

	//処理割り当て比率
	public void evaluatePerformanceBallance() {

		ArrayList<Integer> totalUnits = new ArrayList<>();
		for (int i = 0; i < nodes_.size(); i++) {
			totalUnits.add(0);
		}

		for (int y = 0; y < allocation_map_.h_; y++) {
			for (int x = 0; x < allocation_map_.w_; x++) {

				int current_unit = allocation_map_.get(x, y) - 1;
				int tmp = totalUnits.get(current_unit);
				totalUnits.set(current_unit, tmp + 1);

			}
		}

		double sum = 0;
		for (int i = 0; i < totalUnits.size(); i++) {
			System.out.println("unit amount [" + i + "] : " + totalUnits.get(i));
			sum += totalUnits.get(i);
		}
		for (int i = 0; i < totalUnits.size(); i++) {
			System.out.println("unit ratio [" + (i + 1) + "] : " + totalUnits.get(i) / sum);
		}

	}

	//送受信データ量
	//データの重複を考慮していない
	public void evaluateTranslatedDataAmount() {

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

	/*作り方
	 *	各ノードのallocmapを作る
	 *	allocmapからみてフィルタの届く範囲のデータを追加する
	 *	データの数をカウントする
	 *
	 * */
	//データの重複を考慮する送受信データ量
	public void evaluateTranslatedDataAmount_mod() {
		for (int num_node = 0; num_node < nodes_.size(); num_node++) {
			Hardware node = nodes_.get(num_node);
			AllocationMap map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);

			for (int x = 0; x < allocation_map_.w_; x++) {
				for (int y = 0; y < allocation_map_.h_; y++) {
					int current_unit = allocation_map_.get(x, y) - 1;
					int radius = filter_size_ / 2;
					//8近傍を見る
					for (int j = y - radius; j <= y + radius; j++) {
						for (int i = x - radius; i <= x + radius; i++) {
							//						Todo : ijが範囲内にあることを保証する
							if (i >= 0 && j >= 0 && i < allocation_map_.w_ && j < allocation_map_.h_) {
								int comparasive_unit = allocation_map_.get(i, j) - 1;
								if (current_unit == num_node && current_unit != comparasive_unit) {
									//隣接ノードのうち所属の違うノード
									map.set(i, j, comparasive_unit + 1);

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
			}

		}

	}

}
