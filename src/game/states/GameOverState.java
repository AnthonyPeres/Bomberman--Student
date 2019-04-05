package game.states;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class GameOverState extends GameState {

	Image img;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
		
		try {
			img = ImageIO.read(new File("res/background/gameOver.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void input(MouseHandler mouse, KeyHandler key) {
		if(key.escape) {
			key.escape = false;
				gsm.addAndpop(GameStateManager.MENU, GameStateManager.GAMEOVER);
		}
		
	}

	@Override
	public void render(Graphics2D g) {g.drawImage(img, 0, 0, GamePanel.width, GamePanel.height, null);}

	@Override
	public void update(double time) {}

}
