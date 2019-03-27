package game.entity;

import java.util.LinkedList;

import game.graphics.Sprite;
import game.states.PlayState;
import game.tiles.TileManager;
import game.tiles.TileMapBlock;
import game.util.AABB;
import game.util.Vector2f;

import java.lang.Math;

public class IA extends Entity {

	
	private int[][] matriceInaccessible;
	
	private AABB source;
	private AABB destination;
	LinkedList<AABB> sommetsAExplorer;
	LinkedList<AABB> sommetsVisite;
	
	
	
	
	
	
	
	
	
	
	
	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		
		this.matriceInaccessible = new int[TileManager.getWidth()][TileManager.getHeight()];
		
		sommetsAExplorer = new LinkedList<AABB>();
		sommetsAExplorer.add(source);
		
		sommetsVisite = null;
		
		//destination = Destination();
		
	}

	
	
	
	/** L'IA fait le choix soit de se proteger si elle est en danger soit d'attaquer sinon */
	public void choix() {
		if(this.enDanger()) {
			this.chercherCheminSecure();
		} else {
			this.chercherCheminAttaque();
		}
	}
	
	
	private void chercherCheminAttaque() {
		/* Il faut chercher un autre joueur le plus proche et se diriger vers lui */
		
	}




	private void chercherCheminSecure() {
		
		/* Il faut regarder les deplacements possibles selon les collisions avec les blocks */
		/* Il faut choisir une destination selon celles possibles et au mieux */
		
	}




	private AABB Destination() {
		// TODO Auto-generated method stub

		/** Dans un premier temps, l'IA doit choisir sa destination : donc un IA/joueur le plus proche */
		
		float distance = 10000;
		destination = new AABB(this.pos, 50,50);
		
		/* On regarde les IA */
		
		for(int i = 0; i < PlayState.ia.length; i++) {
			if(PlayState.ia[i] != this) {
				
				float distX = Math.abs(PlayState.ia[i].getCaseActuelleX() - this.getCaseActuelleX());
				float distY = Math.abs(PlayState.ia[i].getCaseActuelleY() - this.getCaseActuelleY());
				if(distance > (distX + distY)){
					destination = PlayState.ia[i].caseActuelle;
				}
			}
		}
		
		
		/* On regarde le joueur */
		
		float distX = Math.abs(PlayState.player.getCaseActuelleX() - this.getCaseActuelleX());
		float distY = Math.abs(PlayState.player.getCaseActuelleY() - this.getCaseActuelleY());
		if(distance > (distX + distY)){
			destination = PlayState.player.getCaseActuelle();
		}
		
		return destination;
	}







	public void update(double time) {
		super.update(time);
		if(!fallen) {
			
			genererMatrice();
			
			
			
			choisirAction();
			
			
			move();
			poserBombe();
			
			if(!collision.collision(dx, 0)) { pos.x += dx; }
			if(!collision.collision(0, dy)) { pos.y += dy; }
			

			
		
		} else {
			if(animation.hasPlayedOnce()) {
				/* Quand l'animation du joueur mort est jouée, on a possibilité 
				 * de remettre le joueur en position initiale si plusieurs vies */
				resetPosition(); 
				fallen = false;
			}
		}
	}

	
	/* Case avec un bloc : 1, case avec une bombe : 2 Case avec le joueur en question : 5  Case avec un ennemy : 10*/
	private void genererMatrice(){
		
		for(int i = 0; i < this.matriceInaccessible.length; i++) {
			for(int j = 0; j < this.matriceInaccessible[i].length; j++) {
				
				for(int l = 0; l < PlayState.ia.length; l++) {
					if(i == (int) (PlayState.ia[l].getCaseActuelleX()) && j == (int) (PlayState.ia[l].getCaseActuelleY() / 50)) {
						this.matriceInaccessible[i][j] = 10;		
					}
				}
				
				if(i == (int) this.getCaseActuelleX() && j == this.getCaseActuelleY()) {
					this.matriceInaccessible[i][j] = 5;
				}
				
				if(i == (int) (PlayState.player.getCaseActuelleX() / 50) && j == (int) (PlayState.player.getCaseActuelleY() / 50)) {
					this.matriceInaccessible[i][j] = 10;		
				}
				
				for(int k = 0; k < PlayState.bombList.size(); k++) {
					if(i == (int) (PlayState.bombList.get(k).getCaseActuelleX()) 
							&& j == (int) (PlayState.bombList.get(k).getCaseActuelleY())) {
						this.matriceInaccessible[i][j] = 2;		
					}
				}
				
				if(TileMapBlock.tmo_blocks.containsKey(String.valueOf((int) i) + "," + String.valueOf((int) j)) == true) {
					this.matriceInaccessible[i][j] = 1;					
				} else {
					if(this.matriceInaccessible[i][j] != 2) {
						this.matriceInaccessible[i][j] = 0;
					}
				}
			}
		}
	}
	
	
	
	
	private void choisirAction() {
		
		
		
		
		/* Maintenant on va regarder ses differentes directions possibles puis par la suite calculer le plus petit cout */
		
		LinkedList<String> directionPossible = new LinkedList<String>();
		while(!sommetsAExplorer.isEmpty() && !sommetsAExplorer.contains(destination)) {
			/* On regarde les 4 directions possibles voir si y'a pas une galere de mur tu connais ... */
			if(!collision.collision(-5, 0)){directionPossible.add("Gauche");}
			if(!collision.collision(5, 0)) {directionPossible.add("Droite");}
			if(!collision.collision(0, -5)){directionPossible.add("Haut");  }
			if(!collision.collision(0, 5)) {directionPossible.add("Bas");   }
			
			/* On parcour les Directions possibles pour regarder celle qui a le plus petit cout */
			for(int j = 0; j < directionPossible.size(); j++) {
				
				
				
			}
		}
		
		
		
		
		
		/*if (this.pos.x > goal.pos.x)
			this.left = true;
		if (this.pos.x < goal.pos.x)
			this.left = false;
			this.right = true;
		if (this.pos.y > goal.pos.y)
			this.up = true;
		if (this.pos.y < goal.pos.y)
			this.up = false;
			this.down = true;

		if (this.pos.x == goal.pos.x && this.pos.y == goal.pos.y)
			rechercherNouvelleDestination();
			
		
		
		*/
		
		
		
		
	}












	private void rechercherNouvelleDestination() {
		// TODO Auto-generated method stub
		if (ZoneDangereuse()) {
		      rechercherNouvelleDestination();
		} else {
			   rechercherNouvelleDestination();
		}
	}












	private boolean ZoneDangereuse() {
		// TODO Auto-generated method stub
		
	
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
