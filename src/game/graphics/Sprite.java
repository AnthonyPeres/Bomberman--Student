package game.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.util.Vector2f;

public class Sprite {

	/** Variables */
	
    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 20;		
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;
    public static Font currentFont;
    
    
    /** Constructeurs */
    
    public Sprite(String file) {
    	SPRITESHEET = loadSprite(file);
    	/* w et h prennent la taille du carré (en l'occurence ici : 20)*/
    	w = TILE_SIZE;
        h = TILE_SIZE;
        
        /* On divise la largeur et la hauteur de l'image par la taille d'un carré
         * pour avoir le nombre de carrés. w = 8 et h = 4 */
        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
    	SPRITESHEET = loadSprite(file);
    	this.w = w;
        this.h = h;
        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }
    
    /** Méthodes */

    /* Chargement d'une image (d'un sprite) */
    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }

    /* chargement d'une liste d'image (on prend chaque images par lignes et colonnes) ici : 4 * 8 */
    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];
        for(int y = 0; y < hSprite; y++) {
            for(int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }
   
    public static void drawArray(Graphics2D g, String word, Vector2f pos, int width, int height, int xOffset) {
        drawArray(g, currentFont, word, pos, width, height, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;
        currentFont = f;
        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) != 32)
                g.drawImage(f.getFont(word.charAt(i)), (int) x, (int) y, width, height, null);
            x += xOffset;
            y += yOffset;
        }
    }
    
    /** Accesseurs */
    
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public BufferedImage getSpriteSheet() {return SPRITESHEET;}
    public BufferedImage getSprite(int x, int y) {return SPRITESHEET.getSubimage(x * w, y * h, w, h);}
    public BufferedImage[] getSpriteArray(int i) {return spriteArray[i];}
    public BufferedImage[][] getSpriteArray2(int i) {return spriteArray;}

    /** Mutateurs */
    
    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    public void setWidth(int i) {
        w = i;
        wSprite = SPRITESHEET.getWidth() / w;
    }
    public void setHeight(int i) {
        h = i;
        hSprite = SPRITESHEET.getHeight() / h;
    }
}