package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class HorizontalBomb extends Bomb {

	public HorizontalBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 1, 3, 0, entity);
	}
}