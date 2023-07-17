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
}
