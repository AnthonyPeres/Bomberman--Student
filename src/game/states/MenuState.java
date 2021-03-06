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
	
	Image img;
	protected int [] posX_T = {435, 460, 435};
	protected int [] posY_T = { 295, 310, 325 };
	int position = 0;
	
	
	/** Constructeur */
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		gsm.remove(GameStateManager.PLAY);
		try {img = ImageIO.read(new File("res/background/Menu_Vide.png"));} 
		catch (IOException e) {e.printStackTrace();}
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
					
				if(selection) {
					if(gsm.isStateActive(GameStateManager.DIFFICULTE)) {
						gsm.pop(GameStateManager.DIFFICULTE);
						gsm.remove(GameStateManager.MENU);
					} else {
						gsm.addAndpop(GameStateManager.DIFFICULTE, GameStateManager.MENU);
					}
				}
			break;
				
			case 1:
				posX_T = new int[]{437, 462, 437};
				posY_T = new int[]{365, 380, 395};
				
				if(selection) {
					if(gsm.isStateActive(GameStateManager.SCORE)) {
						gsm.pop(GameStateManager.SCORE);
						gsm.remove(GameStateManager.MENU);
					} else {
						gsm.addAndpop(GameStateManager.SCORE, GameStateManager.MENU);
						
					}
				}
			break;
				
			case 2:
				posX_T = new int[]{450, 475, 450};
				posY_T = new int[]{435, 450, 465};
				
				if(selection) {
					AideState.setArrivee("Menu");
					if(gsm.isStateActive(GameStateManager.AIDE)) {
						gsm.pop(GameStateManager.AIDE);
						gsm.remove(GameStateManager.MENU);
					} else {
						gsm.addAndpop(GameStateManager.AIDE, GameStateManager.MENU);
						
					}
				}
			break;
				
			case 3:
				posX_T = new int[]{405, 430, 405};
				posY_T = new int[]{505, 520, 535};
				
				if(selection) {System.exit(0);}
			break;
		}
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
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
    	g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null); 	
		g.setColor(Color.red);												
		g.fillPolygon(posX_T, posY_T, 3);
	}

	@Override
	public void update(double time) {}
}
