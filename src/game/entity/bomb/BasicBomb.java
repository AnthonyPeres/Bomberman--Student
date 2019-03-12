package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.graphics.Sprite;
import game.util.Vector2f;


public class BasicBomb extends Bomb {

	/** Constructeur */
	
	public BasicBomb(Sprite sprite, Vector2f pos, int size, Entity entity) {
		super(sprite, pos, size, 0, entity);
		// TODO Auto-generated constructor stub
	
		this.rayonX = 2;
		this.rayonY = 2;
	}
	
	/** Méthodes */
	
	public void update(double time) {
		super.update(time);
	}

	

	@Override
	public void explose() {
		// TODO Auto-generated method stub
/* Gestion du premier bloc a gauche */
		
		if(!fireCollision.collisionIncassable(-1, 0)) {
			if(fireCollision.collisionCassable(-1, 0)) {
				// Collision avec un objet cassant
			} else {
				// Flamme sur un sol
			}
			
			/* Gestion du second bloc en bas */
			if(!fireCollision.collisionIncassable(-2, 0)) {
				if(fireCollision.collisionCassable(-2, 0)) {
					// Collision avec un objet cassant
				} else {
					// Flamme sur un sol
				}
			}
			
		}
		
		/* Gestion du premier bloc a droite */
		
		if(!fireCollision.collisionIncassable(1, 0)) {
			if(fireCollision.collisionCassable(1, 0)) {
				// Collision avec un objet cassant
			} else {
				// Flamme sur un sol
			}
			
			
			/* Gestion du second bloc en bas */
			if(!fireCollision.collisionIncassable(2, 0)) {
				if(fireCollision.collisionCassable(2, 0)) {
					// Collision avec un objet cassant
				} else {
					// Flamme sur un sol
				}
			}
			
			
		}
		
		/* Gestion du premier bloc en haut */
		
		if(!fireCollision.collisionIncassable(0, -1)) {
			if(fireCollision.collisionCassable(0, -1)) {
				// Collision avec un objet cassant
			} else {
				// Flamme sur un sol
			}
			
			/* Gestion du second bloc en bas */
			if(!fireCollision.collisionIncassable(0, -2)) {
				if(fireCollision.collisionCassable(0, -2)) {
					// Collision avec un objet cassant
				} else {
					// Flamme sur un sol
				}
			}
			
			
			
		}
		
		/* Gestion du premier bloc en bas */
		
		if(!fireCollision.collisionIncassable(0, 1)) {
			if(fireCollision.collisionCassable(0, 1)) {
				// Collision avec un objet cassant
			} else {
				// Flamme sur un sol
			}
			
			/* Gestion du second bloc en bas */
			if(!fireCollision.collisionIncassable(0, 1)) {
				if(fireCollision.collisionCassable(0, 1)) {
					// Collision avec un objet cassant
				} else {
					// Flamme sur un sol
				}
			}
			
		}
		
		
		
		
		
		
		this.e.bombList.remove(this);
		this.e.setBombposee(this.e.getBombposee() - 1);
	}
	
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		
		g.drawRect((int) pos.x, (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		
		/* Cases adjacentes */
		g.drawRect((int) (pos.x - (1*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x - (2*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x + (1*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x + (2*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y - (1*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y - (2*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y + (1*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y + (2*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}

}
