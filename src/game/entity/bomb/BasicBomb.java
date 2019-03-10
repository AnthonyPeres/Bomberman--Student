package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.graphics.Sprite;
import game.util.Vector2f;

public class BasicBomb extends Bomb {

	
	
	public BasicBomb(Sprite sprite, Vector2f pos, int size, Entity entity) {
		super(sprite, pos, size, 0, entity);
		// TODO Auto-generated constructor stub
		
		this.bounds.setWidth(50);
		this.bounds.setHeight(50);
		this.bounds.setXOffset(0);
		this.bounds.setYOffset(0);
		
		
		r = 130;
	}
	
	public void update() {
		super.update();
	}

	

	@Override
	public void explose() {
		// TODO Auto-generated method stub
		
		/* Utiliser collisionFire() pour detecter une collision avec une brique cassable */
		
		
		
		this.e.bombList.remove(this);
		this.e.setBombposee(this.e.getBombposee() - 1);
	}
	
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		
		g.drawRect((int) pos.x, (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		
		/* Cases adjacentes */
		g.drawRect((int) (pos.x - (1*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x - (2*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x + (1*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x + (2*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y - (1*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y - (2*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y + (1*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y + (2*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}

}
