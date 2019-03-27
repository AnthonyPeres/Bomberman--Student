package game.tiles;

import java.awt.Graphics2D;
import java.util.HashMap;

import game.graphics.Sprite;
import game.tiles.blocks.Block;
import game.tiles.blocks.BreakableBlock;
import game.tiles.blocks.GroundBlock;
import game.tiles.blocks.ObstacleBlock;
import game.util.Vector2f;

public class TileMapBlock extends TileMap {

	/** Variables */
	public static HashMap<String, Block> tmo_blocks;
	public static HashMap<String, Block> ground;
	
	public static Sprite sprite;
	
    /** Constructeur */
    
    public TileMapBlock(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns) {
    	TileMapBlock.sprite = sprite;
    	Block tempBlock;
        tmo_blocks = new HashMap<String, Block>();
        ground = new HashMap<String, Block>();
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
        	if(block instanceof BreakableBlock) {
        		if(!block.casse) {
        			block.render(g);
        		}
        	} else {
        		block.render(g);
        	}
        }
        for(Block block: ground.values()) {
        	block.render(g);
        }
    }
    
    public static Sprite getSprite() {
    	return sprite;
    }
}