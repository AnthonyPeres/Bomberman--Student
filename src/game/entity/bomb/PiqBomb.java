package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.util.Vector2f;

public class PiqBomb extends Bomb {

	public PiqBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 5, 2, 2, entity);
		// TODO Auto-generated constructor stub
		this.rayonX = 2;
		this.rayonY = 2;
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
