package game.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.GamePanel;

public class MouseHandler implements MouseListener, MouseMotionListener {

	/** Variables */
	
	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	/** Constructeur */
	
	public MouseHandler(GamePanel game) {
		game.addMouseListener(this);
	}
	
	/** Méthodes */

	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	/** Mutateurs */
	public int getX() {return mouseX;}
	public int getY() {return mouseY;}
	public int getButton() {return mouseB;}
}