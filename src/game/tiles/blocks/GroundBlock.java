package game.tiles.blocks;

import java.awt.image.BufferedImage;

import game.util.AABB;
import game.util.Vector2f;

public class GroundBlock extends Block {
    	
    public GroundBlock(BufferedImage img, Vector2f pos) {
        super(img, pos);  
    }

    public boolean update(AABB p) {
		return false;
	}
}

