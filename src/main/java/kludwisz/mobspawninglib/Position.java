package kludwisz.mobspawninglib;

import com.seedfinding.mccore.util.pos.BPos;

public class Position {
	double x;
	double y;
	double z;
	
	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BPos toBlockPos() {
		return new BPos((int)this.x, (int)this.y, (int)this.z);
	}
	
	@Override
	public String toString() {
		return "{" + (int)this.x + ", " + (int)this.y + ", " + (int)this.z + "}";
	}
	
	private static final double E = 0.000001d;
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Position))
			return false;
		if (Math.abs(this.x - ((Position)other).x) > E || Math.abs(this.y - ((Position)other).y) > E || Math.abs(this.z - ((Position)other).z) > E)
			return false;
		return true;
	}
}
