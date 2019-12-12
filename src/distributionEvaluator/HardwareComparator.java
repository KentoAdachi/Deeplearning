package distributionEvaluator;

import java.util.Comparator;

import distributionGenerator.Hardware;

public class HardwareComparator implements Comparator<Hardware>{

	@Override
	public int compare(Hardware o1, Hardware o2) {
		// TODO 自動生成されたメソッド・スタブ
		return o1.performance_ > o2.performance_ ? -1 : 1;
	}

}
