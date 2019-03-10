
package game.tiles.blocks;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.util.*;

public abstract class Block {
   
	/** Variables */
	
	protected int w;
    protected int h;
    
    protected BufferedImage img;
    protected Vector2f pos;

    /** Constructeur */
    
    public Block(BufferedImage img, Vector2f pos, int w, int h) {
        this.img = img;
        this.pos = pos;
        this.w = w;
        this.h = h;
    }

    public abstract boolean update(AABB p);
    public Vector2f getPos() {return pos;}
	
    public void render(Graphics2D g) {
        g.drawImage(img, (int) pos.x, (int) pos.y, w, h, null);
    }
    
    
    public int getXW() {return (int) (this.pos.x + this.w);}
    public int getYH() {return (int) (this.pos.y + this.h);}
}