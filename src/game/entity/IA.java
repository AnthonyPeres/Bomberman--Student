package game.entity;

import java.awt.Graphics2D;

import game.graphics.Sprite;
import game.util.Vector2f;

public class IA extends Entity {

	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
		// Mettre + 20 a la deuxieme taille si on veut qu'il soit plus haut
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}

}
