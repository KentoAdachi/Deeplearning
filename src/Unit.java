
public class Unit {

	/* 深層学習のユニット
	 * 入力と計算と出力を持つ
	 *
	 * */


	double output_;

	public Unit() {




	}


	/* 送受信をエミュレートする．
	 * 実態はディレイとゲッター
	 *
	 * */
	public double sendOutput() {

		//Util.delay(適当);

		return getOutput();
	}

	/*
	 * getter
	 *
	 * */
	private double getOutput() {
		return output_;
	}

}
