package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import game.graphics.Sprite;
import game.util.*;


/**
 * 
 * Class du joueur principal
 * 
 * */

public class Player extends Entity {

	/** Constructeur */
    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        
        bounds.setWidth(44);
        bounds.setHeight(40);
        bounds.setXOffset(0);
        bounds.setYOffset(35);
        
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
    
    public void update() {
		super.update();
		
		move();
		if(!bounds.collisionTile(dx, 0)) { pos.x += dx; }
		if(!bounds.collisionTile(0, dy)) { pos.y += dy; }
 	}
	
	
	@Override
	public void render(Graphics2D g) {
		
		/* Le rectangle entourant le joueur pour tester les collisions */
		g.setColor(Color.blue);
		g.drawRect((int) (pos.x + bounds.getXOffset()), (int) (pos.y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
		
		
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}

    public void input(MouseHandler mouse, KeyHandler key) {

        if(mouse.getButton() == 1) {
            // Quand on clique
        }
       
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
    }
}