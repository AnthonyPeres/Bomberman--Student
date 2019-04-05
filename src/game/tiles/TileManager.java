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
    
    private String imageUtilisee;
    private String fichier;
    		
    /** Constructeur */
    
    public TileManager(String imageUtilisee, String fichier) {
    	this.imageUtilisee = imageUtilisee;
    	this.setFichier(fichier);
    	addTileMap(fichier, 50, 50);  	
    }

    /** Méthodes */
    
    private void addTileMap(String fichier, int blockWidth, int blockHeight) {
    	Sprite sprite;
    	String[] data = new String[10];
        int tileColumns; 
        width = 0; 
        height = 0;
        
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(fichier).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            
            Node node = list.item(0);
             
            Element eElement = (Element) node;
            tileColumns =  Integer.parseInt(eElement.getAttribute("columns"));	// On recupere le nombre de colonnes (ici 6)
            
            sprite = new Sprite("tile/"+this.getImage()+".png", 50, 50);
            
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
    
    public String getImage() { return this.imageUtilisee;}
    public static int getWidth() {return width;}
    public static int getHeight() {return height;}
	public String getFichier() {return fichier;}
    
    public void setImage(String img) {this.imageUtilisee = img;}
	public void setFichier(String fichier) {this.fichier = fichier;}
}