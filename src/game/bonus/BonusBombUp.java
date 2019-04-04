package game.bonus;

import game.entity.Entity;
import game.tiles.TileMap;
import game.util.AABB;
import game.util.Vector2f;

public class BonusBombUp extends Bonus {

	public BonusBombUp(Vector2f pos) {
		super(TileMap.getSprite().getSprite(0,1), pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		return false;
	}

	@Override
	public void effet(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
