package game;

import model.Board;
import model.Movement;

/**
 * Clase abstracta que representa un jugador en el juego. Esta clase proporciona un marco
 * de funcionalidad para los jugadores, incluyendo el manejo de piezas y la realización de movimientos
 * en el tablero del juego. El comportamiento específico para realizar movimientos se implementa
 * en las subclases, permitiendo diferentes tipos de jugadores (por ejemplo, Humano, IA).
 *
 * @version 15.0
 * @since 1.0
 * @see Movement
 * @see Board
 * @author Rafa Martinez Manzanares
 */
public abstract class Player {
    protected String name;  // Nombre del jugador.
    protected String role;  // Rol del jugador en el juego.

    /**
     * Construye un nuevo jugador con un nombre y un rol especificados.
     *
     * @param name El nombre del jugador.
     * @param role El rol del jugador (por ejemplo, "Atacante", "Defensor").
     */
    public Player(String name, String role) {
        this.name = name;
        this.role = role;
    }

    /**
     * Decide el próximo movimiento basado en el estado actual del tablero. Este método debe ser
     * implementado por las subclases para definir comportamientos específicos del jugador.
     *
     * @param board El tablero de juego en el que se realizará el movimiento.
     * @return Un objeto {@code Movement} que representa la decisión del jugador.
     */
    public abstract Movement decideMove(Board board);

    /**
     * Devuelve el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el rol del jugador en el juego.
     *
     * @return El rol del jugador.
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del jugador en el juego.
     *
     * @param role El nuevo rol que se asignará al jugador.
     */
    public void setRole(String role) {
        this.role = role;
    }
}