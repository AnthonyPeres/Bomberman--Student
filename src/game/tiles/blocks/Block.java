package game.tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Affichable;
import game.util.AABB;
import game.util.Vector2f;

public abstract class Block extends Affichable {
   
	/** Variables */
    
    public boolean casse = false;
   
    /** Constructeur */
    
    public Block(BufferedImage img, Vector2f pos) {
        super(img, pos);
    }

    public abstract boolean update(AABB p); 

    public void render(Graphics2D g) {
    	g.drawImage(this.getImage(), (int) this.getPos().x, (int) this.getPos().y, this.getWidth(), this.getHeight(), null);
    }
}