package game.tiles.blocks;

import java.awt.Graphics2D;

import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class GroundBlock extends Block {

	
    public GroundBlock(Vector2f pos, int w, int h) {
        super(TileMapBlock.getSprite().getSprite(3, 1), pos, w, h);
        System.out.println("okkk");
    }

    public boolean update(AABB p) {
    	
    	/** ICI SI ON MET L'ARGUMENT A FALSE IL N'Y A PAS DE COLLISIONS */
        return false;
    }
    
    public void render(Graphics2D g){
        super.render(g);
    }
}