package distributionEvaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class DistributionEvaluatorCore {

	public static void main(String[] args) throws IOException {

		//		DelayMap map = new DelayMap("./resource/delaymap.txt", 3);
		//		System.out.println(map);

		ArrayList<Hardware> nodes = new ArrayList<Hardware>();

		nodes.add(new Hardware(100, "a"));
		nodes.add(new Hardware(200, "b"));
		nodes.add(new Hardware(300, "c"));
//		nodes.add(new Hardware(400, "d"));

//		for (Hardware hardware : nodes) {
//			System.out.println(hardware);
//		}

		//マップの読み取り

		File file = new File("./resource/test.txt");

		AllocationMap allocation_map = new AllocationMap(file);
		System.out.println(allocation_map);

		DistributionEvaluator evaluator = new DistributionEvaluator(allocation_map, nodes);

		evaluator.evaluatePerformanceBallance();
		evaluator.evaluateTranslatedDataAmount();

		evaluator.evaluateTranslatedDataAmount_mod();

		return;
	}

}
