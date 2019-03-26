package game.entity.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Entity;
import game.graphics.Animation;
import game.graphics.Sprite;
import game.util.AABB;
import game.util.Collision;
import game.util.Vector2f;

public class Bomb {
	
	/** Variables */
	
	/* Type */
	protected int type;
	protected final int BASIC = 0;
	protected final int HORIZONTAL = 1;
	protected final int VERTICAL = 2;
	protected final int MINE = 3;
	protected final int RC = 4;
	protected final int PIQ = 5;
	
	/* Animation */
	protected Animation ani;
	protected Sprite sprite;
	protected int currentAnimation;
	
	/* Positionnement */
	protected Vector2f pos;
	protected int size;
	
	/* Cubes pour les colisions */
	protected AABB bounds;
	protected Collision collision;
	
	/* Explosion */
	protected int tempsAvantExplosion = 120;
	protected int rayonX;
	protected int rayonY;
	
	/* Entite posant la bombe */
	protected Entity ent;
	
	/** Constructeur */
	
	public Bomb(Vector2f origin, int size, int type, int rayonX, int rayonY, Entity e) {
		
		this.type = type;
		
		this.sprite = new Sprite("entity/spriteBombe.png",30,30);
		ani = new Animation();
		setAnimation(type, sprite.getSpriteArray(type), 10);
		
		
		this.pos = origin;
		this.size = size;
		
		bounds = new AABB(origin, size, size);
		this.bounds.setCube(50, 50, 0, 0);
		this.collision = new Collision(this);
		
		this.rayonX = rayonX;
		this.rayonY = rayonY;
		
		this.ent = e;
		
	}
	
	
	/** Méthodes */
	
	/* A modifier : On doit faire selon le type de la bombe : selon son rayon */
	public void explose() {		
		
		new Fire(new Vector2f((int) this.pos.x, (int) this.pos.y), "centre", 0);
		
		if(rayonX != 0) {
			/* Gauche */
			for(int i = 1; i <= rayonX; i++) {
				if(collision.FireIncassable(-i, 0)) { i = rayonX;} 
				else {
					new Fire(new Vector2f((int) this.pos.x - (i*50), (int) this.pos.y), "gauche", i);
					collision.FireJoueur(-i, 0);
					collision.FireCassable(-i, 0);	
				}
			}
					
			/* Droite */
			for(int j = 1; j <= rayonX; j++) {
				if(collision.FireIncassable(j, 0)) { j = rayonX; } 
				else {
					new Fire(new Vector2f((int) this.pos.x + (j*50), (int) this.pos.y), "droite", j);
					collision.FireJoueur(j, 0);
					collision.FireCassable(j, 0);
				}
			}
		}
		
		if(rayonY != 0) {
			/* Haut */
			for(int k = 1; k <= rayonY; k++) {
				if(collision.FireIncassable(0, -k)) {k = rayonY;} 
				else {
					new Fire(new Vector2f((int) this.pos.x, (int) this.pos.y - (k*50)), "haut", k);
					collision.FireJoueur(0, -k);
					collision.FireCassable(0, -k);
				}
			}
				
			/* Bas */
			for(int l = 1; l <= rayonY; l++) {
				if(collision.FireIncassable(0, l)) {l = rayonY;} 
				else { 
					new Fire(new Vector2f((int) this.pos.x, (int) this.pos.y + (l*50)), "bas", l);
					collision.FireJoueur(0, l);
					collision.FireCassable(0, l);
				}
			}
		} 
		/* On enleve la bombe de la liste des bombes du joueur */
		this.ent.bombList.remove(this);
		this.ent.setBombposee(this.ent.getBombposee() - 1);
	}
	
	public void animate() {
		if(type == BASIC) { if(currentAnimation != BASIC || ani.getDelay() == -1) { setAnimation(BASIC, sprite.getSpriteArray(BASIC), 5); }} 
		else if(type == HORIZONTAL) { if(currentAnimation != HORIZONTAL || ani.getDelay() == -1) { setAnimation(HORIZONTAL, sprite.getSpriteArray(HORIZONTAL), 5);}} 
		else if(type == VERTICAL) { if(currentAnimation != VERTICAL || ani.getDelay() == -1) {setAnimation(VERTICAL, sprite.getSpriteArray(VERTICAL), 5);}}
		else if(type == MINE) {if(currentAnimation != MINE || ani.getDelay() == -1) {setAnimation(MINE, sprite.getSpriteArray(MINE), 5);}} 
		else if(type == RC){if(currentAnimation != RC || ani.getDelay() == -1) {setAnimation(RC, sprite.getSpriteArray(RC), 4);}} 
		else if(type == PIQ){if(currentAnimation != PIQ || ani.getDelay() == -1) {setAnimation(PIQ, sprite.getSpriteArray(PIQ), 4);}}
		else {setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);}
	}

	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		ani.setFrames(frames);
		ani.setDelay(delay);
	}
	
	public void decouleTemps() {
        if (--tempsAvantExplosion == 0) { explose(); }
	}
	
	public void update(double time) {
		animate();
		ani.update();
		decouleTemps();
	}
	
	public void render(Graphics2D g) {
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
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