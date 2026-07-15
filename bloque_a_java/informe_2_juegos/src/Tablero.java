import java.util.ArrayList;
import java.util.List;

public class Tablero {
    public static final int TAMANIO = 8;
    public static final char VACIO = '.';
    public static final char NEGRO = 'X'; // Jugador Humano
    public static final char BLANCO = 'O'; // IA

    private char[][] matriz;
    private String etiquetaNegro = "Humano (Negras)";
    private String etiquetaBlanco = "IA (Blancas)";

    public void setEtiquetasJugadores(String etiquetaNegro, String etiquetaBlanco) {
        this.etiquetaNegro = etiquetaNegro;
        this.etiquetaBlanco = etiquetaBlanco;
    }

    // Direcciones de búsqueda: [fila, columna]
    private static final int[][] DIRECCIONES = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},          { 0, 1},
        { 1, -1}, { 1, 0}, { 1, 1}
    };

    // Constructor: Inicializa el tablero con las 4 fichas centrales
    public Tablero() {
        matriz = new char[TAMANIO][TAMANIO];
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                matriz[i][j] = VACIO;
            }
        }
        // Configuración inicial clásica
        matriz[3][3] = BLANCO;
        matriz[3][4] = NEGRO;
        matriz[4][3] = NEGRO;
        matriz[4][4] = BLANCO;
    }

    // Constructor copia para clonación
    private Tablero(char[][] otraMatriz, String etiquetaNegro, String etiquetaBlanco) {
        matriz = new char[TAMANIO][TAMANIO];
        for (int i = 0; i < TAMANIO; i++) {
            System.arraycopy(otraMatriz[i], 0, matriz[i], 0, TAMANIO);
        }
        this.etiquetaNegro = etiquetaNegro;
        this.etiquetaBlanco = etiquetaBlanco;
    }

    public Tablero getClon() {
        return new Tablero(this.matriz, this.etiquetaNegro, this.etiquetaBlanco);
    }

    public char getCasilla(int f, int c) {
        return matriz[f][c];
    }

    // Retorna true si las coordenadas están dentro del tablero
    public static boolean esCoordenadaValida(int f, int c) {
        return f >= 0 && f < TAMANIO && c >= 0 && c < TAMANIO;
    }

    // Retorna el oponente de un jugador
    public static char getOponente(char jugador) {
        return (jugador == NEGRO) ? BLANCO : NEGRO;
    }

    /**
     * Verifica si una jugada es válida en una coordenada específica para un jugador.
     */
    public boolean esMovimientoValido(int fila, int columna, char jugador) {
        if (!esCoordenadaValida(fila, columna) || matriz[fila][columna] != VACIO) {
            return false;
        }

        char oponente = getOponente(jugador);

        // Buscar en las 8 direcciones si se encierra al menos una ficha rival
        for (int[] dir : DIRECCIONES) {
            int f = fila + dir[0];
            int c = columna + dir[1];
            boolean tieneOponenteEnMedio = false;

            while (esCoordenadaValida(f, c) && matriz[f][c] == oponente) {
                f += dir[0];
                c += dir[1];
                tieneOponenteEnMedio = true;
            }

            if (tieneOponenteEnMedio && esCoordenadaValida(f, c) && matriz[f][c] == jugador) {
                return true;
            }
        }

        return false;
    }

    /**
     * Obtiene todas las jugadas válidas para un jugador.
     */
    public List<int[]> getMovimientosLegales(char jugador) {
        List<int[]> movimientos = new ArrayList<>();
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (esMovimientoValido(i, j, jugador)) {
                    movimientos.add(new int[]{i, j});
                }
            }
        }
        return movimientos;
    }

    /**
     * Realiza un movimiento. Si es válido, voltea las fichas y retorna true.
     */
    public boolean realizarMovimiento(int fila, int columna, char jugador) {
        if (!esMovimientoValido(fila, columna, jugador)) {
            return false;
        }

        char oponente = getOponente(jugador);
        matriz[fila][columna] = jugador;

        // Voltear fichas en las 8 direcciones
        for (int[] dir : DIRECCIONES) {
            int f = fila + dir[0];
            int c = columna + dir[1];
            List<int[]> aVoltear = new ArrayList<>();

            while (esCoordenadaValida(f, c) && matriz[f][c] == oponente) {
                aVoltear.add(new int[]{f, c});
                f += dir[0];
                c += dir[1];
            }

            // Si al final del tramo encontramos una ficha propia, volteamos las guardadas
            if (esCoordenadaValida(f, c) && matriz[f][c] == jugador) {
                for (int[] pos : aVoltear) {
                    matriz[pos[0]][pos[1]] = jugador;
                }
            }
        }

        return true;
    }

    /**
     * Helper para limpiar la pantalla.
     */
    private void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Helper para pausar el hilo en milisegundos.
     */
    private void pausar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Realiza un movimiento con animación visual del volteo de fichas.
     */
    public boolean realizarMovimientoConAnimacion(int fila, int columna, char jugador) {
        if (!esMovimientoValido(fila, columna, jugador)) {
            return false;
        }

        char oponente = getOponente(jugador);
        List<int[]> aVoltearTotal = new ArrayList<>();

        // Identificar todas las fichas a voltear en las 8 direcciones
        for (int[] dir : DIRECCIONES) {
            int f = fila + dir[0];
            int c = columna + dir[1];
            List<int[]> aVoltearDir = new ArrayList<>();

            while (esCoordenadaValida(f, c) && matriz[f][c] == oponente) {
                aVoltearDir.add(new int[]{f, c});
                f += dir[0];
                c += dir[1];
            }

            if (esCoordenadaValida(f, c) && matriz[f][c] == jugador) {
                aVoltearTotal.addAll(aVoltearDir);
            }
        }

        // --- ANIMACIÓN FRAME-BY-FRAME ---
        
        // Frame 1: Colocar la nueva ficha en el tablero
        matriz[fila][columna] = jugador;
        limpiarConsola();
        imprimirTablero(null);
        pausar(450); // Pausa para notar la colocación

        if (!aVoltearTotal.isEmpty()) {
            // Frame 2: Fichas capturadas entran en estado de volteo (transición 'I' pintado como ▒▒▒)
            for (int[] pos : aVoltearTotal) {
                matriz[pos[0]][pos[1]] = 'I';
            }
            limpiarConsola();
            imprimirTablero(null);
            pausar(450); // Pausa del volteo intermedio

            // Frame 3: Fichas adoptan el color final del jugador
            for (int[] pos : aVoltearTotal) {
                matriz[pos[0]][pos[1]] = jugador;
            }
            limpiarConsola();
            imprimirTablero(null);
        }

        return true;
    }

    /**
     * Verifica si el juego ha terminado.
     * Termina si el tablero está lleno, o si ninguno de los dos jugadores tiene movimientos legales.
     */
    public boolean esTerminal() {
        return getMovimientosLegales(NEGRO).isEmpty() && getMovimientosLegales(BLANCO).isEmpty();
    }

    /**
     * Obtiene la cantidad de fichas de un jugador.
     */
    public int getPuntaje(char jugador) {
        int contador = 0;
        for (int i = 0; i < TAMANIO; i++) {
            for (int j = 0; j < TAMANIO; j++) {
                if (matriz[i][j] == jugador) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Dibuja el tablero en consola usando caracteres y colores ANSI.
     * 
     * @param movimientosLegales Lista de jugadas legales para el jugador actual (para marcarlas).
     */
    public void imprimirTablero(List<int[]> movimientosLegales) {
        // Códigos de colores ANSI
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN_BG = "\u001B[42m";
        final String ANSI_BLACK_TEXT = "\u001B[30m";
        final String ANSI_WHITE_TEXT = "\u001B[97m";
        final String ANSI_YELLOW_TEXT = "\u001B[33m";
        final String ANSI_RESET_BG = "\u001B[49m";
        final String ANSI_GRAY_TEXT = "\u001B[90m";

        System.out.println("\n     A   B   C   D   E   F   G   H");
        System.out.println("   " + ANSI_GRAY_TEXT + "+" + "---+".repeat(TAMANIO) + ANSI_RESET);

        for (int i = 0; i < TAMANIO; i++) {
            System.out.print(" " + (i + 1) + " " + ANSI_GRAY_TEXT + "|" + ANSI_RESET);
            for (int j = 0; j < TAMANIO; j++) {
                char celda = matriz[i][j];
                boolean esSugerencia = false;
                
                // Verificar si la casilla es una sugerencia de jugada legal
                if (movimientosLegales != null) {
                    for (int[] mov : movimientosLegales) {
                        if (mov[0] == i && mov[1] == j) {
                            esSugerencia = true;
                            break;
                        }
                    }
                }

                // Renderizar celda
                System.out.print(ANSI_GREEN_BG);
                if (celda == NEGRO) {
                    // Ficha negra (llena toda la celda)
                    System.out.print(ANSI_BLACK_TEXT + "███");
                } else if (celda == BLANCO) {
                    // Ficha blanca (llena toda la celda)
                    System.out.print(ANSI_WHITE_TEXT + "███");
                } else if (celda == 'I') {
                    // Ficha en transición (bloque difuminado ▒▒▒ en amarillo brillante)
                    System.out.print(ANSI_YELLOW_TEXT + "▒▒▒");
                } else if (esSugerencia) {
                    // Indicador de movimiento posible (pista)
                    System.out.print(" " + ANSI_YELLOW_TEXT + "+" + ANSI_GREEN_BG + " ");
                } else {
                    // Celda vacía
                    System.out.print("   ");
                }
                System.out.print(ANSI_RESET + ANSI_GRAY_TEXT + "|" + ANSI_RESET);
            }
            System.out.println();
            System.out.println("   " + ANSI_GRAY_TEXT + "+" + "---+".repeat(TAMANIO) + ANSI_RESET);
        }
        System.out.println("Fichas -> " + ANSI_GRAY_TEXT + "█" + ANSI_RESET + " " + etiquetaNegro + ": " + getPuntaje(NEGRO) +
                           "  |  " + ANSI_WHITE_TEXT + "█" + ANSI_RESET + " " + etiquetaBlanco + ": " + getPuntaje(BLANCO) + "\n");
    }
}
