package main;

import game.Game;

/**
 * La clase {@code Hnefatafl} es el punto de entrada del juego HNEFATAFL.
 * Inicia y controla el flujo principal del juego a través de su método {@code main}.
 *
 * @version 15.0
 * @since 1.0
 * @see Game
 * @author Rafa Martinez Manzanares
 */
public class Hnefatafl {

	/**
	 * Inicia y ejecuta el juego. Este método crea una instancia de {@code Game}
	 * y llama al método {@code play} para comenzar el juego.
	 */
	private void show() {
		Game game = new Game();
		game.play();
	}

	/**
	 * Método principal que se ejecuta al iniciar la aplicación. Este método es el
	 * punto de entrada del programa y se encarga de iniciar el juego llamando al
	 * método {@code show}.
	 *
	 * @param args Los argumentos de la línea de comandos. No se utilizan en esta aplicación.
	 */
	public static void main(String[] args) {
		new Hnefatafl().show();
	}
}