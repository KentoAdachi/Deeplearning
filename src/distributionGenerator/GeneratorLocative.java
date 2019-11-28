package distributionGenerator;

import java.util.ArrayList;

public class GeneratorLocative extends DistributionGenerator implements IGenerator {

	//	double total_performance_;//正しく反映する
	ArrayList<Double> performance_percentages_;

	public GeneratorLocative(ArrayList<Hardware> nodes, AllocationMap map_init) {
		nodes_ = nodes;
		allocationMap_ = map_init;

		//		max_units_ = allocationMap_.h_*allocationMap_.w_;
	}

	//能力から割り当て数を決定
	//能力から割り当て比率を決定
	@Override
	public void calc() {
		double sum = 0;

		for (Hardware hardware : nodes_) {
			sum += hardware.performance_;
		}

		performance_percentages_ = new ArrayList<>();
		for (int i = 0; i < nodes_.size(); i++) {
			performance_percentages_.add(nodes_.get(i).performance_ / sum);
		}

	}

	@Override
	public void generate() {
		// TODO 自動生成されたメソッド・スタブ
		//		generate4Adj();
		generate8Adj();
	}

	public void generate4Adj() {

		ArrayList<ArrayList<Unit>> unitListList = new ArrayList<ArrayList<Unit>>();
		ArrayList<Double> action_values = new ArrayList<>();

		for (int i = 0; i < nodes_.size(); i++) {
			ArrayList<Unit> unitList = new ArrayList<Unit>();
			unitListList.add(unitList);
			action_values.add(0d);

		}

		for (int x = 0; x < allocationMap_.w_; x++) {
			for (int y = 0; y < allocationMap_.h_; y++) {
				if (allocationMap_.get(x, y) != 0) {
					unitListList.get(allocationMap_.get(x, y) - 1).add(new Unit(x, y, allocationMap_.get(x, y)));
				}
			}
		}

		while (true) {
			boolean next = false;
			for (ArrayList<Unit> arrayList : unitListList) {
				if (!arrayList.isEmpty()) {
					next = true;
				}
			}

			if (!next)
				break;

			//割り当て比率に基づき割り当ての比率を変更する
			for (int i = 0; i < nodes_.size(); i++) {
				if (unitListList.get(i).isEmpty()) {
					continue;
				}

				//割り当て比率が閾値を超えている場合のみ実行
				final double bias = 1d;
				//				System.out.println("action vaule " + i + " : " + action_values.get(i));
				if (action_values.get(i) < bias) {
					action_values.set(i, action_values.get(i) + performance_percentages_.get(i));
					continue;
				} else {
					action_values.set(i, action_values.get(i) - 1);
				}

				//				for (Unit u : unitList) {
				//					System.out.println(u);
				//				}
				//				System.out.println(allocationMap_);
				//				System.out.println("break");

				ArrayList<Unit> unitList = unitListList.get(i);
				Unit unit = unitList.remove(0);
				int parent = unit.parent_;
				int x = unit.x_;
				int y = unit.y_ - 1;
				//			上

				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					//					unitList.add(unit);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//			左
				x = unit.x_ - 1;
				y = unit.y_;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//			右
				x = unit.x_ + 1;
				y = unit.y_;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
					//				continue;

				}
				//			下
				x = unit.x_;
				y = unit.y_ + 1;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
			}
		}
		System.out.println(allocationMap_);
	}

	public void generate8Adj() {

		ArrayList<ArrayList<Unit>> unitListList = new ArrayList<ArrayList<Unit>>();
		ArrayList<Double> action_values = new ArrayList<>();

		for (int i = 0; i < nodes_.size(); i++) {
			ArrayList<Unit> unitList = new ArrayList<Unit>();
			unitListList.add(unitList);
			action_values.add(0d);

		}

		for (int x = 0; x < allocationMap_.w_; x++) {
			for (int y = 0; y < allocationMap_.h_; y++) {
				if (allocationMap_.get(x, y) != 0) {
					unitListList.get(allocationMap_.get(x, y) - 1).add(new Unit(x, y, allocationMap_.get(x, y)));
				}
			}
		}

		while (true) {
			boolean next = false;
			for (ArrayList<Unit> arrayList : unitListList) {
				if (!arrayList.isEmpty()) {
					next = true;
				}
			}

			if (!next)
				break;

			//割り当て比率に基づき割り当ての比率を変更する
			for (int i = 0; i < nodes_.size(); i++) {
				if (unitListList.get(i).isEmpty()) {
					continue;
				}

				//割り当て比率が閾値を超えている場合のみ実行
				final double bias = 1d;
				//				System.out.println("action vaule " + i + " : " + action_values.get(i));
				if (action_values.get(i) < bias) {
					action_values.set(i, action_values.get(i) + performance_percentages_.get(i));
					continue;
				} else {
					action_values.set(i, action_values.get(i) - 1);
				}

				//				for (Unit u : unitList) {
				//					System.out.println(u);
				//				}
				//				System.out.println(allocationMap_);
				//				System.out.println("break");

				ArrayList<Unit> unitList = unitListList.get(i);
				Unit unit = unitList.remove(0);
				int parent = unit.parent_;
				int x = unit.x_;
				int y = unit.y_ - 1;
				//				8近傍で作成する場合
				//			上

				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					//					unitList.add(unit);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//			左
				x = unit.x_ - 1;
				y = unit.y_;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//			右
				x = unit.x_ + 1;
				y = unit.y_;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
					//				continue;

				}
				//			下
				x = unit.x_;
				y = unit.y_ + 1;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}

				//左上
				x = unit.x_ - 1;
				y = unit.y_ - 1;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//右上
				x = unit.x_ + 1;
				y = unit.y_ - 1;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//左下
				x = unit.x_ - 1;
				y = unit.y_ + 1;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					unitList.add(0, unit);
					unitList.add(new Unit(x, y, parent));
					continue;
				}
				//右下
				x = unit.x_ + 1;
				y = unit.y_ + 1;
				if (allocationMap_.isAllocable(x, y)) {
					allocationMap_.set(x, y, parent);
					//					unitList.add(unit);
					unitList.add(new Unit(x, y, parent));
					//				unitList.add(unit);
					continue;
				}

			}
		}
		System.out.println(allocationMap_);

	}

	//	@Override
	//	public AllocationMap getAllocationMap() {
	//		// TODO 自動生成されたメソッド・スタブ
	//		return allocationMap_;
	//	}

}
