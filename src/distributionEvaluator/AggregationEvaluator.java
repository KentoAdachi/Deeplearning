package distributionEvaluator;

import distributionGenerator.AllocationMap;
import distributionGenerator.Unit;

public class AggregationEvaluator {

	//ノード間の距離
	double node_distance_;
	AllocationMap allocationmap_;
	//距離とパケットサイズから到達率の計算

	public double distance(Unit s, Unit d) {

		//		ユークリッド距離を求める
		double x = node_distance_ * (d.x_ - s.x_);
		double y = node_distance_ * (d.y_ - s.y_);
		return Math.sqrt(x * x + y * y);

	}

	public double prob(double distance, int packet_size) {
		return 0;
	}

}
