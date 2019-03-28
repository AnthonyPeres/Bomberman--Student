package game.entity;

import game.graphics.Sprite;
import game.states.PlayState;
import game.util.KeyHandler;
import game.util.MouseHandler;
import game.util.Vector2f;


public class Player extends Entity {
	
	/** Constructeur */
    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
    }
    
    /** Méthodes */
    
    public void update(double time) {
		super.update(time);
		
			move();
			poserBombe();
		
			
			
		
 	}

    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton() == 1) {}
        
        if(!fallen) {
        	if(key.up) { up = true; } else { up = false; }
	        if(key.down) { down = true; } else { down = false; }
	        if(key.left) { left = true; } else { left = false; }
	        if(key.right) { right = true; } else { right = false; }
	        if(key.bomb) { bomb = true; } else { bomb = false; }
	        if(key.choixBombeAvant) { changeBombe(-1);    key.choixBombeAvant = false;} else {}
	        if(key.choixBombeApres) { changeBombe(1);    key.choixBombeApres = false;} else {}
	        if(up && down) {up = false; down = false;}
	        if(right && left) {right = false; left = false;}
        } else {
        	up = false;
        	down = false;
        	left = false;
        	right = false;
        	bomb = false;
        }
    }

	@Override
	protected void meurt() {
		// TODO Auto-generated method stub
		PlayState.player = null;
	}
}