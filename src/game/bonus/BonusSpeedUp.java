package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusSpeedUp extends Bonus {

	public BonusSpeedUp(Vector2f pos) {
		super(TileMap.getSprite().getSprite(0,2), pos);
	}

	@Override
	public void effet() {
		this.entiteActive.setAcc(this.entiteActive.getAcc()+1);
		this.entiteActive.setMaxSpeed(this.entiteActive.getMaxSpeed()+1);
		this.entiteActive.setDeacc((float) (this.entiteActive.getDeacc()+0.5));
	}	
}