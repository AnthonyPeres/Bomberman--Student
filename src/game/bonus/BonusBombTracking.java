package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusBombTracking extends Bonus {

	public BonusBombTracking(Vector2f pos) {
		super(TileMap.getSprite().getSprite(5,0), pos);
	}

	@Override
	public void effet() {
		this.getEntity().setTrackingBomb(true);
	}	
}
