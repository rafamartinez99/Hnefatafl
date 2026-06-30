package model;

import enums.Directions;
import enums.PieceType;
import game.Player;

/**
 * Representa el movimiento de una pieza en un tablero de juego desde una posición inicial hasta una posición final.
 * Esta clase encapsula toda la funcionalidad relacionada con el movimiento, incluyendo la validación
 * y la ejecución.
 *
 * @version 15.0
 * @since 1.0
 * @see Piece
 * @see Position
 * @see Board
 * @see Player
 * @author Rafa Martinez Manzanares
 */
public class Movement {
    private Piece piece;
    private Position initialPosition;
    private Position finalPosition;

    /**
     * Construye un objeto Movement con la pieza especificada, la posición inicial y la posición final.
     *
     * @param piece La pieza que se mueve.
     * @param initialPosition La posición inicial de la pieza.
     * @param finalPosition La posición final de la pieza después del movimiento.
     */
    public Movement(Piece piece, Position initialPosition, Position finalPosition) {
        this.piece = piece;
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }

    /**
     * Devuelve la posición inicial de la pieza antes del movimiento.
     *
     * @return La posición inicial de la pieza.
     */
    public Position getInitialPosition() {
        return initialPosition;
    }

    /**
     * Devuelve la posición final de la pieza después del movimiento.
     *
     * @return La posición final de la pieza.
     */
    public Position getFinalPosition() {
        return finalPosition;
    }

    /**
     * Ejecuta el movimiento en el tablero. Esto incluye mover la pieza desde la posición inicial
     * hasta la posición final y manejar cualquier condición especial como capturas o ocupación del trono.
     *
     * @param board El tablero en el que se ejecuta el movimiento.
     * @return true si el movimiento se ejecuta correctamente, false de lo contrario.
     */
    public boolean execute(Board board) {
        Piece originPiece = board.getSquare(initialPosition).removePiece();
        board.getSquare(finalPosition).placePiece(originPiece);
        originPiece.setPosition(finalPosition);

        if (originPiece.getType() == PieceType.KING) {
            if (board.getSquare(finalPosition).isCorner()) {
                board.setKingInThrone(true);
            }
        }

        for (Directions dir : Directions.values()) {
            board.checkCaptureInDirection(finalPosition, dir, originPiece);
        }

        return true;
    }

    /**
     * Determina si el movimiento es legal basado en las reglas del juego. Esto incluye comprobaciones para
     * movimiento en línea recta, claridad del camino y reglas específicas de las piezas como acceso al trono y las esquinas.
     *
     * @param board El tablero de juego en el que se verifica la legalidad del movimiento.
     * @param player El jugador que intenta el movimiento.
     * @return true si el movimiento es legal, false de lo contrario.
     */
    public boolean isLegal(Board board, Player player) {
        if (!isStraightLine(finalPosition)) {
            return false;
        }

        if (!board.isFreePath(initialPosition, finalPosition)) {
            return false;
        }

        if (board.isSurrounded(initialPosition)) {
            return false;
        }

        Square finalSquare = board.getSquare(finalPosition);
        if (finalSquare.isThrone() && piece.getType() != PieceType.KING) {
            return false;
        }

       if (finalSquare.isCorner() && piece.getType() != PieceType.KING) {
            return false;
        }

        return (player.getRole().equalsIgnoreCase("ATACANTE") && piece.getType() == PieceType.ATTACKER) || (player.getRole().equalsIgnoreCase("DEFENSOR") && (piece.getType() == PieceType.KING || piece.getType() == PieceType.DEFENDER));
    }

    /**
     * Devuelve el motivo por el que el movimiento no es legal, o {@code null} si es legal.
     * Pensado para dar retroalimentación al jugador humano sin ensuciar la salida de la IA.
     *
     * @param board El tablero de juego en el que se verifica la legalidad del movimiento.
     * @param player El jugador que intenta el movimiento.
     * @return Un mensaje explicando por qué el movimiento es ilegal, o {@code null} si es legal.
     */
    public String getIllegalReason(Board board, Player player) {
        if (!isStraightLine(finalPosition)) {
            return "El movimiento debe ser en linea recta.";
        }

        if (!board.isFreePath(initialPosition, finalPosition)) {
            return "El camino no está libre.";
        }

        if (board.isSurrounded(initialPosition)) {
            return "La pieza seleccionada está rodeada y no puede moverse. Elige otra pieza.";
        }

        Square finalSquare = board.getSquare(finalPosition);
        if (finalSquare.isThrone() && piece.getType() != PieceType.KING) {
            return "Solo el rey puede moverse al trono.";
        }

        if (finalSquare.isCorner() && piece.getType() != PieceType.KING) {
            return "Solo el rey puede moverse a las esquinas.";
        }

        if (!((player.getRole().equalsIgnoreCase("ATACANTE") && piece.getType() == PieceType.ATTACKER) || (player.getRole().equalsIgnoreCase("DEFENSOR") && (piece.getType() == PieceType.KING || piece.getType() == PieceType.DEFENDER)))) {
            return "Esa pieza no te pertenece.";
        }

        return null;
    }

    /**
     * Método auxiliar para determinar si el movimiento es a lo largo de una línea recta horizontal o vertical.
     *
     * @param finalPos La posición final para comparar con la posición inicial.
     * @return true si el movimiento es en línea recta, false de lo contrario.
     */
    private boolean isStraightLine(Position finalPos) {
        return initialPosition.getRow() == finalPos.getRow() || initialPosition.getColumn() == finalPos.getColumn();
    }
}
