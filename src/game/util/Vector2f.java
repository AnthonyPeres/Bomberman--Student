package game.util;

public class Vector2f {

	/** Variables */
	
	public float x;
	public float y;
	public static float worldX;
	public static float worldY;
	
	/** Constructeur */
	
	public Vector2f() {
		x = 0;
		y = 0;
	}

	public Vector2f(Vector2f vec) {
		new Vector2f(vec.x, vec.y);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	/** Méthodes */
	
	public void addX(float f) { x += f; }
	public void addY(float f) { y += f; }
	public String toString() {return x + ", " + y;}
	
	/** Accesseurs */
	
	public static float getWorldX() {return worldX;}
	public static float getWorldY() {return worldY;}
	public Vector2f getWorldVar() {return new Vector2f(x - getWorldX(), y - worldY);}
	
	/** Mutateurs */
	
	public static void setWorldX(float worldX) {Vector2f.worldX = worldX;}
	public static void setWorldY(float worldY) {Vector2f.worldY = worldY;}
	public void setX(float f) { x = f ; }
	public void setY(float f) { y = f ; }
	
	public void setVector(Vector2f vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void setVector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static void setWorldVar(float x, float y) {
		setWorldX(x);
		worldY = y;
	}
}