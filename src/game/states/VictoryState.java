package game.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class VictoryState extends GameState{

	Image img;
	
	public VictoryState(GameStateManager gsm) {
		super(gsm);
		try {img = ImageIO.read(new File("res/background/Victory.png"));} 
		catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		if(key.escape) {
			key.escape = false;
			gsm.addAndpop(GameStateManager.MENU, GameStateManager.VICTORY);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null);
	}

	@Override
	public void update(double time) {}
}