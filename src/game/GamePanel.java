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
	public static int ticks = 0;	
	private Thread thread;
	private Boolean running = false;
	public int tickCount = 0;
	
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
	
	/* Méthode qui fait tournée le jeu */
	@Override
	public void run() {
		init();	
		
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000 / 60;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		/** Boucle du jeu */
		while(running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1) {
				ticks++;
				tick();
				update(now);
				input(mouse, key);
				delta -= 1;
				shouldRender = true;
			}
			
			try {Thread.sleep(100); } 
			catch (InterruptedException ex) { ex.printStackTrace(); }
			
			if(shouldRender) {
				input(mouse, key);
				render();
				draw();
			}
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				// Pour voir le nombre d'FPS : System.out.println(ticks + " FPS");
				ticks = 0;
			}
		}
	}
	
	public void tick() {tickCount++;}
	
	public void init() {
		running = true;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		mouse = new MouseHandler(this);
		key = new KeyHandler(this);
		gsm = new GameStateManager();
	}
	
	public void update(double time) {gsm.update(time);}
	
	public void input(MouseHandler mouse, KeyHandler key) {gsm.input(mouse, key);}
	
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