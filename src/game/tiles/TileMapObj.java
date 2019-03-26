package game.tiles;

import java.awt.Graphics2D;
import java.util.HashMap;

import game.graphics.Sprite;
import game.obj.Objet;
import game.util.Vector2f;

/**
 * 
 *  Le sol
 * 
 * */

public class TileMapObj extends TileMap {

	/** Variables */
	public static HashMap<String, Objet> tmo_blocks;

	private static Sprite sprite;
	
	
	/** Constructeur */
	
    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
        
    	TileMapObj.sprite = sprite;
    	Objet tempObjet;
        tmo_blocks = new HashMap<String, Objet>();
        String[] block = data.split(",");
        
        for(int i = 0; i < (width * height); i++) {
        	int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
        	Vector2f pos = new Vector2f((i % width) * 50, (i / width) * 50);
        	
            if(temp != 0) {
            		tempObjet = new Objet(pos, tileWidth, tileHeight);
            	tmo_blocks.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / width)), tempObjet);
            }
        }
    }
    
    
    public void render(Graphics2D g) {
        for(Objet objet: tmo_blocks.values()) {
            objet.render(g);
        }
    }
    
    public static Sprite getSprite() {
    	return sprite;
    }
}