package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusFallen extends Bonus {

	public BonusFallen(Vector2f pos) {
		super(TileMap.getSprite().getSprite(3,3), pos);
	}

	@Override
	public void effet() {
		this.getEntity().setFallen(true);
	}	
}
