package game.entity;

import java.awt.Graphics2D;

import game.graphics.Sprite;
import game.util.Vector2f;

public class IA extends Entity {

	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
		// Mettre + 20 a la deuxieme taille si on veut qu'il soit plus haut
		g.drawImage(animation.getImage(), (int) (pos.x), (int) (pos.y), size, size + 20, null);
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		if(up) { 	// Touche haut appuyée 
			if(currentAnimation != UP || animation.getDelay() == -1) {
				setAnimation(UP, sprite.getSpriteArray(UP), 5);
			}
		} else if(down) {	// Touche bas appuyée
			if(currentAnimation != DOWN || animation.getDelay() == -1) {
				setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
			}
		} else if(left) {	// Touche gauche appuyée 
			if(currentAnimation != LEFT || animation.getDelay() == -1) {
				setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
			}
		} else if(right) {	// Touche droite appuyée
			if(currentAnimation != RIGHT || animation.getDelay() == -1) {
				setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
			}
		} else if(fallen){
			if(currentAnimation != FALLEN || animation.getDelay() == -1) {
				setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 4);
			}
		} else {
			setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
		}
	}

	@Override
	protected void move() {
		// TODO Auto-generated method stub
		
	}

}
