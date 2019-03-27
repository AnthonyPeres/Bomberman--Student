package game.bonus;

import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class BonusMultiBomb extends Bonus {

	public BonusMultiBomb(Vector2f pos) {
		super(TileMapBlock.sprite.getSprite(0,1), pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		return false;
	}
}
