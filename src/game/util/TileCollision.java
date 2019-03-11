package game.util;

import game.entity.Entity;
import game.tiles.TileMapBlock;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.ObstacleBlock;

public class TileCollision {

	/** Variables */
	
	private Entity e;
	
	/** Constructeur */
	
	public TileCollision(Entity e) {
		this.e = e;
	}
	

	/** Méthodes */
	
	/* Fonction de collision avec les blocs */
	public boolean collisionTile(float ax, float ay) {
		
		int ptArriveX = (int) (e.getBounds().getPos().x + ax);
		int ptArriveY = (int) (e.getBounds().getPos().y + ay);
		
		
		/* Pour les collisions en x on appelle la fonction avec la vitesse en x (dx) 
		 * Pour les collisions en y on appelle la fonction avec la vitesse en y (dy) */
		
		for(int c = 0; c < 4; c++) {
			
			/* xt = ((la position actuelle en x du joueur + la vitesse en x) + ((i variant de 0 a 3) % 2) * la taille du joueur + le decalage en x) / 50*/
			int xt = (int) (ptArriveX + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / 50;
			int yt = (int) (ptArriveY + ((int)(c / 2)) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / 50;
			
			if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {
				Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
				
				/* Si c'est un obstacleBlock ou un breakableBlock il y a collision */
				if(block instanceof ObstacleBlock || block instanceof BreakableBlock) {
					return TileMapBlock.tmo_blocks.get(String.valueOf(xt) + "," + String.valueOf(yt)).update(e.getBounds());
				}	
			} 
		}
		return false;
	}
}