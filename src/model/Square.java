package model;

import java.util.Optional;

/**
 * Representa una casilla en el tablero de juego. Cada casilla tiene una posición, una pieza opcional,
 * y un color de fondo. Esta clase proporciona métodos para gestionar la pieza en la casilla
 * y para recuperar varias propiedades de la casilla.
 *
 * @version 15.0
 * @since 1.0
 * @see Position
 * @see Piece
 * @see Color
 * @author Rafa Martinez Manzanares
 */
public class Square {
    private final Position position;
    private Piece piece;
    private String backgroundColor = Color.RESET;
    private boolean recentlyCaptured = false;

    /**
     * Construye una Casilla con una posición especificada.
     *
     * @param position La posición de la casilla en el tablero.
     */
    public Square(Position position) {
        this.position = position;
        this.piece = null;
    }

    /**
     * Establece el estado de captura reciente de la casilla.
     *
     * @param captured true si la casilla fue recientemente el sitio de una pieza capturada, false de lo contrario.
     */
    public void setRecentlyCaptured(boolean captured) {
        recentlyCaptured = captured;
    }

    /**
     * Verifica si la casilla fue recientemente el sitio de una pieza capturada.
     *
     * @return true si la casilla fue recientemente capturada, false de lo contrario.
     */
    public boolean isRecentlyCaptured() {
        return recentlyCaptured;
    }

    /**
     * Devuelve la pieza en la casilla.
     *
     * @return La pieza en la casilla, o null si no hay pieza presente.
     */
    public Piece obtainPiece(){
        return piece;
    }

    /**
     * Devuelve un Optional que contiene la pieza en la casilla, o un Optional vacío si no hay pieza presente.
     *
     * @return Un Optional que contiene la pieza en la casilla.
     */
    public Optional<Piece> getPiece() {
        return Optional.ofNullable(piece);
    }

    /**
     * Coloca una pieza en la casilla si actualmente está vacía.
     *
     * @param newPiece La pieza a colocar en la casilla.
     */
    public void placePiece(Piece newPiece) {
        if (this.piece == null) {
            this.piece = newPiece;
        }
    }

    /**
     * Elimina la pieza de la casilla y la devuelve.
     *
     * @return La pieza que fue eliminada de la casilla.
     */
    public Piece removePiece() {
        Piece previousPiece = this.piece;
        this.piece = null;
        return previousPiece;
    }

    /**
     * Devuelve el color de fondo de la casilla.
     *
     * @return El color de fondo de la casilla.
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Establece el color de fondo de la casilla.
     *
     * @param color El nuevo color de fondo de la casilla.
     */
    public void setBackgroundColor(String color) {
        this.backgroundColor = color;
    }

    /**
     * Verifica si la casilla es la posición del trono.
     *
     * @return true si la casilla es el trono, false de lo contrario.
     */
    public boolean isThrone() {
        int center = 5;
        return this.position.getRow() == center && this.position.getColumn() == center;
    }

    /**
     * Verifica si la casilla es una de las posiciones de esquina.
     *
     * @return true si la casilla es una esquina, false de lo contrario.
     */
    public boolean isCorner() {
        int dimension = 10;
        return (this.position.getRow() == 0 || this.position.getRow() == dimension)
                && (this.position.getColumn() == 0 || this.position.getColumn() == dimension);
    }

    /**
     * Devuelve una representación en cadena de la casilla para mostrar en la consola.
     * La representación incluye la pieza en la casilla (si la hay) y el color de fondo.
     *
     * @param board El tablero que contiene la casilla, usado para verificar condiciones especiales.
     * @return Una representación en cadena de la casilla.
     */
    public String getConsoleRepresentation(Board board) {
        String baseRepresentation;
        if (piece != null) {
            baseRepresentation = " " + piece.getRepresentation() + " " + Color.RESET;
        } else if (recentlyCaptured || isCorner() || (board.getKingCapturedPosition() != null && board.getKingCapturedPosition().equals(position))) {
            baseRepresentation = Color.RED_BACKGROUND + Color.RED + " ♙ " + Color.RESET;
        } else {
            baseRepresentation = switch (backgroundColor) {
                case Color.CYAN_BACKGROUND -> Color.CYAN_BACKGROUND + Color.CYAN + " ♙ " + Color.RESET;
                case Color.BLUE_BACKGROUND -> Color.BLUE_BACKGROUND + Color.BLUE + " ♙ " + Color.RESET;
                case Color.YELLOW_BACKGROUND -> Color.YELLOW_BACKGROUND + Color.YELLOW + " ♙ " + Color.RESET;
                default ->
                        backgroundColor + Color.PURPLE + " ♙ " + Color.RESET;
            };
        }
        return backgroundColor + baseRepresentation + Color.RESET;
    }
}
