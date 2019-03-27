package game.bonus;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.entity.Affichable;
import game.util.AABB;
import game.util.Vector2f;

public abstract class Bonus extends Affichable {

/** Constructeur */
    
    public Bonus(BufferedImage img, Vector2f pos) {
        super(img, pos);
    }

    public abstract boolean update(AABB p);
    
    public void render(Graphics2D g) {
        g.drawImage(this.getImage(), (int) this.getPos().x, (int) this.getPos().y, this.getWidth(), this.getHeight(), null);
    }

    
    /**
     * 		LES BONUS : 
     * */
    public class Vitesse extends Bonus {

		public Vitesse(BufferedImage img, Vector2f pos) {
			super(img, pos);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean update(AABB p) {
			// TODO Auto-generated method stub
			return false;
		}
    }
    
    public class MultiBomb extends Bonus {

		public MultiBomb(BufferedImage img, Vector2f pos) {
			super(img, pos);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean update(AABB p) {
			// TODO Auto-generated method stub
			return false;
		}
    }
}