package game.util;

import game.entity.bomb.Bomb;

public class AABB {

	/** Variables */
	
	private float w;
	private float h;
	private float r;
	private float xOffset = 0;
	private float yOffset = 0;
	private int size;
	private Vector2f pos;
	private Bomb b;
	
	/** Constructeurs */
	
	public AABB(Vector2f pos, int w, int h) {
		this.pos = pos;
		this.w = w;
		this.h = h;
		size = Math.max(w, h);
	}
	
	public AABB(Vector2f pos, int r, Bomb b) {
		this.pos = pos;
		this.r = r;
		this.setBomb(b);
		size = r;
	}
	
	/** Méthodes */
	
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
	public Bomb getBomb() {return b;}

	/** Mutateurs */
	
	public void setPos(Vector2f pos) {this.pos = pos;}
	public void setXOffset(float xOffset) {this.xOffset = xOffset;}
	public void setYOffset(float yOffset) {this.yOffset = yOffset;}
	public void setWidth(float w) {this.w = w;}
	public void setHeight(float h) {this.h = h;}
	public void setRadius(float r) {this.r = r;}
	public void setSize(int size) {this.size = size;}
	public void setBomb(Bomb b) {this.b = b;}
}