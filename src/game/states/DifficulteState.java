package game.states;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

import game.GamePanel;
import game.util.*;

public class DifficulteState extends GameState {

	/** Variables */
	
	Image img;
	protected int [] posX_T = {200, 240, 200};
	protected int [] posY_T = {200, 225, 250};
	int position = 0;
	
	/** Constructeur */
	
	public DifficulteState(GameStateManager gsm) {
		super(gsm);
		try { img = ImageIO.read(new File("res/background/difficulte.png"));} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	/** Méthodes */

	public void gestionChoix(int direction, boolean selection) {
		if(direction == -1) {
			if(position != 0) {position--;} 
			else {position = 2;}
		} else if(direction == 1) {
			if(position != 2) {position++;} 
			else {position = 0;}
		}
		
		if(selection) {
			PlayState.setNiveauDifficulte(position);
			if(gsm.isStateActive(GameStateManager.CHOICE)) {
				gsm.pop(GameStateManager.CHOICE);
				gsm.remove(GameStateManager.DIFFICULTE);
			} else {gsm.addAndpop(GameStateManager.CHOICE, GameStateManager.DIFFICULTE);}
		}
		
		switch(position) {
			case 0: 
				posX_T = new int[]{200, 240, 200};
				posY_T = new int[]{200, 225, 250};
			break;
				
			case 1:
				posX_T = new int[]{200, 240, 200};
				posY_T = new int[]{278, 303, 328};
			break;
				
			case 2:
				posX_T = new int[]{200, 240, 200};
				posY_T = new int[]{350, 375, 400};
			break;
		}
	}

	@Override
	public void update(double time) {}

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
		if(this.position == 0) {g.setColor(Color.green);} 
		else if(this.position == 1) {g.setColor(Color.yellow);} 
		else if(this.position == 2) {g.setColor(Color.red);}
		g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null); 	// Le background
		g.fillPolygon(posX_T, posY_T, 3);
	}
}