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
	public void evaluateTranslatedDataAmount() {
		// TODO 自動生成されたメソッド・スタブ
		this.evaluateTranslatedDataAmount_B();
	}

	/*作り方
	 *	通信成功フィルタを作る
	 *	全てのセンサノードに対して成功失敗を記録した配列を作る
	 *
	 * */
	//データの重複を考慮する送受信データ量
	public void evaluateTranslatedDataAmount_B() {
		for (int y = 0; y < allocation_map_.h_; y++) {
			for (int x = 0; x < allocation_map_.w_; x++) {
				int parent = allocation_map_.get(x, y) - 1;
				Hardware parent_h = nodes_.get(parent);
				//未実装

			}
		}

	}

	@Override
	public AllocationMap getOutputMap() {
		// TODO 自動生成されたメソッド・スタブ
		return allocation_map_;
	}
}
