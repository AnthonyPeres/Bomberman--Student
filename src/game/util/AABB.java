package game.util;

import game.entity.Entity;
import game.tiles.TileMapBlock;
import game.tiles.blocks.Block;
import game.tiles.blocks.NormBlock;

/** Il s'agit d'un rectangle aligné avec les axes, c'est à dire que ses cotés sont parallèles aux axes des x et des y */
public class AABB {

	private Vector2f pos;
	
	private float xOffset = 0;
	private float yOffset = 0;
	
	private float w;
	private float h;
	private float r;
	private int size;
	private Entity e;
	
	public AABB(Vector2f pos, int w, int h) {
		this.pos = pos;
		this.w = w;
		this.h = h;
		
		size = Math.max(w, h);
	}
	
	public AABB(Vector2f pos, int r, Entity e) {
		this.pos = pos;
		this.r = r;
		this.e = e;
		size = r;
	}
	
	public Vector2f getPos() {return pos;}
	
	public float getRadius() {return r;}
	public float getWidth() {return w;}
	public float getHeight() {return h;}
	
	
	public void setBox(Vector2f pos, int w, int h) {
		this.pos = pos;
		this.w = w;
		this.h = h;
		size = Math.max(w, h);
	}
	
	public void setCircle(Vector2f pos, int r) {
		this.pos = pos;
		this.r = r;
		
		size = r;
	}
	
	public void setWidth(float f) {w=f;}
	public void setHeight(float f) {h=f;}
	
	public void setXOffset(float f) {xOffset = f;}
	public void setYOffset(float f) {yOffset = f;}
	
	
	public float getXOffset() { return xOffset;}
	public float getYOffset() { return yOffset;}
	
	public boolean collides(AABB bBox) {
		
		float ax = ((pos.getWorldVar().x + (xOffset)) + (w / 2));
		float ay = ((pos.getWorldVar().y + (yOffset)) + (h / 2));
		
		float bx = ((bBox.pos.getWorldVar().x + (bBox.xOffset / 2)) + (w / 2));
		float by = ((bBox.pos.getWorldVar().y + (bBox.yOffset / 2)) + (h / 2));
		
		if(Math.abs(ax - bx) < (this.w / 2) + (bBox.w / 2)) {
			if(Math.abs(ay - by) < (this.h / 2) + (bBox.h / 2)) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean colCircleBox(AABB aBox) {
		
		float cx = (float) (pos.getWorldVar().x + r / Math.sqrt(2) - e.getSize() / Math.sqrt(2));
		float cy = (float) (pos.getWorldVar().y + r / Math.sqrt(2) - e.getSize() / Math.sqrt(2));
		
		float xDeltat = cx - Math.max(aBox.pos.getWorldVar().x + (aBox.getWidth() / 2), Math.min(cx, aBox.pos.getWorldVar().x));
		float yDeltat = cy - Math.max(aBox.pos.getWorldVar().y + (aBox.getWidth() / 2), Math.min(cx, aBox.pos.getWorldVar().y));
		
		
		if((xDeltat * xDeltat + yDeltat * yDeltat) < ((this.r / Math.sqrt(2)) * (this.r / Math.sqrt(2)))) {
			return true;
		}
		
		return false;
	}
	
	
	
	/* Fonction de collision avec les blocs */
	public boolean collisionTile(float ax, float ay) {
		
		int ptArriveX = (int) (pos.x + ax);
		int ptArriveY = (int) (pos.y + ay);
		
		
		/* Pour les collisions en x on appelle la fonction avec la vitesse en x (dx) 
		 * Pour les collisions en y on appelle la fonction avec la vitesse en y (dy) */
		
		for(int c = 0; c < 4; c++) {
			
			
			/* xt = ((la position actuelle en x du joueur + la vitesse en x) + ((i variant de 0 a 3) % 2) * la taille du joueur + le decalage en x) / 50*/
			int xt = (int) (ptArriveX + (c % 2) * w + xOffset) / 50;
			int yt = (int) (ptArriveY + ((int)(c / 2)) * h + yOffset) / 50;
			
			if(TileMapBlock.tmo_blocks.containsKey(String.valueOf(xt) + "," + String.valueOf(yt))) {
				Block block = TileMapBlock.tmo_blocks.get(String.valueOf(xt) + ","+ String.valueOf(yt));
				if(block instanceof NormBlock) {
					return TileMapBlock.tmo_blocks.get(String.valueOf(xt) + "," + String.valueOf(yt)).update(this);
				}
			
				
			} 
		}
		
		return false;
		
	}
	
	
	
	
	
	
}
