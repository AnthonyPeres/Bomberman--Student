package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusBombPique extends Bonus {

	public BonusBombPique(Vector2f pos) {
		super(TileMap.getSprite().getSprite(1,1), pos);
	}

	@Override
	public void effet() {
		this.getEntity().setPiqBomb(true);
	}	
}