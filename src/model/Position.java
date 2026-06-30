package model;

import java.util.Objects;

/**
 * Representa una posición en el tablero de juego definida por una fila y una columna.
 * Esta clase proporciona métodos para acceder a la fila y la columna, y sobrescribe
 * los métodos hashCode y equals para una funcionalidad correcta en colecciones y comparaciones.
 *
 * @version 15.0
 * @since 1.0
 * @author Rafa Martinez Manzanares
 */
public class Position {
    private int row;
    private int column;

    /**
     * Construye una Posición con el número de fila y columna especificados.
     *
     * @param row El número de fila de la posición.
     * @param column El número de columna de la posición.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Devuelve el número de fila de la posición.
     *
     * @return El número de fila.
     */
    public int getRow() {
        return row;
    }

    /**
     * Devuelve el número de columna de la posición.
     *
     * @return El número de columna.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Calcula el código hash para la posición basado en su fila y columna.
     *
     * @return El código hash de la posición.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Compara esta posición con otro objeto para determinar si son iguales. Dos posiciones se consideran
     * iguales si tienen la misma fila y columna.
     *
     * @param obj El objeto con el que comparar.
     * @return true si el objeto es una Posición con la misma fila y columna, false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && column == position.column;
    }

    /**
     * Devuelve una representación legible de la posición usando coordenadas en base 1,
     * tal y como las introduce el usuario por consola.
     *
     * @return La posición en formato "(fila, columna)".
     */
    @Override
    public String toString() {
        return "(" + (row + 1) + ", " + (column + 1) + ")";
    }
}
