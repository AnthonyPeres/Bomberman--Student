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
	
	private Sommet Source;									// Le sommet ou est placé l'IA
	private Sommet Destination;								// Le sommet ou l'IA veut aller
	private Sommet Prochain;								// Le sommet ou l'IA doit marcher
	
	private LinkedList<String> DirectionsAccessibles; 		// La liste des directions accessibles : Celle ou il n'y a aucun mur
	private LinkedList<String> DirectionsCassables;			// La liste des directions cassables 
	
	private LinkedList<Sommet> SommetsAVisiter;				// La liste des sommets a visiter : Les voisins de l'IA
	
	private int cheminX, 									// Le nombre de cases en X que l'IA devait parcourir pour aller a la destination
				cheminY, 									// Le nombre de cases en Y que l'IA devait parcourir pour aller a la destination
				nouveauCheminX, 							// Le nombre de cases en X que l'IA doit parcourir pour aller a la destination
				nouveauCheminY;								// Le nombre de cases en Y que l'IA doit parcourir pour aller a la destination
	
	private boolean Attaque;								// L'IA est en état d'attaque (ou non : false);
	
	
	
	/** Constructeur */
	
	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		
		/* SOURCE */
		this.Source = new Sommet(null, null, this.SaCase);
		
		/* DESTINATION */
		this.Destination = null;
		
		/* SOMMETS A EXPLORER */
		this.SommetsAVisiter = new LinkedList<Sommet>();
		this.SommetsAVisiter.add(this.Source);
		
		/* DIRECTIONS POSSIBLES */
		this.DirectionsAccessibles = new LinkedList<String>(); 
		
	}
	

	
	/** Méthodes */
	
	public void update(double time) {
		super.update(time);
		
		// TODO: Si l'IA doit casser un bloc cassable : elle casse.

		
		// 1. On va regarder si l'IA est menacé ou non. Si l'IA n'est pas menacé il se met en mode attaque sinon en defense
		situation();
		
		// 2. L'IA vérifie quelles cases sont accessibles et quelles cases sont cassables 
		DirectionsAccessibles = getDirectionsPossibles();
		DirectionsCassables = getDirectionsCassables();
		
		// 3. L'IA doit trouver une situation, elle est différente si il est en attaque ou non. Si il est en attaque : joueur le plus proche
		if(this.Attaque) { this.chercherDestinationAttaque(); } 
		else { this.chercherDestinationDefense();}
		
		// 4. On applique l'algorithme A* pour que l'IA parcour le meilleur chemin pour aller jusqu'a destination
		AlgorithmeAEtoile();
		
		// 5. On a le meilleur chemin ( meilleur case pour l'instant...) On se deplace
		deplacement();
		
		// 6. Si l'IA est dans une situation ou il peut exploser un adversaire, il pose une bombe 
		if(peutPoser()) {this.bomb = true; poserBombe();}
	}
	
	
	
	
	// 1. 
	
	/** Met l'IA en attaque ou en defense selon le danger */
	public void situation() {
		if(this.enDanger(this.getSaCase())) { this.Attaque = false; } 
		else { this.Attaque = true; }
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
				return true;
			}
		}
		return false;
	}

	
	
	
	
	
	// 2.
	
	/** Renvoie la liste des directions possibles : Aucun mur */
	private LinkedList<String> getDirectionsPossibles(){
		LinkedList<String> casesAccessibles = new LinkedList<String>();
		int saCaseX = (int) this.getSaCase().getPos().x /50;
		int saCaseY = (int) this.getSaCase().getPos().y /50;
		Matrice matrice = PlayState.getMatrice();
		
		if(!(matrice.getCase(saCaseX - 1, saCaseY) == 1 || matrice.getCase(saCaseX - 1, saCaseY) == 2)) {casesAccessibles.add("Gauche");}
		if(!(matrice.getCase(saCaseX + 1, saCaseY) == 1 || matrice.getCase(saCaseX + 1, saCaseY) == 2)) {casesAccessibles.add("Droite");}
		if(!(matrice.getCase(saCaseX, saCaseY + 1) == 1 || matrice.getCase(saCaseX, saCaseY + 1) == 2)) {casesAccessibles.add("Bas");}
		if(!(matrice.getCase(saCaseX, saCaseY - 1) == 1 || matrice.getCase(saCaseX, saCaseY - 1) == 2)) {casesAccessibles.add("Haut");}
		
		return casesAccessibles;
	}
	
	/** Renvoie la liste des voisins cassables : Gauche Droite Haut Bas */
	private LinkedList<String> getDirectionsCassables(){
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
	
	
	
	
	
	// 3.
	
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
		
		for(int i = 0; i < this.DirectionsAccessibles.size(); i++) {
			switch(this.DirectionsAccessibles.get(i)) {
			
			case "Gauche": 
				// Si la case de gauche n'est pas dangereuse : ça devient notre destination
				// Si elle est dangereuse, On cherche ses cases voisine qui ne le sont pas et devienent notre destination
				
				if(!enDanger(new AABB(new Vector2f((int)this.getSaCase().getPos().x - 50, (int)this.getSaCase().getPos().y), 50,50))) {
					this.Destination = new Sommet(null, null, new AABB( new Vector2f((int)this.getSaCase().getPos().x - 50, (int)this.getSaCase().getPos().y), 50,50));
				}
				
				
				break;
				
			case "Droite":
				
				if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x + 50, (int)this.getSaCase().getPos().y), 50,50))) {
					this.Destination = new Sommet(null, null, new AABB( new Vector2f((int)this.getSaCase().getPos().x + 50, (int)this.getSaCase().getPos().y), 50,50));
				}
				
				break;
				
			case "Bas":
				
				if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x, (int)this.getSaCase().getPos().y + 50), 50,50))) {
					this.Destination = new Sommet(null, null, new AABB( new Vector2f((int)this.getSaCase().getPos().x, (int)this.getSaCase().getPos().y + 50), 50,50));
				}
				
				
				break;
				
			case "Haut":
				
				if(!enDanger(new AABB( new Vector2f((int)this.getSaCase().getPos().x, (int)this.getSaCase().getPos().y - 50), 50,50))) {
					this.Destination = new Sommet(null, null, new AABB( new Vector2f((int)this.getSaCase().getPos().x, (int)this.getSaCase().getPos().y - 50), 50,50));
				}
				
				
				break;
			}
		}
	}
	
	
	
	
	// 4.
	
	/** Algorithme A* */
	private void AlgorithmeAEtoile() {
		
		this.SommetsAVisiter.clear();
		this.Prochain = null;
		
		int coutX;
		int coutY;
		
		for(int i = 0; i < this.DirectionsAccessibles.size(); i++) {
			
			String direction = this.DirectionsAccessibles.get(i);
			
			switch(direction) {
			
			case "Gauche": 
				
				Sommet g = new Sommet(Source, null, new AABB(new Vector2f((int) (this.getSaCase().getPos().x - this.getSaCase().getWidth()),(int) this.getSaCase().getPos().y), (int) this.getSaCase().getWidth(),(int) this.getSaCase().getHeight()));
				
				
				
				coutX = (int) Math.abs(g.getCase().getPos().x - this.Destination.getCase().getPos().x) / 50;
				coutY = (int) Math.abs(g.getCase().getPos().y - this.Destination.getCase().getPos().y) / 50;
				
				
				g.setCoutTotal(coutX + coutY);
				
				
				this.SommetsAVisiter.add(g);
				
				break;
				
			case "Droite":
				
				Sommet d = new Sommet(Source, null, new AABB(new Vector2f((int) (this.getSaCase().getPos().x + this.getSaCase().getWidth()),(int) this.getSaCase().getPos().y), (int) this.getSaCase().getWidth(),(int) this.getSaCase().getHeight()));
				
				coutX = (int) Math.abs(d.getCase().getPos().x - this.Destination.getCase().getPos().x) / 50;
				coutY = (int) Math.abs(d.getCase().getPos().y - this.Destination.getCase().getPos().y) / 50;
				
				d.setCoutTotal(coutX + coutY);
				
				
				this.SommetsAVisiter.add(d);
				break;
				
				
			case "Bas":
				
				Sommet b = new Sommet(Source, null, new AABB(new Vector2f((int) (this.getSaCase().getPos().x),(int) this.getSaCase().getPos().y + this.getSaCase().getHeight()), (int) this.getSaCase().getWidth(),(int) this.getSaCase().getHeight()));
				
				coutX = (int) Math.abs(b.getCase().getPos().x - this.Destination.getCase().getPos().x) / 50;
				coutY = (int) Math.abs(b.getCase().getPos().y - this.Destination.getCase().getPos().y) / 50;
				
				b.setCoutTotal(coutX + coutY);
				
				
				this.SommetsAVisiter.add(b);
				break;
				
			case "Haut":
				
				Sommet h = new Sommet(Source, null, new AABB(new Vector2f((int) (this.getSaCase().getPos().x),(int) this.getSaCase().getPos().y - this.getSaCase().getHeight()), (int) this.getSaCase().getWidth(),(int) this.getSaCase().getHeight()));
				
				coutX = (int) Math.abs(h.getCase().getPos().x - this.Destination.getCase().getPos().x) / 50;
				coutY = (int) Math.abs(h.getCase().getPos().y - this.Destination.getCase().getPos().y) / 50;
				
				h.setCoutTotal(coutX + coutY);
				
				
				this.SommetsAVisiter.add(h);
				
				break;
			
			
			
			}
			
			
		}
		
		
		
		int coutLePlusFaible = 100;
		
		for(int j = 0; j < this.SommetsAVisiter.size(); j++) {
			
			if(this.SommetsAVisiter.get(j).getCoutTotal() <= coutLePlusFaible && !(enDanger(this.SommetsAVisiter.get(j).getCase()))) {
				coutLePlusFaible = this.SommetsAVisiter.get(j).getCoutTotal();
				
				this.Prochain = this.SommetsAVisiter.get(j);
			} 
		}
		
		if(this.Prochain == null) {
			
			// Faire casser des blocs
			
			
		}
		
		
		
		if(this.Prochain == null) {
			
			for(int j = 0; j < this.SommetsAVisiter.size(); j++) {
				
				if(this.SommetsAVisiter.get(j).getCoutTotal() <= coutLePlusFaible) {
					coutLePlusFaible = this.SommetsAVisiter.get(j).getCoutTotal();
					
					this.Prochain = this.SommetsAVisiter.get(j);
				} 
			}
			
		}
		
	}
	
	
	
	
	// 5.
	
	/** Cette méthode sert a faire deplacer le personnage de son sommet au sommet destination */
	private void deplacement() {
		int x = (int)(this.getBoundsCollision().getPos().x + this.getBoundsCollision().getXOffset());
		int y = (int)(this.getBoundsCollision().getPos().y + this.getBoundsCollision().getYOffset());
		
		if(x < this.Prochain.getCase().getPos().x) {this.right = true; } else { this.right = false;}
		if(x > this.Prochain.getCase().getPos().x) { this.left = true; } else { this.left = false;}
		if(y < this.Prochain.getCase().getPos().y) {this.down = true;} else { this.down = false;}
		if(y > this.Prochain.getCase().getPos().y) {this.up = true;} else { this.up = false;}
		
		move();
	}
	
	
	
	
	// 6.
	
	/** Dit si l'IA peut poser une bombe */
	private boolean peutPoser() {
		// TODO Auto-generated method stub
		return false;
	}



	
	
	// Les fonctions abstraites de la class super
	
	
	@Override
	protected void meurt() {
		// TODO Auto-generated method stub
		for(int i = 0; i < PlayState.ia.length; i++) {
			if(PlayState.ia[i] == this) {
				PlayState.ia[i] = null;
			}
		}
	}
	
	
	public void render(Graphics2D g) {
		super.render(g);
		
		
		g.setColor(Color.RED);
		if(this.Destination !=null) {
			g.drawRect(this.getXDestination(), this.getYDestination(), this.getWDestination(), this.getHDestination());
		}
		
		
		for(int i = 0; i < this.SommetsAVisiter.size(); i++) {
			
			Sommet temp = this.SommetsAVisiter.get(i);
			
			if(temp.equals(this.Prochain)) {
				g.setColor(Color.RED);
				g.drawRect((int)temp.getCase().getPos().x, (int)temp.getCase().getPos().y, (int)temp.getCase().getWidth(),(int) temp.getCase().getHeight());
			} else {
				g.setColor(Color.cyan);
				g.drawRect((int)temp.getCase().getPos().x, (int)temp.getCase().getPos().y, (int)temp.getCase().getWidth(),(int) temp.getCase().getHeight());
			}
		}
	}
	
	
	
	
	
	
	/** Accesseurs */
	
	public Sommet getSaDestination() {return Destination;}
	public int getXDestination() {return (int) (this.getSaDestination().getCase().getPos().x) ;}
	public int getYDestination() {return (int) (this.getSaDestination().getCase().getPos().y) ;}
	public int getWDestination() {return (int) (this.getSaDestination().getCase().getWidth()) ;} 
	public int getHDestination() {return (int) (this.getSaDestination().getCase().getHeight());}
	
}
