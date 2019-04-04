package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusBombUp extends Bonus {

	public BonusBombUp(Vector2f pos) {
		super(TileMap.getSprite().getSprite(0,1), pos);
	}

	@Override
	public void effet() {
		this.entiteActive.setMaxBomb(this.entiteActive.getMaxBomb()+1);
	}
}
