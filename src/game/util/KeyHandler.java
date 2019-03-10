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
	
	
	/** Constructeur */
	
	public KeyHandler(GamePanel game) {
		game.addKeyListener(this);
	}
	
	
	/** Méthodes */
	
	public void releaseAll() {
		for(int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}
	
	public void tick() {
		for(int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}
	
	public void toggle(KeyEvent e, boolean  pressed) {
		if(e.getKeyCode() == KeyEvent.VK_UP) up.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_SPACE) bomb.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_M) menu.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ENTER) enter.toogle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toogle(pressed);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		// Ne rien mettre ici
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		toggle(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		toggle(e, false);
	}
}
