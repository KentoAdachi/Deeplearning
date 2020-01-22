package distributionEvaluator;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import distributionGenerator.AllocationMap;
import distributionGenerator.GeneratorLocative;
import distributionGenerator.GeneratorRandom;
import distributionGenerator.GeneratorSimple;
import distributionGenerator.Hardware;
import distributionGenerator.IGenerator;

public class DistributionEvaluatorScene4 {

	static long SEED = 1;
	static Random rand = new Random(SEED);

	static String OUTPUT_FILE_PATH = "./resource/ConvResult.csv";

	static void execute(int batch, int step, int from, int to) throws Exception {

		for (int i = 0; i < batch; i++) {
			ArrayList<Hardware> nodes = new ArrayList<Hardware>();

			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					nodes.add(new Hardware(x, y, rand.nextDouble() * 200d + 100d, "NODE_" + (x + y * 10)));
				}
			}
			HardwareComparator comparator = new HardwareComparator();
			Collections.sort(nodes, comparator);
			for (Hardware hardware : nodes) {
				System.out.println(hardware);
			}


			for (int num_nodes = from; num_nodes <= to; num_nodes += step) {
//												scene(num_nodes, nodes, "loc",100);
				//				scene(num_nodes, nodes, "rnd", 100);
				//								scene(num_nodes, nodes, "sim",100);
				scene(num_nodes, nodes, "loc", 10);
				scene(num_nodes, nodes, "rnd", 10);
				scene(num_nodes, nodes, "sim", 10);
												scene(num_nodes, nodes, "loc",1);
								scene(num_nodes, nodes, "rnd", 1);
								scene(num_nodes, nodes, "sim",1);

			}
		}
	}

	static void scene(int num_nodes, ArrayList<Hardware> nodes, String alloc_type, float node_interval)
			throws Exception {

		ArrayList<Hardware> nodes_calc = new ArrayList<Hardware>(nodes.subList(0, num_nodes));

		AllocationMap map = new AllocationMap(10, 10, nodes_calc, node_interval);

		System.out.println(map);

		IGenerator generator;

		if (alloc_type.equals("rnd")) {
			generator = new GeneratorRandom(nodes_calc, 10, 10);
		} else if (alloc_type.equals("sim")) {
			generator = new GeneratorSimple(nodes_calc, 10, 10);
		} else {
			generator = new GeneratorLocative(nodes_calc, map);
		}
		//		IGenerator generator = new GeneratorRandom(nodes_calc, 10, 10);
		//		IGenerator generator = new GeneratorSimple(nodes_calc, 10, 10);

		generator.calc();
		generator.generate();

		for (Hardware hardware : nodes_calc) {
			System.out.println(hardware);
		}

		//		System.out.println("Program completed");

		//		File file = new File("./resource/test.txt");
		//
		//		try {
		//			generator.getAllocationMap().write(file);
		//		} catch (IOException ex) {
		//			ex.printStackTrace();
		//		}
		//		System.out.println("filepath : " + file.getPath());

		map = generator.getAllocationMap();
		map.interval_distance_ = node_interval;

		//if文
		//		ConvolutionEvaluator evaluator = new ConvolutionEvaluator(map, nodes_calc);
		AggregationEvaluator aggregation_evaluator = new AggregationEvaluator(map, nodes_calc);
		ConvolutionEvaluator convolution_evaluator = new ConvolutionEvaluator(map, nodes_calc);
		//		IEvaluator evaluator = new PoolingEvaluator(map, nodes_calc);

		aggregation_evaluator.setRandom(rand);
		convolution_evaluator.setRandom(rand);

		float[][] input = new float[10][10];
		for (int x = 0; x < input.length; x++) {
			for (int y = 0; y < input.length; y++) {
				input[x][y] = 1;
			}
		}
		float[][] filter = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
		//		double res_p = evaluator.evaluateTranslatedDataAmount();

		float[][] result_aggr = aggregation_evaluator.forward(input);
		float[][] result_conv_1 = convolution_evaluator.forward_B(result_aggr, filter);
		float[][] result_conv_2 = convolution_evaluator.forward_B(result_conv_1, filter);
		float[][] result_conv_3 = convolution_evaluator.forward_B(result_conv_2, filter);


		File output = new File(OUTPUT_FILE_PATH);
		System.out.println("filepath : " + output.getPath());
		FileWriter fw = new FileWriter(output, true);

		fw.write("割り当て方法 : ," + alloc_type + ",計算ノードの数 :," + nodes_calc.size() + ",ノード間距離 :," + node_interval + "\n");
		for (int x = 0; x < input.length; x++) {
			for (int y = 0; y < input.length; y++) {
				System.out.printf("%5.0f,", result_aggr[x][y]);
				fw.write(result_aggr[x][y] + ",");
			}
			System.out.println();
			fw.write("\n");
		}
		fw.write("割り当て方法 : ," + alloc_type + ",計算ノードの数 :," + nodes_calc.size() + ",ノード間距離 :," + node_interval + "\n");
		for (int x = 0; x < input.length; x++) {
			for (int y = 0; y < input.length; y++) {
				System.out.printf("%5.0f,", result_conv_1[x][y]);
				fw.write(result_conv_1[x][y] + ",");
			}
			System.out.println();
			fw.write("\n");
		}
		fw.write("割り当て方法 : ," + alloc_type + ",計算ノードの数 :," + nodes_calc.size() + ",ノード間距離 :," + node_interval + "\n");
		for (int x = 0; x < input.length; x++) {
			for (int y = 0; y < input.length; y++) {
				System.out.printf("%5.0f,", result_conv_2[x][y]);
				fw.write(result_conv_2[x][y] + ",");
			}
			System.out.println();
			fw.write("\n");
		}
		fw.write("割り当て方法 : ," + alloc_type + ",計算ノードの数 :," + nodes_calc.size() + ",ノード間距離 :," + node_interval + "\n");
		for (int x = 0; x < input.length; x++) {
			for (int y = 0; y < input.length; y++) {
				System.out.printf("%5.0f,", result_conv_3[x][y]);
				fw.write(result_conv_3[x][y] + ",");
			}
			System.out.println();
			fw.write("\n");
		}

		fw.close();
	}

	public static void main(String[] args) throws Exception {

		//Initialize output file.
		File output = new File(OUTPUT_FILE_PATH);
		System.out.println("filepath : " + output.getPath());
		FileWriter fw = new FileWriter(output);
		fw.close();

		System.out.println("start");
		execute(1, 1, 6, 6);
		return;
	}

}
