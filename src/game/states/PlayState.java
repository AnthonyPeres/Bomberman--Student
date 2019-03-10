package game.states;

import java.awt.Graphics2D;

import game.GamePanel;
import game.entity.*;
import game.graphics.*;
import game.tiles.TileManager;
import game.util.*;

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
	private IA[] ia = new IA[3];
	
	/** Constructeur */
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		tm = new TileManager("tile/mapDeveloppement.xml"); // On charge la map (.xml)
		font = new Font("font/font.png", 10, 10);	// On charge la police 
		player = new Player(new Sprite("entity/spriteBomber.png", 16, 25), new Vector2f(400,400), 50);	// On charge le sprite du joueur 
		
		ia[0] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(Vector2f.getWorldX() - 100, 25), 50);
		ia[1] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(50, Vector2f.getWorldY() - 120), 50);
		ia[2] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f((Vector2f.getWorldX()-100),(Vector2f.getWorldY()-120)), 50);
	}

	
	/** Méthodes */
	
	
	public void update() {
		player.update();	
		
		for(int i = 0; i < player.bombList.size(); i++) {if(player.bombList.get(i).getExplose() == false) {player.bombList.get(i).update();}}
		for(int i = 0; i < ia.length; i++) {ia[i].update();}
		
	}
	
	
	public void render(Graphics2D g) {
		
		tm.render(g);				
		Sprite.drawArray(g, font, GamePanel.oldFrameCount + "FPS", new Vector2f(GamePanel.width - 110 , 20), 20, 20, 20, 0);
		
		player.render(g);
		
		for(int i = 0; i < player.bombList.size(); i++) { if(player.bombList.get(i).getExplose() == false) {player.bombList.get(i).render(g);}}
		for(int i = 0; i < ia.length; i++) {ia[i].render(g);}
		
	}
	
	
	public void input(MouseHandler mouse, KeyHandler key) {
		player.input(mouse, key); 
	}
}