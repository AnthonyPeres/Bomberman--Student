package game.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entity.Player;
import game.entity.IA.IA;
import game.entity.IA.Matrice;
import game.entity.bomb.Bomb;
import game.entity.bomb.Fire;
import game.graphics.Sprite;
import game.tiles.TileManager;
import game.util.KeyHandler;
import game.util.MouseHandler;
import game.util.Vector2f;

public class PlayState extends GameState {

	/** Variables */
	
	private TileManager tm;
	public static Player player;
	public static IA[] ia = new IA[3];
	public static ArrayList<Bomb> bombList = new ArrayList<Bomb>();
	public static ArrayList<Fire> listFlammes = new ArrayList<Fire>();
	public static Matrice matrice = new Matrice();
	 
	
	
	/** Constructeur */
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		tm = new TileManager("tile/mapDeveloppement.xml"); // On charge la map (.xml)
		player = new Player(new Sprite("entity/spriteBomber.png", 16, 25), new Vector2f(50,30), 50);	// On charge le sprite du joueur 
		ia[0] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(Vector2f.getWorldX() - 100, 30), 50);
		ia[1] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(50, Vector2f.getWorldY() - 120), 50);
		ia[2] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f((Vector2f.getWorldX()-100),(Vector2f.getWorldY()-120)), 50);
	}

	
	/** Méthodes */
	
	@Override
	public void render(Graphics2D g) {	
		tm.render(g);				
		if(player != null) {Sprite.drawArray(g, "Bombe "+player.getBombeChoisie(), new Vector2f(50 ,15), 20, 20, 20);}
		if(player != null) {player.render(g);}
		for(int i = 0; i < ia.length; i++) {if(ia[i] != null) {ia[i].render(g);}}
		for(int i = 0; i < bombList.size(); i++) { bombList.get(i).render(g);}
		for(int i = 0; i < listFlammes.size(); i++) {listFlammes.get(i).render(g);}
	}
	
	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		if(player != null) {player.input(mouse, key); }
		
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
		if(player != null) {player.update(time);}
		for(int i = 0; i < ia.length; i++) {if(ia[i] != null) {ia[i].update(time);}}
		for(int i = 0; i < bombList.size(); i++) {bombList.get(i).update(time);}
		for(int i = 0; i < listFlammes.size(); i++) {listFlammes.get(i).update(time);}
		
		matrice.update(time);
	}


	/** Méthodes */	
	public static Matrice getMatrice() {return matrice;}
	public static Player getPlayer() {return player;}
	public static IA getIa(int i) {return ia[i];}

}