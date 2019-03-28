package game.bonus;


import game.tiles.TileMap;
import game.util.AABB;
import game.util.Vector2f;

public class BonusBombPique extends Bonus {

	public BonusBombPique(Vector2f pos) {
		super(TileMap.getSprite().getSprite(1,1), pos);
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