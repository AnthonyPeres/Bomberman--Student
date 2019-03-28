package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.graphics.Animation;
import game.graphics.Sprite;
import game.util.AABB;
import game.util.Vector2f;

public abstract class Affichable {

	/** Variables */
	
	protected Animation animation;
	protected Sprite sprite;
	protected BufferedImage image;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	protected int width;
	protected int height;
	protected AABB SaCase;
	
	/** Constructeur */
	
	public Affichable(Sprite sprite, Vector2f pos, int size) {
		animation = new Animation();	
		this.sprite = sprite;
		this.pos = pos;
		this.size = size;
	}
	
	public Affichable(BufferedImage img, Vector2f pos) {
        this.image = img;	this.pos = pos;
        this.width = 50;	this.height = 50;
        
    }
	
	/** Méthodes */
	
	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		animation.setFrames(frames);
		animation.setDelay(delay);
	}
	
	public void update(double time) {
		animation.update();
	}
	
	public abstract void render(Graphics2D g);
	
	
	/** Accesseurs */
	
	public Animation getAnimation() {return animation;}
	public Sprite getSprite() {return sprite;}
	public int getCurrentAnimation() {return currentAnimation;}
	public Vector2f getPos() {return pos;}
	public int getSize() {return size;}
	public BufferedImage getImage() {return image;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	public AABB getSaPosition() {return SaCase;}
	public int getXSaPosition() {return (int) (this.getSaPosition().getPos().x + this.getSaPosition().getXOffset());}
	public int getYSaPosition() {return (int) (this.getSaPosition().getPos().y + this.getSaPosition().getYOffset());}
	public int getWSaPosition() {return (int) (this.getSaPosition().getWidth());} 
	public int getHSaPosition() {return (int) (this.getSaPosition().getHeight());}
	
	
	public AABB getSaCase() {
		int x = ((this.getXSaPosition()/ 50) * 50);
		int y = ((this.getYSaPosition()/ 50) * 50);
		int w = 50;
		int h = 50;
		
		return new AABB(new Vector2f(x,y),w,h);
	} 
	
	
	
	

	/** Mutateurs */
	
	public void setAnimation(Animation animation) {this.animation = animation;}
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	public void setCurrentAnimation(int currentAnimation) {this.currentAnimation = currentAnimation;}
	public void setPos(Vector2f pos) {this.pos = pos;}
	public void setPosX(int x) {this.pos.x = x;}
	public void setPosY(int y) {this.pos.y = y;}
	public void setSize(int size) {this.size = size;}
	public void setImage(BufferedImage image) {this.image = image;}
	public void setWidth(int width) {this.width = width;}
	public void setHeight(int height) {this.height = height;}
}