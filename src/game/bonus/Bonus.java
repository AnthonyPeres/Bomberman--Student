package game.bonus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Affichable;
import game.entity.Entity;
import game.tiles.TileMap;
import game.tiles.blocks.GroundBlock;
import game.util.Vector2f;

public abstract class Bonus extends Affichable {
    
	/** Attributs */
	
	protected boolean active = false;
	protected Entity entiteActive;
	
	/** Constructeur */
	
    public Bonus(BufferedImage img, Vector2f pos) {
        super(img, pos);
        this.entiteActive = null;
    }

    /** Méthodes */
    
   	public abstract void effet();

	public void disparait() {
    	int saCaseX = (int)((this.getEntity().getSaCase().getPos().x) / 50);
		int saCaseY = (int)((this.getEntity().getSaCase().getPos().y) / 50);    	
		TileMap.tmo_bonus.remove(String.valueOf(saCaseX) + "," + String.valueOf(saCaseY));
		TileMap.tmo_blocks.put(String.valueOf(saCaseX) + "," + String.valueOf(saCaseY), new GroundBlock(TileMap.getSprite().getSprite(3,1), new Vector2f(saCaseX * 50, saCaseY * 50)));
    }
	
    public void render(Graphics2D g) {
        g.drawImage(this.getImage(), (int) this.getPos().x, (int) this.getPos().y, this.getWidth(), this.getHeight(), null);
    }
   
    /** Accesseurs */
    
    public boolean getActive() {return this.active;}
    public Entity getEntity() {return this.entiteActive;}
    
    /** Mutateurs */
    
    public void setActive(boolean a) {this.active = a;}
    public void setEntity(Entity e) {this.entiteActive = e;}
}