package distributionEvaluator;

import java.util.Random;

import distributionGenerator.AllocationMap;

public interface IEvaluator {

	//評価項目
	//実行時間
	//割り当てが比率に応じて行われているか


//	評価
	public double evaluatePerformanceBallance();
	 public float evaluateTranslatedDataAmount();
	public AllocationMap getOutputMap();
	public void setRandom(Random rand);
}
