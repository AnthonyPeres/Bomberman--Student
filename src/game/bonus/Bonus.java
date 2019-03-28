package game.bonus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Affichable;
import game.entity.Entity;
import game.util.AABB;
import game.util.Vector2f;

public abstract class Bonus extends Affichable {

/** Constructeur */
    
	protected boolean active = false;
	protected Entity entiteActive;
	
    public Bonus(BufferedImage img, Vector2f pos) {
        super(img, pos);
        this.entiteActive = null;
    }

    public abstract boolean update(AABB p);

    public abstract void effet();
    
    public void render(Graphics2D g) {
        g.drawImage(this.getImage(), (int) this.getPos().x, (int) this.getPos().y, this.getWidth(), this.getHeight(), null);
    }
   
    public boolean getActive() {return this.active;}
    public Entity getEntity() {return this.entiteActive;}
    
    public void setActive(boolean a) {this.active = a;}
    public void setEntity(Entity e) {this.entiteActive = e;}
    
    
}