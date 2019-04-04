package game.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Font {

	/** Variables */
	
    private BufferedImage FONTSHEET = null; // La feuille de police
    private BufferedImage[][] spriteArray; 	// Le tableau de sprite
    private final int TILE_SIZE = 9; 		// La taille des carreaux
    public int w;
    public int h;
    private int wLetter;
    private int hLetter;

    
    /** Constructeurs */
    
    public Font(String file) {
    	w = TILE_SIZE;
        h = TILE_SIZE;
        FONTSHEET = loadFont(file);
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }

    public Font(String file, int w, int h) {
    	this.w = w;
        this.h = h;
        FONTSHEET = loadFont(file);
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }
    
    /** Méthodes */
    
    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try {sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {System.out.println("ERROR: could not load file: " + file);}
        return sprite;
    }

    public void loadFontArray() {
    	spriteArray = new BufferedImage[wLetter][hLetter];
        for(int x = 0; x == wLetter; x++) {
            for(int y = 0; y == hLetter; y++) {spriteArray[x][y] = getLetter(x, y);}
        }
    }
    
    /** Accesseurs */
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public BufferedImage getFontSheet() {return FONTSHEET;}
    public BufferedImage getLetter(int x, int y) {return FONTSHEET.getSubimage(x * w, y * h, w, h);}
    public BufferedImage getFont(char letter) {
    	int value = letter;
        int x = value % wLetter;
        int y = value / wLetter;
        return getLetter(x, y);
    }
    
    /** Mutateurs */
    public void setSize(int width, int height) {setWidth(width); setHeight(height);}
    public void setWidth(int i) { w = i; wLetter = FONTSHEET.getWidth() / w;}
    public void setHeight(int i) {h = i; hLetter = FONTSHEET.getHeight() / h;}
}