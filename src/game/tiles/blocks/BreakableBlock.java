package game.tiles.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.util.AABB;
import game.util.Vector2f;

public class BreakableBlock extends Block {

	public BreakableBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void render(Graphics2D g){
        super.render(g);
        g.setColor(Color.white);
        //g.drawRect((int) pos.x, (int) pos.y, w, h);
    }

}
