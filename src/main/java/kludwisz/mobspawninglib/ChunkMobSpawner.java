package kludwisz.mobspawninglib;

import java.util.ArrayList;
import java.util.List;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcmath.util.Mth;
import com.seedfinding.mcterrain.TerrainGenerator;

public class ChunkMobSpawner {
	
	public List<Creature> getMobsInChunk(Biome biome, CPos chunkPos, ChunkRand rand) {
		return getMobsInChunk(biome, chunkPos.getX(), chunkPos.getZ(), rand, null);
	}
	
	// note: the algorithm requires the rand object to be seeded with the population seed
	public List<Creature> getMobsInChunk(Biome biome, int chunkX, int chunkZ, ChunkRand rand, TerrainGenerator tgen) {
		boolean skipTerrain = tgen == null;
		TerrainChunk tchunk = skipTerrain ? null : new TerrainChunk(new CPos(chunkX, chunkZ), tgen);
		
		ArrayList<Creature> spawnedCreatures = new ArrayList<>();
	    List<CreatureSpawnData> CreatureSpawnDataList = BiomeCreatures.getCreaturesForBiome(biome);
	    
	    if (!CreatureSpawnDataList.isEmpty()) {
	    	int chunkCornerX = chunkX << 4;
	    	int chunkCornerZ = chunkZ << 4;

	    	while(rand.nextFloat() < 0.1F) {
	    		CreatureSpawnData selectedCreature = getRandomCreature(rand, CreatureSpawnDataList);
	    		int creatureCount = selectedCreature.minCount + rand.nextInt(1 + selectedCreature.maxCount - selectedCreature.minCount); 
	    		//System.out.println(creatureCount);
	            
	    		int x = chunkCornerX + rand.nextInt(16);
	    		int z = chunkCornerZ + rand.nextInt(16);
	    		final int originalBlockX = x;
	    		final int originalBlockZ = z;
	    		
	    		for(int i = 0; i < creatureCount; ++i) {
	    			boolean spawnSuccessful = false;
	    			
	    			for(int attempt = 0; !spawnSuccessful && attempt < 4; ++attempt) {
	    				float width = selectedCreature.type.width;
	    				double realX = Mth.clamp((double)x, (double)chunkCornerX + (double)width, (double)chunkCornerX + 16.0D - (double)width);
	    				double realZ = Mth.clamp((double)z, (double)chunkCornerZ + (double)width, (double)chunkCornerZ + 16.0D - (double)width);
	    				int y = skipTerrain ? 64 : tchunk.getHeightAt((int)realX, (int)realZ);

	    				Creature entity = new Creature(selectedCreature.type, realX, y, realZ);
	    				if ((!skipTerrain) && tchunk.checkBlockCollision(entity)) {
	    					//System.out.println("Collision occurred.");
	    					continue;
	    				}
	    				
	    				// in mc source, the entity is created here
	    				entity.yaw = rand.nextFloat() * 360; 
	    				entity.pitch = 0.0F;
	    				
	    				// assuming there are no additional spawn requirements (like light level, type of block below, etc.)
	    				spawnedCreatures.add(entity);
	    				spawnSuccessful = true;
	            
	    				// randomly changing the x,z coords of next spawn attempt, then checking if those coords lie within the chunk
	    				// if they don't, the algorithm returns to the original x,z pair and repeats the process until the attempt is successful.
	    				x += rand.nextInt(5) - rand.nextInt(5);
	    				for(z += rand.nextInt(5) - rand.nextInt(5); x < chunkCornerX || x >= chunkCornerX + 16 || z < chunkCornerZ || z >= chunkCornerZ + 16; z = originalBlockZ + rand.nextInt(5) - rand.nextInt(5)) {
	    					x = originalBlockX + rand.nextInt(5) - rand.nextInt(5);
	    				}
	    			}
	    		}
	    	}
	    	return spawnedCreatures;
	    } 
	    return List.of();
	}
	
	public CreatureSpawnData getRandomCreature(ChunkRand rand, List<CreatureSpawnData> list) {
		int totalWeight = 0;
		for (CreatureSpawnData creature : list) {
			totalWeight += creature.weight;
		}
		
		int m = rand.nextInt(totalWeight);
		for (CreatureSpawnData creature : list) {
			m -= creature.weight;
			if (m<0) {
				return creature;
			}
		}
		
		return null;
	}
}
