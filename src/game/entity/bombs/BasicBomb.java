package game.entity.bombs;

import java.awt.Graphics2D;

import game.graphics.Sprite;
import game.util.Vector2f;

public class BasicBomb extends Bomb {

	public BasicBomb(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}

}
