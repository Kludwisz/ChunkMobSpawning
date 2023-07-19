package kludwisz.mobspawninglib;

import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mcterrain.TerrainGenerator;

public class TerrainChunk {
	public int[][] heightmap;
	
	public TerrainChunk(CPos chunk, TerrainGenerator tgen) {
		this.heightmap = new int[16][16];
		for (int x=0; x<16; x++) for (int z=0; z<16; z++) {
			this.heightmap[x][z] = tgen.getHeightOnGround((chunk.getX()<<4)+x, (chunk.getZ()<<4)+z);
		}
	}
	
	public boolean checkBlockCollision(Creature target) {
		BPos blockMin = target.hitbox.posMin.toBlockPos();
		BPos blockMax = target.hitbox.posMax.toBlockPos();
		
		int standingAtY = blockMax.getY();
		for (int x = blockMin.getX(); x <= blockMax.getX(); x++) {
			for (int z = blockMin.getZ(); z <= blockMax.getZ(); z++) {
				//System.out.println(this.getHeightAt(x, z));
				if (this.getHeightAt(x, z) > standingAtY) {		// assuming that the terrain has no gaps along the Y axis
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int getHeightAt(int x, int z) {
		return this.heightmap[x & 0xF][z & 0xF];
	}
}
