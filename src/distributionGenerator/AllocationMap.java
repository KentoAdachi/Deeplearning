package distributionGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AllocationMap {

	int w_;
	int h_;
	int[][] map_;

	public AllocationMap(int w, int h) {
		// TODO 自動生成されたコンストラクター・スタブ
		w_ = w;
		h_ = h;
		map_ = new int[w][h];
	}

	//未割当であるかを返す
	public boolean isUnassigned(int x, int y) {

		return map_[x][y] == 0;
	}

	public int get(int x, int y) {
		return map_[x][y];
	}

	public void set(int x, int y, int i) {
		map_[x][y] = i;
	}

	@Override
	public String toString() {
		for (int[] is : map_) {
			for (int i : is) {
				System.out.print(i + " ");
			}
			System.out.println("");
		}

		return "";
	}


	public void write(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		for (int[] is : map_) {
//			for (int i : is) {
//				fw.write(i + ",");
//			}
			for (int i = 0; i < is.length; i++) {
				int j = is[i];
				if(i==0) {
					fw.write(j+"");
				}else {
					fw.write(","+j);
				}
			}

			fw.write("\n");

		}
		fw.close();
		System.out.println("file expported to "+ file.getAbsolutePath());
		}

}
