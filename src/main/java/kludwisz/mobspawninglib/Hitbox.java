package kludwisz.mobspawninglib;

public class Hitbox {
	public Position posMin;
	public Position posMax;
	
	public Hitbox(Position posMin, Position posMax) {
		this.posMin = posMin;
		this.posMax = posMax;
	}
	
	public static Hitbox of(CreatureType type, Position pos) {
		return new Hitbox(
			new Position(pos.x - type.width/2.0D, pos.y, pos.z - type.width/2.0D),
			new Position(pos.x + type.width/2.0D, pos.y + type.height, pos.z + type.width/2.0D)
			);
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
