package distributionGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DelayMap {

	double[][] map_;
	ArrayList<Hardware> nodes_;

	public DelayMap(int size) {
		// TODO 自動生成されたコンストラクター・スタブ
		map_ = new double[size][size];
	}

	public DelayMap(double[][] map) {
		// TODO 自動生成されたコンストラクター・スタブ
		map_ = map;
	}

	public double get(int s, int d) {
		return map_[s][d];
	}

	public void set(int x, int y, int i) {
		map_[x][y] = i;
	}

	public DelayMap(String path, int size) {
		// TODO 自動生成されたコンストラクター・スタブ
		map_ = new double[size][size];
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String s;

			// ファイルを行単位で読む
			int i = 0;
			while ((s = br.readLine()) != null) {
				// カンマで分割したString配列を得る
				String array[] = s.split(",");
				// データ数をチェックしたあと代入、プリントする
				int j = 0;
				for (String string : array) {
					map_[i][j++] = Double.parseDouble(string);
				}
				i++;

				// 内容を出力する

			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@Override
	public String toString() {
		for (double[] ds : map_) {
			for (double d : ds) {
				System.out.print(d + " ");
			}
			System.out.println("");
		}

		return "";
	}

}
