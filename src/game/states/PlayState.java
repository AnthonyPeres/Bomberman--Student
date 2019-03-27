package game.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.GamePanel;
import game.entity.Affichable;
import game.entity.IA;
import game.entity.Player;
import game.entity.bomb.Bomb;
import game.entity.bomb.Fire;
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
	
	public static Player player;
	private TileManager tm;
	public static IA[] ia = new IA[3];
	
	public static ArrayList<Fire> listFlammes = new ArrayList<Fire>();
	public static ArrayList<Bomb> bombList = new ArrayList<Bomb>();
	
	public static ArrayList<Affichable> affichable = new ArrayList<Affichable>();
	
	/** Constructeur */
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		tm = new TileManager("tile/mapBomber.xml"); // On charge la map (.xml)
		player = new Player(new Sprite("entity/spriteBomber.png", 16, 25), new Vector2f(55,25), 50);	// On charge le sprite du joueur 
		ia[0] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(Vector2f.getWorldX() - 100, 28), 50);
		ia[1] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(50, Vector2f.getWorldY() - 120), 50);
		ia[2] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f((Vector2f.getWorldX()-100),(Vector2f.getWorldY()-120)), 50);
		
		
		
	}

	
	/** Méthodes */
	
	@Override
	public void render(Graphics2D g) {	
		tm.render(g);				
		Sprite.drawArray(g, GamePanel.oldFrameCount + "FPS", new Vector2f(GamePanel.width - 110 ,15), 20, 20, 20);
		Sprite.drawArray(g, "Bombe "+player.getBombeChoisie(), new Vector2f(50 ,15), 20, 20, 20);
		player.render(g);
		
		for(int i = 0; i < bombList.size(); i++) { bombList.get(i).render(g);}
		for(int i = 0; i < listFlammes.size(); i++) {listFlammes.get(i).render(g);}
		for(int i = 0; i < ia.length; i++) {ia[i].render(g);}
	}
	
	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		player.input(mouse, key); 
		
		/* On met le jeu sur pause */
		if (key.escape) {
			if(gsm.isStateActive(GameStateManager.PAUSE)) {
				gsm.pop(GameStateManager.PAUSE);
			} else {
				gsm.add(GameStateManager.PAUSE);
			}
		}	
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		player.update(time);	
		for(int i = 0; i < ia.length; i++) {ia[i].update(time);}
		for(int i = 0; i < bombList.size(); i++) {bombList.get(i).update(time);}
		for(int i = 0; i < listFlammes.size(); i++) {listFlammes.get(i).update(time);}
		for(int i = 0; i < ia.length; i++) {ia[i].update(time);}		
	}


	public static Player getPlayer() {
		return player;
	}


	public static IA getIa(int i) {
		return ia[i];
	}

}