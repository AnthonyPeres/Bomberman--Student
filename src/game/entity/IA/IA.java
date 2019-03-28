package game.entity.IA;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import game.entity.Entity;
import game.entity.bomb.Bomb;
import game.graphics.Sprite;
import game.states.PlayState;
import game.util.AABB;
import game.util.Vector2f;

public class IA extends Entity {

	/** Variables */
	
	private Sommet Source;
	
	private Sommet Destination;
	
	private LinkedList<Sommet> SommetsAExplorer;
	
	private LinkedList<Sommet> SommetsVisites;
	
	private int cheminX, cheminY, nouveauCheminX, nouveauCheminY;
	
	LinkedList<String> listeDirectionPossible; 
	
	
	/** Constructeur */
	
	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		
		/* SOURCE */
		this.Source = new Sommet(null, null, this.SaCase);
		
		/* DESTINATION */
		this.Destination = null;
		
		/* SOMMETS A EXPLORER */
		this.SommetsAExplorer = new LinkedList<Sommet>();
		this.SommetsAExplorer.add(this.Source);
		
		/* SOMMETS VISITES */
		this.SommetsVisites = new LinkedList<Sommet>();
		
		/* DIRECTIONS POSSIBLES */
		this.listeDirectionPossible = new LinkedList<String>(); 
		
	}

	
	/** Méthodes */
	
	public void update(double time) {
		super.update(time);
		
		listeDirectionPossible = getDirectionsPossibles();
		
		
		situation();
		AlgorithmeAEtoile();
		
		move();
		// poserBombe();
	}
	
	
	/*********************************************\
	 * 											 *
	 * MÉTHODES QUI TESTENT LES CASES ADJACENTES * 
	 * 											 *
	 *********************************************/
	
	/** Renvoie les directions disponnibles : Une Case ou il n'y a ni bloc cassable ni bloc incassable */
	private LinkedList<String> getDirectionsPossibles() {
		LinkedList<String> listeVoisins = new LinkedList<String>();
		int saCaseX = (int) this.getSaCase().getPos().x /50;
		int saCaseY = (int) this.getSaCase().getPos().y /50;
		Matrice matrice = PlayState.getMatrice();
				
		if(!(matrice.getCase(saCaseX - 1, saCaseY) == 1 || matrice.getCase(saCaseX - 1, saCaseY) == 2)) {listeVoisins.add("Gauche");}
		if(!(matrice.getCase(saCaseX + 1, saCaseY) == 1 || matrice.getCase(saCaseX + 1, saCaseY) == 2)) {listeVoisins.add("Droite");}
		if(!(PlayState.getMatrice().getCase(saCaseX, saCaseY + 1) == 1 || matrice.getCase(saCaseX, saCaseY + 1) == 2)) {listeVoisins.add("Bas");}
		if(!(matrice.getCase(saCaseX, saCaseY - 1) == 1 || matrice.getCase(saCaseX, saCaseY - 1) == 2)) {listeVoisins.add("Haut");}
		
		return listeVoisins;
	}
	
	
	/** Renvoie la liste des voisins cassables : Gauche Droite Haut Bas */
	private LinkedList<String> getVoisinsCassables(){
		LinkedList<String> listeVoisinsCassables = new LinkedList<String>();
		int saCaseX = (int) this.getSaCase().getPos().x /50;
		int saCaseY = (int) this.getSaCase().getPos().y /50;
		Matrice matrice = PlayState.getMatrice();
		
		if(!(matrice.getCase(saCaseX - 1, saCaseY) == 2)) {listeVoisinsCassables.add("Gauche");}
		if(!(matrice.getCase(saCaseX + 1, saCaseY) == 2)) {listeVoisinsCassables.add("Droite");}
		if(!(matrice.getCase(saCaseX, saCaseY + 1) == 2)) {listeVoisinsCassables.add("Bas");}
		if(!(matrice.getCase(saCaseX, saCaseY - 1) == 2)) {listeVoisinsCassables.add("Haut");}
		
		return listeVoisinsCassables;
	}
	
	
	
	/*********************************************\
	 * 											 *
	 *   MÉTHODES QUI VERIFIENT LA SITUATION     * 
	 * 											 *
	 *********************************************/
	
	
	/** Se met en position de défense ou d'attaque selon si l'IA est en danger ou non */
	public void situation() {
		if(this.enDanger(this.getSaCase())) { this.chercherDestinationDefense();} 
		else {
			this.chercherDestinationAttaque();
			
			/* On repasse les deplacements à faux */
			this.up = false;
			this.down = false;
			this.left = false;
			this.right = false;
			
		}
	}
	
	/** Retourne true si l'IA est en dangée, false sinon */
	protected boolean enDanger(AABB Case) {
		for(int i = 0; i < PlayState.bombList.size() ; i++) {
			Bomb tempB = PlayState.bombList.get(i);
			if(tempB.getSaCase().getPos().y == Case.getPos().y) {
				for(int j = 1; j <= tempB.getRayonX(); j++) {
					if(((tempB.getSaCase().getPos().x - (j * 50)) / 50) == (Case.getPos().x / 50))  { return true; } 
					else if(((tempB.getSaCase().getPos().x + (j * 50)) / 50) == (Case.getPos().x / 50)) { return true; }
				}
			} 
			if(tempB.getSaCase().getPos().x == Case.getPos().x) {
				for(int j = 1; j <= tempB.getRayonY(); j++) {
					if(((tempB.getSaCase().getPos().y - (j * 50)) / 50) == (Case.getPos().y / 50))  { return true;  } 
					else if(((tempB.getSaCase().getPos().y + (j * 50)) / 50) == (Case.getPos().y / 50)) { return true; }
				}
			}  
			if((tempB.getSaCase().getPos().y == Case.getPos().y) && (tempB.getSaCase().getPos().x == Case.getPos().x)) {
				System.out.println("danger"); return true;
			}
		}
		return false;
	}

	
	/*********************************************\
	 * 											 *
	 *   MÉTHODES QUI CHERCHE UNE DESTINATION    *
	 *   	PAR RAPPORT A LA SITUATION     		 * 
	 * 											 *
	 *********************************************/
	
	
	/** Cherche une destination dans une situation d'attaque */
	private void chercherDestinationAttaque() {
		this.cheminX = 1000;
		this.cheminY = 1000;
		this.nouveauCheminX = 1000;
		this.nouveauCheminY = 1000;
		
		for(int i = 0; i < PlayState.ia.length; i++) {
			if(PlayState.ia[i] != this) {
				if(PlayState.ia[i] != null) {
					if(i == 0) { 
						this.cheminX = Math.abs(this.getXSaPosition() - PlayState.ia[i].getXSaPosition());
						this.cheminY = Math.abs(this.getYSaPosition() - PlayState.ia[i].getYSaPosition());
						this.Destination = new Sommet(null, null, PlayState.ia[i].getSaCase());
					} else {
						this.nouveauCheminX = Math.abs(this.getXSaPosition() - PlayState.ia[i].getXSaPosition());
						this.nouveauCheminY = Math.abs(this.getYSaPosition() - PlayState.ia[i].getYSaPosition());
						
						if((this.cheminX + this.cheminY) > (this.nouveauCheminX + this.nouveauCheminY)){						
							this.cheminX = this.nouveauCheminX;
							this.cheminY = this.nouveauCheminY;
							this.Destination = new Sommet(null, null, PlayState.ia[i].getSaCase());
						}
					}	
				}		
			}
		}
		
		if(PlayState.player != null) {
			this.nouveauCheminX = Math.abs(this.getXSaPosition() - PlayState.player.getXSaPosition());
			this.nouveauCheminY = Math.abs(this.getYSaPosition() - PlayState.player.getYSaPosition());
			if((this.cheminX + this.cheminY) > (this.nouveauCheminX + this.nouveauCheminY)) {
				this.cheminX = this.nouveauCheminX;
				this.cheminY = this.nouveauCheminY;
				this.Destination = new Sommet(null, null, PlayState.player.getSaCase());
			}
		} 	
		
		this.cheminX /= 50;
		this.cheminY /= 50;
		
	}

	/** Cherche une destination dans une situation de défense */
	private void chercherDestinationDefense() {
		
		LinkedList<String> listeDirectionPossible = new LinkedList<String>(); 
		listeDirectionPossible = getDirectionsPossibles();
		
		int lower = 1; int higher = 5; 
		int randomDirection = (int)(Math.random() * (higher-lower)) + lower;
		
		for(int i = 0; i< listeDirectionPossible.size(); i++) {
				if(listeDirectionPossible.get(i) == "Gauche" && randomDirection == 1) {
					if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x - 50, (int)this.getSaCase().getPos().y), 50,50))) {
						this.left = true;
					}
				} else if(listeDirectionPossible.get(i) == "Droite" && randomDirection == 2) {
					if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x + 50, (int)this.getSaCase().getPos().y), 50,50))) {
						this.right = true;
					}
				} else if(listeDirectionPossible.get(i) == "Bas" && randomDirection == 3) {
					if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x, (int)this.getSaCase().getPos().y + 50), 50,50))) {
						this.down = true;
					}
				} else if(listeDirectionPossible.get(i) == "Haut" && randomDirection == 4) {
					if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x, (int)this.getSaCase().getPos().y - 50), 50,50))) {
						this.up = true;
				}
			}
		}
		this.Destination = null; // Le AABB d'une case secure
	}

	
	/*********************************************\
	 * 											 *
	 *   			ALGORITHME A*    			 * 
	 * 											 *
	 *********************************************/
	
	
	private void AlgorithmeAEtoile() {
		
		// this.SommetsVisites.clear();
		
		this.SommetsVisites.add(recupererSommetXmin());
		
		//while(!this.SommetsAExplorer.isEmpty() && !this.SommetsAExplorer.contains(Destination)) {
			
			//this.SommetsVisites.add(recupererSommetXmin());
			
			
		//}
		
		
		
		
	}


	/** Méthode qui permet de renvoyer le sommet ayant un cout minimum par rapport aux destinations possibles */
	private Sommet recupererSommetXmin() {
		
		int coutG = 0; 
		Sommet SommetX = null;
		
		for(int i = 1; i <= SommetsVisites.size(); i++) {
			coutG += (i * 10);
		}
		
		//int coutH = calculerCoutH();
		
		
		
		
		return SommetX;
	}





	
	





	public void render(Graphics2D g) {
		super.render(g);
		
		
		
		// A enlever par la suite
		g.setColor(Color.BLACK);

		g.drawRect((int)this.getSaCase().getPos().x,(int) this.getSaCase().getPos().y, (int)this.getSaCase().getWidth(), (int)this.getSaCase().getHeight());
		
		
		if(this.Destination !=null) {
			g.drawRect(this.getXDestination(), this.getYDestination(), this.getWDestination(), this.getHDestination());
		
			if(this.getSaCase().getPos().x < this.getXDestination()) {
				for(int x = 1; x <= this.cheminX; x++) {
					g.drawRect((int) this.getSaCase().getPos().x + (x * 50), (int) this.getSaCase().getPos().y, 50, 50);
				}
			} else {
				for(int x = 1; x <= this.cheminX; x++) {
					g.drawRect((int) this.getSaCase().getPos().x - (x * 50), (int) this.getSaCase().getPos().y, 50, 50);
				}
			}
			if(this.getSaCase().getPos().y < this.getYDestination()) {
				for(int y = 1; y <= this.cheminY; y++) {
					g.drawRect((int) this.getSaCase().getPos().x + (this.cheminX * 50) , (int) this.getSaCase().getPos().y + (y * 50), 50, 50);
				}
			} else {
				for(int y = 1; y <= this.cheminY; y++) {
					g.drawRect((int) this.getSaCase().getPos().x + (this.cheminX * 50), (int) this.getSaCase().getPos().y - (y * 50), 50, 50);
				}
			}
		}
	}
	
	@Override
	protected void meurt() {
		// TODO Auto-generated method stub
		for(int i = 0; i < PlayState.ia.length; i++) {
			if(PlayState.ia[i] == this) {
				PlayState.ia[i] = null;
			}
		}
	}

	
	/** Accesseurs */
	
	public Sommet getSaDestination() {return Destination;}
	public int getXDestination() {return (int) (this.getSaDestination().getCase().getPos().x) ;}
	public int getYDestination() {return (int) (this.getSaDestination().getCase().getPos().y) ;}
	public int getWDestination() {return (int) (this.getSaDestination().getCase().getWidth()) ;} 
	public int getHDestination() {return (int) (this.getSaDestination().getCase().getHeight());}
	
	
	
	/** Mutateurs */
	

}
