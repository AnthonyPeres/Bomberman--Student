package game;

import javax.swing.JFrame;

/**
 * 	La fenetre du jeu 
 * 
 * */

@SuppressWarnings("serial")
public class Window extends JFrame {

	public Window() {
		this.setTitle("Bomberman Remastered");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new GamePanel(800, 600));
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}	
	
}