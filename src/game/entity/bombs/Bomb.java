package game.entity.bombs;

import java.awt.Graphics2D;

import game.graphics.Animation;
import game.graphics.Sprite;
import game.util.Vector2f;

public abstract class Bomb {

	/* Animation */
	protected Animation ani;
	protected Sprite sprite;
	protected Vector2f pos;
	protected int currentAnimation;
	protected int size;
	
	public Bomb(Sprite sprite, Vector2f origin, int size) {
		this.sprite = sprite;
		this.pos = origin;
		this.size = size;
		
	}

	public abstract void render(Graphics2D g);

}
