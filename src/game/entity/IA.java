package game.entity;

import game.graphics.Sprite;
import game.util.Vector2f;

public class IA extends Entity {

	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		// TODO Auto-generated constructor stub
	}

	
	public void update(double time) {
		super.update(time);
		if(!fallen) {
			move();
			putABomb();
			
			if(!tileCollision.collisionTile(dx, 0)) { pos.x += dx; }
			if(!tileCollision.collisionTile(0, dy)) { pos.y += dy; }
			
		
		} else {
			if(animation.hasPlayedOnce()) {
				/* Quand l'animation du joueur mort est jouée, on a possibilité 
				 * de remettre le joueur en position initiale si plusieurs vies */
				resetPosition(); 
				fallen = false;
			}
		}
	}


}
