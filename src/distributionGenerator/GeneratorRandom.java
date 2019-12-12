package distributionGenerator;

import java.util.ArrayList;
import java.util.Collections;

public class GeneratorRandom extends DistributionGenerator implements IGenerator {

//	int max_units_;
//	AllocationMap allocationMap_;
//	ArrayList<Hardware> nodes_;
//	double total_performance_;

	public GeneratorRandom(ArrayList<Hardware> nodes, int w, int h) {
		nodes_ = nodes;
		max_units_ = w * h;
		allocationMap_ = new AllocationMap(w, h);

		for (int i = 0; i < nodes_.size(); i++) {
			Hardware node = nodes_.get(i);
			allocationMap_.set(node.x_,node.y_, i+1);
		}


	}

	public void generate() {

		//割り当て結果の入ったリストを生成
		ArrayList<Integer> omikuziList = new ArrayList<Integer>();
		for (int i = 0; i < nodes_.size(); i++) {
//			for (int j = 0; j < nodes_.get(i).num_units_; j++) {
			for (int j = 0; j < num_units_.get(i); j++) {
				omikuziList.add(i + 1);
			}
		}

		//シャッフル
		Collections.shuffle(omikuziList);

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



	//能力から割り当て数を決定
//	public void calc() {
//
//		double sum = 0;
//		for (Hardware hardware : nodes_) {
//			sum += hardware.performance_;
//
//		}
//		total_performance_ = sum;
//
//		int tmp = 0;
//		//		for (Hardware hardware : nodes_) {
//		for (int i = 0; i < nodes_.size() - 1; i++) {
//			nodes_.get(i).num_units_left_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
//			nodes_.get(i).num_units_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
//
//			tmp += nodes_.get(i).num_units_left_;
//		}
//
//		nodes_.get(nodes_.size() - 1).num_units_left_ = max_units_ - tmp;
//		nodes_.get(nodes_.size() - 1).num_units_ = max_units_ - tmp;
//
//		//checker
//		tmp = 0;
//		for (Hardware hardware : nodes_) {
//			tmp += hardware.num_units_left_;
////			System.out.println(hardware);
//		}
//		System.out.println("total units : " + tmp);
//
//	}

	@Override
	public AllocationMap getAllocationMap() {
		// TODO 自動生成されたメソッド・スタブ
		return allocationMap_;
	}

}
