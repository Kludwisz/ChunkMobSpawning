package kludwisz.mobspawninglib;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.seedfinding.latticg.reversal.DynamicProgram;
import com.seedfinding.latticg.reversal.calltype.java.JavaCalls;
import com.seedfinding.latticg.util.LCG;
import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.biome.Biomes;
import com.seedfinding.mcbiome.source.BiomeSource;
import com.seedfinding.mccore.block.Block;
import com.seedfinding.mccore.block.Blocks;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcmath.util.Mth;
import com.seedfinding.mcreversal.ChunkRandomReverser;
import com.seedfinding.mcterrain.TerrainGenerator;

/* EXAMPLE OF USE:
 * Finding a seed with 20 naturally-spawning striders in 1 chunk
 *
 * RESULT:
 * 80261316340888 -> 20 striders
*/

public class Main {
	public static final MCVersion VERSION = MCVersion.v1_16_1;
	public static final Dimension NETHER = Dimension.NETHER;
	public static final Biome NETHERBIOME = Biomes.NETHER_WASTES;
	
	public static void main(String[] args) throws IOException {
		//runLattiCG();
		//findWorldSeeds();
		test(12345L);
	}
	
	/* solutions:
	251888924553709
	177057131736104
	220297901824372
	*/
	public static void runLattiCG() {
		DynamicProgram device = DynamicProgram.create(LCG.JAVA);
		
		// going for a pack of (potentially) 20 striders in 1 chunk
		for (int i=0; i<10; i++) {
			device.add(JavaCalls.nextFloat().lessThan(0.1F)); 	// does spawn
			device.skip(1);										// pointless nextInt(60)
			device.skip(1);
			//device.add(JavaCalls.nextInt(2).equalTo(1)); 		// ignoring pack size for optimization
			device.skip(2+5+5); 								// 2 x nextInt(16) -> position 
																// 1 x nextFloat() -> rotation, 4 x nextInt(5) -> position for next mob in pack
																// 1 x nextFloat(), 4 x nextInt(5) -> same as above
		}
		
		device.reverse().boxed().forEach(xoredPopulatioinSeed -> {
			final long populationSeed = xoredPopulatioinSeed ^ LCG.JAVA.multiplier;
			ChunkRand rand = new ChunkRand();
			rand.setSeed(populationSeed);
			
			ChunkMobSpawner spawner = new ChunkMobSpawner();
			List<Creature> creatureList = spawner.getMobsInChunk(NETHERBIOME, 0, 0, rand, null); // using a dummy chunkpos here
			if (creatureList.size() >= 20) {
				System.out.println(populationSeed);
			}
		});
	}
	
	public static final int R = 5;
	public static void findWorldSeeds() throws IOException{
		Scanner reader = new Scanner(new File("C:\\Users\\kludw\\eclipse-workspace\\mobspawninglib\\lib\\src\\main\\java\\mobspawninglib\\solutions.txt"));
		
		while (reader.hasNextLong()) {
			long populationSeed = reader.nextLong();
			
			for (int cx=-R; cx<=R; cx++) for (int cz=-R; cz<=R; cz++) {
				List<Long> structSeeds = ChunkRandomReverser.reversePopulationSeed(populationSeed, cx<<4, cz<<4, VERSION);
				
				for (long structseed : structSeeds) {
					structseed &= Mth.MASK_48;
					BiomeSource nbs = BiomeSource.of(NETHER, VERSION, structseed);
					TerrainGenerator ntg = TerrainGenerator.of(nbs);
					
					// checking for large, open lava area without any blocks in the way (still not ideal, cause high caves mess everything up
					boolean ok = true;
					for (int x=cx<<4; x < (cx<<4)+16; x+=4) for (int z=cz<<4; z < (cz<<4)+16; z+=4) {
						Block [] column = ntg.getColumnAt(x, z);
						
						if (column[30].getId() != Blocks.LAVA.getId()) {
							ok = false;
							break;
						}
						for (int y=33; y<90; y += 4) {
							if (column[y].getId() != Blocks.AIR.getId()) {
								ok = false;
								break;
							}
						}
						if (!ok)
							break;
					}
					
					if (ok) System.out.println(structseed + " /execute in minecraft:the_nether run tp @s " + cx*16 + " 50 " + cz*16);
				}
			}
		}
	}
	
	// useful for debugging
	@SuppressWarnings("unused")
	private static void test(long worldseed) {
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
	
	// according to this, around 0.04 of consecutive mob spawning attempts should feature an entity overlap
	// in reality, this number should actually be higher (due to other mobs in the pack colliding as well)
	@SuppressWarnings("unused")
	private static float entityOverlapProbabilityTest() {
		ChunkRand r = new ChunkRand();
		r.setSeed(new Random().nextLong());
		
		int total=10000000, success=0;
		int testX, testZ;
		for (int i=0; i<total; i++) {
			testX = r.nextInt(5) - r.nextInt(5);
			testZ = r.nextInt(5) - r.nextInt(5);
			if (testX == 0 && testZ == 0)
				success++;
		}
		
		float prob = (float)success / (float)total;
		System.out.println(prob);
		return prob;
	}
}
