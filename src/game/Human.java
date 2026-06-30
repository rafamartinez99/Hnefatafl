package game;

import java.util.Optional;

import main.ConsoleInput;
import enums.PieceType;
import model.Board;
import model.Movement;
import model.Piece;
import model.Position;

/**
 * Representa un jugador humano en el juego. Esta clase maneja todas las interacciones
 * con el usuario necesarias para que un jugador humano tome decisiones sobre sus movimientos
 * en el juego, incluyendo la selección de piezas para mover y determinar sus nuevas posiciones
 * en el tablero.
 *
 * @version 15.0
 * @since 1.0
 * @see Player
 * @see Board
 * @see Movement
 * @author Rafa Martinez Manzanares
 */
public class Human extends Player {
    private ConsoleInput consoleInput; // Manejador de entrada para obtener la entrada del usuario desde la consola.

    /**
     * Construye un nuevo jugador humano con un nombre especificado, rol y manejador de entrada de consola.
     *
     * @param name El nombre del jugador.
     * @param role El rol del jugador en el juego (p. ej., "Atacante", "Defensor").
     * @param consoleInput El manejador de entrada de consola para procesar la entrada del usuario.
     */
    public Human(String name, String role, ConsoleInput consoleInput) {
        super(name, role);
        this.consoleInput = consoleInput;
    }

    /**
     * Decide el movimiento de una pieza basado en la entrada del jugador. Este método guía al jugador
     * a seleccionar un origen y destino válidos para la pieza que elijan mover.
     *
     * @param board El tablero de juego en el que se realizará el movimiento.
     * @return Un objeto {@code Movement} que representa el movimiento elegido.
     */
    @Override
    public Movement decideMove(Board board) {
        Position origin = selectOriginPosition(board);
        Piece piece = board.getPieceAt(origin);

        if (piece == null || !isPlayerPiece(piece)) {
            System.out.println("No tienes una pieza en esa posición, por favor, elige de nuevo");
            return decideMove(board);
        }

        if (board.isSurrounded(origin)) {
            System.out.println("La pieza seleccionada está rodeada y no puede moverse. Elige otra pieza.");
            return decideMove(board);
        }

        Position destination = selectDestinationPosition(board);
        Movement movement = new Movement(piece, origin, destination);

        String illegalReason = movement.getIllegalReason(board, this);
        if (illegalReason != null) {
            System.out.println(illegalReason);
            return decideMove(board);
        }

        return movement;
    }

    /**
     * Determina si una pieza pertenece al jugador actual basado en el tipo de pieza y el rol del jugador.
     *
     * @param piece La pieza a verificar.
     * @return {@code true} si la pieza pertenece al jugador, {@code false} de lo contrario.
     */
    private boolean isPlayerPiece(Piece piece) {
        return (this.getRole().equals("Atacante") && piece.getType() == PieceType.ATTACKER) ||
                (this.getRole().equals("Defensor") && (piece.getType() == PieceType.DEFENDER || piece.getType() == PieceType.KING));
    }

    /**
     * Guía al jugador para seleccionar la posición de origen de la pieza que desean mover.
     *
     * @param board El tablero de juego desde el cual se moverá la pieza.
     * @return Un objeto {@code Position} que representa la posición de origen seleccionada.
     */
    private Position selectOriginPosition(Board board) {
        System.out.println(getName() + ", es tu turno. Elige la pieza que quieres mover.");
        int rowOrigin = consoleInput.readIntInRange(1, board.getDimension()) - 1;
        int columnOrigin = consoleInput.readIntInRange(1, board.getDimension()) - 1;
        Position position = new Position(rowOrigin, columnOrigin);
        Optional<Piece> piece = board.getSquare(position).getPiece();

        if (piece.isEmpty() || !isPlayerPiece(piece.get())) {
            System.out.println("No tienes una pieza en esa posición, elige nuevamente");
            return selectOriginPosition(board);
        }
        return position;
    }

    /**
     * Guía al jugador para seleccionar la posición de destino para la pieza que están moviendo.
     *
     * @param board El tablero de juego al cual se moverá la pieza.
     * @return Un objeto {@code Position} que representa la posición de destino seleccionada.
     */
    private Position selectDestinationPosition(Board board) {
        System.out.println("Elige a qué posición mover la pieza.");
        int rowDestination = consoleInput.readIntInRange(1, board.getDimension()) - 1;
        int columnDestination = consoleInput.readIntInRange(1, board.getDimension()) - 1;
        return new Position(rowDestination, columnDestination);
    }
}