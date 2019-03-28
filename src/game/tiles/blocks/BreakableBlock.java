package game.tiles.blocks;

import java.awt.image.BufferedImage;

import game.bonus.Bonus;
import game.bonus.BonusBombPique;
import game.bonus.BonusMultiBomb;
import game.bonus.BonusVitesse;
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
    		if(random % 5 == 0) {
    			int TypeBonus = (int)(Math.random() * (4-1)) + 1;
    			
    			/* Quel type de bonus est placé ? */
    			switch(TypeBonus) {
	    			case 1 : tempBonus = new BonusBombPique(pos); break;
	    			case 2 : tempBonus = new BonusMultiBomb(pos); break;
	    			
	    			default : tempBonus = new BonusVitesse(pos);
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