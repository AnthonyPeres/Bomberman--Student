package game.util;

import game.entity.Entity;
import game.entity.bomb.Bomb;
import game.states.PlayState;
import game.tiles.TileMapBlock;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.ObstacleBlock;

public class Collision {

	/** Attributs */
	
	private Bomb b;
	private Entity e;
	
	
	/** Constructeur */
	
	public Collision(Bomb b) { this.b = b; }
	public Collision(Entity e) { this.e = e; }
	
	
	/** Méthodes */
	
	/* Fonction de collision avec les blocs */
	
	public boolean collision(float ax, float ay) {
		int ptArriveX = (int) (e.getBoundsCollision().getPos().x + ax);
		int ptArriveY = (int) (e.getBoundsCollision().getPos().y + ay);
	
		for(int c = 0; c < 4; c++) {
			
			int xt = (int) (ptArriveX + (c % 2) * e.getBoundsCollision().getWidth() + e.getBoundsCollision().getXOffset()) / 50;
			int yt = (int) (ptArriveY + ((int)(c / 2)) * e.getBoundsCollision().getHeight() + e.getBoundsCollision().getYOffset()) / 50;
			
			if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {
				Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
				if(block instanceof ObstacleBlock || block instanceof BreakableBlock) {
					return TileMapBlock.tmo_blocks.get(String.valueOf(xt) + "," + String.valueOf(yt)).update(e.getBoundsCollision());
				}	
			} 
			
			for(int i = 0; i < PlayState.bombList.size(); i++) {
				
				Bomb tempB = PlayState.bombList.get(i);
				
				if( !(e.getPos().x + ax <= tempB.getPos().x + 50) || !(e.getDroite() + ax >= tempB.getPos().x ) || !(e.getPos().y + ay <= tempB.getPos().y + 50) || !(e.getPied() + ay >= tempB.getPos().y)) {
					
				} else {
					if(tempB.getTempsAvantExplosion() < 50) {
						return true;
					}
				}	
			}
		} return false;
	}
	
	
	/* Collision du feu */
	
	public boolean FireCassable(float ax, float ay) {
		int xt = (int) (((b.getCaseActuelle().getPos().x + (ax*50)) + b.getCaseActuelle().getXOffset()) / 50);
		int yt = (int) (((b.getCaseActuelle().getPos().y + (ay*50)) + b.getCaseActuelle().getYOffset()) / 50);
		
		if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {		
			Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
			if(block instanceof BreakableBlock) { 
				((BreakableBlock) block).disparait();
				return true; 
			} 
		} return false;
	}
	
	public boolean FireIncassable(float ax, float ay) {
		int xt = (int) (((b.getCaseActuelle().getPos().x + (ax*50)) + b.getCaseActuelle().getXOffset()) / 50);
		int yt = (int) (((b.getCaseActuelle().getPos().y + (ay*50)) + b.getCaseActuelle().getYOffset()) / 50);
		
		if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {		
			Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
			if(block instanceof ObstacleBlock) { return true; } 
		} return false;
	}
	
	public void FireJoueur(float ax, float ay) {
		int xt = (int) (((b.getCaseActuelle().getPos().x + (ax*50)) + b.getCaseActuelle().getXOffset()) / 50);
		int yt = (int) (((b.getCaseActuelle().getPos().y + (ay*50)) + b.getCaseActuelle().getYOffset()) / 50);
		

		
		
		if((((int)(PlayState.getPlayer().getBoundsCollision().getPos().x + 2)  / 50 == xt) 
		 || ((int)(PlayState.getPlayer().getBoundsCollision().getPos().x + 2 + PlayState.getPlayer().getBoundsCollision().getWidth()) / 50 == xt))
		 && ((((int)(PlayState.getPlayer().getBoundsCollision().getPos().y + 40) / 50) == yt) 
		 || ((int)(PlayState.getPlayer().getBoundsCollision().getPos().y + 40+ PlayState.getPlayer().getBoundsCollision().getHeight()) / 50) == yt)) {
			PlayState.getPlayer().setFallen(true);
		}
		
		for(int i = 0; i < 3; i++) {
			if((((int)(PlayState.getIa(i).getBoundsCollision().getPos().x + 2)  / 50 == xt) 
			 || ((int)(PlayState.getIa(i).getBoundsCollision().getPos().x + 2 + PlayState.getIa(i).getBoundsCollision().getWidth()) / 50 == xt))
			 && ((((int)(PlayState.getIa(i).getBoundsCollision().getPos().y + 40) / 50) == yt) 
			 || ((int)(PlayState.getIa(i).getBoundsCollision().getPos().y + 40+ PlayState.getIa(i).getBoundsCollision().getHeight()) / 50) == yt)) {
				PlayState.getIa(i).setFallen(true);
			}
		}
	}
}