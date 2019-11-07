package distributionGenerator;

import java.util.ArrayList;

public class GeneratorSimple implements IGenerator {

	int max_units_;
	AllocationMap allocationMap_;
	DelayMap delayMap_;

	ArrayList<Hardware> nodes_;

	public GeneratorSimple(ArrayList<Hardware> nodes, int w, int h) {
		nodes_ = nodes;
		max_units_ = w * h;
		allocationMap_ = new AllocationMap(w, h);

	}

	public void generate() {
		//割り当て結果の入ったリストを生成
				ArrayList<Integer> omikuziList = new ArrayList<Integer>();
				for (int i = 0; i < nodes_.size(); i++) {
					for (int j = 0; j < nodes_.get(i).num_units_; j++) {
						omikuziList.add(i + 1);
					}
				}

				//		for (Integer integer : omikuziList) {
				//		System.out.print(integer);
				//	}

				//取り出しながら端から埋める
				for (int x = 0; x < allocationMap_.w_; x++) {
					for (int y = 0; y < allocationMap_.h_; y++) {
						allocationMap_.set(x, y, omikuziList.remove(0));
					}
				}

				System.out.println(allocationMap_);
	}

//	void saiki_simple(int x, int y) {
//		int l;
//		for (l = 0; l < nodes_.size(); l++) {
//			if (nodes_.get(l).num_units_left_ > 0) {
//
//				nodes_.get(l).num_units_left_--;
//				break;
//			}
//		}
//		//		System.out.println(l);
//
//		allocationMap_.set(x, y, l + 1);
//
//		//		if(l == nodes_.size())return;
//
//		//		隣接ユニットを取得する
//		//初期値について再考する
//		int min_w = 0;
//		int min_h = 0;
//		int max_w = allocationMap_.w_ - 1;
//		int max_h = allocationMap_.h_ - 1;
//
//		if (x + 1 <= max_w) {
//			saiki_simple(x + 1, y);
//		} else if (y + 1 <= max_h) {
//			saiki_simple(0, y + 1);
//		}
//
//	}

	//能力から割り当て数を決定
	public void calc() {

		double sum = 0;
		for (Hardware hardware : nodes_) {
			sum += hardware.performance_;

		}

		int tmp = 0;
		//		for (Hardware hardware : nodes_) {
		for (int i = 0; i < nodes_.size() - 1; i++) {
			nodes_.get(i).num_units_left_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
			nodes_.get(i).num_units_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));

			tmp += nodes_.get(i).num_units_left_;
		}

		nodes_.get(nodes_.size() - 1).num_units_left_ = max_units_ - tmp;
		nodes_.get(nodes_.size() - 1).num_units_ = max_units_ - tmp;

		//checker
		tmp = 0;
		for (Hardware hardware : nodes_) {
			tmp += hardware.num_units_left_;
		}
		System.out.println("total units : " + tmp);

	}

}
