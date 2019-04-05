package game.bonus;

import game.states.PlayState;
import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusSpeedLow extends Bonus {

	public BonusSpeedLow(Vector2f pos) {
		super(TileMap.getSprite().getSprite(2,3), pos);
	}

	@Override
	public void effet() {
		PlayState.score += 1000;
		this.entiteActive.setAcc(this.entiteActive.getAcc()-1);
		this.entiteActive.setMaxSpeed(this.entiteActive.getMaxSpeed()-1);
		this.entiteActive.setDeacc((float) (this.entiteActive.getDeacc()-0.5));
	}
	
}
