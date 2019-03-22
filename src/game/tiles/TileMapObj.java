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
    
    /**
     * 	data est la matrice des briques 
     * 	sprite est l'image utilisée pour la création de la map
     * 	width et height sont le nombre de blocks en largeur et en hauteur (16 et 12)
     * 	tileWidth et tileWidth sont la taille d'un block en largeur et en hauteur (50 et 50)
     * 	tileColumns est le nombre de colonnes (6)
     * 
     * */
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
    
   public static Sprite getSprite() {
	   return sprite;
   }
    

    public void render(Graphics2D g) {
        for(Objet objet: tmo_blocks.values()) {
            objet.render(g);
        }
    }
}