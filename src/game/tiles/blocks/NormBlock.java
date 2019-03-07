package game.tiles.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.util.AABB;
import game.util.Vector2f;

public class NormBlock extends Block {

    public NormBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    public boolean update(AABB p) {
    	
    	/** ICI SI ON MET L'ARGUMENT A FALSE IL N'Y A PAS DE COLLISIONS */
        return true;
    }
    
    public void render(Graphics2D g){
        super.render(g);
        g.setColor(Color.white);
        g.drawRect((int) pos.x, (int) pos.y, w, h);
    }
}
