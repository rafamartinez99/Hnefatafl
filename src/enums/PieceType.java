package enums;

import model.Color;

/**
 * Enumeración que representa los diferentes tipos de piezas en el juego.
 * Cada tipo de pieza está asociado con un color y un carácter que la representa visualmente en el tablero.
 *
 * @version 15.0
 * @since 1.0
 * @see Color
 * @author Rafa Martinez Manzanares
 */
public enum PieceType {
    /**
     * Representa al Rey con el color blanco y el carácter Unicode '♔' (U+2654).
     */
    KING(Color.WHITE, '♔'),

    /**
     * Representa al Defensor con el color blanco y el carácter Unicode '♙' (U+265F).
     */
    DEFENDER(Color.WHITE, '♙'),

    /**
     * Representa al Atacante con el color negro y el carácter Unicode '♙' (U+265F).
     */
    ATTACKER(Color.BLACK, '♙');

    private final String color;
    private final char character;

    /**
     * Constructor para inicializar un tipo de pieza con su color y carácter correspondiente.
     *
     * @param color El color de la pieza, utilizado para diferenciar equipos o roles dentro del juego.
     * @param character El carácter Unicode que representa visualmente a la pieza en el tablero.
     */
    PieceType(String color, char character) {
        this.color = color;
        this.character = character;
    }

    /**
     * Obtiene el carácter que representa a la pieza.
     *
     * @return El símbolo del carácter de la pieza.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Obtiene el color asociado con la pieza.
     *
     * @return El color de la pieza.
     */
    public String getColor() {
        return color;
    }
}