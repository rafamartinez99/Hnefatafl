package model;

import enums.PieceType;

/**
 * Representa una pieza del juego en el tablero, incluyendo su posición y tipo.
 * Cada pieza tiene un tipo específico que determina su comportamiento y características.
 *
 * @version 15.0
 * @since 1.0
 * @see Position
 * @see PieceType
 * @author Rafa Martinez Manzanares
 */
public class Piece {
    private Position position;
    private PieceType type;

    /**
     * Construye una Pieza con una posición y un tipo especificados.
     *
     * @param position La posición inicial de la pieza en el tablero.
     * @param type El tipo de la pieza, que determina su comportamiento y características.
     */
    public Piece(Position position, PieceType type) {
        this.position = position;
        this.type = type;
    }

    /**
     * Devuelve la posición actual de la pieza en el tablero.
     *
     * @return La posición de la pieza.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Establece una nueva posición para la pieza en el tablero.
     *
     * @param position La nueva posición de la pieza.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Devuelve el tipo de la pieza.
     *
     * @return El tipo de la pieza.
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Devuelve una representación en cadena de la pieza, incluyendo su color y carácter,
     * según lo define el PieceType.
     *
     * @return Una representación en cadena de la pieza.
     */
    public String getRepresentation() {
        return type.getColor() + type.getCharacter();
    }
}
