package distributionGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class AllocationMap {

	public int w_;
	public int h_;
	int[][] map_;

	public double interval_distance_; //ノード間の距離
	Random rand_;
	//double prob(){} //到達率
	//boolean isReachable(){} //到達判定

	public AllocationMap(int w, int h) {
		// TODO 自動生成されたコンストラクター・スタブ
		w_ = w;
		h_ = h;
		map_ = new int[w][h];

		interval_distance_ = 10d;

	}

	//ノードの座標から割り当てる
	public AllocationMap(int w, int h, ArrayList<Hardware> calc_nodes) {

		this(w, h);

		for (int i = 0; i < calc_nodes.size(); i++) {
			Hardware node = calc_nodes.get(i);
			this.set(node.x_, node.y_, i + 1);
		}

	}

	//	一度の読み取りで静的配列を確保するためにかなり回りくどい方法を取っている
	public AllocationMap(File source) throws IOException {
		FileReader fr = new FileReader(source);
		BufferedReader br = new BufferedReader(fr);

		String line;

		//		ArrayList<String>data_list;
		ArrayList<ArrayList<Integer>> buf_lines = new ArrayList<>();

		int x = 0;
		while ((line = br.readLine()) != null) {
			String[] datas = line.split(",");
			h_ = datas.length;
			buf_lines.add(new ArrayList<>());
			for (int y = 0; y < datas.length; y++) {
				//				set(x, y, Integer.parseInt(datas[y]));
				buf_lines.get(x).add(Integer.parseInt(datas[y]));
			}
			x++;
		}

		w_ = x;

		map_ = new int[w_][h_];

		for (int i = 0; i < buf_lines.size(); i++) {
			for (int j = 0; j < buf_lines.get(0).size(); j++) {
				set(j, i, buf_lines.get(i).get(j));
			}
		}

		br.close();
	}

	public boolean isAllocable(int x, int y) {
		return (x >= 0 && x < w_ && y >= 0 && y < h_
				&& isUnassigned(x, y));
	}

	//未割当であるかを返す
	public boolean isUnassigned(int x, int y) {

		return map_[x][y] == 0;
	}

	public int get(int x, int y) {
		return map_[x][y];
	}

	public Unit getUnit(int x, int y) {
		return new Unit(x, y, get(x, y));
	}

	public void set(int x, int y, int i) {
		map_[x][y] = i;
	}

	//Random rand_を初期化しておく必要がある
	public void set(int x, int y, int i, boolean enable_loss) throws Exception {

		if (!enable_loss) {
			set(x, y, i);
			return;
		}
		//ランダムが存在するかチェック
		if (this.rand_ == null) {
			throw new Exception("先にrand_を初期化してください");
		}
		map_[x][y] = rand_.nextDouble() <= connectionProb() ? i : 0;
	}

	//確率は0~1スケール
	double connectionProb() {
		return 1;//実装予定
	}

	@Override
	public String toString() {

		for (int y = 0; y < h_; y++) {
			for (int x = 0; x < w_; x++) {
				System.out.print(get(x, y) + " ");
			}
			System.out.println("");
		}

		return "";
	}

	public void write(File file) throws IOException {
		FileWriter fw = new FileWriter(file);

		for (int y = 0; y < h_; y++) {
			for (int x = 0; x < w_; x++) {
				if (x == h_ - 1) {
					fw.write(get(x, y) + "\n");
				} else {
					fw.write(get(x, y) + ",");
				}
			}
		}

		fw.close();
		System.out.println("file expported to " + file.getAbsolutePath());
	}

	public void setRandom(Random rand) {
		this.rand_ = rand;
	}

	public double distance(Unit s, Unit d) {

		//		ユークリッド距離を求める
		double x = interval_distance_ * (d.x_ - s.x_);
		double y = interval_distance_ * (d.y_ - s.y_);
		return Math.sqrt(x * x + y * y);

	}

}
