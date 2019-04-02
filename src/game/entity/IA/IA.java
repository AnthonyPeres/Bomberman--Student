package game.entity.IA;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entity.Entity;
import game.entity.bomb.Bomb;
import game.graphics.Sprite;
import game.states.PlayState;
import game.util.AABB;
import game.util.Vector2f;


// TODO: Faire avec les situations dangereuses 
// TODO: Refaire les deplacements


public class IA extends Entity {
	
	/** VARIABLES */
	
	private int[][] matrice;
	
	private boolean attaque;
	
	private Sommet Source;
	private Sommet Destination;
	
	private ArrayList<Sommet> sommetsAExplorer;
	private ArrayList<Sommet> sommetsVisites;
	
	private ArrayList<Sommet> directionsPossibles;
	private ArrayList<Sommet> directionsCassables;
	
	
	/** CONSTRUCTEUR */

	public IA(Sprite sprite, Vector2f pos, int size) {
		super(sprite, pos, size);
		
		matrice = PlayState.getMatrice().getMatrice();
		
		this.attaque = true;
		
		this.Source = new Sommet(null, null, this.getSaCase());
		this.Destination = null;
		
		this.sommetsAExplorer = new ArrayList<Sommet>();
		this.sommetsAExplorer.add(this.Source);
		
		this.sommetsVisites = new ArrayList<Sommet>();
		
		this.directionsPossibles = new ArrayList<Sommet>();
		this.directionsCassables = new ArrayList<Sommet>();
		
	}
	
	
	/** MÉTHODES */
	
	
	public void update(double time) {
		super.update(time);
		
		PlayState.getMatrice().rafraichir();
		matrice = PlayState.getMatrice().getMatrice(); 			// On rafraichit la matrice
		
		this.Source = new Sommet(null, null, this.getSaCase());
		
		this.regarderSituation();
		
		this.chercherDestination();
		
		//System.out.println("Source : "+this.Source.getCaseX()+","+this.Source.getCaseY()+" ---> Destination : "+this.Destination.getCaseX()+","+this.Destination.getCaseY());
		
		
		this.AlgorithmeA();
		
	}
	
	
	
	/** Met l'IA en mode attaque si il n'y a pas de danger, en mode 'non' attaque si il y en a un */
	private void regarderSituation() {
		if(this.danger(this.getSaCase())) {
			this.attaque = false;
		} else {
			this.attaque = true;
		}
	}

	/** Regarde si l'IA est en danger, si il y a une bombe dans les alentours */
	private boolean danger(AABB Case) {
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
				return true;
			}
		}
		return false;
	}
	
	/** Méthode qui permet de trouver une destination pour l'IA : Un joueur si elle est en mode attaque, une case sécure si elle est en mode défense */
	private void chercherDestination() {
		if(this.attaque) {
			Sommet temp = null;
			
			// IA VS IA
			for(int i = 0; i < PlayState.ia.length; i++) {
				if(PlayState.ia[i] != this) {
					if(PlayState.ia[i] != null) {
						int positionX = (int) PlayState.ia[i].getSaCase().getPos().x;
						int positionY = (int) PlayState.ia[i].getSaCase().getPos().y;
						
						if(temp == null) {
							temp = new Sommet(null, null, new AABB( new Vector2f(positionX, positionY), 50, 50));
							temp.setCoutG(this.calculerCoutG(temp));
							this.Destination = temp;
						} else {
							temp = new Sommet(null, null, new AABB( new Vector2f(positionX, positionY), 50, 50));
							temp.setCoutG(this.calculerCoutG(temp));
							if(temp.getCoutG() < this.Destination.getCoutG()) { this.Destination = temp; }
						}
					}		
				}
			}
			
			// IA VS JOUEUR
			if(PlayState.player != null) {
				int positionX = (int) PlayState.player.getSaCase().getPos().x;
				int positionY = (int) PlayState.player.getSaCase().getPos().y;
				if(temp == null) {
					temp = new Sommet(null, null, new AABB( new Vector2f(positionX, positionY), 50, 50));
					temp.setCoutG(this.calculerCoutG(temp));
					this.Destination = temp;
				} else {
					temp = new Sommet(null, null, new AABB( new Vector2f(positionX, positionY), 50, 50));
					temp.setCoutG(this.calculerCoutG(temp));
					if(temp.getCoutG() < this.Destination.getCoutG()) {this.Destination = temp;}
				}
			}
		} else if(this.attaque == false){	
			
			/** FAIRE LA SITUATION DE DEFENSE */
			
		}
	}

	/** Renvoie la liste des directions possibles : Aucun mur */
	private ArrayList<Sommet> getDirectionsPossibles(Sommet source){
		int saCaseX = source.getCaseX();
		int saCaseY = source.getCaseY();
		ArrayList<Sommet> listeDesDirectionsPossibles = new ArrayList<Sommet>();
		if(this.matrice[saCaseX - 1][saCaseY] == 3 || this.matrice[saCaseX - 1][saCaseY] == 7) {listeDesDirectionsPossibles.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x - 50, this.getSaCase().getPos().y), 50, 50)));}
		if(this.matrice[saCaseX + 1][saCaseY] == 3 || this.matrice[saCaseX + 1][saCaseY] == 7) {listeDesDirectionsPossibles.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x + 50, this.getSaCase().getPos().y), 50, 50)));}
		if(this.matrice[saCaseX][saCaseY + 1] == 3 || this.matrice[saCaseX][saCaseY + 1] == 7) {listeDesDirectionsPossibles.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x, this.getSaCase().getPos().y + 50), 50, 50)));}
		if(this.matrice[saCaseX][saCaseY - 1] == 3 || this.matrice[saCaseX][saCaseY - 1] == 7) {listeDesDirectionsPossibles.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x, this.getSaCase().getPos().y - 50), 50, 50)));}
		for(int i = 0; i < listeDesDirectionsPossibles.size(); i++) {listeDesDirectionsPossibles.get(i).setPredecesseur(source);}
		return listeDesDirectionsPossibles;
	}
	
	/** Renvoie la liste des voisins cassables : Gauche Droite Haut Bas */
	private ArrayList<Sommet> getDirectionsCassables(Sommet source){
		int saCaseX = source.getCaseX();
		int saCaseY = source.getCaseY();
		ArrayList<Sommet> listeDesDirectionsCassables = new ArrayList<Sommet>();
		if(this.matrice[saCaseX - 1][saCaseY] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x - 50, this.getSaCase().getPos().y), 50, 50)));}
		if(this.matrice[saCaseX + 1][saCaseY] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x + 50, this.getSaCase().getPos().y), 50, 50)));}
		if(this.matrice[saCaseX][saCaseY + 1] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x, this.getSaCase().getPos().y + 50), 50, 50)));}
		if(this.matrice[saCaseX][saCaseY - 1] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, new AABB(new Vector2f(this.getSaCase().getPos().x, this.getSaCase().getPos().y - 50), 50, 50)));}
		for(int i = 0; i < listeDesDirectionsCassables.size(); i++) {listeDesDirectionsCassables.get(i).setPredecesseur(source);}
		return listeDesDirectionsCassables;
	}
	
	/** Calcule le coutH du sommet : Nombre de cases de sa position a sa destination */
	private int calculerCoutH(Sommet temp) {
		int caseX = Math.abs(temp.getCaseX() - this.Destination.getCaseX());
		int caseY = Math.abs(temp.getCaseY() - this.Destination.getCaseY());
		return (caseX + caseY);
	}

	/** Calcule le coutG du sommet : Nombre de cases de sa position au sommet */
	private int calculerCoutG(Sommet temp) {
		int caseX = Math.abs(temp.getCaseX() - this.Source.getCaseX());
		int caseY = Math.abs(temp.getCaseY() - this.Source.getCaseY());
		return (caseX + caseY);
	}


	
	
	/**
	 * 
	 * 	ATTENTION ! IL FAIT DES ALLERS RETOURS ENTRE LA SOURCE ET LA CASE DU DESSUS CAR LE COUT TOTAL EST LE MEM 
	 * 
	 * */
	
	
	
	private void AlgorithmeA() {
		
		/* Tant que la liste des sommets a explorer n'est pas vide : Tant que l'on peut aller quelque part */
		while(!(this.sommetsAExplorer.isEmpty()) && !(this.sommetsAExplorer.contains(Destination))) {
			
			// Il faut calculer le cout pour tout les sommets a explorer
			for(int i = 0; i < sommetsAExplorer.size(); i++) {
				sommetsAExplorer.get(i).setCoutG(calculerCoutG(sommetsAExplorer.get(i)));
				sommetsAExplorer.get(i).setCoutH(calculerCoutH(sommetsAExplorer.get(i)));
				sommetsAExplorer.get(i).setCoutTotal();
			}
			
			
			// On creer un sommet temporaire qui va recuperer le sommet avec le plus petit cout : on le place dans sommets visités 
			Sommet coutMinimum = null;
			
			// On recherche ce sommet
			for(int j = 0; j < sommetsAExplorer.size(); j++) {
				if(j == 0) {
					coutMinimum = sommetsAExplorer.get(j);
				} else {
					
					if(sommetsAExplorer.get(j).getCoutTotal() == coutMinimum.getCoutTotal()) {
						if(sommetsAExplorer.get(j).getCoutG() > coutMinimum.getCoutG()) {
							coutMinimum = sommetsAExplorer.get(j);
						}
					}
					
					else if(sommetsAExplorer.get(j).getCoutTotal() < coutMinimum.getCoutTotal()) {
						coutMinimum = sommetsAExplorer.get(j);
					} 
				}
			}
			
			this.sommetsVisites.add(coutMinimum);
			this.sommetsAExplorer.remove(coutMinimum);
			
			this.directionsPossibles = getDirectionsPossibles(coutMinimum);
			this.directionsCassables = getDirectionsCassables(coutMinimum);		
			
			for(int k = 0; k < directionsPossibles.size(); k++) {
				if(!(this.sommetsVisites.contains(directionsPossibles.get(k)))) {
					this.sommetsAExplorer.add(directionsPossibles.get(k));
				}
			}
			
			for(int k = 0; k < directionsCassables.size(); k++) {
				if(!(this.sommetsVisites.contains(directionsCassables.get(k)))) {
					this.sommetsAExplorer.add(directionsCassables.get(k));
				}
			}
			
		
			System.out.println("ooooooooo");
			System.out.println(this.sommetsAExplorer.size());
			
		}
		
		// VIDER LES LISTES 
		
		
		//deplacer le bomber VVV
		
		
	}


	
	
	
	



	/** Cette méthode sert a faire deplacer le personnage de son sommet au sommet destination */
	private void deplacement(Sommet p) {
		
		/* TANT QUE LE BOUNDSCOLLISION EST ENTRE LE P.GET CASE* 50 ET LE P.GET CASE * 50 + 50 ON FAIT RIEN NANANANA */
		
		int x = (int)(this.getBoundsCollision().getPos().x + this.getBoundsCollision().getXOffset());
		int y = (int)(this.getBoundsCollision().getPos().y + this.getBoundsCollision().getYOffset());
		
		if(x < p.getCaseX() * 50) {this.right = true; } else { this.right = false;}
		if(x > p.getCaseX() * 50) { this.left = true; } else { this.left = false;}
		if(y < p.getCaseY() * 50) {this.down = true;} else { this.down = false;}
		if(y > p.getCaseY() * 50) {this.up = true;} else { this.up = false;}
		
		move();
	}


	
	public void render(Graphics2D g) {
		super.render(g);
		
		g.setColor(Color.CYAN);
		g.drawRect(this.Destination.getCaseX() * 50, this.Destination.getCaseY() * 50, 50, 50);
		
		g.setColor(Color.RED);
		for(int i = 0; i < this.sommetsVisites.size() ; i++) {
			g.drawRect(this.sommetsVisites.get(i).getCaseX() * 50, this.sommetsVisites.get(i).getCaseY() * 50, 50, 50);
		}
		
		
	}

	
	/** Fonction qui permet de supprimer l'IA du PlayState */
	@Override
	protected void meurt() {
		for(int i = 0; i < PlayState.ia.length; i++) {
			if(PlayState.ia[i] == this) {
				PlayState.ia[i] = null;
			}
		}
	}
}