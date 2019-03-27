package game.tiles.blocks;

import java.awt.image.BufferedImage;

import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class BreakableBlock extends Block {
    	
    	public BreakableBlock(BufferedImage img, Vector2f pos) {
    		super(img, pos);
    	}
    	
    	public void disparait() {
    		TileMapBlock.tmo_blocks.remove(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), this);
    		TileMapBlock.ground.put(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf((int) (this.pos.y / 50)), new GroundBlock(TileMapBlock.sprite.getSprite(3,1), this.pos));
    		this.casse = true;
    	}
    	
    	public boolean update(AABB p) {
    		if(casse) {return false;} 
    		return true;
    	}

}

