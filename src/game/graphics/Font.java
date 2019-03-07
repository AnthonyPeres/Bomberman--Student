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
    	/**
    	 *	FONTSHEET.GETWIDTH EST EGAL A 1000 
    	 *  FONTSHEET.GETHEIGHT EST EGAL A 300
    	 * 
    	 * 	w et h valent 100
    	 * 
    	 * 	wLetter est egal a 10
    	 * 	hLetter est egal a 3
    	 * 
    	 * 
    	 */
    	this.w = w;
        this.h = h;

        FONTSHEET = loadFont(file);
        
        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadFontArray();
    }


   
    /* Methode qui charge une image */
    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try {sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {System.out.println("ERROR: could not load file: " + file);}
        return sprite;
    }

    /* Methode qui charge la liste des lettres */
    public void loadFontArray() {
    	
    	// spriteArray[10][3]
        spriteArray = new BufferedImage[wLetter][hLetter];

        for(int x = 0; x == wLetter; x++) {
            for(int y = 0; y == hLetter; y++) {
                spriteArray[x][y] = getLetter(x, y);
            }
        }
    }

    public BufferedImage getFontSheet() {
        return FONTSHEET;
    }

    public BufferedImage getLetter(int x, int y) {
    	return FONTSHEET.getSubimage(x * w, y * h, w, h);
    }

    public BufferedImage getFont(char letter) {
    	/* Il faut mettre letter - 65 pour commencer de A (table ascii) 
    	 * si la premiere lettre est un A ! */
    	int value = letter;
        int x = value % wLetter;
        int y = value / wLetter;
        return getLetter(x, y);
    }
    
    
    
    /** Accesseurs */
    public int getWidth() { return w; }
    public int getHeight() { return h; }
    
    
    /** Mutateurs */
    public void setSize(int width, int height) {setWidth(width); setHeight(height);}
    public void setWidth(int i) { w = i; wLetter = FONTSHEET.getWidth() / w;}
    public void setHeight(int i) {h = i; hLetter = FONTSHEET.getHeight() / h;}

}