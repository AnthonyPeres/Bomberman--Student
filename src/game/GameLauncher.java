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
//TODO: Regler le pb de la pause qui reinitialise la partie
//TODO: Faire en sorte que la mineBomb explose quand un bomber marche dessus
//TODO: Faire en sorte que la bombe chercheuse suit le bomber le plus proche ( sauf celui qui la posé )

//TODO: Il faut update tout les bonus et quand l'un deux a effectuer son effet il passe un boolean effectuer a true et dans le update si effectuer = true il sefface de la map, pour cela il faut que le bonus est une entite qui est set par rapport a celle qui marche dessus 
//TODO: Faire le bonus fireUp
//TODO: Remettre le nombre de fps visible dans le gamepanel
//TODO: Modifier l'animation selon le type de la bombe (fonction explose de bombe)