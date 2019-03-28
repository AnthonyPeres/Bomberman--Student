package game.bonus;

import game.tiles.TileMap;
import game.util.AABB;
import game.util.Vector2f;

public class BonusMultiBomb extends Bonus {

	public BonusMultiBomb(Vector2f pos) {
		super(TileMap.getSprite().getSprite(0,1), pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		if(active) {this.effet();}
		return false;
	}

	@Override
	public void effet() {
		// TODO Auto-generated method stub
		
	}
}
