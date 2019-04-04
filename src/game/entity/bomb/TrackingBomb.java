package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class TrackingBomb extends Bomb {

	public TrackingBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 4, 1, 1, entity);
	}

	
	public void update(double time) {
		super.update(time);
		decouleTemps();
	}

}
