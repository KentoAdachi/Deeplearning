package distributionEvaluator;

import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class AggregationEvaluator extends DistributionEvaluator implements IEvaluator {

	public AggregationEvaluator(AllocationMap map, ArrayList<Hardware> nodes) {
		super(map, nodes);
		// TODO 自動生成されたコンストラクター・ スタブ
	}

	@Override
	public float evaluateTranslatedDataAmount() {
		// TODO 自動生成されたメソッド・スタブ
		this.evaluateTranslatedDataAmount_B();
		return 0;
	}

	/*作り方
	 *	通信成功フィルタを作る
	 *	全てのセンサノードに対して成功失敗を記録した配列を作る
	 *
	 * */
	//データの重複を考慮する送受信データ量
	@Deprecated
	public void evaluateTranslatedDataAmount_B() {
		for (int y = 0; y < allocation_map_.h_; y++) {
			for (int x = 0; x < allocation_map_.w_; x++) {
				int parent = allocation_map_.get(x, y) - 1;
				Hardware parent_h = nodes_.get(parent);
				//未実装

			}
		}

	}

	//各ノードのからデータを集約する場合の評価を行う．
	public float[][] forward(float[][] input, float[][] filter) throws Exception {
		float[][] ret = new float[input.length][input.length];
		int count_translate = 0;
		//		for (int num_node = 0; num_node < nodes_.size(); num_node++) {

		AllocationMap map = new AllocationMap(this.allocation_map_.w_, this.allocation_map_.h_);
		map.setRandom(rand_);
		for (int x = 0; x < allocation_map_.w_; x++) {
			for (int y = 0; y < allocation_map_.h_; y++) {
				int current_unit = allocation_map_.get(x, y) - 1;
				Hardware h_s = new Hardware(x, y, 100, "test");

				Hardware h_d = nodes_.get(current_unit);

				if (allocation_map_.isConnectionSucceed(allocation_map_.distance(h_s, h_d))) {
					//								sum += input[i][j] * filter[i_f][j_f];
					ret[x][y] = input[x][y];
				} else {
					//TODO 線形補間を行う
					ret[x][y] = 0;

				}

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

	@Override
	public AllocationMap getOutputMap() {
		// TODO 自動生成されたメソッド・スタブ
		return allocation_map_;
	}
}
