package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusFireUp extends Bonus {

	public BonusFireUp(Vector2f pos) {
		super(TileMap.getSprite().getSprite(1,3), pos);
	}

	@Override
	public void effet() {
		
	}
}