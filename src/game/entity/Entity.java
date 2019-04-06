package game.entity;

import java.awt.Graphics2D;

import game.bonus.Bonus;
import game.entity.bomb.BasicBomb;
import game.entity.bomb.HorizontalBomb;
import game.entity.bomb.MineBomb;
import game.entity.bomb.PiqBomb;
import game.entity.bomb.TrackingBomb;
import game.entity.bomb.VerticalBomb;
import game.graphics.Sprite;
import game.states.PlayState;
import game.tiles.TileMap;
import game.util.AABB;
import game.util.Collision;
import game.util.Vector2f;

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
	protected float maxSpeed = 2f;
	protected float acc = 2f;
	protected float deacc = 0.6f;
	
	/* Positionnement initialement (utilisé pour la réanimation) */
	protected int positionInitialeX;
	protected int positionInitialeY;
	
	/* La gestion des collisions */
	protected Collision collision;
	protected AABB boundsCollision;
	
	/* Gestion des bombes */
	protected AABB caseDepotDeBombe;
	protected int maxBomb = 4;
	protected int bombposee = 0;
	protected int bombeChoisie = 0;
	protected boolean MineBomb = false;
	protected boolean TrackingBomb = false;
	protected boolean PiqBomb = false;
	
	/* Vies */
	protected int nombreDeVies = 1;
	protected int dureeDeLinvincibilite = 600; 
	protected boolean invincible = false;
	
	/* Matrice utilisée pour detecter les bonus et les blocs */
	protected int[][] matrice;
	
			
	/** Constructeur */
	
	public Entity(Sprite sprite, Vector2f pos, int size) {
		super(sprite, pos, size);
		
		/* Animation */
		setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
		
		/* Positionnement */
		this.positionInitialeX = (int) this.getPos().x;
		this.positionInitialeY = (int) this.getPos().y;
		
		/* Collisions */
		collision = new Collision(this);	
		this.boundsCollision = new AABB(pos, size, size);
		this.boundsCollision.setCube(45, 29, 2, 40);
		
		/* Bombe */
		this.caseDepotDeBombe = new AABB(pos, size, size);
		this.caseDepotDeBombe.setCube(5, 5, ((size/2)-2), ((size/2) + 25));
		
		/* La case relative au joueur */
		this.SaCase = new AABB(pos, 50, 50);
		this.SaCase.setCube(5, 5, ((size/2)-2), ((size/2) + 25));
		
	}
	
	/** Méthodes */
	
	public void update(double time) {
		super.update(time);
		
		/* On rafraichit et on recupere la matrice */
		this.matrice = PlayState.getMatrice().getMatrice();
		
		
		utiliserBonus();
		
		if(this.invincible) {
			if(dureeDeLinvincibilite == 0) {
				dureeDeLinvincibilite = 1200;
				this.invincible = false;
			}
			dureeDeLinvincibilite--;
		}
		
		animate();
		if(fallen) {
			if(animation.hasPlayedOnce()) {
				if(nombreDeVies > 1) {
					resetPosition(); 
					fallen = false;
					nombreDeVies--;
				} else {this.meurt();}
			}
		}
	}
	
	protected abstract void meurt();

	
	protected void utiliserBonus() {
		int saCaseX = (int)((this.getSaCase().getPos().x) / 50);
		int saCaseY = (int)((this.getSaCase().getPos().y) / 50);
		
		Bonus b = null;
		
		if(matrice[saCaseX][saCaseY] == 7) {
			b = TileMap.tmo_bonus.get(String.valueOf(saCaseX) + "," + String.valueOf(saCaseY));
		} 
		
		if(b != null) {
			b.setEntity(this);
			b.effet();
			b.disparait();
			b = null;
		}
	}
	
	
	
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
			switch(bombeChoisie) {
				case 0:
					if(PiqBomb) { this.bombeChoisie = 5;} 
					else if(TrackingBomb) {this.bombeChoisie = 4;} 
					else if(MineBomb) {this.bombeChoisie = 3;} 
					else {this.bombeChoisie = 2;}
					break;
				
				case 1:
					this.bombeChoisie = 0;
					break;
				
				case 2:
					this.bombeChoisie = 1;
					break;
					
				case 3:
					this.bombeChoisie = 2;
					break;
				
				case 4:
					if(MineBomb) {this.bombeChoisie = 3;} 
					else {this.bombeChoisie = 2;}
					break;

				case 5:
					if(this.TrackingBomb) {this.bombeChoisie = 4;} 
					else if(this.MineBomb) {this.bombeChoisie = 3;} 
					else {this.bombeChoisie = 2;}
					break;
			}
		} else if(i == 1) {
			switch(bombeChoisie) {
				case 0:
					this.bombeChoisie = 1;
					break;
					
				case 1:
					this.bombeChoisie = 2;
					break;
				
				case 2:
					if(MineBomb) {this.bombeChoisie = 3;} 
					else if(TrackingBomb) {this.bombeChoisie += 2;} 
					else if(PiqBomb) {this.bombeChoisie += 3;} 
					else {this.bombeChoisie = 0;}
					break;
					
				case 3:
					if(TrackingBomb) {this.bombeChoisie = 4 ;}
					else if(PiqBomb) {this.bombeChoisie = 5;}
					else {this.bombeChoisie = 0;}
					break;
				
				case 4:
					if(PiqBomb) {this.bombeChoisie = 5 ;}
					else {this.bombeChoisie = 0;}
					break;

				case 5:
					this.bombeChoisie = 0;
					break;
			}
		}
	}
	
	protected void poserBombe() {
    	if(bomb) {
    		if(bombposee < maxBomb) {
    			int X = this.getCaseDepotDeBombeX();
        		int Y = this.getCaseDepotDeBombeY();
        		
        		for(int i = 0; i < PlayState.bombList.size(); i++) {
        			if((PlayState.bombList.get(i).getPos().x / 50) == X && (PlayState.bombList.get(i).getPos().y / 50) == Y) { return; }
        		}
    			
    			switch(this.bombeChoisie) {
	    			case 0: PlayState.bombList.add(new BasicBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 1: PlayState.bombList.add(new HorizontalBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 2: PlayState.bombList.add(new VerticalBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 3: PlayState.bombList.add(new MineBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
	    			case 4: PlayState.bombList.add(new TrackingBomb(new Vector2f((X*50), (Y*50)) ,50, this)); break;
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
	public AABB getCaseDepotDeBombe() {return caseDepotDeBombe;}
	public int getCaseDepotDeBombeX() {return (int) ((this.getCaseDepotDeBombe().getPos().x + this.getCaseDepotDeBombe().getXOffset()) / 50);}
	public int getCaseDepotDeBombeY() {return (int) ((this.getCaseDepotDeBombe().getPos().y + this.getCaseDepotDeBombe().getYOffset()) / 50);}
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
	public int getNombreDeVies() {return nombreDeVies;}
	public boolean getMineBomb() {return MineBomb;}
	public boolean getTrackingBomb() {return TrackingBomb;}
	public boolean getPiqBomb() {return PiqBomb;}
	public boolean getInvincible() {return this.invincible;}
	public int getDureeDeLinvincibilite() {return this.dureeDeLinvincibilite;}


	/** Mutateurs */
	
	public void setDx(float dx) {this.dx = dx;}
	public void setDy(float dy) {this.dy = dy;}
	public void setMaxSpeed(float f) {maxSpeed = f;}
	public void setAcc(float f) {acc = f;}
	public void setDeacc(float f) {deacc = f;}
	public void setFallen(boolean b) {fallen = b;}
	public void setMaxBomb(int maxBomb) {this.maxBomb = maxBomb;}
	public void setBombposee(int bombposee) {this.bombposee = bombposee;}
	public void setBombeChoisie(int bombeChoisie) {this.bombeChoisie = bombeChoisie;}
	public void setNombreDeVies(int nombreDeVies) {this.nombreDeVies = nombreDeVies;}
	public void setMineBomb(boolean mineBomb) {MineBomb = mineBomb;}
	public void setTrackingBomb(boolean trackingBomb) {TrackingBomb = trackingBomb;}
	public void setPiqBomb(boolean piqBomb) {PiqBomb = piqBomb;}
	public void setInvincible(boolean i) {this.invincible = i;}
}