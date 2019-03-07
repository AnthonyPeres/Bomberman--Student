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

public class PlayState extends GameState {

	/* La police */
	private Font font;
	
	/* Le joueur */
	private Player player;
	
	/* Le Gestionnaire de tuiles */
	private TileManager tm;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		tm = new TileManager("tile/MapBomber.xml"); // On charge la map (.xml)
		font = new Font("font/font.png", 10, 10);	// On charge la police 
		player = new Player(new Sprite("entity/BomberSprite.png"), new Vector2f(400,400), 75);	// On charge le sprite du joueur 
	}

	/* Ces méthodes sont appelées lorsque nous sommes sur le jeu */
	
	/* On update le joueur */
	public void update() {
		player.update();			
	}
	
	/* On ajoute le clavier et la souris en entrée pour le joueur */
	public void input(MouseHandler mouse, KeyHandler key) {
		player.input(mouse, key); 
	}

	/* On affiche le gestionnaire de tuiles, le joueur et le nombre d'FPS */
	public void render(Graphics2D g) {
		tm.render(g);				
		player.render(g);
		Sprite.drawArray(g, font, GamePanel.oldFrameCount + "FPS", new Vector2f(GamePanel.width - 110 , 20), 20, 20, 20, 0);
	}
}