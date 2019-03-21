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
	
	Image img;
	protected int [] posX_T = {435, 460, 435};
	protected int [] posY_T = { 295, 310, 325 };
	int position = 0;
	
	
	/** Constructeur */
	
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
	
	
	/** Méthodes */
	
	public void gestionChoix(int direction, boolean selection) {
		
		
		if(direction == -1) {
			if(position != 0) {
				position--;
			} else {
				position = 3;
			}
		} else if(direction == 1) {
			if(position != 3) {
				position++;
			} else {
				position = 0;
			}
		}
		
		switch(position) {
		
			case 0: 
				posX_T = new int[]{435, 460, 435};
				posY_T = new int[]{295, 310, 325};
					
				// Nouveau jeu 
				if(selection) {
					if(gsm.isStateActive(GameStateManager.PLAY)) {
						gsm.pop(GameStateManager.PLAY);
						gsm.remove(GameStateManager.MENU);
					} else {
						gsm.addAndpop(GameStateManager.PLAY, GameStateManager.MENU);
						
					}
				}
			break;
				
			case 1:
				posX_T = new int[]{437, 462, 437};
				posY_T = new int[]{365, 380, 395};
				
				// Score
				if(selection) {
					
				}
			break;
				
			case 2:
				posX_T = new int[]{450, 475, 450};
				posY_T = new int[]{435, 450, 465};
				
				// Aide 
				if(selection) {
					
				}
			break;
				
			case 3:
				posX_T = new int[]{405, 430, 405};
				posY_T = new int[]{505, 520, 535};
				
				// Quitter
				if(selection) {
					System.exit(0);
				}
			break;
		}
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		// TODO Auto-generated method stub
		
		if(key.choixHaut) {
			key.choixHaut = false;
			gestionChoix(-1, false);
		}
		
		if(key.choixBas) {
			key.choixBas = false;
			gestionChoix(1, false);
		}
		
		if(key.choix) {
			key.choix = false;
			gestionChoix(0, true);
		}
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
    	g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null); 	// Le background
		g.setColor(Color.red);												// Le triangle
		g.fillPolygon(posX_T, posY_T, 3);
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
	}
}
