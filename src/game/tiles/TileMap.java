package game.tiles;

import java.awt.Graphics2D;
import java.util.HashMap;

import game.bonus.Bonus;
import game.graphics.Sprite;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.GroundBlock;
import game.tiles.blocks.ObstacleBlock;
import game.util.Vector2f;

public class TileMap {

	/** Variables */
	private static Sprite sprite;
	public static HashMap<String, Block> tmo_blocks;
	public static HashMap<String, Bonus> tmo_bonus;
	public static HashMap<String, Block> ground;
	
	
    /** Constructeur */
    
    public TileMap(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
    	this.setSprite(sprite);
    	
        tmo_blocks = new HashMap<String, Block>();
        tmo_bonus = new HashMap<String, Bonus>();
        ground = new HashMap<String, Block>();

        Block tempBlock;
        String[] block = data.split(",");
        
        for(int i = 0; i < (width * height); i++) {
        	int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
        	Vector2f pos = new Vector2f((i % width) * 50, (i / width) * 50);
             
            if(temp != 0) {
            	if(temp == 10) {
            		tempBlock = new GroundBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), pos);
            		ground.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / width)), tempBlock);
            	} else if(temp == 1) {
            		tempBlock = new BreakableBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), pos);
            		tmo_blocks.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / width)), tempBlock);
            	} else {
            		tempBlock = new ObstacleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns) ), pos);
            		tmo_blocks.put(String.valueOf((int) (i % width)) + "," + String.valueOf((int) (i / width)), tempBlock);
            	}
            }
        }
    }
    
    public void render(Graphics2D g) {
        for(Block block: tmo_blocks.values()) {
        	if(block instanceof BreakableBlock) {if(!block.casse) {block.render(g);}} 
        	else {block.render(g);}
        }
        for(Block block: ground.values()) {block.render(g);}
        for(Bonus bonus: tmo_bonus.values()) {bonus.render(g);}
        
    }
    
    
    public static Sprite getSprite() {return sprite;}
	public void setSprite(Sprite sprite) {TileMap.sprite = sprite;}
}