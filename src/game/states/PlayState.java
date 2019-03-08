package game.states;

import java.awt.Graphics2D;

import game.GamePanel;
import game.entity.Player;
import game.graphics.Font;
import game.graphics.Sprite;
import game.tiles.TileManager;
import game.util.KeyHandler;
import game.util.MouseHandler;
import game.util.Vector2f;

/**
 * 
 * 	Cette class sert a gerer une partie de bomberman
 * 
 * */

public class PlayState extends GameState {

	/** Variables */
	
	private Font font;
	private Player player;
	private TileManager tm;
	
	
	/** Constructeur */
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		tm = new TileManager("tile/MapBomber.xml"); // On charge la map (.xml)
		font = new Font("font/font.png", 10, 10);	// On charge la police 
		player = new Player(new Sprite("entity/spriteBomber1.png", 16, 25), new Vector2f(400,400), 50);	// On charge le sprite du joueur 
	}

	
	/** Méthodes */
	
	public void update() {
		player.update();			
	}
	
	public void input(MouseHandler mouse, KeyHandler key) {
		player.input(mouse, key); 
	}

	public void render(Graphics2D g) {
		tm.render(g);				
		player.render(g);
		Sprite.drawArray(g, font, GamePanel.oldFrameCount + "FPS", new Vector2f(GamePanel.width - 110 , 20), 20, 20, 20, 0);
	}
}