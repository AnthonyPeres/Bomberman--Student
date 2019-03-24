package game.tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

public class BreakableBlock extends Block {

	
	Block tempBlock;
	
	public BreakableBlock(BufferedImage img, Vector2f pos, int w, int h) {
		super(img, pos, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean update(AABB p) {
		// TODO Auto-generated method stub
		/*if(casse) {
			return false;
		}*/
		return true;
	}
	
	public void render(Graphics2D g){
        if(!casse) {
        	super.render(g);
        }
	}

	public void disparait() {
		// TODO Auto-generated method stub
		TileMapBlock.tmo_blocks.remove(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf(this.pos.y / 50), this);
		TileMapBlock.tmo_blocks.put(String.valueOf((int) (this.pos.x / 50)) + "," + String.valueOf(this.pos.y / 50), new GroundBlock(TileMapBlock.sprite.getSprite(3,1), this.pos, 50, 50));
		this.casse = true;
	}
}
