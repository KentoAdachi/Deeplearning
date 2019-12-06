package distributionEvaluator;

import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class DistributionEvaluator {

	AllocationMap allocation_map_;
	ArrayList<Hardware> nodes_;

	int filter_size_;
	int stride_;

	public boolean enable_zero_padding_ = false;

	public DistributionEvaluator(AllocationMap map, ArrayList<Hardware> nodes) {
		allocation_map_ = map;
		nodes_ = nodes;

		filter_size_ = 3;
		stride_ = 1;
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




}
