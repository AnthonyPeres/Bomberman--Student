package game.tiles;

import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import game.graphics.Sprite;

/**
 * 	
 * 	Gestionnaire de Tuiles 
 * 
 * */
public class TileManager {

	/** Variables */
	
    public static ArrayList<TileMap> tm;	// Liste de tuiles

    
    /** Constructeur */
    /* Le path est le lien du document a charger */
    
    public TileManager() {
        tm = new ArrayList<TileMap>();
    }
    
    public TileManager(String path) {
        tm = new ArrayList<TileMap>();
        addTileMap(path, 50, 50);  				
    }

    public TileManager(String path, int blockWidth, int blockHeight) {
        tm = new ArrayList<TileMap>();
        addTileMap(path, blockWidth, blockHeight);
    }

    
    /** Méthodes */
    
    /* La fonction addTileMap ajoute une map de tuiles */
    private void addTileMap(String path, int blockWidth, int blockHeight) {
    	
    	String imagePath; // l'image utilisée avec le document xml. Ici : "img" (elle se trouve dans le meme doc) 
    	
    	int tileWidth;	// largeur d'une tuile (50)
        int tileHeight;	// hauteur d'une tuile (50)
        int tileCount;	// nombre de tuiles (24)
        int tileColumns;	// nombre de colonnes (6) (de l'image chargée) 
        
        int layers = 0; // layers contient le nombre de layers du document xml (ici 2)
        
        Sprite sprite; 	// Contient l'image utilisé dans le document xml (map)
        
        int width = 0; 	// Taille de la map en largeur (16)
        int height = 0;	// Taille de la map en hauteur (12)
        
        String[] data = new String[10];	// Contient la matrice 

        try {
        	
        	// Creation d'un document DOM nommé builderFactory
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            
            // On recupere un document passé en argument (le path) et on le normalise
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            // La list de noeuds contient les elements du document ayant pour nom 'tileset'
            NodeList list = doc.getElementsByTagName("tileset");
            
            Node node = list.item(0);
            
            // On recupere les informations du document 
            Element eElement = (Element) node;
            imagePath = eElement.getAttribute("name");		// On recupere l'image utilisée dans le doc 
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));	// On recupere la largeur d'une tuile dans le doc (ici 50)
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));	// On recupere la hauteur d'une tuile dans le doc (ici 50)
            tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));	// On recupere le nombre de tuiles dans ce tileset (ici 24)
            tileColumns =  Integer.parseInt(eElement.getAttribute("columns"));	// On recupere le nombre de colonnes (ici 6)
            
            // On charge l'image utilisée dans le document xml ("tile/img.png"), avec 50 et 50 en largeur et hauteur des tuiles 
            sprite = new Sprite("tile/"+imagePath+".png", tileWidth, tileHeight);
            
            // La list contient maintenant les elements layer du document xml
            list = doc.getElementsByTagName("layer");
            
            // layers recupere le nombre de layers du doc xml
            layers = list.getLength();
            
            // On parcour les layers (ici 2)
            for(int i = 0; i < layers; i++) { 
            	node = list.item(i);
                eElement = (Element) node;
                
                // Au premier tour, on recupere les attributs width (16) et height (12) --> La taille de la map  
                if(i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }
                
                // data contient la matrice du document xml. au premier tour elle contient tout les sols (les 17), au second tout les murs
                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();
                /*	Si i < 1 : donc au premier tour de boucle. On va regarder dans le premier layer, celui ou l'on place 
                 *  les trous et les objets. On va creer ces objets
                 *  
                 *  Si i >= 1 : donc au second tour de boucle. On va regarder dans le second layer, celui ou l'on place les 
                 *  murs et les briques. On va creer ces objets
                 * 
                 * */
                //if(i >= 1) {
                //	tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                //} else {
                	tm.add(new TileMapBlock(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                //}
            }
        } catch(Exception e) {
            System.out.println("Erreur lecture Tilemap");
        }
    }

    /* tm.size est egal au nombre d'objets. on affiche donc tout ces objets ( murs, briques, objets...) */
    public void render(Graphics2D g) {
        for(int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g);
        }
    }
}