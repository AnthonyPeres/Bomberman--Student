package game.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import game.GamePanel;

/**
 * 
 * 	Class définissant le clavier, une classe key est crée 
 * 
 * */

public class KeyHandler implements KeyListener {	
	
	/** Class "key" */
	
	public class Key {
		
		/** Variables */
		
		public int presses, absorbs;
		public boolean down, clicked;
		
		/** Constructeur */
		
		public Key() {
			keys.add(this);
		}
		
		/** Méthodes */
		
		public void toogle(boolean pressed) {
			if(pressed != down) {
				down = pressed;
			}
			if(pressed) {
				presses++;
			}
		}
		
		public void tick() {
			if(absorbs < presses) {
				absorbs++;
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}
	
	/** Variables */
	
	public static List<Key> keys = new ArrayList<Key>();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key bomb = new Key();
	public Key menu = new Key();
	public Key enter = new Key();
	public Key escape = new Key();
	public Key choixBombe = new Key();
	
	
	/** Constructeur */
	
	public KeyHandler(GamePanel game) {
		game.addKeyListener(this);
	}
	
	
	/** Méthodes */
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_C) { System.out.println("ok");}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) up.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_SPACE) bomb.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_M) menu.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_ENTER) enter.toogle(true);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toogle(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) up.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_SPACE) bomb.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_M) menu.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_ENTER) enter.toogle(false);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toogle(false);
	}
}
