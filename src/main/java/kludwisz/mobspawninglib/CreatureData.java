package kludwisz.mobspawninglib;

public class CreatureData {
	CreatureType type;
	int weight;
	int minCount;
	int maxCount;
	
	public CreatureData(CreatureType type, int weight, int minCount, int maxCount) {
		this.type = type;
		this.weight = weight;
		this.minCount = minCount;
		this.maxCount = maxCount;
	}
}
