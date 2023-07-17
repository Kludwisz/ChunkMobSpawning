package kludwisz.mobspawninglib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.seedfinding.mcbiome.biome.Biome;

public class BiomeCreatures {
	public static List<CreatureData> getCreaturesForBiome(Biome biome) {
		Preset p = BIOME_CREATURES.getOrDefault(biome.getName(), null);
		if (p != null)
			return p.creatures;
		return List.of();
	}
	
	private static final HashMap<String, Preset> BIOME_CREATURES = new HashMap<>();
	static {
		BIOME_CREATURES.put("plains", Preset.PLAINS);
		BIOME_CREATURES.put("desert", Preset.DESERT);
		BIOME_CREATURES.put("mountains", Preset.MOUNTAINS);
		BIOME_CREATURES.put("forest", Preset.FOREST);
		BIOME_CREATURES.put("taiga", Preset.TAIGA);
		BIOME_CREATURES.put("swamp", Preset.STANDARD);
		BIOME_CREATURES.put("nether_wastes", Preset.NETHER);
		BIOME_CREATURES.put("frozen_ocean", Preset.FROZENOCEAN);
		BIOME_CREATURES.put("snowy_tundra", Preset.FROZENBIOME);
		BIOME_CREATURES.put("snowy_mountains", Preset.PLAINS);
		BIOME_CREATURES.put("mushroom_fields", Preset.MUSHROOM);
		BIOME_CREATURES.put("mushroom_field_shore", Preset.MUSHROOM);
		BIOME_CREATURES.put("desert_hills", Preset.DESERT);
		BIOME_CREATURES.put("wooded_hills", Preset.FOREST);
		BIOME_CREATURES.put("taiga_hills", Preset.TAIGA);
		BIOME_CREATURES.put("mountain_edge", Preset.MOUNTAINS);
		BIOME_CREATURES.put("jungle", Preset.JUNGLE);
		BIOME_CREATURES.put("jungle_hills", Preset.JUNGLEHILLS);
		BIOME_CREATURES.put("jungle_edge", Preset.JUNGLEEDGE);
		BIOME_CREATURES.put("birch_forest", Preset.STANDARD);
		BIOME_CREATURES.put("birch_forest_hills", Preset.STANDARD);
		BIOME_CREATURES.put("dark_forest", Preset.STANDARD);
		BIOME_CREATURES.put("snowy_taiga", Preset.TAIGA);
		BIOME_CREATURES.put("snowy_taiga_hills", Preset.TAIGA);
		BIOME_CREATURES.put("giant_tree_taiga", Preset.TAIGA);
		BIOME_CREATURES.put("giant_tree_taiga_hills", Preset.TAIGA);
		BIOME_CREATURES.put("wooded_mountains", Preset.TAIGA);
		BIOME_CREATURES.put("savanna", Preset.SAVANNA);
		BIOME_CREATURES.put("savanna_plateau", Preset.SAVANNAPLATEAU);
		BIOME_CREATURES.put("sunflower_plains", Preset.PLAINS);
		BIOME_CREATURES.put("desert_lakes", Preset.DESERT);
		BIOME_CREATURES.put("gravelly_mountains", Preset.MOUNTAINS);
		BIOME_CREATURES.put("flower_forest", Preset.FLOWERFOREST);
		BIOME_CREATURES.put("taiga_mountains", Preset.TAIGA);
		BIOME_CREATURES.put("swamp_hills", Preset.STANDARD);
		BIOME_CREATURES.put("ice_spikes", Preset.FROZENBIOME);
		BIOME_CREATURES.put("modified_jungle", Preset.JUNGLEMOD);
		BIOME_CREATURES.put("modified_jungle_edge", Preset.JUNGLEEDGE);
		BIOME_CREATURES.put("tall_birch_forest", Preset.STANDARD);
		BIOME_CREATURES.put("tall_birch_hills", Preset.STANDARD);
		BIOME_CREATURES.put("dark_forest_hills", Preset.STANDARD);
		BIOME_CREATURES.put("snowy_taiga_mountains", Preset.TAIGA);
		BIOME_CREATURES.put("giant_spruce_taiga", Preset.TAIGA);
		BIOME_CREATURES.put("giant_spruce_taiga_hills", Preset.TAIGA);
		BIOME_CREATURES.put("modified_gravelly_mountains", Preset.MOUNTAINS);
		BIOME_CREATURES.put("shattered_savanna", Preset.SAVANNA);
		BIOME_CREATURES.put("shattered_savanna_plateau", Preset.SAVANNA);
		BIOME_CREATURES.put("bamboo_jungle", Preset.BAMBOOJUNGLE);
		BIOME_CREATURES.put("bamboo_jungle_hills", Preset.BAMBOOJUNGLEHILLS);
		BIOME_CREATURES.put("soul_sand_valley", Preset.NETHER);
		BIOME_CREATURES.put("crimson_forest", Preset.NETHER);
		BIOME_CREATURES.put("warped_forest", Preset.NETHER);
		BIOME_CREATURES.put("basalt_deltas", Preset.NETHER);
	}
	
	private static enum Preset {
		STANDARD(List.of(
				new CreatureData(CreatureType.SHEEP, 12, 4, 4),
				new CreatureData(CreatureType.PIG, 10, 4, 4),
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4),
				new CreatureData(CreatureType.COW, 8, 4, 4)
				)),
		FOREST(STANDARD, List.of(
				new CreatureData(CreatureType.WOLF, 5, 4, 4)
				)),
		FLOWERFOREST(STANDARD, List.of(
				new CreatureData(CreatureType.RABBIT, 4, 2, 3)
				)),
		PLAINS(STANDARD, List.of(
				new CreatureData(CreatureType.HORSE, 5, 2, 6),
				new CreatureData(CreatureType.DONKEY, 1, 1, 3)
				)),
		SAVANNA(STANDARD, List.of(
				new CreatureData(CreatureType.HORSE, 1, 2, 6),
				new CreatureData(CreatureType.DONKEY, 1, 1, 1)
				)),
		SAVANNAPLATEAU(SAVANNA, List.of(
				new CreatureData(CreatureType.LLAMA, 8, 4, 4)
				)),
		MOUNTAINS(STANDARD, List.of(
				new CreatureData(CreatureType.LLAMA, 5, 4, 6)
				)),
		TAIGA(STANDARD, List.of(
				new CreatureData(CreatureType.WOLF, 8, 4, 4),
				new CreatureData(CreatureType.RABBIT, 4, 2, 3),
				new CreatureData(CreatureType.FOX, 8, 2, 4)
				)),
		JUNGLE(STANDARD, List.of(
				new CreatureData(CreatureType.PARROT, 40, 1, 2),
				new CreatureData(CreatureType.PANDA, 1, 1, 2),
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4)
				)),
		JUNGLEEDGE(STANDARD, List.of(
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4)
				)),
		JUNGLEHILLS(STANDARD, List.of(
				new CreatureData(CreatureType.PARROT, 10, 1, 1),
				new CreatureData(CreatureType.PANDA, 1, 1, 2),
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4)
				)),
		JUNGLEMOD(STANDARD, List.of(
				new CreatureData(CreatureType.PARROT, 10, 1, 1),
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4)
				)),
		BAMBOOJUNGLE(STANDARD, List.of(
				new CreatureData(CreatureType.PARROT, 40, 1, 2),
				new CreatureData(CreatureType.PANDA, 80, 1, 2),
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4)
				)),
		BAMBOOJUNGLEHILLS(STANDARD, List.of(
				new CreatureData(CreatureType.PARROT, 10, 1, 1),
				new CreatureData(CreatureType.PANDA, 80, 1, 2),
				new CreatureData(CreatureType.CHICKEN, 10, 4, 4)
				)),
		FROZENBIOME(List.of(
				new CreatureData(CreatureType.RABBIT, 10, 2, 3),
				new CreatureData(CreatureType.POLAR_BEAR, 1, 1, 2)
				)),
		FROZENOCEAN( new CreatureData(CreatureType.POLAR_BEAR, 1, 1, 2) ),
		DESERT( new CreatureData(CreatureType.RABBIT, 4, 2, 3) ),
		BEACH( new CreatureData(CreatureType.TURTLE, 5, 2, 5) ),
		MUSHROOM( new CreatureData(CreatureType.MOOSHROOM, 8, 4, 8) ),
		NETHER( new CreatureData(CreatureType.STRIDER, 60, 1, 2) );
		
		
		private List<CreatureData> creatures;
		
		Preset(CreatureData D){
			this.creatures = List.of(D);
		}
		
		Preset(List<CreatureData> L) {
			this.creatures = L;
		}
		
		Preset(Preset a, List<CreatureData> L) {
			this.creatures = new ArrayList<>(a.creatures);
			this.creatures.addAll(L);
		}
	}
}
