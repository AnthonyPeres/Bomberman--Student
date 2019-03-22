package game.util;

import game.entity.bomb.Bomb;
import game.tiles.TileMapBlock;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.ObstacleBlock;

public class FireCollision {

	
	private Bomb b;
	
	public FireCollision(Bomb b) {
		this.b = b;
	}
	
	public boolean collisionCassable(float ax, float ay) {
		
		/* xt et yt valent la case exacte */
		int xt = (int) (((b.getBounds().getPos().x + (ax*50)) + b.getBounds().getXOffset()) / 50);
		int yt = (int) (((b.getBounds().getPos().y + (ay*50)) + b.getBounds().getYOffset()) / 50);
		
		if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {		
			Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
			if(block instanceof BreakableBlock) { 
				
				
				// Je pense que c'est ici que l'on doit s'occuper de la suppresion du block
				
				
				
				((BreakableBlock) block).disparait();
				
				return true; 
			} 
		} return false;
	}
	
	public boolean collisionIncassable(float ax, float ay) {
		
		/* xt et yt valent la case exacte */
		int xt = (int) (((b.getBounds().getPos().x + (ax*50)) + b.getBounds().getXOffset()) / 50);
		int yt = (int) (((b.getBounds().getPos().y + (ay*50)) + b.getBounds().getYOffset()) / 50);
		
		if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {		
			Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
			if(block instanceof ObstacleBlock) { return true; } 
		} return false;
	}
}