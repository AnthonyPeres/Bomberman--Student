package game.entity;

import game.graphics.Sprite;
import game.util.KeyHandler;
import game.util.MouseHandler;
import game.util.Vector2f;

/**
 * 
 * Class du joueur principal
 * 
 * */
public class Player extends Entity {
	
	/** Constructeur */
    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
    }
    
    /** Méthodes */
    
    public void update(double time) {
		super.update(time);
		
		if(!fallen) {
			move();
			putABomb();
			
			if(!collision.collisionBlock(dx, 0)) { pos.x += dx; }
			if(!collision.collisionBlock(0, dy)) { pos.y += dy; }
		
		} else {
			if(animation.hasPlayedOnce()) {
				/* Quand l'animation du joueur mort est jouée, on a possibilité 
				 * de remettre le joueur en position initiale si plusieurs vies */
				resetPosition(); 
				fallen = false;
			}
		}
 	}

    public void input(MouseHandler mouse, KeyHandler key) {

        if(mouse.getButton() == 1) {}
        
        if(!fallen) {
        	if(key.up) { up = true; } else { up = false; }
	        if(key.down) { down = true; } else { down = false; }
	        if(key.left) { left = true; } else { left = false; }
	        if(key.right) { right = true; } else { right = false; }
	        if(key.bomb) { bomb = true; } else { bomb = false; }
	        if(key.choixBombe) { changeBombe();    key.choixBombe = false;} else {}
	        if(up && down) {up = false; down = false;}
	        if(right && left) {right = false; left = false;}
        } else {
        	up = false;
        	down = false;
        	left = false;
        	right = false;
        	bomb = false;
        	choixBombe = false;
        }
    }
}