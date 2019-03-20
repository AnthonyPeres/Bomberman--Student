package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.util.Vector2f;

public class HorizontalBomb extends Bomb {

	public HorizontalBomb(Vector2f origin, int size, Entity e) {
		super(origin, size, 1, e);
		// TODO Auto-generated constructor stub
		this.rayonX = 3;
		this.rayonY = 0;
	}
	
	public void update(double time) {
		super.update(time);
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		g.drawRect((int) pos.x, (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}


}
