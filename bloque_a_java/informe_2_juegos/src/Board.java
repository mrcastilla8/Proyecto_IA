
import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int SIZE = 8;
    public static final char EMPTY = '.';
    public static final char BLACK = 'X'; // Jugador Humano
    public static final char WHITE = 'O'; // IA

    private char[][] grid;

    // Direcciones de búsqueda: [fila, columna]
    private static final int[][] DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},          { 0, 1},
        { 1, -1}, { 1, 0}, { 1, 1}
    };

    // Constructor: Inicializa el tablero con las 4 fichas centrales
    public Board() {
        grid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = EMPTY;
            }
        }
        // Configuración inicial clásica
        grid[3][3] = WHITE;
        grid[3][4] = BLACK;
        grid[4][3] = BLACK;
        grid[4][4] = WHITE;
    }

    // Constructor copia para clonación
    private Board(char[][] otherGrid) {
        grid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(otherGrid[i], 0, grid[i], 0, SIZE);
        }
    }

    public Board getClone() {
        return new Board(this.grid);
    }

    public char getCell(int r, int c) {
        return grid[r][c];
    }

    // Retorna true si las coordenadas están dentro del tablero
    public static boolean isValidCoordinate(int r, int c) {
        return r >= 0 && r < SIZE && c >= 0 && c < SIZE;
    }

    // Retorna el oponente de un jugador
    public static char getOpponent(char player) {
        return (player == BLACK) ? WHITE : BLACK;
    }

    /**
     * Verifica si una jugada es válida en una coordenada específica para un jugador.
     */
    public boolean isValidMove(int row, int col, char player) {
        if (!isValidCoordinate(row, col) || grid[row][col] != EMPTY) {
            return false;
        }

        char opponent = getOpponent(player);

        // Buscar en las 8 direcciones si se encierra al menos una ficha rival
        for (int[] dir : DIRECTIONS) {
            int r = row + dir[0];
            int c = col + dir[1];
            boolean hasOpponentBetween = false;

            while (isValidCoordinate(r, c) && grid[r][c] == opponent) {
                r += dir[0];
                c += dir[1];
                hasOpponentBetween = true;
            }

            if (hasOpponentBetween && isValidCoordinate(r, c) && grid[r][c] == player) {
                return true;
            }
        }

        return false;
    }

    /**
     * Obtiene todas las jugadas válidas para un jugador.
     */
    public List<int[]> getLegalMoves(char player) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isValidMove(i, j, player)) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    /**
     * Realiza un movimiento. Si es válido, voltea las fichas y retorna true.
     */
    public boolean makeMove(int row, int col, char player) {
        if (!isValidMove(row, col, player)) {
            return false;
        }

        char opponent = getOpponent(player);
        grid[row][col] = player;

        // Voltear fichas en las 8 direcciones
        for (int[] dir : DIRECTIONS) {
            int r = row + dir[0];
            int c = col + dir[1];
            List<int[]> toFlip = new ArrayList<>();

            while (isValidCoordinate(r, c) && grid[r][c] == opponent) {
                toFlip.add(new int[]{r, c});
                r += dir[0];
                c += dir[1];
            }

            // Si al final del tramo encontramos una ficha propia, volteamos las guardadas
            if (isValidCoordinate(r, c) && grid[r][c] == player) {
                for (int[] pos : toFlip) {
                    grid[pos[0]][pos[1]] = player;
                }
            }
        }

        return true;
    }

    /**
     * Helper para limpiar la pantalla.
     */
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Helper para pausar el hilo en milisegundos.
     */
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Realiza un movimiento con animación visual del volteo de fichas.
     */
    public boolean makeMoveWithAnimation(int row, int col, char player) {
        if (!isValidMove(row, col, player)) {
            return false;
        }

        char opponent = getOpponent(player);
        List<int[]> toFlipTotal = new ArrayList<>();

        // Identificar todas las fichas a voltear en las 8 direcciones
        for (int[] dir : DIRECTIONS) {
            int r = row + dir[0];
            int c = col + dir[1];
            List<int[]> toFlipDir = new ArrayList<>();

            while (isValidCoordinate(r, c) && grid[r][c] == opponent) {
                toFlipDir.add(new int[]{r, c});
                r += dir[0];
                c += dir[1];
            }

            if (isValidCoordinate(r, c) && grid[r][c] == player) {
                toFlipTotal.addAll(toFlipDir);
            }
        }

        // --- ANIMACIÓN FRAME-BY-FRAME ---
        
        // Frame 1: Colocar la nueva ficha en el tablero
        grid[row][col] = player;
        clearConsole();
        printBoard(null);
        sleep(450); // Pausa para notar la colocación

        if (!toFlipTotal.isEmpty()) {
            // Frame 2: Fichas capturadas entran en estado de volteo (transición 'I' pintado como ▒▒▒)
            for (int[] pos : toFlipTotal) {
                grid[pos[0]][pos[1]] = 'I';
            }
            clearConsole();
            printBoard(null);
            sleep(450); // Pausa del volteo intermedio

            // Frame 3: Fichas adoptan el color final del jugador
            for (int[] pos : toFlipTotal) {
                grid[pos[0]][pos[1]] = player;
            }
            clearConsole();
            printBoard(null);
        }

        return true;
    }

    /**
     * Verifica si el juego ha terminado.
     * Termina si el tablero está lleno, o si ninguno de los dos jugadores tiene movimientos legales.
     */
    public boolean isTerminal() {
        return getLegalMoves(BLACK).isEmpty() && getLegalMoves(WHITE).isEmpty();
    }

    /**
     * Obtiene la cantidad de fichas de un jugador.
     */
    public int getScore(char player) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == player) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Dibuja el tablero en consola usando caracteres y colores ANSI.
     * 
     * @param legalMoves Lista de jugadas legales para el jugador actual (para marcarlas).
     */
    public void printBoard(List<int[]> legalMoves) {
        // Códigos de colores ANSI
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN_BG = "\u001B[42m";
        final String ANSI_BLACK_TEXT = "\u001B[30m";
        final String ANSI_WHITE_TEXT = "\u001B[97m";
        final String ANSI_YELLOW_TEXT = "\u001B[33m";
        final String ANSI_RESET_BG = "\u001B[49m";
        final String ANSI_GRAY_TEXT = "\u001B[90m";

        System.out.println("\n     A   B   C   D   E   F   G   H");
        System.out.println("   " + ANSI_GRAY_TEXT + "+" + "---+".repeat(SIZE) + ANSI_RESET);

        for (int i = 0; i < SIZE; i++) {
            System.out.print(" " + (i + 1) + " " + ANSI_GRAY_TEXT + "|" + ANSI_RESET);
            for (int j = 0; j < SIZE; j++) {
                char cell = grid[i][j];
                boolean isHint = false;
                
                // Verificar si la casilla es una sugerencia de jugada legal
                if (legalMoves != null) {
                    for (int[] move : legalMoves) {
                        if (move[0] == i && move[1] == j) {
                            isHint = true;
                            break;
                        }
                    }
                }

                // Renderizar celda
                System.out.print(ANSI_GREEN_BG);
                if (cell == BLACK) {
                    // Ficha negra (llena toda la celda)
                    System.out.print(ANSI_BLACK_TEXT + "███");
                } else if (cell == WHITE) {
                    // Ficha blanca (llena toda la celda - ahora sólida)
                    System.out.print(ANSI_WHITE_TEXT + "███");
                } else if (cell == 'I') {
                    // Ficha en transición (bloque difuminado ▒▒▒ en amarillo brillante)
                    System.out.print(ANSI_YELLOW_TEXT + "▒▒▒");
                } else if (isHint) {
                    // Indicador de movimiento posible (pista)
                    System.out.print(" " + ANSI_YELLOW_TEXT + "+" + ANSI_GREEN_BG + " ");
                } else {
                    // Celda vacía
                    System.out.print("   ");
                }
                System.out.print(ANSI_RESET + ANSI_GRAY_TEXT + "|" + ANSI_RESET);
            }
            System.out.println();
            System.out.println("   " + ANSI_GRAY_TEXT + "+" + "---+".repeat(SIZE) + ANSI_RESET);
        }
        System.out.println("Fichas -> " + ANSI_GRAY_TEXT + "█" + ANSI_RESET + " Humano (Negras): " + getScore(BLACK) +
                           "  |  " + ANSI_WHITE_TEXT + "█" + ANSI_RESET + " IA (Blancas): " + getScore(WHITE) + "\n");
    }
}
