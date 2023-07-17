package kludwisz.mobspawninglib;

import java.util.ArrayList;
import java.util.List;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcmath.util.Mth;

public class ChunkMobSpawner {
	
	public List<Creature> getMobsInChunk(Biome biome, CPos chunkPos, ChunkRand rand) {
		return getMobsInChunk(biome, chunkPos.getX(), chunkPos.getZ(), rand);
	}
	
	// note: the algorithm requires the rand object to be seeded with the population seed
	public List<Creature> getMobsInChunk(Biome biome, int chunkX, int chunkZ, ChunkRand rand) {
		
		ArrayList<Creature> spawnedCreatures = new ArrayList<>();
	    List<CreatureData> creatureDataList = BiomeCreatures.getCreaturesForBiome(biome);
	    
	    if (!creatureDataList.isEmpty()) {
	    	int chunkCornerX = chunkX << 4;
	    	int chunkCornerZ = chunkZ << 4;

	    	while(rand.nextFloat() < 0.1F) {
	    		CreatureData selectedCreature = getRandomCreature(rand, creatureDataList);
	    		int creatureCount = selectedCreature.minCount + rand.nextInt(1 + selectedCreature.maxCount - selectedCreature.minCount); 
	            
	    		int x = chunkCornerX + rand.nextInt(16);
	    		int z = chunkCornerZ + rand.nextInt(16);
	    		final int originalBlockX = x;
	    		final int originalBlockZ = z;

	    		for(int i = 0; i < creatureCount; ++i) {
	    			boolean spawnSuccessful = false;

	    			for(int attempt = 0; !spawnSuccessful && attempt < 4; ++attempt) {
	    				float width = selectedCreature.type.width;
	    				double var20 = Mth.clamp((double)x, (double)chunkCornerX + (double)width, (double)chunkCornerX + 16.0D - (double)width);
	    				double var22 = Mth.clamp((double)z, (double)chunkCornerZ + (double)width, (double)chunkCornerZ + 16.0D - (double)width);
	                  
	    				Creature entity = new Creature(selectedCreature.type, var20, 64, var22); // ignoring y-position for the moment
	    				if (checkEntityCollision(entity, spawnedCreatures) || checkBlockCollision(entity)) {
	    					//System.out.println("Collision occurred.");
	    					continue;
	    				}
	    				
	    				entity.yaw = rand.nextFloat() * 360; // in mc source, the entity is created here
	    				entity.pitch = 0.0F;
	    				
	    				// assuming there is no spawn obstruction and no additional spawn rules
	    				spawnedCreatures.add(entity);
	    				spawnSuccessful = true;
	            
	    				// randomly changing the x,z coords of next spawn attempt, then checking if those coords lie within the chunk
	    				// if they don't, the algorithm returns to the original x,z pair and repeats the process until the attempt is successful.
	    				x += rand.nextInt(5) - rand.nextInt(5);
	    				for(z += rand.nextInt(5) - rand.nextInt(5); x < chunkCornerX || x >= chunkCornerX + 16 || z < chunkCornerZ || z >= chunkCornerZ + 16; z = originalBlockZ + rand.nextInt(5) - rand.nextInt(5)) {
	    					x = originalBlockX + rand.nextInt(5) - rand.nextInt(5);
	    				}
	    				
	    				// the following do-while loop is an alternative which has the same properties as the code above
	    				/*
	    				do {
	    					x = originalBlockX + rand.nextInt(5) - rand.nextInt(5);
	    					z = originalBlockZ + rand.nextInt(5) - rand.nextInt(5);
	    				} while (x < chunkCornerX || x >= chunkCornerX + 16 || z < chunkCornerZ || z >= chunkCornerZ + 16);
	    				*/
	    			}
	    		}
	    	}
	    	return spawnedCreatures;
	    } 
	    return List.of();
	}
	
	public CreatureData getRandomCreature(ChunkRand rand, List<CreatureData> list) {
		int totalWeight = 0;
		for (CreatureData creature : list) {
			totalWeight += creature.weight;
		}
		
		int m = rand.nextInt(totalWeight);
		for (CreatureData creature : list) {
			m -= creature.weight;
			if (m<0) {
				return creature;
			}
		}
		
		return null;
	}
	
	public boolean checkEntityCollision(Creature target, List<Creature> entities) {
		for (Creature other : entities) {
			if (target.hitbox.collidesWithXZ(other.hitbox)) // using the shortcut without y axis for now
				return true;
		}
		return false;
	}
	
	private boolean checkBlockCollision(Creature target) {
		if (target.type.width < 1.0F)	// if the creature fits within a block, it won't collide with other blocks on the x,z axes
			return false;				// ignoring y-axis collisions since they usually involve decorators such as trees
		else {
			// TODO (maybe??) use TerrainGenerator to detect some of the entity-block collisions
		}
		
		return false;
	}
}
