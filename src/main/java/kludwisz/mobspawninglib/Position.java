package kludwisz.mobspawninglib;

public class Position {
	double x;
	double y;
	double z;
	
	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "{" + (int)this.x + ", " + (int)this.y + ", " + (int)this.z + "}";
	}
}
