package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.graphics.Sprite;
import game.util.Vector2f;


public class BasicBomb extends Bomb {

	/** Constructeur */
	
	public BasicBomb(Sprite sprite, Vector2f pos, int size, Entity entity) {
		super(sprite, pos, size, 0, entity);
		this.rayonX = 2;
		this.rayonY = 2;
	}
	
	/** Méthodes */
	
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
