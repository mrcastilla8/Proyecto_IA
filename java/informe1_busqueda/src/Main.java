import java.util.List;
import java.util.Scanner;

/**
 * Controlador CLI del proyecto 8-Puzzle. Permite elegir un caso de prueba
 * predefinido o ingresar un tablero manualmente, ejecuta secuencialmente
 * BFS, DFS, Greedy Best-First Search y A*, mide el tiempo de cada uno y
 * presenta un reporte comparativo en consola.
 *
 * Nota de portabilidad: todo el texto impreso usa unicamente caracteres
 * ASCII (sin tildes ni simbolos Unicode). Esto es intencional: "javac
 * Main.java" sin la bandera -encoding usa el charset por defecto de la
 * plataforma, que varia entre sistemas operativos (por ejemplo, Windows
 * suele usar Windows-1252 en vez de UTF-8). Si el codigo fuente tuviera
 * caracteres acentuados o de dibujo de cajas en bytes UTF-8, un charset
 * distinto al compilar podria interpretarlos mal y producir texto
 * corrupto en pantalla. Usando solo ASCII se garantiza el mismo resultado
 * en cualquier maquina, sin pasos de configuracion adicionales.
 */
public class Main {

    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GRAY = "\u001B[90m";
    private static final String BOLD = "\u001B[1m";

    // Casos de prueba predefinidos (tablero inicial -> solucion optima conocida)
    private static final int[] FACIL = {4, 1, 2, 0, 5, 3, 7, 8, 6};   // 5 pasos
    private static final int[] MEDIO = {1, 8, 5, 4, 0, 3, 7, 2, 6};   // 14 pasos
    private static final int[] DIFICIL = {3, 2, 0, 8, 5, 7, 1, 6, 4}; // 22 pasos

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            printWelcome();
            System.out.print(BOLD + "Seleccione una opcion: " + RESET);
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    ejecutarCaso(new State(FACIL), "Caso Facil (solucion optima: 5 pasos)");
                    pausar();
                    break;
                case "2":
                    ejecutarCaso(new State(MEDIO), "Caso Medio (solucion optima: 14 pasos)");
                    pausar();
                    break;
                case "3":
                    ejecutarCaso(new State(DIFICIL), "Caso Dificil (solucion optima: 22 pasos)");
                    pausar();
                    break;
                case "4":
                    State manual = leerTableroManual();
                    if (manual != null) {
                        ejecutarCaso(manual, "Tablero ingresado manualmente");
                        pausar();
                    }
                    break;
                case "5":
                    salir = true;
                    System.out.println(GREEN + "\nHasta luego!" + RESET);
                    break;
                default:
                    System.out.println(RED + "\nOpcion invalida. Presione ENTER para continuar..." + RESET);
                    scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pausar() {
        System.out.print(BOLD + "\nPresione ENTER para volver al menu..." + RESET);
        scanner.nextLine();
    }

    private static void printWelcome() {
        clearScreen();
        System.out.println(CYAN + BOLD +
                "===========================================================\n" +
                "|             8-PUZZLE - ARBOL DE BUSQUEDA               |\n" +
                "|        BFS - DFS - Greedy Best-First - A*              |\n" +
                "===========================================================" + RESET);
        System.out.println();
        System.out.println(YELLOW + "  1)" + RESET + " Caso Facil    " + GRAY + "(solucion en 5 pasos)" + RESET);
        System.out.println(YELLOW + "  2)" + RESET + " Caso Medio    " + GRAY + "(solucion en 14 pasos)" + RESET);
        System.out.println(YELLOW + "  3)" + RESET + " Caso Dificil  " + GRAY + "(solucion en 22 pasos)" + RESET);
        System.out.println(YELLOW + "  4)" + RESET + " Ingresar tablero manualmente");
        System.out.println(YELLOW + "  5)" + RESET + " Salir");
        System.out.println();
    }

    /**
     * Solicita al usuario 9 numeros (0-8) para armar un tablero, validando
     * que sea una permutacion valida y que tenga solucion (inversiones pares).
     */
    private static State leerTableroManual() {
        System.out.println();
        System.out.println(BOLD + "Ingrese los 9 valores del tablero (0 = espacio vacio)," + RESET);
        System.out.println("separados por espacios, fila por fila. Ejemplo: 1 2 3 4 5 6 7 8 0");
        System.out.print("> ");
        String linea = scanner.nextLine().trim();
        String[] partes = linea.split("[\\s,]+");

        if (partes.length != 9) {
            System.out.println(RED + "Error: debe ingresar exactamente 9 valores." + RESET);
            pausar();
            return null;
        }

        int[] board = new int[9];
        boolean[] usados = new boolean[9];
        try {
            for (int i = 0; i < 9; i++) {
                int v = Integer.parseInt(partes[i]);
                if (v < 0 || v > 8 || usados[v]) {
                    System.out.println(RED + "Error: el tablero debe contener cada numero de 0 a 8 exactamente una vez." + RESET);
                    pausar();
                    return null;
                }
                usados[v] = true;
                board[i] = v;
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Error: todos los valores deben ser numeros enteros." + RESET);
            pausar();
            return null;
        }

        if (!State.isSolvable(board)) {
            System.out.println(RED + "Este tablero NO tiene solucion (numero de inversiones impar)." + RESET);
            pausar();
            return null;
        }

        System.out.println(GREEN + "Tablero valido y resoluble." + RESET);
        return new State(board);
    }

    /**
     * Ejecuta los 4 algoritmos sobre el mismo estado inicial, midiendo
     * tiempo y nodos expandidos de cada uno, y muestra el reporte final.
     */
    private static void ejecutarCaso(State inicial, String descripcion) {
        clearScreen();
        System.out.println(CYAN + BOLD + descripcion + RESET);
        System.out.println();
        System.out.println(BOLD + "Estado inicial:" + RESET);
        inicial.printState();
        System.out.println();
        System.out.println(GRAY + "Ejecutando algoritmos..." + RESET);

        long t0, t1;

        t0 = System.nanoTime();
        SearchAlgorithms.SearchResult bfsResult = SearchAlgorithms.bfs(inicial);
        t1 = System.nanoTime();
        double bfsMs = (t1 - t0) / 1_000_000.0;
        System.out.println(GREEN + "  [OK] BFS completado" + RESET);

        t0 = System.nanoTime();
        SearchAlgorithms.SearchResult dfsResult = SearchAlgorithms.dfs(inicial);
        t1 = System.nanoTime();
        double dfsMs = (t1 - t0) / 1_000_000.0;
        System.out.println(GREEN + "  [OK] DFS completado" + RESET);

        t0 = System.nanoTime();
        SearchAlgorithms.SearchResult greedyResult = SearchAlgorithms.greedy(inicial);
        t1 = System.nanoTime();
        double greedyMs = (t1 - t0) / 1_000_000.0;
        System.out.println(GREEN + "  [OK] Greedy Best-First Search completado" + RESET);

        t0 = System.nanoTime();
        SearchAlgorithms.SearchResult aStarResult = SearchAlgorithms.aStar(inicial);
        t1 = System.nanoTime();
        double aStarMs = (t1 - t0) / 1_000_000.0;
        System.out.println(GREEN + "  [OK] A* completado" + RESET);

        try {
            Thread.sleep(400);
        } catch (InterruptedException ignored) {
        }

        clearScreen();
        System.out.println(CYAN + BOLD + descripcion + RESET);
        System.out.println();
        printComparisonTable(
                new String[]{"BFS", "DFS", "Greedy Best-First", "A*"},
                new SearchAlgorithms.SearchResult[]{bfsResult, dfsResult, greedyResult, aStarResult},
                new double[]{bfsMs, dfsMs, greedyMs, aStarMs}
        );

        System.out.println();
        if (aStarResult.found) {
            printSolutionSteps(inicial, aStarResult.goalNode);
        } else {
            System.out.println(RED + "A* no encontro solucion para este tablero." + RESET);
        }
    }

    private static void printComparisonTable(String[] nombres, SearchAlgorithms.SearchResult[] resultados, double[] tiempos) {
        String horizontal = repeat("-", 21);
        System.out.println(GRAY + "+" + repeat("-", 22) + "+" + horizontal + "+" + horizontal + "+" + horizontal + "+" + RESET);
        System.out.printf(GRAY + "|" + RESET + BOLD + " %-20s " + RESET + GRAY + "|" + RESET +
                        BOLD + " %-19s " + RESET + GRAY + "|" + RESET +
                        BOLD + " %-19s " + RESET + GRAY + "|" + RESET +
                        BOLD + " %-19s " + RESET + GRAY + "|\n" + RESET,
                "Algoritmo", "Nodos Expandidos", "Costo Solucion", "Tiempo (ms)");
        System.out.println(GRAY + "+" + repeat("-", 22) + "+" + horizontal + "+" + horizontal + "+" + horizontal + "+" + RESET);

        for (int i = 0; i < nombres.length; i++) {
            SearchAlgorithms.SearchResult r = resultados[i];
            String costo = r.found ? String.valueOf(r.solutionCost) : "N/A";
            String tiempo = String.format("%.3f", tiempos[i]);
            System.out.printf(GRAY + "|" + RESET + CYAN + " %-20s " + RESET + GRAY + "|" + RESET +
                            " %-19d " + GRAY + "|" + RESET +
                            " %-19s " + GRAY + "|" + RESET +
                            " %-19s " + GRAY + "|\n" + RESET,
                    nombres[i], r.nodesExpanded, costo, tiempo);
        }
        System.out.println(GRAY + "+" + repeat("-", 22) + "+" + horizontal + "+" + horizontal + "+" + horizontal + "+" + RESET);
    }

    private static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    private static void printSolutionSteps(State inicial, SearchNode goalNode) {
        List<String> pasos = goalNode.reconstructPath();
        System.out.println(BOLD + YELLOW + "Secuencia de movimientos de la solucion optima (A*):" + RESET);
        System.out.println(GRAY + "(" + pasos.size() + " movimientos: " + String.join(" -> ", pasos) + ")" + RESET);
        System.out.println();

        State actual = inicial;
        System.out.println(BOLD + "Paso 0 (inicial):" + RESET);
        actual.printState();

        int[] tablero = actual.getBoard();
        for (int i = 0; i < pasos.size(); i++) {
            String accion = pasos.get(i);
            tablero = aplicarMovimiento(tablero, accion);
            State siguiente = new State(tablero);
            System.out.println(BOLD + "Paso " + (i + 1) + ": mover " + accion + RESET);
            siguiente.printState();
        }
        System.out.println(GREEN + BOLD + "Estado meta alcanzado en " + pasos.size() + " movimientos!" + RESET);
    }

    private static int[] aplicarMovimiento(int[] board, String accion) {
        int[] nuevo = board.clone();
        int blank = -1;
        for (int i = 0; i < nuevo.length; i++) {
            if (nuevo[i] == 0) {
                blank = i;
                break;
            }
        }
        int row = blank / State.SIZE;
        int col = blank % State.SIZE;
        int newRow = row, newCol = col;
        switch (accion) {
            case "ARRIBA": newRow = row - 1; break;
            case "ABAJO": newRow = row + 1; break;
            case "IZQUIERDA": newCol = col - 1; break;
            case "DERECHA": newCol = col + 1; break;
        }
        int newBlank = newRow * State.SIZE + newCol;
        nuevo[blank] = nuevo[newBlank];
        nuevo[newBlank] = 0;
        return nuevo;
    }
}
