package kludwisz.mobspawninglib;

public class Hitbox {
	public Position posMin;
	public Position posMax;
	
	public Hitbox(Position posMin, Position posMax) {
		this.posMin = posMin;
		this.posMax = posMax;
	}
	
	public boolean collidesWithXZ(Hitbox other) {
		if (   this.posMax.x >= other.posMin.x && this.posMin.x <= other.posMax.x 
			&& this.posMax.z >= other.posMin.z && this.posMin.z <= other.posMax.z) {
			return true;
		}
		return false;
	}
	
	public boolean collidesWithXYZ(Hitbox other) {
		if (	this.posMax.x >= other.posMin.x && this.posMin.x <= other.posMax.x 
			&&  this.posMax.y >= other.posMin.y && this.posMin.y <= other.posMax.y
			&&  this.posMax.z >= other.posMin.z && this.posMin.z <= other.posMax.z) {
			return true;
		}	
		return false;
	}
}
