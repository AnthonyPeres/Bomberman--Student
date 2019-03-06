package game.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.GamePanel;
import game.util.*;

/**
 * 
 * 	Class gerant la transition entre les différents états du jeu.
 * 
 * */
public class GameStateManager {

	/** Variables */
	
	private ArrayList<GameState> states; // Liste d'état du jeu 
	
	public static final int PLAY = 0; 
	public static final int MENU = 1; 
	public static final int PAUSE = 2; 
	public static final int GAMEOVER = 3; 
	
	public static Vector2f map;
	
	
	/** Constructeur */
	
	public GameStateManager() {
		
		map = new Vector2f(GamePanel.width, GamePanel.height);
		
		Vector2f.setWorldVar(map.x, map.y);
		
		states = new ArrayList<GameState>();
		
		states.add(new PlayState(this)); // Ici on ajoute le statut de jeu (PlayState) dans le GSM
	}
	
	
	/** Méthodes */
	
	/* Fonction ajoutant les menus */
	public void add(int state) {
		if (state == PLAY){states.add(new PlayState(this));} 			// On ajoute un jeu
		if (state == MENU){states.add(new MenuState(this));}			// On ajoute un menu
		if (state == PAUSE){states.add(new PauseState(this));}			// On ajoute une pause
		if (state == GAMEOVER){states.add(new GameOverState(this));}	// On ajoute une fin de jeu 
	}
	
	public void addAndpop(int state) { states.remove(0);  add(state); }
	public void pop(int state) { states.remove(state); }
	
	
	/** Les 3 prochaines fonctions sont abstraites dans la classe GameState 
	 * 	Elles sont différentes pour chaque classes héritant de GameState.
	 * */
	
	/* Appel la fonction update de chaque état */
	public void update() {
		Vector2f.setWorldVar(map.x, map.y);
		for(int i = 0; i < states.size(); i++) { states.get(i).update(); }
	}
	
	/* Appel la fonction input de chaque état avec la souris et le clavier */
	public void input(MouseHandler mouse, KeyHandler key) {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).input(mouse, key);
		}	
	}
	
	/* Appel la fonction render de chaque état */
	public void render(Graphics2D g) {
		for(int i = 0; i < states.size(); i++) {
			states.get(i).render(g);
		}
	}
}