package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.graphics.*;
import game.util.*;

public abstract class Entity {
	
	/** Variables */
	
	/* Direction */
	protected final int RIGHT = 0; 	// Right est la premiere ligne du SPRITE
	protected final int LEFT = 1;		// Left est la deuxieme ligne du SPRITE
	protected final int DOWN = 2;		// Down est la troisieme ligne du SPRITE
	protected final int UP = 3;		// Up est la quatrieme ligne du SPRITE
	protected final int FALLEN = 4;
	
	/* Touches */
	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	protected boolean bomb;
	protected boolean fallen;
	
	/* Deplacement */
	protected float dx;
	protected float dy;
	protected float maxSpeed = 3.5f;
	protected float acc = 2.5f;
	protected float deacc = 0.25f;
	
	/* Animation */
	protected Animation ani;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	
	/* Cubes pour les colisions */
	protected AABB bounds;
	protected AABB bombBounds;
	
	protected TileCollision tc;
	
	/** Constructeur */
	
	public Entity(Sprite sprite, Vector2f origin, int size) {
		
		this.sprite = sprite;
		this.pos = origin;
		this.size = size;
		
		bounds = new AABB(origin, size, size);
		bombBounds = new AABB(origin, size, size);
		
		ani = new Animation();
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
		
		tc = new TileCollision(this);
	}
	
	
	
	/** Méthodes */
	
	public void animate() {
		if(up) { 	// Touche haut appuyée 
			if(currentAnimation != UP || ani.getDelay() == -1) {
				setAnimation(UP, sprite.getSpriteArray(UP), 5);
			}
		} else if(down) {	// Touche bas appuyée
			if(currentAnimation != DOWN || ani.getDelay() == -1) {
				setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
			}
		} else if(left) {	// Touche gauche appuyée 
			if(currentAnimation != LEFT || ani.getDelay() == -1) {
				setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
			}
		} else if(right) {	// Touche droite appuyée
			if(currentAnimation != RIGHT || ani.getDelay() == -1) {
				setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
			}
		} else if(fallen){
			if(currentAnimation != FALLEN || ani.getDelay() == -1) {
				setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 4);
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
	
	
	
	public abstract void render(Graphics2D g);

	public void update() {
		animate();
		ani.update();
	}

	/** Accesseurs */

	public int getSize() {return size;}
	public Animation getAnimation() {return ani;}
	public AABB getBounds() {return bounds;}
	public Vector2f getPos() {return pos;}


	/** Mutateurs */
	
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	public void setSize(int i) {size = i;}
	public void setMaxSpeed(float f) {maxSpeed = f;}
	public void setAcc(float f) {acc = f;}
	public void setDeacc(float f) {deacc = f;}
	public void setFallen(boolean b) {fallen = b;}

}