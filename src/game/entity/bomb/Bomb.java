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
	
	/* Type */
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
	protected int tempsAvantExplosion = 120;					// A REGLER (pour l'instant : 2 secondes) 
	protected int rayonX;
	protected int rayonY;
	
	/* Entite posant la bombe */
	protected Entity ent;
	
	protected FireCollision fireCollision;
	
	/** Constructeur */
	
	public Bomb(Vector2f origin, int size, int type, int rayonX, int rayonY, Entity e) {
		
		this.sprite = new Sprite("entity/spriteBombe.png",30,30);
		this.pos = origin;
		this.size = size;
		this.type = type;
		this.rayonX = rayonX;
		this.rayonY = rayonY;
		
		bounds = new AABB(origin, size, size);
		this.bounds.setWidth(50);
		this.bounds.setHeight(50);
		this.bounds.setXOffset(0);
		this.bounds.setYOffset(0);
		
		ani = new Animation();
		setAnimation(type, sprite.getSpriteArray(type), 10);
		
		this.ent = e;
		this.fireCollision = new FireCollision(this);
	}
	
	
	/** Méthodes */
	
	public void explose() {															// ON DOIT FAIRE L'EXPLOSION SELON LE TYPE DE LA BOMBE ( SI ELLE EST LARGE ECT ) 
		
		new Fire(new Vector2f((int) this.pos.x, (int) this.pos.y), "centre", 0);
			
		if(rayonX != 0) {
			/* On test toutes les collisions des flammes allant vers la gauche */
			for(int i = 1; i <= rayonX; i++) {
				
				if(fireCollision.collisionIncassable(-i, 0)) {
					// On arrete de bruler
					i = rayonX;
				} else {
					// Si il n'y a pas de collision incassable on brule dans tout les cas 
					new Fire(new Vector2f((int) this.pos.x - (i*50), (int) this.pos.y), "gauche", i);
					
					// Et on casse les briques cassables si il y en a 
					if(fireCollision.collisionCassable(-i, 0)) {
						System.out.println(("collision cassable en g - "+i));
					}
				}
			}
					
			/* On test toutes les collisions des flammes allant vers la droite */
			for(int j = 1; j <= rayonX; j++) {
				if(fireCollision.collisionIncassable(j, 0)) {
					// On arrete de bruler
					j = rayonX;
				} else {
					// Si il n'y a pas de collision incassable on brule dans tout les cas 
					new Fire(new Vector2f((int) this.pos.x + (j*50), (int) this.pos.y), "droite", j);
					
					// Et on casse les briques cassables si il y en a 
					if(fireCollision.collisionCassable(j, 0)) {
						System.out.println(("collision cassable en d - "+j));
					}
				}
			}
		}
		
		if(rayonY != 0) {
			/* On test toutes les collisions des flammes allant vers le haut */
			for(int k = 1; k <= rayonY; k++) {
				if(fireCollision.collisionIncassable(0, -k)) {
					// On arrete de bruler
					k = rayonY;
				} else {
					// Si il n'y a pas de collision incassable on brule dans tout les cas 
					new Fire(new Vector2f((int) this.pos.x, (int) this.pos.y - (k*50)), "haut", k);
					
					// Et on casse les briques cassables si il y en a 
					if(fireCollision.collisionCassable(0, -k)) {
						System.out.println(("collision incassable en h - "+k));
					}
				}
			}
				
			/* On test toutes les collisions des flammes allant vers le bas */
			for(int l = 1; l <= rayonY; l++) {
				if(fireCollision.collisionIncassable(0, l)) {
					// On arrete de bruler
					l = rayonY;
				} else {
					// Si il n'y a pas de collision incassable on brule dans tout les cas 
					new Fire(new Vector2f((int) this.pos.x, (int) this.pos.y + (l*50)), "bas", l);
					
					// Et on casse les briques cassables si il y en a 
					if(fireCollision.collisionCassable(0, l)) {
						System.out.println(("collision incassable en b - "+l));
					}
				}
			}
		} 
		
		/* On enleve la bombe de la liste des bombes du joueur */
		this.ent.bombList.remove(this);
		this.ent.setBombposee(this.ent.getBombposee() - 1);
	}
	
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
        if (--tempsAvantExplosion == 0) { explose(); }
	}
	
	public abstract void render(Graphics2D g);
	
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
	public int getTempsAvantExplosion() {return tempsAvantExplosion;}
	public int getRayonX() { return this.rayonX; }
	public int getRayonY() { return this.rayonY; }

	/** Mutateurs */
	
	public void setSprite(Sprite sprite) {this.sprite = sprite;}
	public void setSize(int i) {size = i;}
	public void setTempsAvantExplosion(int tempsAvantExplosion) {this.tempsAvantExplosion = tempsAvantExplosion;}
}