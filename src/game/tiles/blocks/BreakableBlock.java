package game.tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.util.AABB;
import game.util.Vector2f;

public class BreakableBlock extends Block {

	public boolean casse = false;
	
	public BreakableBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		
		if(casse) {
			
			new GroundBlock(this.pos, this.w, this.h);
			
			return false;
		}
		return true;
	}
	
	public void render(Graphics2D g){
        if(!casse)
        	super.render(g);
	}

}
