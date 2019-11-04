package distributionGenerator;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorLocative implements IGenerator {

	int max_units_;
	AllocationMap allocationMap_;
//	DelayMap delayMap_;

	ArrayList<Hardware> nodes_;

	@Deprecated
	public GeneratorLocative(ArrayList<Hardware> nodes, int w, int h) {
		nodes_ = nodes;
		max_units_ = w * h;
		allocationMap_ = new AllocationMap(w, h);
//		delayMap_ = new DelayMap("./resource/delaymap.txt", nodes.size());

	}

	public GeneratorLocative(ArrayList<Hardware> nodes, AllocationMap map_init) {
		nodes_ = nodes;
		allocationMap_ = map_init;
	}


	//割り当てを決定
	//初期配置が割り当てられていることを保証して
	@Override
	public void generate() {


		//割り当てる順番を決定する
		//順番を管理する
		//隣接するノードを割り当てる


	}


	//割り当てを決定
	@Deprecated
	public void generate_old() {

		//今日やること，割り当てアルゴリズムを実装する
		//		ランダムな位置にノードを割り当てる
		int x, y;
		Random rand = new Random(0);
		x = rand.nextInt(allocationMap_.w_ - 1);
		y = rand.nextInt(allocationMap_.h_ - 1);
		System.out.println("init x : " + x + " init y : " + y);

		//		allocationMap_.set(x, y, 1);
		saiki(x, y);
		////		ユニットにノードを割り当てる
		//		map_.set(x, y, 1);
		//
		//
		//		int filterSize = 1;
		//
		//		int stride = 1;
		//
		////		隣接ユニットを取得する
		//		//初期値について再考する
		//		int min_w = 0;
		//		int min_h = 0;
		//		int max_w = map_.w_;
		//		int max_h = map_.h_;
		//
		//
		//		for (int i = x-filterSize;i <= x+filterSize;i+=stride) {
		//
		//			if(i >= min_w && i<= max_w) {
		//				System.out.println("test");
		//				for (int j = y-filterSize;j <= y+filterSize;j+=stride) {
		//					if(j >= min_h && j<= max_h) {
		//						if(map_.get(i, j) == 0) {
		//							map_.set(i, j, 2);
		//						}
		//					}
		//				}
		//			}
		//		}
		// フィルタサイズ と フィルタの移動量も考慮すればそれなりにいい感じになるのではないか
		//		遅延マップの低い中から選択
		System.out.println(allocationMap_);
	}

	void saiki(int x, int y) {
		//残っているノードを探す
		int l;
		//		double min = 99999;
		for (l = 0; l < nodes_.size(); l++) {
			if (nodes_.get(l).num_units_left_ > 0) {

				//				if( delayMap_.get(x, y)< min) {
				//					min = delayMap_.get(x, y);
				//				}

				nodes_.get(l).num_units_left_--;
				break;
			}
		}
		if (l == nodes_.size())
			return;

		//		System.out.println(l);

		//設定する
		allocationMap_.set(x, y, l + 1);

		int filterSize = 1;

		int stride = 1;

		//		隣接ユニットを取得する
		//初期値について再考する
		int min_w = 0;
		int min_h = 0;
		int max_w = allocationMap_.w_ - 1;
		int max_h = allocationMap_.h_ - 1;

		for (int i = x - filterSize; i <= x + filterSize; i += stride) {

			if (i >= min_w && i <= max_w) {
				for (int j = y - filterSize; j <= y + filterSize; j += stride) {
					if (j >= min_h && j <= max_h) {
						if (allocationMap_.get(i, j) == 0) {
							saiki(i, j);
						}
					}
				}
			}
		}

	}

	//能力から割り当て数を決定
	public void calc() {

		double sum = 0;
		for (Hardware hardware : nodes_) {
			sum += hardware.performance_;

		}

		int tmp = 0;
		//		for (Hardware hardware : nodes_) {
		for (int i = 0; i < nodes_.size() - 1; i++) {
			nodes_.get(i).num_units_left_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));
			nodes_.get(i).num_units_ = Math.round((float) (max_units_ * nodes_.get(i).performance_ / sum));

			tmp += nodes_.get(i).num_units_left_;
		}

		nodes_.get(nodes_.size() - 1).num_units_left_ = max_units_ - tmp;
		nodes_.get(nodes_.size() - 1).num_units_ = max_units_ - tmp;

		//checker
		tmp = 0;
		for (Hardware hardware : nodes_) {
			tmp += hardware.num_units_left_;
		}
		System.out.println("total units : " + tmp);

	}



}
