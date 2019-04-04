package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class BasicBomb extends Bomb {

	/** Constructeur */
	
	public BasicBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 0, 2, 2, entity);
	}
	
	public void update(double time) {
		super.update(time);
		decouleTemps();
	}
}