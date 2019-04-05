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
	public static Matrice matrice;
	private static boolean pause = false;
	
	private String imageUtilisee = "img1";
    private String fichier = "tile/mapDev.xml";
	private static int difficulte = -1;
	private static int numeroJoueur = 0;
    private static int numeroStyle = 0;
    private static int numeroMap = 0;
	public static int score;
	public static int compteur;
	private String nomSprite;
	
	
	/** Constructeur */
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		int j = getNumeroJoueur();
		int s = getNumeroStyle();
		int m = getNumeroMap();
		
		switch(j) {
			case 0: this.nomSprite = "spriteBomberBleu"; break;
			case 1: this.nomSprite = "spriteBomber"; break;
			case 2: this.nomSprite = "spriteBomberVert"; break;
			case 3: this.nomSprite = "spriteBomberRouge"; break;
			case 4: this.nomSprite = "spriteBomberRose"; break;
			case 5: this.nomSprite = "spriteBomberOr"; break;
		}
		
		player = new Player(new Sprite("entity/"+nomSprite+".png", 16, 25), new Vector2f(50,30), 50);
		
		switch(s) {
			case 0: this.imageUtilisee = "img1"; break;
			case 1:	this.imageUtilisee = "img2"; break;
		}
		
		switch(m) {
			case 0: this.fichier = "tile/map1.xml"; break;
			case 1:	this.fichier = "tile/map2.xml"; break;
			case 2:	this.fichier = "tile/mapDev.xml"; break;
		}
		
		tm = new TileManager(this.imageUtilisee, this.fichier);
		
		ia[0] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(Vector2f.getWorldX() - 100, 30), 50);
		ia[1] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f(50, Vector2f.getWorldY() - 120), 50);
		ia[2] = new IA(new Sprite("entity/spriteLink.png", 16,25), new Vector2f((Vector2f.getWorldX()-100),(Vector2f.getWorldY()-120)), 50);
		matrice = new Matrice();
		
		score = 0;
		compteur = 0;
	}
	
	/** Méthodes */
	
	@Override
	public void render(Graphics2D g) {	
		if(!pause) {
			tm.render(g);				
			
			if(player != null) {
				if(player.getInvincible()) {
					Sprite.drawArray(g, "Invincible "+ player.getDureeDeLinvincibilite()/60+" / "+player.getMaxBomb()+" "+player.getBombeChoisie(), new Vector2f(10 ,15), 20, 20, 20);
				} else {
					Sprite.drawArray(g, "Vies : "+player.getNombreDeVies()+" / "+player.getBombeChoisie()+" : "+player.getMaxBomb(), new Vector2f(10 ,15), 20, 20, 20);
				}
			}
			Sprite.drawArray(g, "Score : "+ score, new Vector2f(Vector2f.getWorldX()-240 ,15), 20, 20, 20);
				
			if(difficulte != -1) {
				String niveau = "";
				
				if(difficulte == 0) { niveau = "Facile"; }
				else if(difficulte == 1) { niveau = "Intermediaire"; }
				else if(difficulte == 2) { niveau = "Difficile"; }
				Sprite.drawArray(g, "Difficulte : "+niveau, new Vector2f(200 ,Vector2f.getWorldY()-30), 20, 20, 20);
			}
			
			if(player != null) {player.render(g);}
			for(int i = 0; i < ia.length; i++) {if(ia[i] != null) {ia[i].render(g);}}
			for(int i = 0; i < bombList.size(); i++) { bombList.get(i).render(g);}
			for(int i = 0; i < listFlammes.size(); i++) {listFlammes.get(i).render(g);}
		}
	}
	
	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		if(!pause) {
			if(player != null) {player.input(mouse, key); }
			
			if (key.escape) {
				key.escape = false;
				
				key.choix = false;
				key.choixHaut = false;
				
				pause = true;
				if(gsm.isStateActive(GameStateManager.PAUSE)) {
					gsm.pop(GameStateManager.PAUSE);
				} else {
					gsm.addAndpop(GameStateManager.PAUSE);
				}
			}	
		}
	}

	@Override
	public void update(double time) {
		if(!pause) {			
			if(player != null) {player.update(time);}
			for(int i = 0; i < ia.length; i++) {if(ia[i] != null) {ia[i].update(time);}}
			for(int i = 0; i < bombList.size(); i++) {bombList.get(i).update(time);}
			for(int i = 0; i < listFlammes.size(); i++) {listFlammes.get(i).update(time);}
			
			matrice.update(time);
			
			/* Score */
			compteur++;
			if(compteur % 60 == 0) {
				score += 1;
			}
			
			if(player == null) {
				gsm.remove(GameStateManager.PLAY);
				gsm.addAndpop(GameStateManager.GAMEOVER, GameStateManager.PLAY);
			}
			
			if(ia[0] == null && ia[1] == null && ia[2] == null) {
				gsm.remove(GameStateManager.PLAY);
				gsm.addAndpop(GameStateManager.VICTORY, GameStateManager.PLAY);
			}
		}
	}


	/** Accesseurs */
	
	public static Matrice getMatrice() {return matrice;}
	public static Player getPlayer() {return player;}
	public static IA getIa(int i) {return ia[i];}
	public static boolean getPause() {return pause;}	
	public static int getNumeroJoueur() {return numeroJoueur;}
	public static int getNumeroStyle() {return numeroStyle;}
	public static int getNumeroMap() {return numeroMap;}
	
	/** Mutateurs */
	
	public static void setNiveauDifficulte(int position) {difficulte = position;}
	public static void setPause(boolean b) {pause = b;}
	public static void setNumeroJoueur(int j) {numeroJoueur =j;}
	public static void setNumeroStyle(int s) {numeroStyle = s;}
	public static void setNumeroMap(int m) {numeroMap = m;}
}