package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class VerticalBomb extends Bomb {

	public VerticalBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 2, 0, 3, entity);
	}


	public void update(double time) {
		super.update(time);
		decouleTemps();
	}

}
