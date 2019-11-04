package distributionGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DistributionGeneratorCore {

	public static void main(String[] args) {

		DelayMap map = new DelayMap("./resource/delaymap.txt", 3);
		System.out.println(map);

		ArrayList<Hardware> nodes = new ArrayList<Hardware>();

		nodes.add(new Hardware(100, "a"));
		nodes.add(new Hardware(200, "b"));
		nodes.add(new Hardware(300, "c"));
		nodes.add(new Hardware(400, "d"));

		IGenerator generator = new GeneratorSimple(nodes, 28, 28);

		generator.calc();
		//		generator.generate();
		generator.generate();

		for (Hardware hardware : nodes) {
			System.out.println(hardware);
		}

		System.out.println("Program succeed");

		File file = new File("./resource/test.txt");

		try {
			FileWriter fw = new FileWriter(file);
			fw.write("テスト");

			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("filepath : " + file.getPath());
		return;
	}
}
