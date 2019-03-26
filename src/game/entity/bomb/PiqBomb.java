package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class PiqBomb extends Bomb {

	public PiqBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 5, 2, 2, entity);
	}
}
