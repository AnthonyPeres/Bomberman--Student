package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class RcBomb extends Bomb {

	public RcBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 4, 1, 1, entity);
	}
}
