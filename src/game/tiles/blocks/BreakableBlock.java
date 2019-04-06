package game.tiles.blocks;

import java.awt.image.BufferedImage;

import game.bonus.Bonus;
import game.bonus.BonusBombMine;
import game.bonus.BonusBombPique;
import game.bonus.BonusBombTracking;
import game.bonus.BonusBombUp;
import game.bonus.BonusFallen;
import game.bonus.BonusFireUp;
import game.bonus.BonusInvincible;
import game.bonus.BonusLifeUp;
import game.bonus.BonusSpeedLow;
import game.bonus.BonusSpeedUp;
import game.tiles.TileMap;
import game.util.AABB;
import game.util.Vector2f;

public class BreakableBlock extends Block {
    	
	public BreakableBlock(BufferedImage img, Vector2f pos) {
		super(img, pos);
	}
	
	public void disparait() {
		this.casse = true;
		TileMap.tmo_blocks.remove(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), this);
		Bonus tempBonus;
		
		int lower = 1; int higher = 10; int random = (int)(Math.random() * (higher-lower)) + lower;
		if(random % 4 == 0) { 
			int TypeBonus = (int)(Math.random() * (11-1)) + 1;
			switch(TypeBonus) {
    			case 1 : tempBonus = new BonusBombPique(pos); break;
    			case 2 : tempBonus = new BonusBombUp(pos); break;
    			case 3 : tempBonus = new BonusFallen(pos); break;
    			case 4 : tempBonus = new BonusFireUp(pos); break;
    			case 5 : tempBonus = new BonusInvincible(pos); break;
    			case 6 : tempBonus = new BonusLifeUp(pos); break;
    			case 7 : tempBonus = new BonusSpeedLow(pos); break;
    			case 8 : tempBonus = new BonusBombTracking(pos); break;
    			case 9 : tempBonus = new BonusBombMine(pos); break;
    			default : tempBonus = new BonusSpeedUp(pos); break;
			} TileMap.tmo_bonus.put(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), tempBonus);
		} else {
    		TileMap.ground.put(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), new GroundBlock(TileMap.getSprite().getSprite(3,1), this.pos));
		}
	}
	
	public boolean update(AABB p) {
		if(casse) {return false;} 
		return true;
	}
}