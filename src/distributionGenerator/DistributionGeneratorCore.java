package distributionGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DistributionGeneratorCore {

	public static void main(String[] args) {

//		DelayMap map = new DelayMap("./resource/delaymap.txt", 3);
//		System.out.println(map);

		ArrayList<Hardware> nodes = new ArrayList<Hardware>();

		nodes.add(new Hardware(0,0,100, "a"));
		nodes.add(new Hardware(9,0,200, "b"));
		nodes.add(new Hardware(9,9,300, "c"));
//		nodes.add(new Hardware(3,7,300, "d"));
//		nodes.add(new Hardware(400, "d"));

		//初期割り当て
//		AllocationMap alloc_init = new AllocationMap(28, 28);
//		alloc_init.set(0, 18, 1);
//		alloc_init.set(18, 0, 2);
//		alloc_init.set(10, 10, 3);
//		alloc_init.set(27, 27, 4);

		Random rand = new Random();
		AllocationMap alloc_init = new AllocationMap(10, 10,nodes,10);
		alloc_init.setRandom(rand);
//		alloc_init.set(0, 0, 1);
//		alloc_init.set(9, 0, 2);
//		alloc_init.set(9, 9, 3);
//		alloc_init.set(0, 9, 4);

		System.out.println(alloc_init);

//		IGenerator generator = new GeneratorSimple(nodes, 28, 28);
		IGenerator generator = new GeneratorLocative(nodes, alloc_init);
//		IGenerator generator = new GeneratorRandom(nodes, 28, 28);

		generator.calc();
		generator.generate();

		for (Hardware hardware : nodes) {
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
		return;
	}
}
