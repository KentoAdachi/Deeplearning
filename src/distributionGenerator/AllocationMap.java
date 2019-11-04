package distributionGenerator;

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

}
