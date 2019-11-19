package distributionEvaluator;

import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class DistributionEvaluator{

	AllocationMap allocation_map_;
	ArrayList<Hardware> nodes_;

	public boolean enable_zero_padding_ = false;

	public DistributionEvaluator(AllocationMap map,ArrayList<Hardware>nodes) {
		allocation_map_ = map;
		nodes_ = nodes;
	}

	//処理割り当て比率
	public void evaluatePerformanceBallance() {

	}

	//送受信データ量
	public void evaluateTranslatedDataAmount() {

	}


}
