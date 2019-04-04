package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusInvincible extends Bonus {

	public BonusInvincible(Vector2f pos) {
		super(TileMap.getSprite().getSprite(0,3), pos);
	}
	
	@Override
	public void effet() {
		this.getEntity().setInvincible(true);
	}
}
