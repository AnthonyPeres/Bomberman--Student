package game.util;

import game.entity.bomb.Bomb;
import game.states.PlayState;
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
	
	public void collisionJoueur(float ax, float ay) {
		
		/* xt et yt valent la case exacte */
		int xt = (int) (((b.getBounds().getPos().x + (ax*50)) + b.getBounds().getXOffset()) / 50);
		int yt = (int) (((b.getBounds().getPos().y + (ay*50)) + b.getBounds().getYOffset()) / 50);
		
		if((((int)(PlayState.getPlayer().getBounds().getPos().x + 2)  / 50 == xt) 
		 || ((int)(PlayState.getPlayer().getBounds().getPos().x + 2 + PlayState.getPlayer().getBounds().getWidth()) / 50 == xt))
		 && ((((int)(PlayState.getPlayer().getBounds().getPos().y + 40) / 50) == yt) 
		 || ((int)(PlayState.getPlayer().getBounds().getPos().y + 40+ PlayState.getPlayer().getBounds().getHeight()) / 50) == yt)) {
			PlayState.getPlayer().setFallen(true);
		}
		
		for(int i = 0; i < 3; i++) {
			if((((int)(PlayState.getIa(i).getBounds().getPos().x + 2)  / 50 == xt) 
			 || ((int)(PlayState.getIa(i).getBounds().getPos().x + 2 + PlayState.getIa(i).getBounds().getWidth()) / 50 == xt))
			 && ((((int)(PlayState.getIa(i).getBounds().getPos().y + 40) / 50) == yt) 
			 || ((int)(PlayState.getIa(i).getBounds().getPos().y + 40+ PlayState.getIa(i).getBounds().getHeight()) / 50) == yt)) {
				PlayState.getIa(i).setFallen(true);
			}
		}
	}
}