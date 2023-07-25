package kludwisz.mobspawninglib;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import com.seedfinding.mcbiome.source.BiomeSource;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcmath.util.Mth;
import com.seedfinding.mcseed.rand.JRand;
import com.seedfinding.mcterrain.TerrainGenerator;

public class Main {
	public static final MCVersion VERSION = MCVersion.v1_16_1;
	public static final Dimension NETHER = Dimension.NETHER;
	public static final Biome PLAINS = Biomes.PLAINS;
	
	public static void main(String[] args) throws IOException {
		//debugFunction(12345L);
		//entityOverlapProbabilityTest();
		Example2.run();
	}

	
	// useful for testing
	@SuppressWarnings("unused")
	private static void debugFunction(long worldseed) {
		ChunkMobSpawner spawner = new ChunkMobSpawner();
		ChunkRand rand = new ChunkRand();

		System.out.println(worldseed);
		for (int chunkX = -16; chunkX <= 16; chunkX++) for (int chunkZ = -16; chunkZ <= 16; chunkZ++) {
			rand.setPopulationSeed(worldseed & Mth.MASK_48, chunkX<<4, chunkZ<<4, MCVersion.v1_16_1);
			
			BiomeSource obs = BiomeSource.of(Dimension.OVERWORLD, VERSION, worldseed);
			Biome b = obs.getBiome((chunkX<<4)+8, 0, (chunkZ<<4)+8);
			List<Creature> creatureList = spawner.getMobsInChunk(b, chunkX, chunkZ, rand, TerrainGenerator.of(obs));
			
			if (creatureList.isEmpty())
				continue;
			
			System.out.println("Likely creatures in " + b.getName() + ": ");
			System.out.println("Chunk: " + chunkX + "," + chunkZ);
			for (Creature c : creatureList) {
				System.out.println(c.toString());
			}
		}
	}
	
	// according to this, around 0.022 of chunks should feature an entity overlap
	@SuppressWarnings("unused")
	private static float entityOverlapProbabilityTest() {
		ChunkMobSpawner spawner = new ChunkMobSpawner();
		ChunkRand rand = new ChunkRand();
		JRand seedGen = new JRand(new Random().nextLong());
		
		int total=10000000, success=0;
		
		for (int i=0; i<total; i++) {
			long worldseed = seedGen.nextLong();
			rand.setPopulationSeed(worldseed, 3, 7, VERSION);
			List<Creature> creatureList = spawner.getMobsInChunk(PLAINS, 3, 7, rand, null);
			
			int counter = 0;
			for (Creature first : creatureList) {
				for (Creature second : creatureList) {
					if (first.position.equals(second.position))
						counter++;
				}
			}
			if (counter > creatureList.size())
				success++;
		}
		
		float prob = (float)success / (float)total;
		System.out.println(prob);
		return prob;
	}
}
