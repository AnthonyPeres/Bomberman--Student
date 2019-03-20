package game.util;

import game.entity.bomb.Explosion;
import game.tiles.TileMapBlock;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.ObstacleBlock;

public class FireCollision {

	
	private Explosion e;
	
	public FireCollision(Explosion e) {
		this.e = e;
	}
	
	public boolean collisionCassable(float ax, float ay) {
		
		/* xt et yt valent la case exacte */
		int xt = (int) (((e.getBounds().getPos().x + (ax*50)) + e.getBounds().getXOffset()) / 50);
		int yt = (int) (((e.getBounds().getPos().y + (ay*50)) + e.getBounds().getYOffset()) / 50);
		
		if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {		
			Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
			if(block instanceof BreakableBlock) { return true; 
			
				// Je pense que c'est ici que l'on doit s'occuper de la suppresion du block
				
			
			} 
		} return false;
	}
	
	public boolean collisionIncassable(float ax, float ay) {
		
		/* xt et yt valent la case exacte */
		int xt = (int) (((e.getBounds().getPos().x + (ax*50)) + e.getBounds().getXOffset()) / 50);
		int yt = (int) (((e.getBounds().getPos().y + (ay*50)) + e.getBounds().getYOffset()) / 50);
		
		if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {		
			Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
			if(block instanceof ObstacleBlock) { return true; } 
		} return false;
	}
}