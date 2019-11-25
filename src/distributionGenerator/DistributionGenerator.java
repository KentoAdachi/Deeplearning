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

		double sum_performance = 0;
		for (Hardware hardware : nodes_) {
			sum_performance += hardware.performance_;
		}
//		total_performance_ = sum;



		int total_unit = 0;
		//		for (Hardware hardware : nodes_) {
		for (int i = 0; i < nodes_.size() - 1; i++) {
//			nodes_.get(i).num_units_left_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
//			nodes_.get(i).num_units_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
			int num_unit = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum_performance));
			num_units_.add(num_unit);
//			System.out.println(Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum)));
//			System.out.println(nodes_.get(i).num_units_);
//			tmp += nodes_.get(i).num_units_left_;
			total_unit += num_unit;
		}

//		nodes_.get(nodes_.size() - 1).num_units_left_ = max_units_ - tmp;
//		nodes_.get(nodes_.size() - 1).num_units_ = max_units_ - tmp;

		num_units_.add(max_units_ - total_unit);

		//checker
		total_unit = 0;
		for (Hardware hardware : nodes_) {
//			tmp += hardware.num_units_left_;
			System.out.println(hardware);
		}
		System.out.println("total units : " + total_unit);

		for (int i = 0; i < num_units_.size(); i++) {
			System.out.println("unit ["+i+"] : "+num_units_.get(i));
		}

	}

	@Override
	public AllocationMap getAllocationMap() {
		// TODO 自動生成されたメソッド・スタブ
		return allocationMap_;
	}

}
