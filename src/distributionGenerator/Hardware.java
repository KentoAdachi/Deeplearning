package distributionGenerator;

/*
 * ハードウェアノードのエミュレート
 *
 * */
public class Hardware {

	//	double bitrate_;
	double performance_;

	int num_units_;
	int num_units_left_;

	String name_;

	public Hardware(double performance, String name) {
		// TODO 自動生成されたコンストラクター・スタブ

		performance_ = performance;
		num_units_left_ = 0;
		name_ = name;

	}

	@Override
	public String toString() {
		return "name : " + name_ + " performance : " + performance_ + " num_units_ : " + num_units_
				+ " num_units_left_ : " + num_units_left_;
	}

}
