package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import game.graphics.Sprite;
import game.util.*;


/**
 * 
 * Class du joueur principal
 * 
 * 
 * POUR LE TUER ON PASSE FALLEN A TRUE
 * 
 * */

public class Player extends Entity {

	/** Constructeur */
    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        
        /* Rectangle des collisions */
        bounds.setWidth(47);
        bounds.setHeight(29);
        bounds.setXOffset(0);
        bounds.setYOffset(40);
        
        /* Rectangle ou on pose la bombe */
        bombBounds.setWidth(5);
        bombBounds.setHeight(5);
        bombBounds.setXOffset((size/2)-2);
        bombBounds.setYOffset(size/2 + 25);
        
        /* Acceleration et vitesse max */
        acc = 2.5f;
        maxSpeed = 3.5f;
    }

    /** Méthodes */
    
    private void move() {
        if(up) {
            dy -= acc;
            if(dy < -maxSpeed) {dy = -maxSpeed;}
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {dy = 0;}
            }
        }
        if(down) {
            dy += acc;
            if(dy > maxSpeed) {dy = maxSpeed;}
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {dy = 0;}
            }
        }
        if(left) {
            dx -= acc;
            if(dx < -maxSpeed) {dx = -maxSpeed;}
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {dx = 0;}
            }
        }
        if(right) {
            dx += acc;
            if(dx > maxSpeed) {dx = maxSpeed;}
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {dx = 0;}
            }
        }
    }
    
    public void putABomb() {
    	if(bomb) {		
    			int nombreDeBriqueEnX = (int) (pos.x + bombBounds.getWidth() + bombBounds.getXOffset()) / 50;
    			int nombreDeBriqueEnY = (int) (pos.y + bombBounds.getHeight() + bombBounds.getYOffset()) / 50;
    			System.out.println(nombreDeBriqueEnX+"   ----   "+nombreDeBriqueEnY);
    	}
    }
    
    
    public void update() {
		super.update();
		if(!fallen) {
			move();
			putABomb();
			
			if(!tc.collisionTile(dx, 0)) { pos.x += dx; }
			if(!tc.collisionTile(0, dy)) { pos.y += dy; }
		} else {
			if(ani.hasPlayedOnce()) {
				/* Si l'animation du joueur mort est jouée, on remet la position du joueur et on passe fallen a false, 
				 * on peut modifier cette partie pour que le joueur ai plusieurs vies. */
				
				resetPosition();
				fallen = false;
			}
		}
 	}
    
    private void resetPosition() {
    	System.out.println("reset position");
    	pos.x = 50;
    	pos.y = 50;
    	setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
    }
	
	
	@Override
	public void render(Graphics2D g) {
		
		/* Ici je mets un rectangle de couleur rouge au niveau des pieds du joueur, 
		 * ce rectangle est petit car il sert a renvoyer la case ou est le joueur d'une maniere 
		 * la plus précise possible. */
		g.setColor(Color.red);
		g.drawRect((int) (pos.x + bombBounds.getXOffset()), (int) (pos.y + bombBounds.getYOffset()), (int) bombBounds.getWidth(), (int) bombBounds.getHeight());
			
		/* Le rectangle entourant le joueur pour tester les collisions */
		g.setColor(Color.green);
		g.drawRect((int) (pos.x + bounds.getXOffset()), (int) (pos.y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
		
		// J'ai ajouté + 20 pour qu'il soit plus grand en hauteur
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size + 20, null);
	}

    public void input(MouseHandler mouse, KeyHandler key) {

        if(mouse.getButton() == 1) {
            // Quand on clique
        }
       
        if(!fallen) {
        	if(key.up.down) {
            up = true;
	        } else {
	            up = false;
	        }
	        if(key.down.down) {
	            down = true;
	        } else {
	            down = false;
	        }
	        if(key.left.down) {
	            left = true;
	        } else {
	            left = false;
	        }
	        if(key.right.down) {
	            right = true;
	        } else {
	            right = false;
	        }
	        if(key.bomb.down) {
	        	bomb = true;
	        } else {
	        	bomb = false;
	        }
        } else {
        	up = false;
        	down = false;
        	left = false;
        	right = false;
        	bomb = false;
        }
    }
}