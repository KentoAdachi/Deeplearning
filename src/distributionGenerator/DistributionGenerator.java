package distributionGenerator;

import java.util.ArrayList;

public class DistributionGenerator {


	int max_units_;

	ArrayList<Hardware> nodes_;






	public DistributionGenerator(ArrayList<Hardware>nodes,int max_units) {
		nodes_ = nodes;
		max_units_ = max_units;

	}


	//割り当てを決定
	public void generate() {

	}

	//能力から割り当て数を決定
	public void calculateBalanceFromPerformance() {


		double sum = 0;
		for (Hardware hardware : nodes_) {
			sum += hardware.performance_;

		}



		int tmp = 0;
//		for (Hardware hardware : nodes_) {
		for(int i = 0;i < nodes_.size()-1;i++) {
			nodes_.get(i).num_units_ = Math.round((float)(max_units_*nodes_.get(i).performance_/sum));

			tmp += nodes_.get(i).num_units_;
		}

		nodes_.get(nodes_.size()-1).num_units_ = max_units_-tmp;

		//checker
		tmp = 0;
		for (Hardware hardware : nodes_) {
			tmp += hardware.num_units_;
		}
		System.out.println("total units : "+ tmp);

	}

}
