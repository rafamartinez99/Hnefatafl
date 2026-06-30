package test;

import main.ConsoleInput;
import enums.PieceType;
import game.Human;
import model.Board;
import model.Movement;
import model.Piece;
import model.Position;
import game.Player;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HnefataflTest {

    @ParameterizedTest
    @CsvSource({
            "6, 2, 6, 3, DEFENDER, false",
            "1, 6, 1, 5, ATTACKER, false",
            "4, 6, 4, 8, DEFENDER, true",
            "8, 1, 8, 5, ATTACKER, true",
            "4, 6, 5, 7, DEFENDER, false",
    })
    void testHorizontalMoves(int rowOrigin, int columnOrigin, int rowFinal, int columnFinal, PieceType type, boolean expectedResult) {
        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza = type.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano = new Human("Player", tipoPieza, new ConsoleInput(new Scanner(System.in)));
        Position originPosition = new Position(rowOrigin - 1, columnOrigin - 1);
        Position finalPosition = new Position(rowFinal - 1, columnFinal - 1);

        Piece piece = board.getSquare(originPosition).obtainPiece();
        Movement movement = new Movement(piece, originPosition, finalPosition);
        boolean isValid = board.makeMove(movement, humano);

        assertEquals(expectedResult, isValid, "El movimiento debe ser válido");
    }

    @ParameterizedTest
    @CsvSource({
            "4, 1, 4, 3, ATTACKER, true",
            "4, 6, 3, 6, DEFENDER, true",
            "7, 6, 8, 6, ATTACKER, false",
            "7, 1, 9, 1, DEFENDER, false",
            "4, 1, 3, 2, ATTACKER, false",
    })
    void testVerticalMoves(int rowOrigin, int columnOrigin, int rowFinal, int columnFinal, PieceType type, boolean expectedResult) {
        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza = type.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano = new Human("Player", tipoPieza, new ConsoleInput(new Scanner(System.in)));
        Position originPosition = new Position(rowOrigin - 1, columnOrigin - 1);
        Position finalPosition = new Position(rowFinal - 1, columnFinal - 1);

        Piece piece = board.getSquare(originPosition).obtainPiece();
        Movement movement = new Movement(piece, originPosition, finalPosition);
        boolean isValid = board.makeMove(movement, humano);

        assertEquals(expectedResult, isValid, "El movimiento debe ser válido");
    }

    @ParameterizedTest
    @CsvSource({
            "8, 6, 8, 2, DEFENDER, true, 7, 6, 9, 6, DEFENDER, true, 6, 6, 8, 6, DEFENDER, true, 5, 6, 6, 6, DEFENDER, false",
    })
    void testInvalidDefenderMovesToThrone(
            int rowOrigin1, int colOrigin1, int rowFinal1, int colFinal1, PieceType type1, boolean expectedResult1,
            int rowOrigin2, int colOrigin2, int rowFinal2, int colFinal2, PieceType type2, boolean expectedResult2,
            int rowOrigin3, int colOrigin3, int rowFinal3, int colFinal3, PieceType type3, boolean expectedResult3,
            int rowOrigin4, int colOrigin4, int rowFinal4, int colFinal4, PieceType type4, boolean expectedResult4) {
        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza1 = type1.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano1 = new Human("Player", tipoPieza1, new ConsoleInput(new Scanner(System.in)));
        Position originPosition1 = new Position(rowOrigin1 - 1, colOrigin1 - 1);
        Position finalPosition1 = new Position(rowFinal1 - 1, colFinal1 - 1);
        Piece piece1 = board.getSquare(originPosition1).obtainPiece();
        Movement movement1 = new Movement(piece1, originPosition1, finalPosition1);
        boolean isValid1 = board.makeMove(movement1, humano1);

        String tipoPieza2 = type2.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano2 = new Human("Player", tipoPieza2, new ConsoleInput(new Scanner(System.in)));
        Position originPosition2 = new Position(rowOrigin2 - 1, colOrigin2 - 1);
        Position finalPosition2 = new Position(rowFinal2 - 1, colFinal2 - 1);
        Piece piece2 = board.getSquare(originPosition2).obtainPiece();
        Movement movement2 = new Movement(piece2, originPosition2, finalPosition2);
        boolean isValid2 = board.makeMove(movement2, humano2);

        String tipoPieza3 = type3.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano3 = new Human("Player", tipoPieza3, new ConsoleInput(new Scanner(System.in)));
        Position originPosition3 = new Position(rowOrigin3 - 1, colOrigin3 - 1);
        Position finalPosition3 = new Position(rowFinal3 - 1, colFinal3 - 1);
        Piece piece3 = board.getSquare(originPosition3).obtainPiece();
        Movement movement3 = new Movement(piece3, originPosition3, finalPosition3);
        boolean isValid3 = board.makeMove(movement3, humano3);

        String tipoPieza4 = type4.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano4 = new Human("Player", tipoPieza4, new ConsoleInput(new Scanner(System.in)));
        Position originPosition4 = new Position(rowOrigin4 - 1, colOrigin4 - 1);
        Position finalPosition4 = new Position(rowFinal4 - 1, colFinal4 - 1);
        Piece piece4 = board.getSquare(originPosition4).obtainPiece();
        Movement movement4 = new Movement(piece4, originPosition4, finalPosition4);
        boolean isValid4 = board.makeMove(movement4, humano4);

        assertEquals(expectedResult1, isValid1, "El primer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult2, isValid2, "El segundo movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult3, isValid3, "El tercer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult4, isValid4, "El cuarto movimiento debe ser válido según lo esperado");
    }

    @ParameterizedTest
    @CsvSource({
            "8, 6, 8, 10, DEFENDER, true, 7, 6, 8, 6, DEFENDER, true, 8, 6, 8, 9, DEFENDER, true, 6, 6, 9, 6, DEFENDER, true, 8, 1, 8, 6, ATTACKER, true, 8, 6, 6, 6, ATTACKER, false",
    })
    void testInvalidAttackerMovesToThrone(
            int rowOrigin1, int colOrigin1, int rowFinal1, int colFinal1, PieceType type1, boolean expectedResult1,
            int rowOrigin2, int colOrigin2, int rowFinal2, int colFinal2, PieceType type2, boolean expectedResult2,
            int rowOrigin3, int colOrigin3, int rowFinal3, int colFinal3, PieceType type3, boolean expectedResult3,
            int rowOrigin4, int colOrigin4, int rowFinal4, int colFinal4, PieceType type4, boolean expectedResult4,
            int rowOrigin5, int colOrigin5, int rowFinal5, int colFinal5, PieceType type5, boolean expectedResult5,
            int rowOrigin6, int colOrigin6, int rowFinal6, int colFinal6, PieceType type6, boolean expectedResult6 ) {

        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza1 = type1.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano1 = new Human("Player", tipoPieza1, new ConsoleInput(new Scanner(System.in)));
        Position originPosition1 = new Position(rowOrigin1 - 1, colOrigin1 - 1);
        Position finalPosition1 = new Position(rowFinal1 - 1, colFinal1 - 1);
        Piece piece1 = board.getSquare(originPosition1).obtainPiece();
        Movement movement1 = new Movement(piece1, originPosition1, finalPosition1);
        boolean isValid1 = board.makeMove(movement1, humano1);

        String tipoPieza2 = type2.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano2 = new Human("Player", tipoPieza2, new ConsoleInput(new Scanner(System.in)));
        Position originPosition2 = new Position(rowOrigin2 - 1, colOrigin2 - 1);
        Position finalPosition2 = new Position(rowFinal2 - 1, colFinal2 - 1);
        Piece piece2 = board.getSquare(originPosition2).obtainPiece();
        Movement movement2 = new Movement(piece2, originPosition2, finalPosition2);
        boolean isValid2 = board.makeMove(movement2, humano2);

        String tipoPieza3 = type3.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano3 = new Human("Player", tipoPieza3, new ConsoleInput(new Scanner(System.in)));
        Position originPosition3 = new Position(rowOrigin3 - 1, colOrigin3 - 1);
        Position finalPosition3 = new Position(rowFinal3 - 1, colFinal3 - 1);
        Piece piece3 = board.getSquare(originPosition3).obtainPiece();
        Movement movement3 = new Movement(piece3, originPosition3, finalPosition3);
        boolean isValid3 = board.makeMove(movement3, humano3);

        String tipoPieza4 = type4.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano4 = new Human("Player", tipoPieza4, new ConsoleInput(new Scanner(System.in)));
        Position originPosition4 = new Position(rowOrigin4 - 1, colOrigin4 - 1);
        Position finalPosition4 = new Position(rowFinal4 - 1, colFinal4 - 1);
        Piece piece4 = board.getSquare(originPosition4).obtainPiece();
        Movement movement4 = new Movement(piece4, originPosition4, finalPosition4);
        boolean isValid4 = board.makeMove(movement4, humano4);

        String tipoPieza5 = type5.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano5 = new Human("Player", tipoPieza5, new ConsoleInput(new Scanner(System.in)));
        Position originPosition5 = new Position(rowOrigin5 - 1, colOrigin5 - 1);
        Position finalPosition5 = new Position(rowFinal5 - 1, colFinal5 - 1);
        Piece piece5 = board.getSquare(originPosition5).obtainPiece();
        Movement movement5 = new Movement(piece5, originPosition5, finalPosition5);
        boolean isValid5 = board.makeMove(movement5, humano5);

        String tipoPieza6 = type6.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano6 = new Human("Player", tipoPieza6, new ConsoleInput(new Scanner(System.in)));
        Position originPosition6 = new Position(rowOrigin6 - 1, colOrigin6 - 1);
        Position finalPosition6 = new Position(rowFinal6 - 1, colFinal6 - 1);
        Piece piece6 = board.getSquare(originPosition6).obtainPiece();
        Movement movement6 = new Movement(piece6, originPosition6, finalPosition6);
        boolean isValid6 = board.makeMove(movement6, humano6);

        assertEquals(expectedResult1, isValid1, "El primer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult2, isValid2, "El segundo movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult3, isValid3, "El tercer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult4, isValid4, "El cuarto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult5, isValid5, "El quinto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult6, isValid6, "El sexto movimiento debe ser válido según lo esperado");
    }

    @ParameterizedTest
    @CsvSource({
            "4, 6, 4, 2, DEFENDER, true, 5, 6, 3, 6, DEFENDER, true, 3, 6, 3, 1, DEFENDER, true, 6, 6, 3, 6, DEFENDER, true, 3, 6, 3, 2, DEFENDER, true, 7, 6, 3, 6, DEFENDER, true, 8, 6, 4, 6, DEFENDER, true, 10, 6, 5, 6, ATTACKER, true",
    })
    void testValidMovesThroughThrone(
            int rowOrigin1, int colOrigin1, int rowFinal1, int colFinal1, PieceType type1, boolean expectedResult1,
            int rowOrigin2, int colOrigin2, int rowFinal2, int colFinal2, PieceType type2, boolean expectedResult2,
            int rowOrigin3, int colOrigin3, int rowFinal3, int colFinal3, PieceType type3, boolean expectedResult3,
            int rowOrigin4, int colOrigin4, int rowFinal4, int colFinal4, PieceType type4, boolean expectedResult4,
            int rowOrigin5, int colOrigin5, int rowFinal5, int colFinal5, PieceType type5, boolean expectedResult5,
            int rowOrigin6, int colOrigin6, int rowFinal6, int colFinal6, PieceType type6, boolean expectedResult6,
            int rowOrigin7, int colOrigin7, int rowFinal7, int colFinal7, PieceType type7, boolean expectedResult7,
            int rowOrigin8, int colOrigin8, int rowFinal8, int colFinal8, PieceType type8, boolean expectedResult8 ) {

        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza1 = type1.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano1 = new Human("Player", tipoPieza1, new ConsoleInput(new Scanner(System.in)));
        Position originPosition1 = new Position(rowOrigin1 - 1, colOrigin1 - 1);
        Position finalPosition1 = new Position(rowFinal1 - 1, colFinal1 - 1);
        Piece piece1 = board.getSquare(originPosition1).obtainPiece();
        Movement movement1 = new Movement(piece1, originPosition1, finalPosition1);
        boolean isValid1 = board.makeMove(movement1, humano1);

        String tipoPieza2 = type2.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano2 = new Human("Player", tipoPieza2, new ConsoleInput(new Scanner(System.in)));
        Position originPosition2 = new Position(rowOrigin2 - 1, colOrigin2 - 1);
        Position finalPosition2 = new Position(rowFinal2 - 1, colFinal2 - 1);
        Piece piece2 = board.getSquare(originPosition2).obtainPiece();
        Movement movement2 = new Movement(piece2, originPosition2, finalPosition2);
        boolean isValid2 = board.makeMove(movement2, humano2);

        String tipoPieza3 = type3.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano3 = new Human("Player", tipoPieza3, new ConsoleInput(new Scanner(System.in)));
        Position originPosition3 = new Position(rowOrigin3 - 1, colOrigin3 - 1);
        Position finalPosition3 = new Position(rowFinal3 - 1, colFinal3 - 1);
        Piece piece3 = board.getSquare(originPosition3).obtainPiece();
        Movement movement3 = new Movement(piece3, originPosition3, finalPosition3);
        boolean isValid3 = board.makeMove(movement3, humano3);

        String tipoPieza4 = type4.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano4 = new Human("Player", tipoPieza4, new ConsoleInput(new Scanner(System.in)));
        Position originPosition4 = new Position(rowOrigin4 - 1, colOrigin4 - 1);
        Position finalPosition4 = new Position(rowFinal4 - 1, colFinal4 - 1);
        Piece piece4 = board.getSquare(originPosition4).obtainPiece();
        Movement movement4 = new Movement(piece4, originPosition4, finalPosition4);
        boolean isValid4 = board.makeMove(movement4, humano4);

        String tipoPieza5 = type5.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano5 = new Human("Player", tipoPieza5, new ConsoleInput(new Scanner(System.in)));
        Position originPosition5 = new Position(rowOrigin5 - 1, colOrigin5 - 1);
        Position finalPosition5 = new Position(rowFinal5 - 1, colFinal5 - 1);
        Piece piece5 = board.getSquare(originPosition5).obtainPiece();
        Movement movement5 = new Movement(piece5, originPosition5, finalPosition5);
        boolean isValid5 = board.makeMove(movement5, humano5);

        String tipoPieza6 = type6.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano6 = new Human("Player", tipoPieza6, new ConsoleInput(new Scanner(System.in)));
        Position originPosition6 = new Position(rowOrigin6 - 1, colOrigin6 - 1);
        Position finalPosition6 = new Position(rowFinal6 - 1, colFinal6 - 1);
        Piece piece6 = board.getSquare(originPosition6).obtainPiece();
        Movement movement6 = new Movement(piece6, originPosition6, finalPosition6);
        boolean isValid6 = board.makeMove(movement6, humano6);

        String tipoPieza7 = type7.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano7 = new Human("Player", tipoPieza7, new ConsoleInput(new Scanner(System.in)));
        Position originPosition7 = new Position(rowOrigin7 - 1, colOrigin7 - 1);
        Position finalPosition7 = new Position(rowFinal7 - 1, colFinal7 - 1);
        Piece piece7 = board.getSquare(originPosition7).obtainPiece();
        Movement movement7 = new Movement(piece7, originPosition7, finalPosition7);
        boolean isValid7 = board.makeMove(movement7, humano7);

        String tipoPieza8 = type8.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano8 = new Human("Player", tipoPieza8, new ConsoleInput(new Scanner(System.in)));
        Position originPosition8 = new Position(rowOrigin8 - 1, colOrigin8 - 1);
        Position finalPosition8 = new Position(rowFinal8 - 1, colFinal8 - 1);
        Piece piece8 = board.getSquare(originPosition8).obtainPiece();
        Movement movement8 = new Movement(piece8, originPosition8, finalPosition8);
        boolean isValid8 = board.makeMove(movement8, humano8);

        assertEquals(expectedResult1, isValid1, "El primer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult2, isValid2, "El segundo movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult3, isValid3, "El tercer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult4, isValid4, "El cuarto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult5, isValid5, "El quinto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult6, isValid6, "El sexto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult7, isValid7, "El sexto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult8, isValid8, "El sexto movimiento debe ser válido según lo esperado");
    }

    @ParameterizedTest
    @CsvSource({
            "4, 1, 1, 1, ATTACKER, false",
            "1, 8, 1, 11, ATTACKER, false",
    })
    void testCornerAttackerMove(int rowOrigin, int columnOrigin, int rowFinal, int columnFinal, PieceType type, boolean expectedResult) {
        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza = type.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano = new Human("Player", tipoPieza, new ConsoleInput(new Scanner(System.in)));
        Position originPosition = new Position(rowOrigin - 1, columnOrigin - 1);
        Position finalPosition = new Position(rowFinal - 1, columnFinal - 1);

        Piece piece = board.getSquare(originPosition).obtainPiece();
        Movement movement = new Movement(piece, originPosition, finalPosition);
        boolean isValid = board.makeMove(movement, humano);

        assertEquals(expectedResult, isValid, "El movimiento debe ser válido");
    }

    @ParameterizedTest
    @CsvSource({
            "4, 6, 4, 2, DEFENDER, true, 4, 2, 1, 2, DEFENDER, true, 1, 2, 1, 1, DEFENDER, false",
            "8, 6, 8, 2, DEFENDER, true, 8, 2, 11, 2, DEFENDER, true, 11, 2, 11, 1, DEFENDER, false",
    })
    void testCornerDefenderMove(
            int rowOrigin1, int colOrigin1, int rowFinal1, int colFinal1, PieceType type1, boolean expectedResult1,
            int rowOrigin2, int colOrigin2, int rowFinal2, int colFinal2, PieceType type2, boolean expectedResult2,
            int rowOrigin3, int colOrigin3, int rowFinal3, int colFinal3, PieceType type3, boolean expectedResult3) {

        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza1 = type1.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano1 = new Human("Player", tipoPieza1, new ConsoleInput(new Scanner(System.in)));
        Position originPosition1 = new Position(rowOrigin1 - 1, colOrigin1 - 1);
        Position finalPosition1 = new Position(rowFinal1 - 1, colFinal1 - 1);
        Piece piece1 = board.getSquare(originPosition1).obtainPiece();
        Movement movement1 = new Movement(piece1, originPosition1, finalPosition1);
        boolean isValid1 = board.makeMove(movement1, humano1);

        String tipoPieza2 = type2.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano2 = new Human("Player", tipoPieza2, new ConsoleInput(new Scanner(System.in)));
        Position originPosition2 = new Position(rowOrigin2 - 1, colOrigin2 - 1);
        Position finalPosition2 = new Position(rowFinal2 - 1, colFinal2 - 1);
        Piece piece2 = board.getSquare(originPosition2).obtainPiece();
        Movement movement2 = new Movement(piece2, originPosition2, finalPosition2);
        boolean isValid2 = board.makeMove(movement2, humano2);

        String tipoPieza3 = type3.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano3 = new Human("Player", tipoPieza3, new ConsoleInput(new Scanner(System.in)));
        Position originPosition3 = new Position(rowOrigin3 - 1, colOrigin3 - 1);
        Position finalPosition3 = new Position(rowFinal3 - 1, colFinal3 - 1);
        Piece piece3 = board.getSquare(originPosition3).obtainPiece();
        Movement movement3 = new Movement(piece3, originPosition3, finalPosition3);
        boolean isValid3 = board.makeMove(movement3, humano3);

        assertEquals(expectedResult1, isValid1, "El primer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult2, isValid2, "El segundo movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult3, isValid3, "El tercer movimiento debe ser válido según lo esperado");
    }

    @ParameterizedTest
    @CsvSource({
        "4, 6, 4, 2, DEFENDER, true, 5, 6, 4, 6, DEFENDER, true, 4, 6, 4, 3, DEFENDER, true, 6, 6, 3, 6, DEFENDER, true, 3, 6, 3, 1, DEFENDER, true, 3, 1, 1, 1, DEFENDER, true",
    })
    void testCornerKingMove(
        int rowOrigin1, int colOrigin1, int rowFinal1, int colFinal1, PieceType type1, boolean expectedResult1,
        int rowOrigin2, int colOrigin2, int rowFinal2, int colFinal2, PieceType type2, boolean expectedResult2,
        int rowOrigin3, int colOrigin3, int rowFinal3, int colFinal3, PieceType type3, boolean expectedResult3,
        int rowOrigin4, int colOrigin4, int rowFinal4, int colFinal4, PieceType type4, boolean expectedResult4,
        int rowOrigin5, int colOrigin5, int rowFinal5, int colFinal5, PieceType type5, boolean expectedResult5,
        int rowOrigin6, int colOrigin6, int rowFinal6, int colFinal6, PieceType type6, boolean expectedResult6) {

        Board board = new Board();
        board.initializeBoard();
        board.display();

        String tipoPieza1 = type1.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano1 = new Human("Player", tipoPieza1, new ConsoleInput(new Scanner(System.in)));
        Position originPosition1 = new Position(rowOrigin1 - 1, colOrigin1 - 1);
        Position finalPosition1 = new Position(rowFinal1 - 1, colFinal1 - 1);
        Piece piece1 = board.getSquare(originPosition1).obtainPiece();
        Movement movement1 = new Movement(piece1, originPosition1, finalPosition1);
        boolean isValid1 = board.makeMove(movement1, humano1);

        String tipoPieza2 = type2.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano2 = new Human("Player", tipoPieza2, new ConsoleInput(new Scanner(System.in)));
        Position originPosition2 = new Position(rowOrigin2 - 1, colOrigin2 - 1);
        Position finalPosition2 = new Position(rowFinal2 - 1, colFinal2 - 1);
        Piece piece2 = board.getSquare(originPosition2).obtainPiece();
        Movement movement2 = new Movement(piece2, originPosition2, finalPosition2);
        boolean isValid2 = board.makeMove(movement2, humano2);

        String tipoPieza3 = type3.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano3 = new Human("Player", tipoPieza3, new ConsoleInput(new Scanner(System.in)));
        Position originPosition3 = new Position(rowOrigin3 - 1, colOrigin3 - 1);
        Position finalPosition3 = new Position(rowFinal3 - 1, colFinal3 - 1);
        Piece piece3 = board.getSquare(originPosition3).obtainPiece();
        Movement movement3 = new Movement(piece3, originPosition3, finalPosition3);
        boolean isValid3 = board.makeMove(movement3, humano3);

        String tipoPieza4 = type4.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano4 = new Human("Player", tipoPieza4, new ConsoleInput(new Scanner(System.in)));
        Position originPosition4 = new Position(rowOrigin4 - 1, colOrigin4 - 1);
        Position finalPosition4 = new Position(rowFinal4 - 1, colFinal4 - 1);
        Piece piece4 = board.getSquare(originPosition4).obtainPiece();
        Movement movement4 = new Movement(piece4, originPosition4, finalPosition4);
        boolean isValid4 = board.makeMove(movement4, humano4);

        String tipoPieza5 = type5.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano5 = new Human("Player", tipoPieza5, new ConsoleInput(new Scanner(System.in)));
        Position originPosition5 = new Position(rowOrigin5 - 1, colOrigin5 - 1);
        Position finalPosition5 = new Position(rowFinal5 - 1, colFinal5 - 1);
        Piece piece5 = board.getSquare(originPosition5).obtainPiece();
        Movement movement5 = new Movement(piece5, originPosition5, finalPosition5);
        boolean isValid5 = board.makeMove(movement5, humano5);

        String tipoPieza6 = type6.name().equals("ATTACKER") ? "Atacante" : "Defensor";
        Player humano6 = new Human("Player", tipoPieza6, new ConsoleInput(new Scanner(System.in)));
        Position originPosition6 = new Position(rowOrigin6 - 1, colOrigin6 - 1);
        Position finalPosition6 = new Position(rowFinal6 - 1, colFinal6 - 1);
        Piece piece6 = board.getSquare(originPosition6).obtainPiece();
        Movement movement6 = new Movement(piece6, originPosition6, finalPosition6);
        boolean isValid6 = board.makeMove(movement6, humano6);

        assertEquals(expectedResult1, isValid1, "El primer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult2, isValid2, "El segundo movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult3, isValid3, "El tercer movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult4, isValid4, "El cuarto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult5, isValid5, "El quinto movimiento debe ser válido según lo esperado");
        assertEquals(expectedResult6, isValid6, "El sextto movimiento debe ser válido según lo esperado");
    }
}