package game.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class ScoreState extends GameState {


	/** Variables */
	
	/* Image d'aide */
	
	Image img;
	
	
	/** Constructeur */
	
	public ScoreState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		
		/* Lecture de l'image de fond */
		try {
			img = ImageIO.read(new File("res/background/scores.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/** Méthodes */
	
	public void RetourMenu(boolean selection) {
		if(selection) {
			if(gsm.isStateActive(GameStateManager.MENU)) {
				gsm.pop(GameStateManager.MENU);
				gsm.remove(GameStateManager.SCORE);
			} else {
				gsm.addAndpop(GameStateManager.MENU, GameStateManager.SCORE);
				
			}
		}
	}
	
	public void Quitter(boolean quitter) {
		System.exit(0);
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		// TODO Auto-generated method stub`
		if(key.choix) {
			key.choix = false;
			RetourMenu(true);
		}
		
		if(key.escape) {
			key.escape = true;
			Quitter(true);
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
    	g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null); 	// Le background
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
	}
}
