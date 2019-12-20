package distributionEvaluator;

import java.util.ArrayList;
import java.util.Random;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class DistributionEvaluator {

	AllocationMap allocation_map_;
	ArrayList<Hardware> nodes_;

	int filter_size_;
	int stride_;
	int padding_;

	Random rand_;

	public boolean enable_zero_padding_ = false;

	public DistributionEvaluator(AllocationMap map, ArrayList<Hardware> nodes) {
		allocation_map_ = map;
		nodes_ = nodes;

		filter_size_ = 3;
		stride_ = 1;
		padding_ = 1;
	}

	//処理割り当て比率
	public void evaluatePerformanceBallanceA() {

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

	public void setRandom(Random rand) {
		rand_ = rand;
	}

	//
	public double evaluatePerformanceBallance() {
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
		double sump = 0;
		double qsum = 0;

		for (int i = 0; i < totalUnits.size(); i++) {
			System.out.println("unit amount [" + i + "] : " + totalUnits.get(i));
			sum += totalUnits.get(i);
			sump += nodes_.get(i).performance_;
		}
		for (int i = 0; i < totalUnits.size(); i++) {

			double ratio =  totalUnits.get(i) / sum;
			double p_ratio = nodes_.get(i).performance_ / sump;
			System.out.println("unit ratio [" + (i + 1) + "] : " + ratio);
			System.out.println("performance ratio [" + (i + 1) + "] : " + p_ratio);
			qsum += (ratio - p_ratio)*(ratio - p_ratio);

		}

		double ret = qsum / nodes_.size();
		ret = Math.sqrt(ret);
		ret *= nodes_.size();//全体の誤差

//		System.out.println(ret);

		return ret;


	}




}
