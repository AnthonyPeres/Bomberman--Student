package game.entity.IA;

import java.util.ArrayList;

import game.util.AABB;
import game.util.Vector2f;

public class Sommet {

	private int caseX;
	private int caseY;
	
	private Sommet Predecesseur;
	private ArrayList<Sommet> Successeur;
	private AABB saCase;
	private int coutG = 0;
	private int coutH = 0;
	private int coutTotal;
	
	public Sommet(Sommet Predecesseur, Sommet Successeur, AABB Case) {
		this.Predecesseur = Predecesseur;
		this.Successeur = new ArrayList<Sommet>();
		this.saCase = Case;
		
		this.setCaseX((int) Case.getPos().x / 50);
		this.setCaseY((int) Case.getPos().y / 50);
		
		if(this.Predecesseur != null) {this.Predecesseur.AjouterSuccesseur(this);}
		
	}
	
	public Sommet(Sommet Predecesseur, Sommet Successeur, int x, int y) {
		this.Predecesseur = Predecesseur;
		this.Successeur = new ArrayList<Sommet>();
		
		this.saCase = new AABB(new Vector2f(x*50, y*50), 50, 50);
		
		this.setCaseX(x);
		this.setCaseY(y);
	}
	
	
	public AABB getSaCase() {return this.saCase;}
	
	public void AjouterSuccesseur(Sommet successeur) {Successeur.add(successeur);}
	
	
	public Sommet getPredecesseur() {return Predecesseur;}
	public ArrayList<Sommet> getSuccesseur() {return Successeur;}
	public int getCoutG() {return coutG;}
	public int getCoutH() {return coutH;}
	public int getCoutTotal() {return coutTotal;}


	public int getCaseX() {
		return caseX;
	}

	public int getCaseY() {
		return caseY;
	}

	public void setCaseY(int caseY) {
		this.caseY = caseY;
	}

	public void setCaseX(int caseX) {
		this.caseX = caseX;
	}

	public void setPredecesseur(Sommet predecesseur) {Predecesseur = predecesseur;}
	public void setSuccesseur(ArrayList<Sommet> successeur) {Successeur = successeur;}
	public void setCoutG(int coutG) {this.coutG = coutG;}
	public void setCoutH(int coutH) {this.coutH = coutH;}
	public void setCoutTotal() {this.coutTotal = getCoutG() + getCoutH();}
}
