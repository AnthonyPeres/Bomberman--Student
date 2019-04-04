package game.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class PauseState extends GameState {

	Image img;
	
	public PauseState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		
		/* Lecture de l'image de fond */
		try {
			img = ImageIO.read(new File("res/background/pause.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		// TODO Auto-generated method stub 
		if(key.escape) {
			key.escape = false;
			PlayState.setPause(false);
			if(gsm.isStateActive(GameStateManager.PLAY)) {
				gsm.remove(GameStateManager.PAUSE);
			} else {
				gsm.remove(GameStateManager.PAUSE);	
			}
		}
		
		if(gsm.isStateActive(GameStateManager.PAUSE)) {
			if(key.choix) {
				key.choix = false;
				if(gsm.isStateActive(GameStateManager.MENU)) {
					gsm.pop(GameStateManager.MENU);
					gsm.remove(GameStateManager.PAUSE);
				} else {
					gsm.addAndpop(GameStateManager.MENU, GameStateManager.PAUSE);
					
				}
			}
			
			if(key.choixHaut) {
				key.choixHaut = false;
				AideState.setArrivee("Pause");
				if(gsm.isStateActive(GameStateManager.AIDE)) {
					gsm.pop(GameStateManager.AIDE);
					gsm.remove(GameStateManager.PAUSE);
				} else {
					gsm.addAndpop(GameStateManager.AIDE, GameStateManager.PAUSE);
					
				}
			}
		}
	}

	
	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
	
	}
	


		@Override
		public void render(Graphics2D g) {
			// TODO Auto-generated method stub
	    	g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null); 	// Le background
		}

		
}
