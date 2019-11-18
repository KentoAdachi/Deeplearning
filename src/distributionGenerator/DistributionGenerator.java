package distributionGenerator;

import java.util.ArrayList;

public abstract class DistributionGenerator implements IGenerator {

	int max_units_;
	AllocationMap allocationMap_;
	DelayMap delayMap_;
	ArrayList<Hardware> nodes_;

	ArrayList<Integer> num_units_;

//	double total_performance_;

	//num_units_をこちら側で管理したほうがいいのではないか
	@Override
	public abstract void generate();


	//能力から割り当て数を決定
	@Override
	public void calc() {

		num_units_ = new ArrayList<>();

		double sum = 0;
		for (Hardware hardware : nodes_) {
			sum += hardware.performance_;
		}
//		total_performance_ = sum;



		int tmp = 0;
		//		for (Hardware hardware : nodes_) {
		for (int i = 0; i < nodes_.size() - 1; i++) {
//			nodes_.get(i).num_units_left_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
//			nodes_.get(i).num_units_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));

			num_units_.add(Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum)));

//			System.out.println(nodes_.get(i).num_units_);
//			tmp += nodes_.get(i).num_units_left_;
		}

//		nodes_.get(nodes_.size() - 1).num_units_left_ = max_units_ - tmp;
//		nodes_.get(nodes_.size() - 1).num_units_ = max_units_ - tmp;

		num_units_.add(max_units_ - tmp);

		//checker
		tmp = 0;
		for (Hardware hardware : nodes_) {
//			tmp += hardware.num_units_left_;
			System.out.println(hardware);
		}
		System.out.println("total units : " + tmp);

	}

	@Override
	public AllocationMap getAllocationMap() {
		// TODO 自動生成されたメソッド・スタブ
		return allocationMap_;
	}

}
