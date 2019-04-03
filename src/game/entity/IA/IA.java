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
	private Sommet coutMinimum;
	
	private ArrayList<Sommet> sommetsAExplorer;
	private ArrayList<Sommet> sommetsVisites;
	private ArrayList<Sommet> directionsPossibles;
	private ArrayList<Sommet> directionsCassables;
	
	/** CONSTRUCTEUR */

	public IA(Sprite sprite, Vector2f pos, int size) {
		super(sprite, pos, size);
		this.attaque = true;
	}
	
	
	/** MÉTHODES */
	
	/* Gère les tours de l'IA */
	public void update(double time) {
		super.update(time);
		
		/* On rafraichit et on recupere la matrice */
		PlayState.getMatrice().rafraichir();
		matrice = PlayState.getMatrice().getMatrice();
		
		/* On recupere la source */
		this.Source = new Sommet(null, null, this.getSaCase());
		
		/* On remet la destination a null en attendant qu'on en ai une */
		this.Destination = null;
		
		/* On reinitialise la liste des sommets a explorer en y ajoutant la source */
		this.sommetsAExplorer = new ArrayList<Sommet>();
		this.sommetsAExplorer.add(this.Source);
		
		/* On reinitialise la liste des sommets a visiter */
		this.sommetsVisites = new ArrayList<Sommet>();
		
		/* On reinitialise la liste des directions possibles */
		this.directionsPossibles = new ArrayList<Sommet>();
		this.directionsCassables = new ArrayList<Sommet>();
		
		/* On reinitialise le sommet qui contient le cout minimum a la source */
		this.coutMinimum = this.Source;
				
		/* On appel la méthode qui se charge de regarder la situation dans laquelle est l'IA*/
		this.regarderSituation();
		
		/* On appel la méthode qui se charge de trouver une destination à l'IA */
		this.chercherDestination();
		
		/* On lance l'algorithme A* */
		this.AlgorithmeA();
		
		/* On fait deplacer l'IA */
		//this.deplacement(Prochain);
	}
	
	
	
	/**	Algorithme A* */
	private void AlgorithmeA() {
		
		/* Creation du parcours de l'IA a sa destination tant que le parcours n'est pas terminé */
		while(!(this.sommetsAExplorer.isEmpty()) && !(this.coutMinimum.getCaseX() == this.Destination.getCaseX() && this.coutMinimum.getCaseY() == this.Destination.getCaseY())) {
			
			/* On recherche les directions possibles et les directions cassables du coutMinimum */
			this.directionsPossibles = getDirectionsPossibles(coutMinimum);
			this.directionsCassables = getDirectionsCassables(coutMinimum);		
			
			/* On ajoute les directions possibles dans la liste des sommets a explorer si ils ne sont pas déjà dans la liste des sommets a visiter */
			for(int k = 0; k < directionsPossibles.size(); k++) {				
				if(!(this.sommetsVisites.contains(directionsPossibles.get(k)))) { this.sommetsAExplorer.add(directionsPossibles.get(k)); }
			}
			
			/* On ajoute les directions cassables dans la liste des sommets a explorer si ils ne sont pas déjà dans la liste des sommets a visiter */
			for(int l = 0; l < directionsCassables.size(); l++) {
				if(!(this.sommetsVisites.contains(directionsCassables.get(l)))) { this.sommetsAExplorer.add(directionsCassables.get(l)); }
			}
			
			/* Il faut calculer le cout pour tout les sommets a explorer */
			for(int i = 0; i < sommetsAExplorer.size(); i++) {
				sommetsAExplorer.get(i).setCoutG(calculerCoutG(sommetsAExplorer.get(i)));
				sommetsAExplorer.get(i).setCoutH(calculerCoutH(sommetsAExplorer.get(i)));
				sommetsAExplorer.get(i).setCoutTotal();
			}
			
			/* On fait en sorte que coutminimum prend le sommet avec le cout le plus petit de la liste des sommets a explorer */
			for(int j = 0; j < sommetsAExplorer.size(); j++) {
				if(coutMinimum == null) {coutMinimum = sommetsAExplorer.get(j); } 
				else {
					if(sommetsAExplorer.get(j).getCoutTotal() == coutMinimum.getCoutTotal()) {
						if(sommetsAExplorer.get(j).getCoutG() >= coutMinimum.getCoutG()) { coutMinimum = sommetsAExplorer.get(j); }
					} else if(sommetsAExplorer.get(j).getCoutTotal() < coutMinimum.getCoutTotal()) { coutMinimum = sommetsAExplorer.get(j); } 
				}
			}
			
			/* On ajoute le sommet coutMinimum dans la liste des sommetsVisites et on le supprime de la liste des sommets a explorer */
			this.sommetsVisites.add(coutMinimum);
			this.sommetsAExplorer.remove(coutMinimum);
		}	
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
		ArrayList<Sommet> listeDesDirectionsPossibles = new ArrayList<Sommet>();
		int saCaseX = source.getCaseX();
		int saCaseY = source.getCaseY();
		if(!(this.matrice[saCaseX - 1][saCaseY] == 1) && !(this.matrice[saCaseX - 1][saCaseY] == 2)) {listeDesDirectionsPossibles.add(new Sommet(source, null, saCaseX - 1, saCaseY ));}
		if(!(this.matrice[saCaseX + 1][saCaseY] == 1) && !(this.matrice[saCaseX + 1][saCaseY] == 2)) {listeDesDirectionsPossibles.add(new Sommet(source, null, saCaseX + 1, saCaseY ));}
		if(!(this.matrice[saCaseX][saCaseY + 1] == 1) && !(this.matrice[saCaseX][saCaseY + 1] == 2)) {listeDesDirectionsPossibles.add(new Sommet(source, null, saCaseX, saCaseY + 1 ));}
		if(!(this.matrice[saCaseX][saCaseY - 1] == 1) && !(this.matrice[saCaseX][saCaseY - 1] == 2)) {listeDesDirectionsPossibles.add(new Sommet(source, null, saCaseX, saCaseY - 1 ));}		
		for(int i = 0; i < listeDesDirectionsPossibles.size(); i++) {listeDesDirectionsPossibles.get(i).setPredecesseur(source);}			
		return listeDesDirectionsPossibles;
	}
	
	/** Renvoie la liste des voisins cassables : Gauche Droite Haut Bas */
	private ArrayList<Sommet> getDirectionsCassables(Sommet source){
		ArrayList<Sommet> listeDesDirectionsCassables = new ArrayList<Sommet>();
		int saCaseX = source.getCaseX();
		int saCaseY = source.getCaseY();		
		if(this.matrice[saCaseX - 1][saCaseY] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, saCaseX - 1, saCaseY ));}
		if(this.matrice[saCaseX + 1][saCaseY] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, saCaseX + 1, saCaseY ));}
		if(this.matrice[saCaseX][saCaseY + 1] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, saCaseX, saCaseY + 1 ));}
		if(this.matrice[saCaseX][saCaseY - 1] == 2) {listeDesDirectionsCassables.add(new Sommet(source, null, saCaseX, saCaseY - 1 ));}
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
	
	/** Cette méthode sert a faire deplacer le personnage de son sommet au sommet destination */
	private void deplacement(Sommet p) {
		/* Revoir les conditions */
		
		int x = (int)(this.getBoundsCollision().getPos().x + this.getBoundsCollision().getXOffset());
		int y = (int)(this.getBoundsCollision().getPos().y + this.getBoundsCollision().getYOffset());
		
		if(x < p.getCaseX() * 50) {this.right = true; } else { this.right = false;}
		if(x > p.getCaseX() * 50) { this.left = true; } else { this.left = false;}
		if(y < p.getCaseY() * 50) {this.down = true;} else { this.down = false;}
		if(y > p.getCaseY() * 50) {this.up = true;} else { this.up = false;}
		
		move();
	}

	/** Affiche les composants désirés */
	public void render(Graphics2D g) {
		super.render(g);
		
		/* La destination */
		g.setColor(Color.CYAN);
		if(this.Destination != null) { g.drawRect(this.Destination.getCaseX() * 50, this.Destination.getCaseY() * 50, 50, 50); }
		
		/* Les sommets a explorer */
		g.setColor(Color.yellow);
		if(this.sommetsAExplorer.isEmpty() == false) {
			for(int i = 0; i < this.sommetsAExplorer.size(); i++) { g.drawRect(this.sommetsAExplorer.get(i).getCaseX() * 50, this.sommetsAExplorer.get(i).getCaseY() * 50, 50, 50); }
		}
		
		/* Les sommets visités */
		g.setColor(Color.RED);
		if(this.sommetsVisites.isEmpty() == false) {
			for(int i = 0; i < this.sommetsVisites.size(); i++) { g.drawRect(this.sommetsVisites.get(i).getCaseX() * 50, this.sommetsVisites.get(i).getCaseY() * 50, 50, 50); }
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