package game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class MenuState extends GameState {


	/** Variables */
	
	/* Position du triangle selon la sélection */
	private int [] posx_play = { 440, 462, 440 };
	private int [] posy_play = { 286, 296, 309 };
	private int [] posx_score = { 440, 462, 440 };
	private int [] posy_score = { 352, 362, 375 };
	private int [] posx_help = { 470, 492, 470 };
	private int [] posy_help = { 420, 433, 446 };
	private int [] posx_quit = { 412, 434, 412 };
	private int [] posy_quit = { 490, 503, 516 };
	
	int position = 0;
	Image img;
	
	
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		
		/* Lecture de l'image de fond */
		try {
			img = ImageIO.read(new File("res/background/Menu_Vide.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		// TODO Auto-generated method stub
		
		
		if(key.choixHaut) {
			key.choixHaut = false;
		
			if(position != 0) {
				position --;
			}
		
		
		}
		
		if(key.choixBas) {
			key.choixBas = false;
			
			if(position != 3) {
				position ++;
			}
		}
		
		
		if(key.choix) {
			key.choix = false;
			
			switch(position) {
			
				case 0: 		// Nouveau jeu
					if(gsm.getState(GameStateManager.PLAY)) {
						gsm.addAndpop(GameStateManager.PLAY);
						
					} else {
						gsm.addAndpop(GameStateManager.PLAY);
					}
					break;
				
				case 1:			// Score
					break;
					
				case 2:			// Aide 
					break;
					
				case 3:			// Quitter
					System.exit(0);
					break;
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
		
		
		/* Dessin de l'image de fond du menu */
    	g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null);
		
		/* Positionnement du triangle selon la sélection */
		g.setColor(Color.red);
		if(position == 0) g.fillPolygon(posx_play, posy_play, 3);
		if(position == 1) g.fillPolygon(posx_score, posy_score, 3);
		if(position == 2) g.fillPolygon(posx_help, posy_help, 3);
		if(position == 3) g.fillPolygon(posx_quit, posy_quit, 3);
		
	    }

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
		
	}
}
