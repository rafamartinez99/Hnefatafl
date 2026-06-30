package game;

import java.util.Scanner;

import main.ConsoleInput;
import model.Board;
import model.Movement;

/**
 * La clase {@code Game} gestiona todo el proceso del juego para HNEFATAFL,
 * incluyendo la configuración de jugadores, progresión del juego y asignación de roles.
 * Controla el flujo del juego y maneja las interacciones con el usuario.
 *
 * @version 15.0
 * @since 1.0
 * @see Player
 * @see Board
 * @see Movement
 * @author Rafa Martinez Manzanares
 */
public class Game {
	private Player attackerPlayer; // Jugador en el rol de atacante
	private Player defenderPlayer; // Jugador en el rol de defensor
	private Board board; // Tablero del juego
	private ConsoleInput consoleInput; // Utilidad de entrada para manejar la entrada del usuario
	private Player playerWithNoMoves; // Jugador que se ha quedado sin movimientos posibles, si lo hubiera

	/**
	 * Construye una nueva instancia de Game, inicializa el tablero del juego y las utilidades de entrada.
	 */
	public Game() {
		this.board = new Board();
		this.consoleInput = new ConsoleInput(new Scanner(System.in));
	}

	/**
	 * Inicia el juego configurando a los jugadores, asignando roles y mostrando la información inicial.
	 */
	private void startGame() {
		System.out.println("Bienvenido al juego HNEFATAFL!");
		this.attackerPlayer = configurePlayer(1, "Atacante");
		this.defenderPlayer = configurePlayer(2, "Defensor");
		if (attackerPlayer instanceof Human && defenderPlayer instanceof Human) {
			assignRoles();
		}
		showPlayerInformation();
	}

	/**
	 * Configura a un jugador con un número especificado y un rol.
	 *
	 * @param playerNumber el número secuencial del jugador (1 o 2).
	 * @param defaultRole el rol por defecto ("Atacante" o "Defensor").
	 * @return Player el jugador configurado, ya sea Humano o Máquina basado en la entrada del usuario.
	 */
	private Player configurePlayer(int playerNumber, String defaultRole) {
		System.out.print("Jugador " + playerNumber + ", eres humano o maquina? (h/m): ");
		char playerType = consoleInput.readChar("[hmHM]");
		String name = "";
		boolean isHuman = Character.toLowerCase(playerType) == 'h';
		if (isHuman) {
			System.out.print("Introduce el nombre del jugador " + playerNumber + ": ");
			name = consoleInput.readString();
		}
		return isHuman ? new Human(name, defaultRole, consoleInput) : new Machine(defaultRole);
	}

	/**
	 * Asigna roles entre el atacante y el defensor basado en la elección del jugador.
	 */
	private void assignRoles() {
		System.out.println(attackerPlayer.getName() + ", quieres ser el atacante o el defensor? (a/d): ");
		char choice = consoleInput.readChar("[adAD]");
		if (Character.toLowerCase(choice) != 'a') {
			Player temp = attackerPlayer;
			attackerPlayer = defenderPlayer;
			defenderPlayer = temp;
		}
		attackerPlayer.setRole("Atacante");
		defenderPlayer.setRole("Defensor");
	}

	/**
	 * Muestra la información de ambos jugadores incluyendo sus roles.
	 */
	private void showPlayerInformation() {
		System.out.println("\nInformacion de los jugadores:");
		System.out.println(attackerPlayer.getName() + " es el atacante.");
		System.out.println(defenderPlayer.getName() + " es el defensor.\n");
	}

	/**
	 * Controla el juego, incluyendo los turnos y verificando el estado del juego para las condiciones de victoria.
	 */
	public void play() {
		boolean keepPlaying = true;
		while (keepPlaying) {
			startGame();
			board.initializeBoard();
			boolean attackerTurn = true;
			playerWithNoMoves = null;
			board.display();
			while (!board.isWhiteVictory() && !board.isBlackVictory() && playerWithNoMoves == null) {
				processTurn(attackerTurn);
				attackerTurn = !attackerTurn;
			}
			endGame();
			keepPlaying = askRestartGame();
		}
		System.out.println("Gracias por jugar! Hasta la próxima.");
	}

	/**
	 * Procesa el turno para el jugador actual basado en la bandera de turno.
	 *
	 * @param attackerTurn true si es el turno del atacante, false si es del defensor.
	 */
	private void processTurn(boolean attackerTurn) {
		Player currentPlayer = attackerTurn ? attackerPlayer : defenderPlayer;
		boolean validMove;
		do {
			try {
				Movement move = currentPlayer.decideMove(board);
				if (move == null) {
					playerWithNoMoves = currentPlayer;
					return;
				}
				validMove = board.makeMove(move, currentPlayer);
				if (validMove) {
					System.out.println("\n" + currentPlayer.getName() + " (" + currentPlayer.getRole() + ") mueve la pieza de "
							+ move.getInitialPosition() + " a " + move.getFinalPosition() + ".");
					board.display();
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				validMove = false;
			}
		} while (!validMove);
	}

	/**
	 * Finaliza el juego y anuncia al ganador.
	 */
	private void endGame() {
		if (board.isWhiteVictory()) {
			System.out.println("Victoria para " + defenderPlayer.getName() + "!");
		} else if (board.isBlackVictory()) {
			System.out.println("Victoria para " + attackerPlayer.getName() + "!");
		} else if (playerWithNoMoves != null) {
			Player winner = playerWithNoMoves == attackerPlayer ? defenderPlayer : attackerPlayer;
			System.out.println(playerWithNoMoves.getName() + " no tiene movimientos posibles. Victoria para " + winner.getName() + "!");
		}
	}

	/**
	 * Pregunta al usuario si desea reiniciar el juego.
	 *
	 * @return true si el usuario quiere reiniciar, false de lo contrario.
	 */
	private boolean askRestartGame() {
		System.out.print("Quieres jugar otra partida? (s/n): ");
		consoleInput.cleanInput();
		char response = consoleInput.readChar("[snSN]");
		return Character.toLowerCase(response) == 's';
	}
}
