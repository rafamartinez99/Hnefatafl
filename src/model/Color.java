package model;

/**
 * Proporciona códigos de colores ANSI como constantes para dar estilo a la salida de la consola.
 * Esta clase incluye colores tanto para el texto como para los fondos.
 *
 * @version 15.0
 * @since 1.0
 * @author Rafa Martinez Manzanares
 */
public final class Color {
    /**
     * Reinicia el color actual al color predeterminado del terminal.
     */
    public static final String RESET = "\u001B[0m";

    /**
     * Código de color blanco para el texto.
     */
    public static final String WHITE = "\u001B[37m";

    /**
     * Código de color negro para el texto.
     */
    public static final String BLACK = "\u001B[30m";

    /**
     * Código de color cian para el texto, añadido para opciones de estilo adicionales.
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * Código de color azul para el texto, añadido para opciones de estilo adicionales.
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * Código de color púrpura para el texto.
     */
    public static final String PURPLE = "\u001B[35m";

    /**
     * Código de color rojo para el texto.
     */
    public static final String RED = "\u001B[31m";

    /**
     * Código de color amarillo para el texto.
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * Código de color de fondo rojo.
     */
    public static final String RED_BACKGROUND = "\u001B[41m";

    /**
     * Código de color de fondo cian.
     */
    public static final String CYAN_BACKGROUND = "\u001B[46m";

    /**
     * Código de color de fondo azul.
     */
    public static final String BLUE_BACKGROUND = "\u001B[44m";

    /**
     * Código de color de fondo amarillo.
     */
    public static final String YELLOW_BACKGROUND = "\u001B[43m";

    /**
     * Código de color de fondo púrpura.
     */
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
}
