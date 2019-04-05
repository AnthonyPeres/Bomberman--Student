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
//TODO: Faire en sorte que la mineBomb explose quand un bomber marche dessus
//TODO: Faire en sorte que la bombe chercheuse suit le bomber le plus proche ( sauf celui qui la posé )


//TODO: Remettre le random des bonus a %3
//TODO: Faire le bonus fireUp
//TODO: Remettre le nombre de fps visible dans le gamepanel
//TODO: Modifier l'animation selon le type de la bombe (fonction explose de bombe)