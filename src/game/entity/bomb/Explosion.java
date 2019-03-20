package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Entity;
import game.graphics.Animation;
import game.graphics.Sprite;
import game.util.AABB;
import game.util.FireCollision;
import game.util.Vector2f;

public class Explosion {

	/** Les attributs */
	
	
	/* Les collisions des flammes avec les blocks */
	protected FireCollision fireCollision;
	
	/* Animation */
	public Animation animation;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	
	public boolean propagee = false;
	protected AABB bounds;
	
	protected Entity e;
	
	protected int rayonX;
	protected int rayonY;
	
	
	
	/** Le constructeur */
	
	public Explosion(Vector2f pos, int rayonX, int rayonY, Entity e) {
		
		
		this.pos = pos;
		
		bounds = new AABB(this.pos, 50, 50);
		this.bounds.setWidth(50);
		this.bounds.setHeight(50);
		this.bounds.setXOffset(0);
		this.bounds.setYOffset(0);
		
		this.fireCollision = new FireCollision(this);
		
		this.rayonX = rayonX;
		this.rayonY = rayonY;
		
		this.e = e;
		
		animation = new Animation();
	}
	
	public void propagation(int rayonX, int rayonY) {
		
		if(rayonX != 0) {
			/* On test toutes les collisions des flammes allant vers la gauche */
			for(int i = 1; i <= rayonX; i++) {
				
				
				if(fireCollision.collisionIncassable(-i, 0)) {
					// On arrete de bruler
					System.out.println(("collision incassable en g - "+i));
					i = rayonX;
				} else if(fireCollision.collisionCassable(-i, 0)) {
					// On casse et on propage 
					System.out.println(("collision cassable en g - "+i));
				} else {
					// On propage
					System.out.println(("Aucune collisions en g - "+i));
				}
			}
			
			/* On test toutes les collisions des flammes allant vers la droite */
			for(int j = 1; j <= rayonX; j++) {
				
				if(fireCollision.collisionIncassable(j, 0)) {
					// On arrete de bruler
					System.out.println(("collision incassable en d - "+j));
					j = rayonX;
				} else if(fireCollision.collisionCassable(j, 0)) {
					// On casse et on propage 
					System.out.println(("collision incassable en d - "+j));
					
				} else {
					// On propage
					System.out.println(("collision incassable en d - "+j));
				}
			}
		}
		
		if(rayonY != 0) {
			/* On test toutes les collisions des flammes allant vers le haut */
			for(int k = 1; k <= rayonY; k++) {
				
				if(fireCollision.collisionIncassable(0, -k)) {
					// On arrete de bruler
					System.out.println(("collision incassable en h - "+k));
					k = rayonY;
				} else if(fireCollision.collisionCassable(0, -k)) {
					// On casse et on propage 
					System.out.println(("collision incassable en h - "+k));
				} else {
					// On propage
					System.out.println(("collision incassable en h - "+k));
				}
			}
			
			/* On test toutes les collisions des flammes allant vers le bas */
			for(int l = 1; l <= rayonY; l++) {
				
				if(fireCollision.collisionIncassable(0, l)) {
					// On arrete de bruler
					System.out.println(("collision incassable en b - "+l));
					l = rayonY;
				} else if(fireCollision.collisionCassable(0, l)) {
					// On casse et on propage 
					System.out.println(("collision incassable en b - "+l));
				} else {
					// On propage
					System.out.println(("collision incassable en b - "+l));
				}
			}
		}
		
		/* On supprime de la liste des explosions */
		this.propagee = true;
		
		
	}
	
	public AABB getBounds() {
		return bounds;
	}

	public void setAnimation(int i, BufferedImage[] frames, int delay) {
		currentAnimation = i;
		animation.setFrames(frames);
		animation.setDelay(delay);
	}
	
	public void update(double time) {
		animate();
		animation.update();
		propagation(this.rayonX, this.rayonY);
		System.out.println("update");
	}
	
	public void animate() {
		
	}
	
	public void render(Graphics2D g) {
		
		/* On dessine les rectangles des rayons des flammes */
		g.setColor(Color.red);
		for(int i = 1; i <= this.rayonX; i++) {g.drawRect((int) (pos.x - ( i *bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());}
		for(int i = 1; i <= this.rayonX; i++) {g.drawRect((int) (pos.x + ( i *bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());}
		for(int i = 1; i <= this.rayonY; i++) {g.drawRect((int) pos.x, (int) (pos.y - (i * bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());}
		for(int i = 1; i <= this.rayonY; i++) {g.drawRect((int) pos.x, (int) (pos.y + (i * bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());}
	}
}