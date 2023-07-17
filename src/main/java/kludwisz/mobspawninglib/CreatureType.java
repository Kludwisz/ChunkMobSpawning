package kludwisz.mobspawninglib;

public enum CreatureType {
	SHEEP(0.9F, 1.3F),
	PIG(0.9F, 0.9F),
	CHICKEN(0.4F, 0.7F),
	COW(0.9F, 1.4F),
	WOLF(0.6F, 0.85F),
	HORSE(0.9F, 1.6F),
	DONKEY(1.3964F, 1.5F),
	MOOSHROOM(0.9F, 1.4F),
	LLAMA(0.9F, 1.875F),
	PARROT(0.5F, 0.9F),
	STRIDER(0.9F, 1.7F),
	PANDA(1.3F, 1.25F),
	TURTLE(1.1F, 0.4F),
	RABBIT(0.4F, 0.5F),
	FOX(0.6F, 0.7F),
	POLAR_BEAR(1.3F, 1.4F);
	
	float width;
	float height;
	CreatureType(float width, float height) {
		this.width = width;
		this.height = height;
	}
}
