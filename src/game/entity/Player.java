package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

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
    
    public void animate() {
		if(up) { 			// Touche haut appuyée 
			if(currentAnimation != UP || animation.getDelay() == -1) {setAnimation(UP, sprite.getSpriteArray(UP), 5);}
		} else if(down) {	// Touche bas appuyée
			if(currentAnimation != DOWN || animation.getDelay() == -1) {setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);}
		} else if(left) {	// Touche gauche appuyée 
			if(currentAnimation != LEFT || animation.getDelay() == -1) {setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);}
		} else if(right) {	// Touche droite appuyée
			if(currentAnimation != RIGHT || animation.getDelay() == -1) {setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);}
		} else if(fallen){ 	// Mort
			if(currentAnimation != FALLEN || animation.getDelay() == -1) {setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 4);}
		} else {setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);}
	}
    
    
    @Override
	public void render(Graphics2D g) {
		
		/* Rectangle qui sert a placer la bombe sur une tuile */
		g.setColor(Color.red);
		g.drawRect((int) (pos.x + boundsBomb.getXOffset()), (int) (pos.y + boundsBomb.getYOffset()), (int) boundsBomb.getWidth(), (int) boundsBomb.getHeight());
			
		/* Le rectangle entourant le joueur pour tester les collisions */
		g.setColor(Color.green);
		g.drawRect((int) (pos.x + boundsCollision.getXOffset()), (int) (pos.y + boundsCollision.getYOffset()), (int) boundsCollision.getWidth(), (int) boundsCollision.getHeight());
		
		/* Affichage de l'animation */
		g.drawImage(animation.getImage(), (int) (pos.x), (int) (pos.y), size, size + 20, null); // + 20 pour qu'il ne soit pas carré
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
    }
    
    public void update() {
		super.update();
		
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

    public void input(MouseHandler mouse, KeyHandler key) {

        if(mouse.getButton() == 1) { /* Clique */ }
       
        if(!fallen) {
        	if(key.up.down) { up = true; } else { up = false; }
	        if(key.down.down) { down = true; } else { down = false; }
	        if(key.left.down) { left = true; } else { left = false; }
	        if(key.right.down) { right = true; } else { right = false; }
	        if(key.bomb.down) { bomb = true; } else { bomb = false; }
        } else {
        	up = false;
        	down = false;
        	left = false;
        	right = false;
        	bomb = false;
        }
    }
}