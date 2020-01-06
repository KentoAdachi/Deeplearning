package distributionEvaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import distributionGenerator.AllocationMap;
import distributionGenerator.Hardware;

public class DistributionEvaluatorCore {

	public static void main(String[] args) throws IOException {

		//		DelayMap map = new DelayMap("./resource/delaymap.txt", 3);
		//		System.out.println(map);

		ArrayList<Hardware> nodes = new ArrayList<Hardware>();

//		nodes.add(new Hardware(100, "a"));
//		nodes.add(new Hardware(200, "b"));
//		nodes.add(new Hardware(300, "c"));
//		nodes.add(new Hardware(400, "d"));
		nodes.add(new Hardware(0,0,100, "a"));
		nodes.add(new Hardware(9,0,200, "b"));
		nodes.add(new Hardware(9,9,300, "c"));

//		for (Hardware hardware : nodes) {
//			System.out.println(hardware);
//		}

		//マップの読み取り

		File file = new File("./resource/test.txt");

		Random rand = new Random(0);
		AllocationMap allocation_map = new AllocationMap(file);
		System.out.println(allocation_map);


		IEvaluator evaluator = new ConvolutionEvaluator(allocation_map, nodes);
//		IEvaluator evaluator = new PoolingEvaluator(allocation_map, nodes);
		evaluator.setRandom(rand);


		evaluator.evaluatePerformanceBallance();
		System.out.println("通信量 : "+evaluator.evaluateTranslatedDataAmount());

		evaluator.getOutputMap();


//		evaluator.evaluateTranslatedDataAmount();

		return;
	}

}
