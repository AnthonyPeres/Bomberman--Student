package game.entity.bomb;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entity.Entity;
import game.graphics.Sprite;
import game.util.Vector2f;

public class BasicBomb extends Bomb {

	
	
	public BasicBomb(Sprite sprite, Vector2f pos, int size, Entity entity) {
		super(sprite, pos, size, 0, entity);
		// TODO Auto-generated constructor stub
		
		/* Si ne marche pas : faire deux tableaux avec :
		 * casesToucheesX = {1,1,0,1,1};
		 * casesToucheesY = {1,1,0,1,1};*/
		
		this.casesAdjacentes = new int[][]{
			{0,0,1,0,0},
			{0,0,1,0,0},
			{1,1,0,1,1},
			{0,0,1,0,0},
			{0,0,1,0,0} 
		};
		
		
		
		
	}
	
	public void update(double time) {
		super.update(time);
	}

	

	@Override
	public void explose() {
		// TODO Auto-generated method stub
		
		/* Tout d'abord on va tester si il y a une collision incassable sur les cases adjacentes,
		 * si il n'y en a pas on va tester si il y a une collision cassables sur toutes les cases
		 * puis agir en conséquence */
		
		
		for(int i = 0; i < this.casesAdjacentes.length; i++) {
			for(int j = 0; j < this.casesAdjacentes.length; j++) {
				
				if(this.casesAdjacentes[i][j] == 1) {
					System.out.println(i+", "+j);
				}
			}
		}
		
		
		this.e.bombList.remove(this);
		this.e.setBombposee(this.e.getBombposee() - 1);
	}
	
	
	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.red);
		
		g.drawRect((int) pos.x, (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		
		/* Cases adjacentes */
		g.drawRect((int) (pos.x - (1*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x - (2*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x + (1*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) (pos.x + (2*bounds.getWidth())), (int) pos.y, (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y - (1*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y - (2*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y + (1*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		g.drawRect((int) pos.x, (int) (pos.y + (2*bounds.getHeight())), (int) bounds.getWidth(), (int) bounds.getHeight());
		
		g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size, null);
	}

}
