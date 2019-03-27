package game.util;

import game.entity.Entity;

/** 
 * 
 * 	Class qui creer un rectangle afin de gerer les collisions, les depots de bombe...
 *  
 */
public class AABB {

	/** Variables */
	
	private float w;
	private float h;
	private float r;
	private float xOffset = 0;
	private float yOffset = 0;
	
	private int size;
	
	private Vector2f pos;
	
	private Entity e;
	
	
	/** Constructeurs */
	
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
	
	
	/** Méthodes */
	
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
	
	public void setCube(int w, int h, int xoffset, int yoffset) {
		this.w = w;
		this.h = h;
		this.xOffset = xoffset;
		this.yOffset = yoffset;
	}
	
	
	/** Accesseurs */

	public Vector2f getPos() {return pos;}
	public float getXOffset() {return xOffset;}
	public float getYOffset() {return yOffset;}
	public float getWidth() {return w;}
	public float getHeight() {return h;}
	public float getRadius() {return r;}
	public int getSize() {return size;}
	public Entity getE() {return e;}

	/** Mutateurs */
	
	public void setPos(Vector2f pos) {this.pos = pos;}
	public void setXOffset(float xOffset) {this.xOffset = xOffset;}
	public void setYOffset(float yOffset) {this.yOffset = yOffset;}
	public void setWidth(float w) {this.w = w;}
	public void setHeight(float h) {this.h = h;}
	public void setRadius(float r) {this.r = r;}
	public void setSize(int size) {this.size = size;}
	public void setE(Entity e) {this.e = e;}	
}