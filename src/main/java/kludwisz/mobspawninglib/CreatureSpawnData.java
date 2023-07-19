package kludwisz.mobspawninglib;

public class CreatureSpawnData {
	public CreatureType type;
	public int weight;
	public int minCount;
	public int maxCount;
	
	public CreatureSpawnData(CreatureType type, int weight, int minCount, int maxCount) {
		this.type = type;
		this.weight = weight;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}
}
