package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusLifeUp extends Bonus {

	public BonusLifeUp(Vector2f pos) {
		super(TileMap.getSprite().getSprite(1,2), pos);
	}

	@Override
	public void effet() {
		this.getEntity().setNombreDeVies(this.getEntity().getNombreDeVies()+1);
	}
}