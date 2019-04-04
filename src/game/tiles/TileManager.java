package game.tiles;

import java.awt.Graphics2D;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import game.graphics.Sprite;

public class TileManager {

	/** Variables */
	
    public static TileMap tm;
    private static int width;
    private static int height;
    
    /** Constructeur */
    
    public TileManager(String fichier) {
        addTileMap(fichier, 50, 50);  				
    }

    /** Méthodes */
    
    /* La fonction addTileMap ajoute une map de tuiles */
    private void addTileMap(String fichier, int blockWidth, int blockHeight) {
    	Sprite sprite;
    	String imagePath; 
    	String[] data = new String[10];
        int tileColumns; 
        width = 0; 
        height = 0;
        
        try {
        	// Creation d'un document DOM nommé builderFactory
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            
            // On recupere un document passé en argument (le path) et on le normalise
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(fichier).toURI()));
            doc.getDocumentElement().normalize();

            // La list de noeuds contient les elements du document ayant pour nom 'tileset'
            NodeList list = doc.getElementsByTagName("tileset");
            
            Node node = list.item(0);
            
            // On recupere les informations du document 
            Element eElement = (Element) node;
            imagePath = eElement.getAttribute("name");		// On recupere l'image utilisée dans le doc 
            tileColumns =  Integer.parseInt(eElement.getAttribute("columns"));	// On recupere le nombre de colonnes (ici 6)
            
            // On charge l'image utilisée dans le document xml ("tile/img.png"), avec 50 et 50 en largeur et hauteur des tuiles 
            sprite = new Sprite("tile/"+imagePath+".png", 50, 50);
            
            // La list contient maintenant les elements layer du document xml
            list = doc.getElementsByTagName("layer");
            node = list.item(0);
            eElement = (Element) node;
            width = Integer.parseInt(eElement.getAttribute("width"));
            height = Integer.parseInt(eElement.getAttribute("height"));
            data[0] = eElement.getElementsByTagName("data").item(0).getTextContent();
            tm = new TileMap(data[0], sprite, width, height, blockWidth, blockHeight, tileColumns);
        } catch(Exception e) {
            System.out.println("Erreur lecture Tilemap");
        }
    }

    public void render(Graphics2D g) {if(tm != null) {tm.render(g);}}
    
    public static int getWidth() {return width;}
    public static int getHeight() {return height;}
}