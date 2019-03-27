package game.entity.bomb;

import java.awt.Graphics2D;

import game.entity.Affichable;
import game.graphics.Sprite;
import game.states.PlayState;
import game.util.Vector2f;

public class Fire extends Affichable {

	/** Variables */
	
	protected final int CENTRE = 0;
	protected final int BAS = 1;
	protected final int BAS2 = 2;
	protected final int GAUCHE = 3;
	protected final int GAUCHE2 = 4;
	protected final int DROITE = 5;
	protected final int DROITE2 = 6;
	protected final int HAUT = 7;
	protected final int HAUT2 = 8;
	protected String direction;
	protected int ecart;
	
	
	/** Constructeur */
	
	public Fire(Vector2f pos, String direction, int ecart) {
		super(new Sprite("entity/spriteFlammes.png",16,16), pos, 50);
		
		setAnimation(0, sprite.getSpriteArray(0), 5);
		
		this.direction = direction;
		this.ecart = ecart;
		
		PlayState.listFlammes.add(this);
	}

	/** Méthodes */
	
	private void animate() {
		switch(direction) {
			case "centre":
				if(currentAnimation != CENTRE || animation.getDelay() == -1) {setAnimation(CENTRE, sprite.getSpriteArray(CENTRE), 5);}
			break;
			case "haut":
				if(ecart == 1) { if(currentAnimation != HAUT || animation.getDelay() == -1) {setAnimation(HAUT, sprite.getSpriteArray(HAUT), 5);}}
				else if(ecart == 2) { if(currentAnimation != HAUT2 || animation.getDelay() == -1) {setAnimation(HAUT2, sprite.getSpriteArray(HAUT2), 5); }}
			break;
			case "bas":
				if(ecart == 1) {
					if(currentAnimation != BAS || animation.getDelay() == -1) {setAnimation(BAS, sprite.getSpriteArray(BAS), 5);}} 
					else if(ecart == 2) { if(currentAnimation != BAS2 || animation.getDelay() == -1) {setAnimation(BAS2, sprite.getSpriteArray(BAS2), 5);}}
			break;
			case "gauche":
				if(ecart == 1) {
					if(currentAnimation != GAUCHE || animation.getDelay() == -1) {setAnimation(GAUCHE, sprite.getSpriteArray(GAUCHE), 5);}} 
					else if(ecart == 2) { if(currentAnimation != GAUCHE2 || animation.getDelay() == -1) {setAnimation(GAUCHE2, sprite.getSpriteArray(GAUCHE2), 5);}}
			break;
			case "droite":
				if(ecart == 1) {
					if(currentAnimation != DROITE || animation.getDelay() == -1) {setAnimation(DROITE, sprite.getSpriteArray(DROITE), 5);}} 
					else if(ecart == 2) { if(currentAnimation != DROITE2 || animation.getDelay() == -1) {setAnimation(DROITE2, sprite.getSpriteArray(DROITE2), 5);}}
			break;
			default: setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
		}
	}
	
	public void update(double time) {
		super.update(time);
		animate();
		if(animation.hasPlayedOnce()) {
			PlayState.listFlammes.remove(this);
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(animation.getImage(), (int) this.getPos().x,(int) this.getPos().y, size, size, null);
	}
}