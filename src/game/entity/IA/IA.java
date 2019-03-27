package game.entity.IA;

import game.entity.Entity;
import game.graphics.Sprite;
import game.states.PlayState;
import game.util.Vector2f;

public class IA extends Entity {

	/** Variables */
	
	
	
	
	
	
	/** Constructeur */
	
	public IA(Sprite sprite, Vector2f origin, int size) {
		super(sprite, origin, size);
		
		
		
	}

	
	/** Méthodes */
	
	
	public void situation() {
		if(this.enDanger()) {
			this.chercherCheminSecure();
		} else {
			this.chercherCheminAttaque();
		}
	}
	
	
	private void chercherCheminAttaque() {
		/* Il faut chercher un autre joueur le plus proche et se diriger vers lui */
		//System.out.println("attaque");
	}




	private void chercherCheminSecure() {
		
		/* Il faut regarder les deplacements possibles selon les collisions avec les blocks */
		/* Il faut choisir une destination selon celles possibles et au mieux */
		//System.out.println("defense");
	}








	public void update(double time) {
		super.update(time);
			
			situation();
			
			/* Mettre ici les actions que l'IA fait */
			
			// move();
			// poserBombe();
			
			
			
		
			
		
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

}
