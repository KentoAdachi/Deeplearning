package distributionEvaluator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import distributionGenerator.AllocationMap;
import distributionGenerator.GeneratorLocative;
import distributionGenerator.Hardware;
import distributionGenerator.IGenerator;

public class DistributionEvaluatorScene1 {

	static long SEED = 0;
	static Random rand = new Random(SEED);

	static void execute(int batch, int step, int from, int to) throws IOException {

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
				scene(num_nodes,nodes);
			}
		}
	}

	static void scene(int num_nodes,ArrayList<Hardware> nodes) throws IOException {

		ArrayList<Hardware> nodes_calc = new ArrayList<Hardware>(nodes.subList(0, num_nodes));

		AllocationMap map = new AllocationMap(10, 10, nodes_calc,10);

		System.out.println(map);

		IGenerator generator = new GeneratorLocative(nodes_calc,map);
//		IGenerator generator = new GeneratorRandom(nodes_calc, 10, 10);

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

		map = generator.getAllocationMap();

		ConvolutionEvaluator evaluator = new ConvolutionEvaluator(map, nodes_calc);

		double res_p = evaluator.evaluatePerformanceBallance();

		File output = new File("./resource/output.txt");
		System.out.println("filepath : " + output.getPath());
		FileWriter fw = new FileWriter(output, true);

		fw.write("計算ノードの数 :," + nodes_calc.size() + ",評価 : ," + res_p + "\n");

		fw.close();
	}

	public static void main(String[] args) throws IOException {

		//Initialize output file.
		File output = new File("./resource/output.txt");
		System.out.println("filepath : " + output.getPath());
		FileWriter fw = new FileWriter(output);
		fw.close();

		System.out.println("start");
		execute(100, 1, 1, 100);
		return;
	}

}
