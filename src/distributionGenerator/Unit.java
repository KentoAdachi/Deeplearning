package distributionGenerator;

public class Unit {
	public int x_,y_,parent_;

	public Unit(int x,int y,int parent) {
		x_ = x;
		y_ = y;
		parent_ = parent;

	}

	@Override
	public String toString() {
		return "x : "+x_+" y : "+y_+" parent : " + parent_;
	}

}
