import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Modela un estado (tablero) del problema del 8-Puzzle.
 * El tablero se representa como un arreglo unidimensional de 9 posiciones,
 * donde el valor 0 representa el espacio vacio. La fila/columna de la
 * posicion i se calcula como i/3 y i%3 respectivamente.
 *
 * Nota de portabilidad: todas las cadenas impresas en consola usan
 * unicamente caracteres ASCII (sin tildes ni simbolos Unicode de dibujo de
 * cajas), para garantizar que "javac Main.java" y "java Main" produzcan
 * exactamente el mismo resultado en cualquier sistema operativo, sin
 * depender del charset por defecto de la plataforma.
 */
public class State {

    public static final int SIZE = 3;
    public static final int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};

    private final int[] board;

    public State(int[] board) {
        if (board == null || board.length != SIZE * SIZE) {
            throw new IllegalArgumentException("El tablero debe tener exactamente 9 posiciones.");
        }
        this.board = Arrays.copyOf(board, board.length);
    }

    public int[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    public boolean isGoal() {
        return Arrays.equals(board, GOAL);
    }

    private int findBlank() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return i;
            }
        }
        throw new IllegalStateException("El tablero no contiene el espacio vacio (0).");
    }

    /**
     * Envuelve un estado sucesor junto con la accion que lo genero y su costo.
     */
    public static class Successor {
        public final State state;
        public final String action;
        public final int cost;

        public Successor(State state, String action, int cost) {
            this.state = state;
            this.action = action;
            this.cost = cost;
        }
    }

    /**
     * Genera los estados sucesores validos moviendo el espacio en blanco
     * hacia Arriba, Abajo, Izquierda o Derecha. Cada accion tiene costo 1.
     */
    public List<Successor> getSuccessors() {
        List<Successor> successors = new ArrayList<>();
        int blank = findBlank();
        int row = blank / SIZE;
        int col = blank % SIZE;

        int[][] deltas = {
                {-1, 0}, // ARRIBA
                {1, 0},  // ABAJO
                {0, -1}, // IZQUIERDA
                {0, 1}   // DERECHA
        };
        String[] names = {"ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA"};

        for (int m = 0; m < deltas.length; m++) {
            int newRow = row + deltas[m][0];
            int newCol = col + deltas[m][1];
            if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) {
                int newBlank = newRow * SIZE + newCol;
                int[] newBoard = Arrays.copyOf(board, board.length);
                newBoard[blank] = newBoard[newBlank];
                newBoard[newBlank] = 0;
                successors.add(new Successor(new State(newBoard), names[m], 1));
            }
        }
        return successors;
    }

    /**
     * Verifica si un tablero (arreglo de 9 posiciones, permutacion de 0-8)
     * tiene solucion, usando la regla clasica de paridad de inversiones
     * para tableros 3x3: es resoluble si el numero de inversiones es par.
     */
    public static boolean isSolvable(int[] boardArr) {
        int[] flat = new int[boardArr.length - 1];
        int idx = 0;
        for (int v : boardArr) {
            if (v != 0) {
                flat[idx++] = v;
            }
        }
        int inversions = 0;
        for (int i = 0; i < flat.length; i++) {
            for (int j = i + 1; j < flat.length; j++) {
                if (flat[i] > flat[j]) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }

    // ---------- Renderizador ANSI (ASCII puro, seguro entre plataformas) ----------
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GRAY = "\u001B[90m";
    private static final String BOLD = "\u001B[1m";

    public void printState() {
        String border = GRAY + "  +-------+-------+-------+" + RESET;
        String divider = GRAY + "  +-------+-------+-------+" + RESET;
        System.out.println(border);
        for (int r = 0; r < SIZE; r++) {
            System.out.print(GRAY + "  |" + RESET);
            for (int c = 0; c < SIZE; c++) {
                int val = board[r * SIZE + c];
                if (val == 0) {
                    System.out.print("   " + GRAY + "#" + RESET + "   ");
                } else {
                    System.out.print("   " + CYAN + BOLD + val + RESET + "   ");
                }
                System.out.print(GRAY + "|" + RESET);
            }
            System.out.println();
            if (r < SIZE - 1) {
                System.out.println(divider);
            }
        }
        System.out.println(border);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State other = (State) o;
        return Arrays.equals(this.board, other.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        return Arrays.toString(board);
    }
}
