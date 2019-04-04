package game.bonus;

import game.tiles.TileMap;
import game.util.Vector2f;

public class BonusBombMine extends Bonus {

	/** Constructeur */
	
	public BonusBombMine(Vector2f pos) {
		super(TileMap.getSprite().getSprite(4,3), pos);
		// TODO Auto-generated constructor stub
	}

	/** Méthodes */
	
	@Override
	public void effet() {
		this.getEntity().setMineBomb(true);
	}	
}
