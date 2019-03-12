package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.entity.bomb.BasicBomb;
import game.entity.bomb.Bomb;
import game.graphics.*;
import game.util.*;

/**
 * 
 * Class entity gerant les entités présentes sur le jeu
 * 
 */
public abstract class Entity {
	
	/** Variables */
	
	/* Direction (entier correspondant a la ligne du sprite) */
	protected final int RIGHT = 0;
	protected final int LEFT = 1;
	protected final int DOWN = 2;
	protected final int UP = 3;
	protected final int FALLEN = 4;
	
	/* Touches */
	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	protected boolean fallen;
	protected boolean bomb;
	
	/* Deplacement */
	protected float dx;
	protected float dy;
	protected float maxSpeed = 3.5f;
	protected float acc = 2.5f;
	protected float deacc = 1f;
	
	/* Animation */
	protected Animation animation;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	protected int positionInitialeX;
	protected int positionInitialeY;
	
	/* Les bornes collisions et depot bombe */
	protected AABB boundsCollision;
	protected AABB boundsBomb;
	
	/* La gestion des collisions */
	protected TileCollision tileCollision;
	
	/* Gestion des bombes */
	protected int maxBomb = 4;
	protected int bombposee = 0;
	protected int exPosY;
	protected int exPosX;
	public ArrayList<Bomb> bombList = new ArrayList<Bomb>();
				
			
	/** Constructeur */
	
	public Entity(Sprite sprite, Vector2f origin, int size) {
		
		this.sprite = sprite;
		this.pos = origin;
		this.size = size;
		
		this.positionInitialeX = (int) this.pos.x;
		this.positionInitialeY = (int) this.pos.y;
		
		
		/* Rectangle des collisions */
		boundsCollision = new AABB(origin, size, size);
		boundsCollision.setWidth(45);
		boundsCollision.setHeight(29);
		boundsCollision.setXOffset(2);
		boundsCollision.setYOffset(40);
        
        /* Rectangle ou on pose la bombe */
		boundsBomb = new AABB(origin, size, size);
		boundsBomb.setWidth(5);
        boundsBomb.setHeight(5);
        boundsBomb.setXOffset((size/2)-2);
        boundsBomb.setYOffset(size/2 + 25);
		
		/* Animation */
		animation = new Animation();
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
		
		/* Gestion des collisions */
		tileCollision = new TileCollision(this);	
	}
	
	
	/** Méthodes */
	
	public abstract void animate();
	public abstract void render(Graphics2D g);
	protected abstract void move();
	
	/*
	 * L'update relative à toutes les entités, chaque entités dispose d'une autre 
	 * fonction update dans laquelle on ajoute les autres fonctionnalités
	 */
	public void update(double time) {
		animate();
		animation.update();
	}
	
	
	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		animation.setFrames(frames);
		animation.setDelay(delay);
	}
	
	protected void resetPosition() {
    	System.out.println("reset position");
    	pos.x = positionInitialeX;
    	pos.y = positionInitialeY;
    	setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
    }
	
	protected void putABomb() {
    	
    	/*  (suite) Faire en sorte que je ne puisse pas poser une bombe sur une autre bombe 
    	 *  grace a une arraylist
    	 * */
    	
    	if(bomb) {
    		int nbX = (int) (pos.x + boundsBomb.getWidth() + boundsBomb.getXOffset()) / 50;
    		int nbY = (int) (pos.y + boundsBomb.getHeight() + boundsBomb.getYOffset()) / 50;
    		
    		if(bombposee < maxBomb && (nbX != exPosX || nbY != exPosY)) {
    			
    			bombList.add(new BasicBomb(new Sprite("entity/spriteBombe.png",30,30), new Vector2f((nbX*50), (nbY*50)) ,50, this));
    			
    			this.exPosX = nbX;
    			this.exPosY = nbY;
    			
    			bombposee++;	
    		}
    	}
    }
	

	/** Accesseurs */

	public int getSize() {return size;}
	public Animation getAnimation() {return animation;}
	public AABB getBounds() {return boundsCollision;}
	public Vector2f getPos() {return pos;}
	public float getDx() {return dx;}
	public float getDy() {return dy;}
	public float getMaxSpeed() {return maxSpeed;}
	public float getAcc() {return acc;}
	public float getDeacc() {return deacc;}
	public int getMaxBomb() {return maxBomb;}
	public int getBombposee() {return bombposee;}
	public int getExPosY() {return exPosY;}
	public int getExPosX() {return exPosX;}


	/** Mutateurs */
	
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	public void setSize(int i) {size = i;}
	public void setMaxSpeed(float f) {maxSpeed = f;}
	public void setAcc(float f) {acc = f;}
	public void setDeacc(float f) {deacc = f;}
	public void setFallen(boolean b) {fallen = b;}
	public void setDx(float dx) {this.dx = dx;}
	public void setDy(float dy) {this.dy = dy;}
	public void setMaxBomb(int maxBomb) {this.maxBomb = maxBomb;}
	public void setBombposee(int bombposee) {this.bombposee = bombposee;}
	public void setExPosY(int exPosY) {this.exPosY = exPosY;}
	public void setExPosX(int exPosX) {this.exPosX = exPosX;}
}