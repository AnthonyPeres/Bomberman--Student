package game.bonus;

import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class BonusVitesse extends Bonus {

	public BonusVitesse(Vector2f pos) {
		super(TileMapBlock.sprite.getSprite(0,2), pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		return false;
	}
}