package game.bonus;


import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class BonusBombPique extends Bonus {

	public BonusBombPique(Vector2f pos) {
		super(TileMapBlock.sprite.getSprite(1,1), pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		return false;
	}
}