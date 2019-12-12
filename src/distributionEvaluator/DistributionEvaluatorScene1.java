package distributionEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import distributionGenerator.Hardware;

public class DistributionEvaluatorScene1 {

	static long SEED = 0;



	public static void main(String[] args) throws IOException {

		//		DelayMap map = new DelayMap("./resource/delaymap.txt", 3);
		//		System.out.println(map);

		ArrayList<Hardware> nodes = new ArrayList<Hardware>();


		Random rand = new Random(SEED);

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				nodes.add(new Hardware(x, y, rand.nextDouble()*200d+100d, "NODE_"+(x + y*10)));
			}
		}

		HardwareComparator comparator = new HardwareComparator();
		Collections.sort(nodes, comparator);



		for (Hardware hardware : nodes) {
			System.out.println(hardware);
		}

//		AllocationMap map = new AllocationMap(10, 10, nodes.subList(0, 3));
//		IGenerator generator = new GeneratorLocative(nodes.subList(0, 3))



//		for (Hardware hardware : nodes) {
//			System.out.println(hardware);
//		}

		//マップの読み取り

//		File file = new File("./resource/test.txt");
//
//		AllocationMap allocation_map = new AllocationMap(file);
//		System.out.println(allocation_map);
//
////		IEvaluator evaluator = new ConvolutionEvaluator(allocation_map, nodes);
//		IEvaluator evaluator = new PoolingEvaluator(allocation_map, nodes);
//
//		evaluator.evaluatePerformanceBallance();
//		evaluator.evaluateTranslatedDataAmount();
//
//		evaluator.getOutputMap();


//		evaluator.evaluateTranslatedDataAmount();

		return;
	}

}
