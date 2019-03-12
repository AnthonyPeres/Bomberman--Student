package game.entity.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Entity;
import game.graphics.Animation;
import game.graphics.Sprite;
import game.util.AABB;
import game.util.FireCollision;
import game.util.Vector2f;

public abstract class Bomb {
	
	/** Variables */
	
	/* Direction */
	protected final int BASIC = 0;
	protected final int HORIZONTAL = 1;
	protected final int VERTICAL = 2;
	protected final int MINE = 3;
	protected final int RC = 4;
	protected final int PIQ = 5;
	
	protected int type;
	
	/* Animation */
	protected Animation ani;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	
	/* Cubes pour les colisions */
	protected AABB bounds;
	
	/* Explosion */
	protected FireCollision fireCollision;
	protected boolean explose;
	protected int tempsAvantExplosion = 120;
	protected int rayon;
	
	/* Entite posant la bombe */
	protected Entity e;
	
	/** Constructeur */
	
	public Bomb(Sprite sprite, Vector2f origin, int size, int type, Entity e) {
		
		this.sprite = sprite;
		this.pos = origin;
		this.size = size;
		this.type = type;
		
		ani = new Animation();
		setAnimation(type, sprite.getSpriteArray(type), 10);
		
		bounds = new AABB(origin, size, size);
		this.bounds.setWidth(50);
		this.bounds.setHeight(50);
		this.bounds.setXOffset(0);
		this.bounds.setYOffset(0);
		
		fireCollision = new FireCollision(this);
		this.explose = false;
		
		this.e = e;
	}
	
	
	/** Méthodes */
	
	public abstract void explose();
	
	public abstract void render(Graphics2D g);
	
	public void animate() {
		if(type == BASIC) { 	// Touche haut appuyée 
			if(currentAnimation != BASIC || ani.getDelay() == -1) {
				setAnimation(BASIC, sprite.getSpriteArray(BASIC), 5);
			}
		} else if(type == HORIZONTAL) {	// Touche bas appuyée
			if(currentAnimation != HORIZONTAL || ani.getDelay() == -1) {
				setAnimation(HORIZONTAL, sprite.getSpriteArray(HORIZONTAL), 5);
			}
		} else if(type == VERTICAL) {	// Touche gauche appuyée 
			if(currentAnimation != VERTICAL || ani.getDelay() == -1) {
				setAnimation(VERTICAL, sprite.getSpriteArray(VERTICAL), 5);
			}
		} else if(type == MINE) {	// Touche droite appuyée
			if(currentAnimation != MINE || ani.getDelay() == -1) {
				setAnimation(MINE, sprite.getSpriteArray(MINE), 5);
			}
		} else if(type == RC){
			if(currentAnimation != RC || ani.getDelay() == -1) {
				setAnimation(RC, sprite.getSpriteArray(RC), 4);
			}
		} else if(type == PIQ){
			if(currentAnimation != PIQ || ani.getDelay() == -1) {
				setAnimation(PIQ, sprite.getSpriteArray(PIQ), 4);
			}
		} else {
			setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
		}
	}

	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		ani.setFrames(frames);
		ani.setDelay(delay);
	}
	
	public void decouleTemps() {
        if (--tempsAvantExplosion == 0)
        	 explose();
	}

	public void update(double time) {
		animate();
		ani.update();
		decouleTemps();
		
	}

	/** Accesseurs */

	public int getSize() {return size;}
	public Animation getAnimation() {return ani;}
	public AABB getBounds() {return bounds;}
	public Vector2f getPos() {return pos;}
	public boolean getExplose() {return explose;}
	public int getTempsAvantExplosion() {return tempsAvantExplosion;}

	/** Mutateurs */
	
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	public void setSize(int i) {size = i;}
	public void setTempsAvantExplosion(int tempsAvantExplosion) {this.tempsAvantExplosion = tempsAvantExplosion;}
}