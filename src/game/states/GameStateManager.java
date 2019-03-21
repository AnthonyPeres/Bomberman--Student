package game.states;

import java.awt.Graphics2D;

import game.GamePanel;
import game.graphics.Font;
import game.graphics.Sprite;
import game.util.KeyHandler;
import game.util.MouseHandler;
import game.util.Vector2f;

/**
 * 
 * 	Class gerant la transition entre les différents états du jeu.
 * 
 * */
public class GameStateManager {

	/** Variables */
	
	private GameState states[];
	
	public static final int MENU = 0; 
	public static final int PLAY = 1; 	
	public static final int PAUSE = 2; 
	public static final int GAMEOVER = 3; 
	public static final int SCORE = 4; 
	public static final int AIDE = 5; 
	
	private static Vector2f map;
	
	public static Font font;
	
	
	/** Constructeur */
	
	public GameStateManager() {
		
		map = (new Vector2f(GamePanel.width, GamePanel.height));
		
		Vector2f.setWorldVar(map.x, map.y);
		
		font = new Font("font/font.png", 10,10);
		
		Sprite.currentFont = font;
		
		states = new GameState[6];
		
		states[MENU] = new MenuState(this);
		//states[PLAY] = new PlayState(this);
	}
	
	
	/** Méthodes */
	
	
	public boolean isStateActive(int state) { return states[state] != null; }
	
	public void remove(int state) { this.states[state] = null; }
	
	
	public GameState getState(int state) { return states[state]; }
	
	
	
	
	
	public void pop(int state) { states[state] = null; }
	
	
	/* Fonction ajoutant les menus */
	public void add(int state) {
		if(states[state] != null) 
			return;
			
		if (state == PLAY){states[PLAY] = (new PlayState(this));} 			// On ajoute un jeu
		if (state == MENU){states[MENU] = (new MenuState(this));}			// On ajoute un menu
		if (state == PAUSE){states[PAUSE] = (new PauseState(this));}			// On ajoute une pause
		if (state == GAMEOVER){states[GAMEOVER] = (new GameOverState(this));}	// On ajoute une fin de jeu 
		//if (state == SCORE){states[SCORE] = (new GameOverState(this));}	// On ajoute une fin de jeu 
		//if (state == AIDE){states[AIDE] = (new GameOverState(this));}	// On ajoute une fin de jeu 
	}
	
	
	
	
	
	public void addAndpop(int state) { addAndpop(state, 0); }

    public void addAndpop(int state, int remove) {
        pop(state);
        add(state);
        remove(remove);
    }
	
	
	
	/** Les 3 prochaines fonctions sont abstraites dans la classe GameState 
	 * 	Elles sont différentes pour chaque classes héritant de GameState.
	 * */
	
	/* Appel la fonction update de chaque état */
	public void update(double time) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].update(time);
            }
        }
    }
	
	/* Appel la fonction input de chaque état avec la souris et le clavier */
	public void input(MouseHandler mouse, KeyHandler key) {
        
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].input(mouse, key);
            }
        }        
    }

    
	/* Appel la fonction render de chaque état */
	public void render(Graphics2D g) {
		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				states[i].render(g);
			}
		}
	}
}