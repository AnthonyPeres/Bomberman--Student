package game.states;

import java.awt.Graphics2D;

import game.util.KeyHandler;
import game.util.MouseHandler;

public class PauseState extends GameState {

	public PauseState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
		System.out.println("PAUSED");
		
	}


	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		// TODO Auto-generated method stub 
		if(key.escape) {
			key.escape = false;
			if(gsm.isStateActive(GameStateManager.PLAY)) {
				gsm.pop(GameStateManager.PLAY);
			} else {
				gsm.add(GameStateManager.PLAY);
			}
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub
	
	}
}
