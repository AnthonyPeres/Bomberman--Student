package game.tiles.blocks;

import java.awt.image.BufferedImage;

import game.bonus.Bonus;
import game.bonus.BonusBombPique;
import game.bonus.BonusMultiBomb;
import game.bonus.BonusVitesse;
import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class BreakableBlock extends Block {
    	
    	public BreakableBlock(BufferedImage img, Vector2f pos) {
    		super(img, pos);
    	}
    	
    	Bonus tempBonus;
    	
    	public void disparait() {
    		
    		this.casse = true;
    		TileMapBlock.tmo_blocks.remove(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), this);
    		
    		int lower = 1; int higher = 10; int random = (int)(Math.random() * (higher-lower)) + lower;
    		if(random % 3 == 0) {
    			int random2 = (int)(Math.random() * (4-1)) + 1;
    			if(random2 == 1) {
    				tempBonus = new BonusVitesse(pos);
    			} else if(random2 == 2) {
    				tempBonus = new BonusMultiBomb(pos);
    			} else if(random2 == 3) {
    				tempBonus = new BonusBombPique(pos);
    			} 
    			TileMapBlock.tmo_bonus.put(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), tempBonus);
    		} else {
        		TileMapBlock.ground.put(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), new GroundBlock(TileMapBlock.sprite.getSprite(3,1), this.pos));
    		}
    	}
    	
    	public boolean update(AABB p) {
    		if(casse) {return false;} 
    		return true;
    	}
}