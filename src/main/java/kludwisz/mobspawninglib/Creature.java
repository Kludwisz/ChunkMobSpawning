package kludwisz.mobspawninglib;

public class Creature {
	public CreatureType type;
	public Position position;
	public Hitbox hitbox;
	public double yaw;
	public double pitch;
	
	public Creature(CreatureType type, double x, double y, double z, double yaw, double pitch) {
		this.type = type;
		this.position = new Position(x,y,z);
		this.yaw = yaw;
		this.pitch = pitch;
		this.hitbox = new Hitbox(new Position(this.position.x - this.type.width/2, this.position.y, this.position.z - this.type.width/2),
				 				 new Position(this.position.x + this.type.width/2, this.position.y + this.type.height, this.position.z + this.type.width/2));
	}
	
	public Creature(CreatureType type, Position pos, double yaw, double pitch) {
		this.type = type;
		this.position = pos;
		this.yaw = yaw;
		this.pitch = pitch;
		this.hitbox = new Hitbox(new Position(this.position.x - this.type.width/2, this.position.y, this.position.z - this.type.width/2),
				 new Position(this.position.x + this.type.width/2, this.position.y + this.type.height, this.position.z + this.type.width/2));
	}
	
	public Creature(CreatureType type, double x, double y, double z) {
		this.type = type;
		this.position = new Position(x,y,z);
		this.hitbox = new Hitbox(new Position(this.position.x - this.type.width/2, this.position.y, this.position.z - this.type.width/2),
				 new Position(this.position.x + this.type.width/2, this.position.y + this.type.height, this.position.z + this.type.width/2));
	}
}
