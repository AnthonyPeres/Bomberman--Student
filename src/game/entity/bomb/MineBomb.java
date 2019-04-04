package game.entity.bomb;

import game.entity.Entity;
import game.util.Vector2f;

public class MineBomb extends Bomb {

	public MineBomb(Vector2f pos, int size, Entity entity) {
		super(pos, size, 3, 1, 1, entity);
	}

	
	
	public void update(double time) {
		super.update(time);
		
		
		// si un bomber marche dessus: this.explose();
		
		
	}

}