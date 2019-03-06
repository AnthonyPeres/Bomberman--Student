package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.graphics.*;
import game.util.*;

public abstract class Entity {
	
	/** Variables */
	
	/* Direction */
	private final int RIGHT = 0; 	// Right est la premiere ligne du SPRITE
	private final int LEFT = 1;		// Left est la deuxieme ligne du SPRITE
	private final int DOWN = 2;		// Down est la troisieme ligne du SPRITE
	private final int UP = 3;		// Up est la quatrieme ligne du SPRITE
	
	/* Animation */
	protected Animation ani;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	
	/* Touches */
	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	protected boolean bomb;
	
	/* Deplacement */
	protected float dx;
	protected float dy;
	protected float maxSpeed = 3f;
	protected float acc = 2f;
	protected float deacc = 0.2f;
	
	/* Cubes pour les colisions */
	protected AABB hitBounds;
	protected AABB bounds;
	
	
	/** Constructeur */
	
	public Entity(Sprite sprite, Vector2f origin, int size) {
		this.sprite = sprite;
		this.pos = origin;
		this.size = size;
		
		bounds = new AABB(origin, size, size);
		hitBounds = new AABB(origin, size, size);
		hitBounds.setXOffset(size / 2);
		
		ani = new Animation();
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 10);
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
		} else {
			setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
		}
		
	}
	
	
	
	public void update() {
		animate();
		setHitBoxDirection();
		ani.update();
	}
	
	public abstract void render(Graphics2D g);
	



	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		ani.setFrames(frames);
		ani.setDelay(delay);
	}
	
	private void setHitBoxDirection() {
		if(up) {
            hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(0);
        }
        else if(down) {
            hitBounds.setYOffset(size / 2);
            hitBounds.setXOffset(0);
        }
        else if(left) {
            hitBounds.setXOffset(-size / 2);
            hitBounds.setYOffset(0);
        }
        else if(right) {
            hitBounds.setXOffset(size / 2);
            hitBounds.setYOffset(0);
        }
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

}