package game.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.GamePanel;

public class KeyHandler implements KeyListener {	
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	public boolean bomb;
	public boolean menu;
	public boolean enter;
	
	public boolean escape;
	public boolean choixBombeAvant;
	public boolean choixBombeApres;
	public boolean choixHaut;
	public boolean choixBas;
	public boolean choixDroite;
	public boolean choixGauche;
	public boolean choix;
	public boolean tab;
	
	/** Constructeur */
	
	public KeyHandler(GamePanel game) {
		game.addKeyListener(this);
	}
	
	
	/** Méthodes */
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) up = true; 
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
		if(e.getKeyCode() == KeyEvent.VK_SPACE) bomb = true;
		if(e.getKeyCode() == KeyEvent.VK_M) menu = true;
		if(e.getKeyCode() == KeyEvent.VK_ENTER) enter = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
		if(e.getKeyCode() == KeyEvent.VK_SPACE) bomb = false;
		if(e.getKeyCode() == KeyEvent.VK_M) menu = false;
		if(e.getKeyCode() == KeyEvent.VK_ENTER) enter = false;
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) escape = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) choixBas = true;
		if(e.getKeyCode() == KeyEvent.VK_UP) choixHaut = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) choixDroite = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) choixGauche = true;
		if(e.getKeyCode() == KeyEvent.VK_X) choixBombeAvant = true;
		if(e.getKeyCode() == KeyEvent.VK_C) choixBombeApres = true;
		if(e.getKeyCode() == KeyEvent.VK_ENTER) choix = true;
		if(e.getKeyCode() == KeyEvent.VK_TAB) tab = true;
	}
}