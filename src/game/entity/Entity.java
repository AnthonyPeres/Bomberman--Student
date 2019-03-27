package game.entity;

import java.awt.Graphics2D;

import game.entity.bomb.*;
import game.graphics.Sprite;
import game.states.PlayState;
import game.util.*;


public abstract class Entity extends Affichable {
	
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
	
	/* Deplacement */
	protected float dx;
	protected float dy;
	protected float maxSpeed = 3.5f;
	protected float acc = 3f;
	protected float deacc = 1f;
	
	/* Positionnement */
	protected int positionInitialeX;
	protected int positionInitialeY;
	
	/* La gestion des collisions */
	protected Collision collision;
	protected AABB boundsCollision;
	
	/* Gestion des bombes */
	protected int maxBomb = 4;
	protected int bombposee = 0;
	protected int bombeChoisie = 0;
	
	/* Vies */
	protected int nombreDeVies = 2;
	
			
	/** Constructeur */
	
	public Entity(Sprite sprite, Vector2f pos, int size) {
		super(sprite, pos, size);
		
		/* Animation */
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
		
		/* Positionnement */
		this.caseActuelle = new AABB(pos, size, size);
		this.caseActuelle.setCube(5, 5, ((size/2)-2), ((size/2) + 25));
		this.positionInitialeX = (int) this.getPos().x;
		this.positionInitialeY = (int) this.getPos().y;
		
		/* Collisions */
		collision = new Collision(this);	
		this.boundsCollision = new AABB(pos, size, size);
		this.boundsCollision.setCube(45, 29, 2, 40);
		
	}
	
	/** Méthodes */
	
	public void update(double time) {
		super.update(time);
		animate();
		
		if(fallen) {
			if(nombreDeVies != 0) {
				if(animation.hasPlayedOnce()) {
					resetPosition(); 
					fallen = false;
					nombreDeVies--;
				}
			} else { 
				this.meurt();
			}
		}
		
		
	}
	
	protected abstract void meurt();

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
        
        if(!collision.collision(dx, 0)) { pos.x += dx; }
		if(!collision.collision(0, dy)) { pos.y += dy; }
        
    }
	
	protected void resetPosition() {
    	this.setPosX(positionInitialeX);
    	pos.y = positionInitialeY;
    	setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
    }
	
	protected void changeBombe(int i) {
		if(i == -1) {
			if(this.bombeChoisie == 0) {this.bombeChoisie = 5;} 
			else { this.bombeChoisie--; }
		} else if(i == 1) {
			if(this.bombeChoisie == 5) {this.bombeChoisie = 0;} 
			else { this.bombeChoisie++; }
		}
	}
	
	protected boolean enDanger() {
		if(this.enDangerX() || this.enDangerY()) {
			return true;
		} return false;
	}
	
	protected boolean enDangerX() {
		for(int i = 0; i < PlayState.bombList.size() ; i++) {
			Bomb tempB = PlayState.bombList.get(i);
			
			
			
			if(Math.abs(tempB.getCaseActuelleX() - this.getCaseActuelleX()) < 3) {
				if(tempB.getCaseActuelleY() == this.getCaseActuelleY()) {
					return true;
				}
			}
		} return false;
	}
	
	protected boolean enDangerY() {
		for(int i = 0; i < PlayState.bombList.size() ; i++) {
			Bomb tempB = PlayState.bombList.get(i);
			
			if(Math.abs(tempB.getCaseActuelleY() - this.getCaseActuelleY()) < 3) {
				if(tempB.getCaseActuelleX() == this.getCaseActuelleX()) {
					return true;
				}
			}
		} return false;
	}
	
	protected void poserBombe() {
    	if(bomb) {
    		if(bombposee < maxBomb) {
    			int X = this.getCaseActuelleX();
        		int Y = this.getCaseActuelleY();
        		
        		for(int i = 0; i < PlayState.bombList.size(); i++) {
        			if((PlayState.bombList.get(i).getPos().x / 50) == X && (PlayState.bombList.get(i).getPos().y / 50) == Y) { return; }
        		}
    			
    			switch(this.bombeChoisie) {
	    			case 0: PlayState.bombList.add(new BasicBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 1: PlayState.bombList.add(new HorizontalBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 2: PlayState.bombList.add(new VerticalBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 3: PlayState.bombList.add(new MineBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 4: PlayState.bombList.add(new RcBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 5: PlayState.bombList.add(new PiqBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
				}
				bombposee++;
    		}
    	}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getImage(), (int) (pos.x), (int) (pos.y), size, size + 20, null);
	}
	
	/** Accesseurs */
	
	public float getDx() {return dx;}
	public float getDy() {return dy;}
	public float getMaxSpeed() {return maxSpeed;}
	public float getAcc() {return acc;}
	public float getDeacc() {return deacc;}
	public boolean getFallent() {return fallen;}
	public AABB getBoundsCollision() {return this.boundsCollision;}
	public int getMaxBomb() {return maxBomb;}
	public int getBombposee() {return bombposee;}
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
	public int getPied() {return (int) (this.getPos().y + this.getSize() + 20);}
	public int getDroite() {return (int) (this.getPos().x + this.getSize());}

	/** Mutateurs */
	
	public void setDx(float dx) {this.dx = dx;}
	public void setDy(float dy) {this.dy = dy;}
	public void setMaxSpeed(float f) {maxSpeed = f;}
	public void setAcc(float f) {acc = f;}
	public void setDeacc(float f) {deacc = f;}
	public void setFallen(boolean b) {fallen = b;}
	public void setMaxBomb(int maxBomb) {this.maxBomb = maxBomb;}
	public void setBombposee(int bombposee) {this.bombposee = bombposee;}
}