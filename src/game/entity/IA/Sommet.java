package game.entity.IA;

import game.util.AABB;
import game.util.Vector2f;

public class Sommet {

	private Sommet Predecesseur;
	private Sommet Successeur;
	
	private AABB Case;
	
	private int coutG;
	private int coutH;
	private int coutTotal;
	
	
	public Sommet(Sommet Predecesseur, Sommet Successeur, AABB Case) {
		
		this.Predecesseur = Predecesseur;
		this.Successeur = Successeur;
		this.Case = Case;
	}
	
	
	public Sommet(Sommet Predecesseur, Sommet Successeur, int x, int y, int size) {
		
		this.Predecesseur = Predecesseur;
		this.Successeur = Successeur;
		this.Case = new AABB(new Vector2f(x*size, y*size), size, size);
		
	}
	
	
	public Sommet(Sommet Successeur) {
		
	}


	public Sommet getPredecesseur() {
		return Predecesseur;
	}


	public Sommet getSuccesseur() {
		return Successeur;
	}


	public AABB getCase() {
		return Case;
	}


	public int getCoutG() {
		return coutG;
	}


	public int getCoutH() {
		return coutH;
	}


	public int getCoutTotal() {
		return coutTotal;
	}


	public void setPredecesseur(Sommet predecesseur) {
		Predecesseur = predecesseur;
	}


	public void setSuccesseur(Sommet successeur) {
		Successeur = successeur;
	}


	public void setCase(AABB case1) {
		Case = case1;
	}


	public void setCoutG(int coutG) {
		this.coutG = coutG;
	}


	public void setCoutH(int coutH) {
		this.coutH = coutH;
	}


	public void setCoutTotal(int coutTotal) {
		this.coutTotal = coutTotal;
	}
	
	
	
	
}
