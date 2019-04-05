package game.bonus;

import game.states.PlayState;
import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusInvincible extends Bonus {

	public BonusInvincible(Vector2f pos) {
		super(TileMap.getSprite().getSprite(0,3), pos);
	}
	
	@Override
	public void effet() {
		PlayState.score += 3000;
		this.getEntity().setInvincible(true);
	}
}
