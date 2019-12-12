package distributionEvaluator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import distributionGenerator.AllocationMap;
import distributionGenerator.GeneratorRandom;
import distributionGenerator.Hardware;
import distributionGenerator.IGenerator;

public class DistributionEvaluatorScene1 {

	static long SEED = 0;
	static Random rand = new Random(SEED);


	static void execute(int batch, int step, int from, int to) throws IOException {


		for (int num_nodes = from; num_nodes <= to; num_nodes+=step) {
			for (int i = 0; i < batch; i++) {
				scene(num_nodes);
			}
		}
	}

	static void scene(int num_nodes) throws IOException {

		ArrayList<Hardware> nodes = new ArrayList<Hardware>();

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

		ArrayList<Hardware> nodes_calc = new ArrayList<Hardware>(nodes.subList(0, num_nodes));


		AllocationMap map = new AllocationMap(10, 10, nodes_calc);

		System.out.println(map);

//		IGenerator generator = new GeneratorLocative(nodes_calc,map);
		IGenerator generator = new GeneratorRandom(nodes_calc, 10, 10);

		generator.calc();
		generator.generate();

		for (Hardware hardware : nodes_calc) {
			System.out.println(hardware);
		}

//		System.out.println("Program completed");

		File file = new File("./resource/test.txt");

		try {
			generator.getAllocationMap().write(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("filepath : " + file.getPath());


		ConvolutionEvaluator evaluator = new ConvolutionEvaluator(map, nodes_calc);

		double res_p =  evaluator.evaluatePerformanceBallance();

		File output = new File("./resource/output.txt");
		System.out.println("filepath : " + output.getPath());
		FileWriter fw = new FileWriter(output,true);

		fw.write("計算ノードの数 :,"+ nodes_calc.size() +",評価 : ,"+res_p+"\n");

		fw.close();
	}

	public static void main(String[] args) throws IOException {

		//		DelayMap map = new DelayMap("./resource/delaymap.txt", 3);
		//		System.out.println(map);


		File output = new File("./resource/output.txt");
		System.out.println("filepath : " + output.getPath());
		FileWriter fw = new FileWriter(output);
		fw.close();

		System.out.println("start");
		execute(100, 1, 1, 100);
//		scene(100);


//ここからループ





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
