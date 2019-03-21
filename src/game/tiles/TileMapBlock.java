package game.tiles;

import java.awt.Graphics2D;
import java.util.HashMap;

import game.graphics.Sprite;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.GroundBlock;
import game.tiles.blocks.ObstacleBlock;
import game.util.Vector2f;

/**
 * 
 *  Le sol
 * 
 * */

public class TileMapBlock extends TileMap {

	/** Variables */
	public static HashMap<String, Block> tmo_blocks;

	private static Sprite sprite;
	
    /** Constructeur */
    
    /**
     * 	data est la matrice des briques 
     * 	sprite est l'image utilisée pour la création de la map
     * 	width et height sont le nombre de blocks en largeur et en hauteur (16 et 12)
     * 	tileWidth et tileWidth sont la taille d'un block en largeur et en hauteur (50 et 50)
     * 	tileColumns est le nombre de colonnes (6)
     * 
     * */
    public TileMapBlock(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        
    	TileMapBlock.sprite = sprite;
    	
    	Block tempBlock;
        
        tmo_blocks = new HashMap<String, Block>();
        
        String[] block = data.split(",");
        
        for(int i = 0; i < (width * height); i++) {
        	
        	int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
        	
        	Vector2f pos = new Vector2f((i % width) * 50, (i / width) * 50);
        	
             
            if(temp != 0) {
            	if(temp == 10) {
            		tempBlock = new GroundBlock(pos, tileWidth, tileHeight);
            	} else if(temp == 1) {
            		tempBlock = new BreakableBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), pos, tileWidth, tileHeight);
            	} else {
            		tempBlock = new ObstacleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), pos, tileWidth, tileHeight);
            	}
            	tmo_blocks.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / width)), tempBlock);
            }
        }
    }
    
   public static Sprite getSprite() {
	   return sprite;
   }
    

    public void render(Graphics2D g) {
        for(Block block: tmo_blocks.values()) {
            block.render(g);
        }
    }
}