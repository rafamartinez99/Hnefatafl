package game;

import model.Board;
import model.Movement;
import model.Piece;
import model.Position;
import enums.PieceType;

import java.util.*;

/**
 * Representa un jugador controlado por inteligencia artificial en el juego. Para cada turno, la
 * máquina simula todos sus movimientos posibles y, para cada uno, la mejor respuesta del rival
 * (búsqueda tipo minimax a dos niveles), eligiendo el movimiento que deja la mejor posición
 * posible una vez el rival ha respondido lo mejor que puede. La posición se valora con una
 * heurística que combina el material restante de cada bando y la cercanía del rey a una esquina.
 *
 * @version 16.0
 * @since 1.0
 * @see Player
 * @see Board
 * @see Movement
 * @author Rafa Martinez Manzanares
 */
public class Machine extends Player {
    private static final int WIN_SCORE = 100_000;
    private static final int NO_MOVES_SCORE = 5_000;
    private static final int ATTACKER_PIECE_VALUE = 1;
    private static final int DEFENDER_PIECE_VALUE = 2;
    private static final int KING_DISTANCE_VALUE = 2;

    private final Random random = new Random();

    /**
     * Inicializa un jugador controlado por la maquina con un rol específico.
     *
     * @param role El rol del jugador ("Atacante" o "Defensor").
     */
    public Machine(String role) {
        super("IARafa-" + role, role);
    }

    /**
     * Elige el mejor movimiento disponible evaluando, para cada candidato propio, la mejor
     * respuesta posible del rival.
     *
     * @param board El tablero donde la maquina debe realizar un movimiento.
     * @return El mejor movimiento encontrado, o null si no hay movimientos posibles.
     */
    @Override
    public Movement decideMove(Board board) {
        List<Movement> ownMoves = generateLegalMoves(board, getRole(), this);
        if (ownMoves.isEmpty()) {
            return null;
        }

        String opponentRole = oppositeRole(getRole());
        Player opponent = new Machine(opponentRole);

        List<Movement> bestMoves = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        for (Movement move : ownMoves) {
            Board afterOwnMove = board.copy();
            move.execute(afterOwnMove);

            int score = evaluateAfterOpponentReply(afterOwnMove, opponentRole, opponent);

            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
        }

        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    /**
     * Evalúa una posición desde el punto de vista de la maquina, asumiendo que a continuación
     * el rival jugará su mejor respuesta posible.
     *
     * @param board El tablero resultante de un movimiento propio.
     * @param opponentRole El rol del rival.
     * @param opponent Un jugador con el rol del rival, usado solo para comprobar legalidad.
     * @return La puntuación de la posición desde el punto de vista de la maquina.
     */
    private int evaluateAfterOpponentReply(Board board, String opponentRole, Player opponent) {
        if (board.isWhiteVictory() || board.isBlackVictory()) {
            return evaluateTerminal(board);
        }

        List<Movement> opponentMoves = generateLegalMoves(board, opponentRole, opponent);
        if (opponentMoves.isEmpty()) {
            return NO_MOVES_SCORE;
        }

        int worstForMe = Integer.MAX_VALUE;
        for (Movement opponentMove : opponentMoves) {
            Board afterOpponentMove = board.copy();
            opponentMove.execute(afterOpponentMove);

            int score = afterOpponentMove.isWhiteVictory() || afterOpponentMove.isBlackVictory()
                    ? evaluateTerminal(afterOpponentMove)
                    : evaluatePosition(afterOpponentMove, getRole());

            worstForMe = Math.min(worstForMe, score);
        }

        return worstForMe;
    }

    /**
     * Puntúa una posición ya finalizada (rey capturado o coronado) desde el punto de vista de
     * la maquina.
     *
     * @param board El tablero en estado terminal.
     * @return {@code WIN_SCORE} si la maquina ha ganado, {@code -WIN_SCORE} si ha perdido.
     */
    private int evaluateTerminal(Board board) {
        boolean defenderWins = board.isWhiteVictory();
        boolean machineIsDefender = getRole().equalsIgnoreCase("Defensor");
        boolean machineWins = defenderWins == machineIsDefender;
        return machineWins ? WIN_SCORE : -WIN_SCORE;
    }

    /**
     * Valora una posición no terminal según el material restante de cada bando y la cercanía
     * del rey a una esquina de escape.
     *
     * @param board El tablero a evaluar.
     * @param role El rol desde cuyo punto de vista se valora la posición.
     * @return La puntuación de la posición; cuanto mayor, mejor para {@code role}.
     */
    private int evaluatePosition(Board board, String role) {
        int attackerCount = 0;
        int defenderCount = 0;
        Position kingPosition = null;

        for (int row = 0; row < board.getDimension(); row++) {
            for (int column = 0; column < board.getDimension(); column++) {
                Piece piece = board.getPieceAt(new Position(row, column));
                if (piece == null) {
                    continue;
                }
                if (piece.getType() == PieceType.ATTACKER) {
                    attackerCount++;
                } else if (piece.getType() == PieceType.DEFENDER) {
                    defenderCount++;
                } else if (piece.getType() == PieceType.KING) {
                    kingPosition = piece.getPosition();
                }
            }
        }

        int kingDistanceToCorner = kingPosition == null ? 0 : distanceToNearestCorner(kingPosition, board.getDimension());

        int scoreForAttacker = attackerCount * ATTACKER_PIECE_VALUE
                - defenderCount * DEFENDER_PIECE_VALUE
                + kingDistanceToCorner * KING_DISTANCE_VALUE;

        return role.equalsIgnoreCase("Atacante") ? scoreForAttacker : -scoreForAttacker;
    }

    /**
     * Calcula la distancia Manhattan mínima desde una posición hasta cualquiera de las cuatro
     * esquinas del tablero.
     *
     * @param position La posición de origen.
     * @param dimension La dimensión del tablero.
     * @return La distancia mínima a una esquina.
     */
    private int distanceToNearestCorner(Position position, int dimension) {
        int lastIndex = dimension - 1;
        int[] cornerRows = {0, 0, lastIndex, lastIndex};
        int[] cornerColumns = {0, lastIndex, 0, lastIndex};

        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < cornerRows.length; i++) {
            int distance = Math.abs(position.getRow() - cornerRows[i]) + Math.abs(position.getColumn() - cornerColumns[i]);
            minDistance = Math.min(minDistance, distance);
        }
        return minDistance;
    }

    /**
     * Devuelve el rol contrario al indicado.
     *
     * @param role El rol de referencia.
     * @return "Defensor" si el rol es "Atacante", o "Atacante" en caso contrario.
     */
    private String oppositeRole(String role) {
        return role.equalsIgnoreCase("Atacante") ? "Defensor" : "Atacante";
    }

    /**
     * Genera todos los movimientos legales para las piezas de un rol determinado en un tablero.
     *
     * @param board El tablero donde se evaluan los movimientos.
     * @param role El rol cuyas piezas se mueven.
     * @param player Un jugador con ese rol, usado solo para comprobar legalidad.
     * @return Una lista de movimientos legales.
     */
    private List<Movement> generateLegalMoves(Board board, String role, Player player) {
        List<Movement> moves = new ArrayList<>();

        for (int row = 0; row < board.getDimension(); row++) {
            for (int col = 0; col < board.getDimension(); col++) {
                Position currentPosition = new Position(row, col);
                Piece piece = board.getPieceAt(currentPosition);
                if (piece != null && belongsToRole(piece, role) && !board.isSurrounded(currentPosition)) {
                    exploreMoves(board, currentPosition, piece, player, moves);
                }
            }
        }

        return moves;
    }

    /**
     * Verifica si la pieza especificada pertenece al rol indicado.
     *
     * @param piece La pieza a verificar.
     * @param role El rol a comprobar.
     * @return Verdadero si la pieza pertenece al rol, falso en caso contrario.
     */
    private boolean belongsToRole(Piece piece, String role) {
        return (role.equalsIgnoreCase("Atacante") && piece.getType() == PieceType.ATTACKER) ||
                (role.equalsIgnoreCase("Defensor") && (piece.getType() == PieceType.DEFENDER || piece.getType() == PieceType.KING));
    }

    /**
     * Explora y añade a la lista todos los movimientos válidos desde la posición de una pieza.
     *
     * @param board El tablero de juego.
     * @param sourcePosition La posición inicial desde donde se exploran los movimientos.
     * @param piece La pieza para la cual se exploran los movimientos.
     * @param player El jugador propietario de la pieza, usado para comprobar legalidad.
     * @param moves Lista acumulativa de movimientos válidos.
     */
    private void exploreMoves(Board board, Position sourcePosition, Piece piece, Player player, List<Movement> moves) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Derecha, abajo, izquierda, arriba

        for (int[] dir : directions) {
            int nextRow = sourcePosition.getRow() + dir[0];
            int nextCol = sourcePosition.getColumn() + dir[1];
            Position targetPosition = new Position(nextRow, nextCol);

            while (board.isValidPosition(targetPosition) && board.isPositionFree(targetPosition)) {
                Movement move = new Movement(piece, sourcePosition, targetPosition);
                if (move.isLegal(board, player)) {
                    moves.add(move);
                }
                nextRow += dir[0];
                nextCol += dir[1];
                targetPosition = new Position(nextRow, nextCol);
            }
        }
    }
}
