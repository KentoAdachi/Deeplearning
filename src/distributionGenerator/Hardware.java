package distributionGenerator;


/*
 * ハードウェアノードのエミュレート
 *
 * */
public class Hardware {

	//	double bitrate_;
	public double performance_;

//	int num_units_;
//	int num_units_left_;
	// int 緯度
	// int 経度

	//x座標
	public int x_ =-1;
	//y座標
	public int y_ =-1;


	String name_;

	public Hardware(double performance, String name) {
		// TODO 自動生成されたコンストラクター・スタブ

		performance_ = performance;
//		num_units_left_ = 0;
		name_ = name;

	}

	public Hardware(int x,int y,double performance, String name) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.x_= x;
		this.y_ = y;
		this.performance_ = performance;
		this.name_ = name;
	}

	/*	相手のノードを指定して通信．
	 * 座標を元に距離を算出，距離とパケットサイズを元に，成功確率が決定
	 * public 通信 send(Hardware dist);
	 * */



	@Override
	public String toString() {
//		return "name : " + name_ + " performance : " + performance_ + " num_units_ : " + num_units_
//				+ " num_units_left_ : " + num_units_left_;
		return "name : " + name_ + " performance : " + performance_ ;
	}

}
