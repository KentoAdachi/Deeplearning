package distributionGenerator;

import java.util.ArrayList;

public class GeneratorSimple extends DistributionGenerator implements IGenerator {

	//	int max_units_;
	//	AllocationMap allocationMap_;
	//	DelayMap delayMap_;
	//	ArrayList<Hardware> nodes_;

	public GeneratorSimple(ArrayList<Hardware> nodes, int w, int h) {
		nodes_ = nodes;
		max_units_ = w * h;
		allocationMap_ = new AllocationMap(w, h);

	}

	//num_units_をこちら側で管理したほうがいいのではないか
	public void generate() {
		//割り当て結果の入ったリストを生成
		ArrayList<Integer> omikuziList = new ArrayList<Integer>();

		for (int i = 0; i < nodes_.size(); i++) {
			for (int j = 0; j < num_units_.get(i); j++) {
				omikuziList.add(i + 1);

			}
		}
		//取り出しながら端から埋める
		for (int x = 0; x < allocationMap_.w_; x++) {
			for (int y = 0; y < allocationMap_.h_; y++) {
				allocationMap_.set(x, y, omikuziList.remove(0));
			}
		}

		System.out.println(allocationMap_);
	}

}
