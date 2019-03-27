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
	protected AABB caseActuelle;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	protected int width;
	protected int height;
	
	/** Constructeur */
	
	public Affichable(Sprite sprite, Vector2f pos, int size) {
		animation = new Animation();	
		this.sprite = sprite;
		this.pos = pos;
		this.size = size;
		caseActuelle = new AABB(pos, size, size);
		this.caseActuelle.setCube(50, 50, 0, 20);
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
	public AABB getCaseActuelle() {return caseActuelle;}
	public int getCaseActuelleX() {return (int) (this.getCaseActuelle().getPos().x / 50);}
	public int getCaseActuelleY() {return (int) (this.getCaseActuelle().getPos().y / 50);}
	public BufferedImage getImage() {return image;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}

	/** Mutateurs */
	
	public void setAnimation(Animation animation) {this.animation = animation;}
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	public void setCurrentAnimation(int currentAnimation) {this.currentAnimation = currentAnimation;}
	public void setPos(Vector2f pos) {this.pos = pos;}
	public void setPosX(int x) {this.pos.x = x;}
	public void setPosY(int y) {this.pos.y = y;}
	public void setSize(int size) {this.size = size;}
	public void setCaseActuelle(AABB caseActuelle) {this.caseActuelle = caseActuelle;}
	public void setImage(BufferedImage image) {this.image = image;}
	public void setWidth(int width) {this.width = width;}
	public void setHeight(int height) {this.height = height;}
}