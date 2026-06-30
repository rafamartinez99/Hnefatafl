package model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import enums.Directions;
import enums.PieceType;
import game.Player;

/**
 * Representa el tablero de juego para un juego de tablero estratégico. Esta clase gestiona el
 * estado e interacciones dentro del tablero, como la colocación de piezas, movimientos
 * y la lógica de captura.
 *
 * @version 15.0
 * @since 1.0
 * @see Square
 * @see Piece
 * @see Movement
 * @see Player
 * @see Position
 * @author Rafa Martinez Manzanares
 */
public class Board {
    private Square[][] squares;
    private static final int DIMENSION = 11;
    private Position lastInitialPosition;
    private Position lastFinalPosition;
    private boolean kingInThrone = false;
    private boolean kingCaptured = false;
    private Position kingCapturedPosition;

    /**
     * Construye un nuevo tablero con una dimensión predeterminada de 11x11 casillas.
     */
    public Board() {
        squares = new Square[DIMENSION][DIMENSION];
    }

    /**
     * Inicializa el tablero con configuraciones predeterminadas y coloca las piezas en sus
     * posiciones iniciales para un nuevo juego.
     */
    public void initializeBoard() {
        kingInThrone = false;
        kingCaptured = false;
        kingCapturedPosition = null;

        List<Position> attackerPositions = Arrays.asList(
            new Position(3, 0), new Position(4, 0), new Position(5, 0), new Position(6, 0), new Position(7, 0),
            new Position(5, 1), new Position(3, 10), new Position(4, 10), new Position(5, 10), new Position(6, 10),
            new Position(7, 10), new Position(5, 9), new Position(0, 3), new Position(0, 4), new Position(0, 5),
            new Position(0, 6), new Position(0, 7), new Position(1, 5), new Position(10, 3), new Position(10, 4),
            new Position(10, 5), new Position(10, 6), new Position(10, 7), new Position(9, 5));

        List<Position> defenderPositions = Arrays.asList(
            new Position(5, 4), new Position(4, 5), new Position(5, 5), new Position(6, 5), new Position(5, 6),
            new Position(5, 7), new Position(3, 5), new Position(4, 6), new Position(6, 4), new Position(6, 6),
            new Position(4, 4), new Position(7, 5), new Position(5, 3));

        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                Position currentPosition = new Position(i, j);
                Square currentSquare = new Square(currentPosition);
                String background = (i + j) % 2 == 0 ? Color.BLUE_BACKGROUND : Color.CYAN_BACKGROUND;

                if (currentSquare.isThrone()) {
                    background = Color.YELLOW_BACKGROUND;
                } else if (currentSquare.isCorner()) {
                    background = Color.RED_BACKGROUND;
                }

                squares[i][j] = currentSquare;
                squares[i][j].setBackgroundColor(background);

                if (attackerPositions.contains(currentPosition)) {
                    squares[i][j].placePiece(new Piece(currentPosition, PieceType.ATTACKER));
                } else if (defenderPositions.contains(currentPosition)) {
                    PieceType pieceType = (currentSquare.isThrone()) ? PieceType.KING : PieceType.DEFENDER;
                    squares[i][j].placePiece(new Piece(currentPosition, pieceType));
                }
            }
        }
    }

    /**
     * Muestra el estado actual del tablero en la consola, resaltando condiciones especiales
     * como capturas recientes o movimientos de piezas.
     */
    public void display() {
        System.out.print("  ");
        int[] spacesBeforeNumber = { 2, 2, 3, 2, 2, 3, 2, 2, 2, 2, 2 };

        for (int i = 1; i <= DIMENSION; i++) {
            System.out.printf("%" + spacesBeforeNumber[i - 1] + "s%d", "", i);
        }
        System.out.println();

        for (int row = 0; row < DIMENSION; row++) {
            System.out.printf("%2d ", row + 1);
            for (int column = 0; column < DIMENSION; column++) {
                Square square = squares[row][column];
                String originalColor = square.getBackgroundColor();

                if (lastInitialPosition != null && lastInitialPosition.equals(new Position(row, column))
                        || lastFinalPosition != null && lastFinalPosition.equals(new Position(row, column))) {
                    square.setBackgroundColor(Color.PURPLE_BACKGROUND);
                } else if (square.isRecentlyCaptured() || (getKingCapturedPosition() != null && getKingCapturedPosition().equals(new Position(row, column)))) {
                    square.setBackgroundColor(Color.RED_BACKGROUND);
                }

                System.out.print(square.getConsoleRepresentation(this));
                square.setBackgroundColor(originalColor);
                square.setRecentlyCaptured(false);
            }
            System.out.println();
        }
    }

    /**
     * Intenta ejecutar un movimiento en el tablero basado en el movimiento proporcionado y el jugador.
     *
     * @param movement El intento de movimiento del jugador.
     * @param player El jugador que realiza el movimiento.
     * @return true si el movimiento fue exitoso, false en caso contrario.
     */
    public boolean makeMove(Movement movement, Player player) {

        if (!movement.isLegal(this, player)) {
            return false;
        }

        if (movement.execute(this)) {
            this.lastInitialPosition = movement.getInitialPosition();
            this.lastFinalPosition = movement.getFinalPosition();
            return true;
        }

        return false;
    }

    /**
     * Obtiene la pieza en una posición especificada del tablero.
     *
     * @param position La posición para verificar la pieza.
     * @return La pieza en la posición dada, o null si no hay ninguna pieza presente.
     */
    public Piece getPieceAt(Position position) {
        if (isValidPosition(position)) {
            return squares[position.getRow()][position.getColumn()].getPiece().orElse(null);
        }
        return null;
    }


    /**
     * Verifica si una posición dada está dentro del rango válido de las dimensiones del tablero.
     *
     * @param position La posición a validar.
     * @return true si la posición es válida, false de lo contrario.
     */
    public boolean isValidPosition(Position position) {
        return position.getRow() >= 0 && position.getRow() < DIMENSION && position.getColumn() >= 0
                && position.getColumn() < DIMENSION;
    }

    /**
     * Verifica si el camino entre dos posiciones está libre de piezas.
     *
     * @param start La posición inicial del camino.
     * @param end La posición final del camino.
     * @return true si el camino está libre, false si hay al menos una pieza bloqueando el camino.
     */
    public boolean isFreePath(Position start, Position end) {
        int rowStep = Integer.compare(end.getRow(), start.getRow());
        int columnStep = Integer.compare(end.getColumn(), start.getColumn());

        int currentRow = start.getRow() + rowStep;
        int currentColumn = start.getColumn() + columnStep;

        while (currentRow != end.getRow() || currentColumn != end.getColumn()) {
            if (squares[currentRow][currentColumn].getPiece().isPresent()) {
                return false;
            }
            currentRow += rowStep;
            currentColumn += columnStep;
        }

        return squares[end.getRow()][end.getColumn()].getPiece().isEmpty();
    }

    /**
     * Determina si una pieza en una posición especificada está completamente rodeada por otras piezas.
     *
     * @param position La posición de la pieza a verificar.
     * @return true si la pieza está rodeada por todos los lados posibles, false de lo contrario.
     */
    public boolean isSurrounded(Position position) {
        for (Directions direction : Directions.values()) {
            Position adjacent = getAdjacentPosition(position, direction);

            if (isValidPosition(adjacent) && isPositionFree(adjacent)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica si una posición especificada en el tablero está desocupada.
     *
     * @param position La posición a verificar.
     * @return true si no hay una pieza en la posición, false si hay una pieza.
     */
    public boolean isPositionFree(Position position) {
        return isValidPosition(position) && getSquare(position).getPiece().isEmpty();
    }

    /**
     * Devuelve la casilla en una posición especificada.
     *
     * @param position La posición de la casilla.
     * @return La casilla en la posición especificada.
     */
    public Square getSquare(Position position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Elimina una pieza de una posición específica en el tablero.
     *
     * @param position La posición de la cual remover la pieza.
     */
    public void removePiece(Position position) {
        Square square = squares[position.getRow()][position.getColumn()];
        Optional<Piece> pieceOpt = square.getPiece();
        if (pieceOpt.isPresent()) {
            square.removePiece();
        }
    }

    /**
     * Devuelve la dimensión del tablero.
     *
     * @return La dimensión del tablero.
     */
    public int getDimension() {
        return DIMENSION;
    }

    /**
     * Establece el estado de captura del rey y su posición si ha sido capturado.
     *
     * @param value true si el rey ha sido capturado, false de lo contrario.
     * @param position La posición donde fue capturado el rey, null si no ha sido capturado.
     */
    public void setKingCaptured(boolean value, Position position) {
        kingCaptured = value;
        kingCapturedPosition = value ? position : null;
    }

    /**
     * Recupera la posición donde fue capturado el rey.
     *
     * @return La posición del rey capturado, o null si el rey no ha sido capturado.
     */
    public Position getKingCapturedPosition() {
        return kingCapturedPosition;
    }

    /**
     * Establece el estado que indica si el rey está en la posición del trono.
     *
     * @param value true si el rey está en el trono, false de lo contrario.
     */
    public void setKingInThrone(boolean value) {
        kingInThrone = value;
    }

    /**
     * Verifica las condiciones de captura en una dirección dada después de que una pieza se ha movido.
     *
     * @param finalPosition La posición final de la pieza movida.
     * @param direction La dirección para verificar las capturas.
     * @param movedPiece La pieza que se ha movido.
     */
    public void checkCaptureInDirection(Position finalPosition, Directions direction, Piece movedPiece) {
        Position firstAdjacentPosition = getAdjacentPosition(finalPosition, direction);
        Position secondAdjacentPosition = getAdjacentPosition(firstAdjacentPosition, direction);

        if (isValidPosition(firstAdjacentPosition)) {
            Piece adjacentPiece = getPieceAt(firstAdjacentPosition);

            if (adjacentPiece != null) {
                if (adjacentPiece.getType() == PieceType.KING) {
                    checkKingCapture(movedPiece, firstAdjacentPosition, secondAdjacentPosition);
                } else {
                    checkNormalCapture(movedPiece, adjacentPiece, firstAdjacentPosition, secondAdjacentPosition);
                }
            }
        }
    }

    /**
     * Verifica si un rey está capturado basado en las piezas adyacentes.
     *
     * @param movedPiece La pieza que inicia la captura.
     * @param firstAdjacentPosition La posición adyacente al rey.
     * @param secondAdjacentPosition La posición siguiente a la primera posición adyacente.
     */
    private void checkKingCapture(Piece movedPiece, Position firstAdjacentPosition, Position secondAdjacentPosition) {
        if (isValidPosition(secondAdjacentPosition)) {
            Piece opposingPiece = getPieceAt(secondAdjacentPosition);

            if (opposingPiece != null && opposingPiece.getType() == movedPiece.getType()) {
                if (isSurroundedByEnemies(firstAdjacentPosition, movedPiece)) {
                    removePiece(firstAdjacentPosition);
                    setKingCaptured(true, firstAdjacentPosition);
                }
            } else if (!isValidPosition(secondAdjacentPosition) || getSquare(secondAdjacentPosition).isCorner() || getSquare(secondAdjacentPosition).isThrone()) {
                if (isSurroundedByEnemies(firstAdjacentPosition, movedPiece)) {
                    removePiece(firstAdjacentPosition);
                    setKingCaptured(true, firstAdjacentPosition);
                }
            }
        }
    }

    /**
     * Verifica la captura de una pieza regular basada en las piezas adyacentes.
     *
     * @param movedPiece La pieza que inicia la captura.
     * @param adjacentPiece La pieza que se captura.
     * @param firstAdjacentPosition La posición de la pieza que se captura.
     * @param secondAdjacentPosition La posición siguiente a la pieza que se captura.
     */
    private void checkNormalCapture(Piece movedPiece, Piece adjacentPiece, Position firstAdjacentPosition, Position secondAdjacentPosition) {
        if (isValidPosition(secondAdjacentPosition)) {
            Piece opposingPiece = getPieceAt(secondAdjacentPosition);

            if (opposingPiece != null && opposingPiece.getType() == movedPiece.getType() && adjacentPiece.getType() != movedPiece.getType()) {
                removePiece(firstAdjacentPosition);
                getSquare(firstAdjacentPosition).setRecentlyCaptured(true);
            }
        }
    }

    /**
     * Determina si una posición está rodeada por piezas enemigas.
     *
     * @param position La posición a verificar.
     * @param movedPiece La pieza que se movió para iniciar la verificación.
     * @return true si está rodeada por piezas enemigas, false de lo contrario.
     */
    private boolean isSurroundedByEnemies(Position position, Piece movedPiece) {
        int enemyPiecesCount = 0;
        for (Directions direction : Directions.values()) {
            Position adjacentPosition = getAdjacentPosition(position, direction);
            if (isValidPosition(adjacentPosition)) {
                Piece adjacentPiece = getPieceAt(adjacentPosition);
                if (adjacentPiece != null && adjacentPiece.getType() != movedPiece.getType()) {
                    enemyPiecesCount++;
                }
            }
        }
        return enemyPiecesCount == 4 || (getSquare(position).isThrone() && enemyPiecesCount >= 3) ||
               (getSquare(position).isCorner() && enemyPiecesCount == 2);
    }

    /**
     * Obtiene la posición adyacente a una posición dada en una dirección especificada.
     *
     * @param position La posición actual.
     * @param direction La dirección para encontrar la posición adyacente.
     * @return La posición adyacente.
     */
    private Position getAdjacentPosition(Position position, Directions direction) {
        return switch (direction) {
            case UP -> new Position(position.getRow() - 1, position.getColumn());
            case DOWN -> new Position(position.getRow() + 1, position.getColumn());
            case LEFT -> new Position(position.getRow(), position.getColumn() - 1);
            case RIGHT -> new Position(position.getRow(), position.getColumn() + 1);
        };
    }

    /**
     * Crea una copia independiente del tablero, con piezas y casillas nuevas pero el mismo
     * estado lógico. Se usa para simular movimientos (por ejemplo, en la IA) sin alterar la
     * partida real.
     *
     * @return Una copia profunda de este tablero.
     */
    public Board copy() {
        Board copy = new Board();
        for (int row = 0; row < DIMENSION; row++) {
            for (int column = 0; column < DIMENSION; column++) {
                Position position = new Position(row, column);
                Square originalSquare = this.squares[row][column];
                Square newSquare = new Square(position);
                newSquare.setBackgroundColor(originalSquare.getBackgroundColor());

                Optional<Piece> piece = originalSquare.getPiece();
                if (piece.isPresent()) {
                    newSquare.placePiece(new Piece(position, piece.get().getType()));
                }
                copy.squares[row][column] = newSquare;
            }
        }
        copy.kingInThrone = this.kingInThrone;
        copy.kingCaptured = this.kingCaptured;
        copy.kingCapturedPosition = this.kingCapturedPosition;
        return copy;
    }

    /**
     * Verifica si el juego ha sido ganado por el lado con el rey.
     *
     * @return true si el rey está en su trono y seguro, false de lo contrario.
     */
    public boolean isWhiteVictory() {
        return kingInThrone;
    }

    /**
     * Verifica si el juego ha sido ganado por el lado opuesto al rey.
     *
     * @return true si el rey ha sido capturado, false de lo contrario.
     */
    public boolean isBlackVictory() {
        return kingCaptured;
    }
}
