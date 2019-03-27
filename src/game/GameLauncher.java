package game;

public class GameLauncher {

	public GameLauncher() {
		new Window();
	}
	
	public static void main(String[] args) {
		new GameLauncher();
	}
}

//TODO: Mettre les collisions avec les bombes genre quand le bomber est sur une bombe il peut partir meme si elle s'apprete a exploser
//TODO: Faire les bombes en réseau (si une explose a coté d'une autre : la faire exploser)
//TODO: Changer la taille de la fenetre quand on lance le jeu pour pouvoir lire les infos a droite (mettre la size de la fen en variable)
//TODO: Regler le pb de la pause qui reinitialise la partie