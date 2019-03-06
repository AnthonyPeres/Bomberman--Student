package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import game.states.*;
import game.util.*;

/**
 * 	GamePanel est la vue du jeu.
 * 
 * */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	/** Variables */
	public static int width;
	public static int height;
	
	public static int oldFrameCount;	// Nombre d'ancienne image, utilisé pour avoir le FPS
	
	private Thread thread;
	private Boolean running = false;
	
	private BufferedImage img;
	private Graphics2D g;
	
	/* La souris et le clavier du jeu */
	private MouseHandler mouse;
	private KeyHandler key;
	
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
	
	/* Fonction run */
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		
		/* On initialise le jeu */
		init();
		
		/* On veut du 60GHz, TBU est le temps avant l'update */
		final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ;

        final int MUBR = 5; // Must Update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60.0;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render
		
		int frameCount = 0;
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		oldFrameCount = 0;
		
		
		/* Boucle du jeu */
		while(running) {
			double now = System.nanoTime();
			int updateCount = 0;
			
			while(((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
				update();
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
				
				try { thread.sleep(100); } catch(Exception e) {
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
		
		/* La souris et le clavier sont ecoutes par le GamePanel */
		mouse = new MouseHandler(this);
		key = new KeyHandler(this);
		
		gsm = new GameStateManager();
	}
	
	/* On update le Gestionnaire de statut ce qui va update tout les menus (MenuState, PlayState...) */
	public void update() {
		gsm.update();
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