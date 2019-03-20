package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.util.Vector2f;

public class RcBomb extends Bomb {

	public RcBomb(Vector2f origin, int size, Entity e) {
		super(origin, size, 4, e);
		// TODO Auto-generated constructor stub
		this.rayonX = 1;
		this.rayonY = 1;
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
