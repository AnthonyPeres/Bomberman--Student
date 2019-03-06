package game.states;

import java.awt.Graphics2D;

import game.util.*;

/**
 * 	class état du jeu
 */
public abstract class GameState {

	/** Variables */
	private GameStateManager gsm;
	
	/** Constructeur */
	public GameState(GameStateManager gsm) {
		this.setGsm(gsm);
	}
	
	/** Méthodes */
	public abstract void update();
	public abstract void input(MouseHandler mouse, KeyHandler key);
	public abstract void render(Graphics2D g);

	/** Accesseurs */
	public GameStateManager getGsm() {return gsm;}

	/** Mutateurs */
	public void setGsm(GameStateManager gsm) {this.gsm = gsm;}
}