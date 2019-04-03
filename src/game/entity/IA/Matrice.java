package game.entity.IA;

import java.awt.Color;
import java.awt.Graphics2D;

import game.states.PlayState;
import game.tiles.TileMap;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.ObstacleBlock;

public class Matrice {

	
	private int[][] matrice = new int[16][12];
	
	
	public Matrice() {
		
	}
	
	
	public int[][] getMatrice(){return this.matrice;}
	
	public int getCase(int x, int y){return this.matrice[x][y];}
	
	public void rafraichir() {
    	
    	for(int i = 0; i < matrice.length ; i++) {
    		for(int j = 0; j < matrice[i].length; j++) {
    			
    			if(TileMap.tmo_blocks.containsKey(String.valueOf(i)+","+String.valueOf(j))) {
    				
    				Block block = TileMap.tmo_blocks.get(String.valueOf(i) + ","+ String.valueOf(j));
    				if(block instanceof ObstacleBlock) {
    					matrice[i][j] = 1;
    				} else if(block instanceof BreakableBlock) {
    					matrice[i][j] = 2;
    				}
    			}
    			
    			if(TileMap.ground.containsKey(String.valueOf(i)+","+String.valueOf(j))) {
    				matrice[i][j] = 3;
    			}
    			
    			if(TileMap.tmo_bonus.containsKey(String.valueOf(i)+","+String.valueOf(j))) {
    				matrice[i][j] = 7;
    			}
    		
    		
    		for(int k = 0; k < PlayState.bombList.size(); k++) {
				
				int X = (int)(PlayState.bombList.get(k).getSaCase().getPos().x / 50);
				int Y = (int)(PlayState.bombList.get(k).getSaCase().getPos().y / 50);
				
				matrice[X][Y] = 4;
			}
    		
    		
    		for(int l = 0; l < PlayState.ia.length; l++) {
    			
    			if(PlayState.ia[l] != null) {
	    			int X = (int) (PlayState.ia[l].getSaCase().getPos().x / 50);
					int Y = (int)(PlayState.ia[l].getSaCase().getPos().y / 50);
					matrice[X][Y] = 5;
    			}
    		}
    		
    		if(PlayState.player != null) {
    			int X = (int)(PlayState.player.getSaCase().getPos().x / 50);
        		int Y = (int)(PlayState.player.getSaCase().getPos().y / 50);
        		matrice[X][Y] = 6;
    		}
    	
    		
    		
    		}
    	}
    }
	
	public void update(double time) {
		rafraichir();
		
	}

	public void render(Graphics2D g) {
		for(int i = 0; i< matrice.length;i++) {
	    	for(int j = 0; j< matrice[i].length; j++) {
	    		
	    		switch(matrice[i][j]) {
	    		case 1: g.setColor(Color.green); g.drawRect(i*50, j*50, 50, 50); break;
	    		case 2: g.setColor(Color.red); g.drawRect(i*50, j*50, 50, 50); break;
	    		case 3: g.setColor(Color.blue); g.drawRect(i*50, j*50, 50, 50); break;
	    		case 4: g.setColor(Color.yellow); g.drawRect(i*50, j*50, 50, 50); break;
	    		case 5: g.setColor(Color.cyan); g.drawRect(i*50, j*50, 50, 50); break;
	    		case 6: g.setColor(Color.magenta); g.drawRect(i*50, j*50, 50, 50); break;
	    		case 7: g.setColor(Color.WHITE); g.drawRect(i*50, j*50, 50, 50); break;
	    		
	    			
	    		}
	    	}
	    }
	}
	
}
