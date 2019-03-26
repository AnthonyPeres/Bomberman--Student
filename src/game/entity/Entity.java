package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.entity.bomb.BasicBomb;
import game.entity.bomb.Bomb;
import game.entity.bomb.HorizontalBomb;
import game.entity.bomb.MineBomb;
import game.entity.bomb.PiqBomb;
import game.entity.bomb.RcBomb;
import game.entity.bomb.VerticalBomb;
import game.graphics.Animation;
import game.graphics.Sprite;
import game.util.AABB;
import game.util.Collision;
import game.util.Vector2f;

/**
 * 
 * Class entity gerant les entités présentes sur le jeu
 * 
 */
public class Entity {
	
	/** Variables */
	
	/* Directions */
	protected final int RIGHT = 0;
	protected final int LEFT = 1;
	protected final int DOWN = 2;
	protected final int UP = 3;
	protected final int FALLEN = 4;
	
	/* Actions */
	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	protected boolean fallen;
	protected boolean bomb;
	protected boolean choixBombe;
	
	/* Deplacement */
	protected float dx;
	protected float dy;
	protected float maxSpeed = 3.5f;
	protected float acc = 2.5f;
	protected float deacc = 1f;
	
	/* Animation */
	protected Animation animation;
	protected Sprite sprite;
	protected int currentAnimation;
	
	/* Positionnement */
	protected Vector2f pos;
	protected int size;
	protected int positionInitialeX;
	protected int positionInitialeY;
	
	/* La gestion des collisions */
	protected AABB boundsCollision;
	protected Collision collision;
	
	/* Gestion des bombes */
	public ArrayList<Bomb> bombList = new ArrayList<Bomb>();
	protected AABB boundsBomb;
	protected int maxBomb = 4;
	protected int bombposee = 0;
	protected int exPosY;
	protected int exPosX;
	protected int bombeChoisie = 0;
	
			
	/** Constructeur */
	
	public Entity(Sprite sprite, Vector2f origin, int size) {
		
		/* Animation */
		animation = new Animation();
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
		this.sprite = sprite;
		
		/* Positionnement */
		this.pos = origin;
		this.size = size;
		this.positionInitialeX = (int) this.pos.x;
		this.positionInitialeY = (int) this.pos.y;
		
		/* Collisions */
		collision = new Collision(this);	
		boundsCollision = new AABB(origin, size, size);
		this.boundsCollision.setCube(45, 29, 2, 40);
		
        
        /* Endroit ou l'on pose la bombe */
		boundsBomb = new AABB(origin, size, size);
		this.boundsBomb.setCube(5, 5, ((size/2)-2), ((size/2) + 25));
		
	}
	
	/** Méthodes */
	
	public void animate() {
		if(up) { if(currentAnimation != UP || animation.getDelay() == -1) {setAnimation(UP, sprite.getSpriteArray(UP),5);}} 
		else if(down) { if(currentAnimation != DOWN || animation.getDelay() == -1) {setAnimation(DOWN, sprite.getSpriteArray(DOWN),5);}} 
		else if(left) {if(currentAnimation != LEFT || animation.getDelay() == -1) {setAnimation(LEFT, sprite.getSpriteArray(LEFT),5);}} 
		else if(right) {if(currentAnimation != RIGHT || animation.getDelay() == -1) {setAnimation(RIGHT, sprite.getSpriteArray(RIGHT),5);}} 
		else if(fallen){if(currentAnimation != FALLEN || animation.getDelay() == -1) {setAnimation(FALLEN, sprite.getSpriteArray(FALLEN),5);}} 
		else {setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);}
	}
    
    protected void move() {
        if(up) {dy -= acc;
            if(dy < -maxSpeed) {dy = -maxSpeed;}
        } else {
            if(dy < 0) {dy += deacc;
                if(dy > 0) {dy = 0;}
            }
        }
        if(down) {dy += acc;
            if(dy > maxSpeed) {dy = maxSpeed;}
        } else {
            if(dy > 0) {dy -= deacc;
                if(dy < 0) {dy = 0;}
            }
        }
        if(left) {dx -= acc;
            if(dx < -maxSpeed) {dx = -maxSpeed;}
        } else {
            if(dx < 0) {dx += deacc;
                if(dx > 0) {dx = 0;}
            }
        }
        if(right) {dx += acc;
            if(dx > maxSpeed) {dx = maxSpeed;}
        } else {
            if(dx > 0) {dx -= deacc;
                if(dx < 0) {dx = 0;}
            }
        }
    }
	
	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		animation.setFrames(frames);
		animation.setDelay(delay);
	}
	
	protected void resetPosition() {
    	pos.x = positionInitialeX;
    	pos.y = positionInitialeY;
    	setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
    }
	
	protected void changeBombe() {
		if(this.bombeChoisie == 5) {this.bombeChoisie = 0;} 
		else { this.bombeChoisie++; }
	}
	
	protected void putABomb() {
    	
    	if(bomb) {
    		int X = (int) (pos.x + boundsBomb.getWidth() + boundsBomb.getXOffset()) / 50;
    		int Y = (int) (pos.y + boundsBomb.getHeight() + boundsBomb.getYOffset()) / 50;
    		
    		for(int i = 0; i < bombList.size(); i++) {
    			if((bombList.get(i).getPos().x / 50) == X && (bombList.get(i).getPos().y / 50) == Y) {
    				return;
    			}
    		}
    		
    		if(bombposee < maxBomb) {
    			switch(this.bombeChoisie) {
	    			case 0: bombList.add(new BasicBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 1: bombList.add(new HorizontalBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 2: bombList.add(new VerticalBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 3: bombList.add(new MineBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 4: bombList.add(new RcBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 5: bombList.add(new PiqBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
				}
				bombposee++;
    		}
    	}
	}
	
	public void update(double time) {
		animate();
		animation.update();
	}
	
	public void render(Graphics2D g) {
		/* Rectangle qui sert a placer la bombe sur une tuile */
		g.setColor(Color.red);
		g.drawRect((int) (pos.x + boundsBomb.getXOffset()), 
				   (int) (pos.y + boundsBomb.getYOffset()), 
				   (int) boundsBomb.getWidth(), 
				   (int) boundsBomb.getHeight());
			
		/* Le rectangle entourant le joueur pour tester les collisions */
		g.setColor(Color.green);
		g.drawRect((int) (pos.x + boundsCollision.getXOffset()), 
				   (int) (pos.y + boundsCollision.getYOffset()), 
				   (int) boundsCollision.getWidth(), 
				   (int) boundsCollision.getHeight());
		
		/* Affichage de l'animation (+20 en hauteur pour que le perso ne soit pas carré */
		g.drawImage(animation.getImage(), (int) (pos.x), (int) (pos.y), size, size + 20, null);
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
	public String getBombeChoisie() {
		switch(this.bombeChoisie) {
			case 1: return "Horizontale";
			case 2: return "Verticale";
			case 3: return "Mine";
			case 4: return "RC";
			case 5: return "Piques";
			default : return "Basique";
		}
	}


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