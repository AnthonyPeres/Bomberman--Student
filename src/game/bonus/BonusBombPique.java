package game.bonus;

import game.states.PlayState;
import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusBombPique extends Bonus {

	public BonusBombPique(Vector2f pos) {
		super(TileMap.getSprite().getSprite(1,1), pos);
	}

	@Override
	public void effet() {
		PlayState.score += 1000;
		this.getEntity().setPiqBomb(true);
	}	
}