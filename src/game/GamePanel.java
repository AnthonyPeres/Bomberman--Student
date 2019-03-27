package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import game.states.*;
import game.util.*;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	
	/** Variables */
	
	/* Dimensions */
	public static int width;
	public static int height;
	
	/* Processus du jeu */
	public static int oldFrameCount;	
	private Thread thread;
	private Boolean running = false;
	
	/* L'affichage */
	private BufferedImage img;
	private Graphics2D g;
	
	/* Les inputs */
	private MouseHandler mouse;
	private KeyHandler key;
	
	/* Le gestionnaire d'états */
	private GameStateManager gsm;
	
	
	/** Constructeur */
	
	public GamePanel(int width, int height) {
		GamePanel.width = width;
		GamePanel.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setFocusable(true);
		this.requestFocus();
	}

	
	/** Methodes */
	
	@Override
	public void run() {
		
		init();
		
		/* On veut du 60GHz, TBU est le temps avant l'update */
		final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ;
        final double TARGET_FPS = 60.0;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render
        final int MUBR = 5; // Must Update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;
        
		int frameCount = 0;
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		
		oldFrameCount = 0;
		
		
		/** Boucle du jeu */
		while(running) {
			
			double now = System.nanoTime();
			int updateCount = 0;
			
			while(((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
				update(now);
				input(mouse, key);
				lastUpdateTime += TBU;
				updateCount ++;
			}
			
			if(now - lastUpdateTime > TBU) {
				lastUpdateTime = now - TBU;
			}
			
			input(mouse, key);
			render();
			draw();
			lastRenderTime = now;
			frameCount++;
			
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if(thisSecond > lastSecondTime) {
				if(frameCount != oldFrameCount) {
					oldFrameCount = frameCount;
				}
				frameCount = 0;
				lastSecondTime = thisSecond;
			}
			
			while(now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
				Thread.yield();
				
				try { Thread.sleep(100); } catch(Exception e) {
					System.out.println("ERROR : yielding thread");
				}
				now = System.nanoTime();
			}
		}
	}
	
	public void init() {

		running = true;
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		
		mouse = new MouseHandler(this);
		key = new KeyHandler(this);
		gsm = new GameStateManager();
	}
	
	public void update(double time) {
        gsm.update(time);
    }
	
	public void input(MouseHandler mouse, KeyHandler key) {
		gsm.input(mouse, key);
	}
	
	public void render() {
		if(g != null) {
			g.setColor(new Color(100,100,100));
			g.fillRect(0, 0, width, height);
			gsm.render(g);
		}
	}
	
	public void draw() {
		Graphics g2 = (Graphics) this.getGraphics();
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this, "GameThread");
			thread.start();
		}
	}
}