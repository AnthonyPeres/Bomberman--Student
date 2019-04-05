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

public class ChoiceState extends GameState {


	/** Variables */
	
	Image img;
	protected int [] posX_T = {110, 130, 150};
	protected int [] posY_T = {80, 110, 80};
	int position = 0;
	
	private int joueur;
	private int style;
	private int map;
	
	/** Constructeur */
	
	public ChoiceState(GameStateManager gsm) {
		super(gsm);
		
		this.joueur = 0;
		this.style = 0;
		this.map = 0;
		
		try {
			img = ImageIO.read(new File("res/background/choice.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** Méthodes */
	
	public void gestionChoix(int direction, boolean selection) {
		
		if(direction == -1) {
			if(position != 0) {
				position--;
			} else {
				position = 2;
			}
		} else if(direction == 1) {
			if(position != 2) {
				position++;
			} else {
				position = 0;
			}
		}
		
		switch(position) {
			case 0: 
				if(direction == -2) {
					if(joueur != 0) {
						joueur--;
					} else {
						joueur = 5;
					}
				} else if(direction == 2) {
					if(joueur != 5) {
						joueur++;
					} else {
						joueur = 0;
					}
				}
				
				switch(joueur) {
					case 0: posX_T = new int[]{100, 120, 140}; posY_T = new int[]{80, 110, 80}; break;
					case 1: posX_T = new int[]{200, 220, 240}; posY_T = new int[]{80, 110, 80}; break;
					case 2: posX_T = new int[]{310, 330, 350}; posY_T = new int[]{80, 110, 80}; break;
					case 3: posX_T = new int[]{410, 430, 450}; posY_T = new int[]{80, 110, 80}; break;
					case 4: posX_T = new int[]{510, 530, 550}; posY_T = new int[]{80, 110, 80}; break;
					case 5: posX_T = new int[]{610, 630, 650}; posY_T = new int[]{80, 110, 80}; break;
				}
			break;
			
			case 1:
				if(direction == -2) {
					if(style == 1) {
						style = 0;
					} else {
						style = 1;
					}
				} else if(direction == 2) {
					if(style == 0) {
						style = 1;
					} else {
						style = 0;
					}
				}
				
				switch(style) {
					case 0: posX_T = new int[]{234, 254, 274}; posY_T = new int[]{310, 340, 310}; break;
					case 1: posX_T = new int[]{484, 504, 524}; posY_T = new int[]{310, 340, 310}; break;
				}
			break;
			
			case 2:
				posX_T = new int[]{450, 475, 450};
				posY_T = new int[]{435, 450, 465};
				
				if(direction == -2) {
					if(map != 0) {
						map--;
					} else {
						map = 2;
					}
				} else if(direction == 2) {
					if(map != 2) {
						map++;
					} else {
						map = 0;
					}
				}
				switch(map) {
					case 0: posX_T = new int[]{175, 200, 175}; posY_T = new int[]{515, 527, 540}; break;
					case 1: posX_T = new int[]{290, 315, 290}; posY_T = new int[]{515, 527, 540}; break;
					case 2: posX_T = new int[]{405, 431, 405}; posY_T = new int[]{515, 527, 540}; break;
				}
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
		
		if(key.choixGauche) {
			key.choixGauche = false;
			gestionChoix(-2, false);
		}
		
		if(key.choixDroite) {
			key.choixDroite = false;
			gestionChoix(2, false);
		}
		
		if(key.choix) {
			key.choix = false;
			PlayState.setNumeroJoueur(joueur);
			PlayState.setNumeroStyle(style);
			PlayState.setNumeroMap(map);
			if(gsm.isStateActive(GameStateManager.PLAY)) {
				gsm.pop(GameStateManager.PLAY);
				gsm.remove(GameStateManager.CHOICE);
			} else {gsm.addAndpop(GameStateManager.PLAY, GameStateManager.CHOICE);}
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
