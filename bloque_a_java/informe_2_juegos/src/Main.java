
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Códigos ANSI para dar estilo
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_WHITE = "\u001B[37m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BOLD = "\u001B[1m";
        final String ANSI_GREEN_BG = "\u001B[42m";
        final String ANSI_BLACK_TEXT = "\u001B[30m";
        final String ANSI_WHITE_TEXT = "\u001B[97m";
        final String ANSI_YELLOW_TEXT = "\u001B[33m";
        final String ANSI_GRAY_TEXT = "\u001B[90m";

        System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + ANSI_BOLD + "          OTHELLO (REVERSI) EN JAVA            " + ANSI_RESET);
        System.out.println(ANSI_CYAN + ANSI_BOLD + "     Búsqueda Adversarial con Poda Alfa-Beta   " + ANSI_RESET);
        System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
        System.out.println("Responsable: " + ANSI_YELLOW + "Marco Renato Castilla Huanca" + ANSI_RESET + "\n");

        // Selección de dificultad
        int depth = 5; // Valor predeterminado
        System.out.println("Selecciona la dificultad de la IA (Profundidad de búsqueda):");
        System.out.println("1) Fácil (Profundidad 3) - Respuesta instantánea, juego casual.");
        System.out.println("2) Medio (Profundidad 5) - Reto moderado, excelente balance. [Recomendado]");
        System.out.println("3) Difícil (Profundidad 6) - Pensamiento profundo, más estratégico.");
        System.out.println("4) Personalizado");
        System.out.print("Opción (1-4): ");

        String optStr = scanner.nextLine().trim();
        if (optStr.equals("1")) {
            depth = 3;
        } else if (optStr.equals("2")) {
            depth = 5;
        } else if (optStr.equals("3")) {
            depth = 6;
        } else if (optStr.equals("4")) {
            System.out.print("Introduce la profundidad deseada (1-10): ");
            try {
                depth = Integer.parseInt(scanner.nextLine().trim());
                if (depth < 1)
                    depth = 1;
                if (depth > 10) {
                    System.out.println(
                            ANSI_RED + "Profundidad limitada a 8 para evitar sobrecarga de memoria." + ANSI_RESET);
                    depth = 8;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Usando profundidad 5 por defecto.");
                depth = 5;
            }
        } else {
            System.out.println("Opción no válida. Usando profundidad 5 por defecto.");
        }

        System.out.println("\nIniciando partida. Tú eres " + ANSI_GRAY_TEXT + "█" + ANSI_RESET + ANSI_BOLD
                + " Humano (Negras)" + ANSI_RESET +
                ". La IA es " + ANSI_WHITE_TEXT + "█" + ANSI_RESET + ANSI_BOLD + " IA (Blancas)" + ANSI_RESET + ".");
        System.out.println(
                "Las casillas marcadas con '" + ANSI_YELLOW_TEXT + "+" + ANSI_RESET + "' son tus movimientos válidos.");
        System.out.print("Presiona ENTER para comenzar...");
        scanner.nextLine();

        Board board = new Board();
        MinimaxAgent agent = new MinimaxAgent();

        char turn = Board.BLACK; // Comienza el jugador Negro (Humano)

        while (!board.isTerminal()) {
            // Limpiar pantalla al iniciar el turno
            System.out.print("\033[H\033[2J");
            System.out.flush();

            List<int[]> legalMoves = board.getLegalMoves(turn);

            if (legalMoves.isEmpty()) {
                // Lógica de "Pass" en Othello
                String playerStr = (turn == Board.BLACK) ? "Humano (Negras)" : "IA (Blancas)";
                System.out.println(
                        ANSI_RED + "\n¡No hay movimientos legales para " + playerStr + "! Pasa de turno." + ANSI_RESET);
                System.out.print("Presiona ENTER para continuar...");
                scanner.nextLine();
                turn = Board.getOpponent(turn);
                continue;
            }

            if (turn == Board.BLACK) {
                // Turno del Humano
                System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TU TURNO: HUMANO (NEGRAS) ===" + ANSI_RESET);
                board.printBoard(legalMoves);
                int[] selectedMove = null;

                while (selectedMove == null) {
                    System.out.print("Tu turno. Introduce tu coordenada (Ejemplo: F5 o C4): ");
                    String input = scanner.nextLine().trim().toUpperCase();

                    if (input.length() != 2) {
                        System.out.println(ANSI_RED + "Entrada inválida. Debe ser una letra (A-H) y un número (1-8)."
                                + ANSI_RESET);
                        continue;
                    }

                    char colChar = input.charAt(0);
                    char rowChar = input.charAt(1);

                    int col = colChar - 'A';
                    int row = rowChar - '1';

                    if (!Board.isValidCoordinate(row, col)) {
                        System.out.println(ANSI_RED + "Coordenadas fuera del tablero." + ANSI_RESET);
                        continue;
                    }

                    // Verificar si es un movimiento legal
                    boolean isLegal = false;
                    for (int[] move : legalMoves) {
                        if (move[0] == row && move[1] == col) {
                            isLegal = true;
                            break;
                        }
                    }

                    if (!isLegal) {
                        System.out.println(
                                ANSI_RED + "¡Ese no es un movimiento válido! Revisa las pistas (*)." + ANSI_RESET);
                        continue;
                    }

                    selectedMove = new int[] { row, col };
                }

                board.makeMoveWithAnimation(selectedMove[0], selectedMove[1], Board.BLACK);
                System.out.println("\nJugaste en " + ANSI_YELLOW + (char) ('A' + selectedMove[1])
                        + (selectedMove[0] + 1) + ANSI_RESET);
                // Pausa para que el usuario pueda asimilar su jugada y ver el mensaje
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            } else {
                // Turno de la IA
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA: PENSANDO... ===" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "IA pensando... (Profundidad: " + depth + ")" + ANSI_RESET);
                board.printBoard(null);

                agent.resetCounters();

                // 1. Ejecutar con Poda Alfa-Beta para tomar la decisión
                long startTimePruning = System.nanoTime();
                int[] bestMove = agent.getBestMove(board, depth, true);
                long endTimePruning = System.nanoTime();
                double durationPruningMs = (endTimePruning - startTimePruning) / 1_000_000.0;

                // 2. Ejecutar sin Poda Alfa-Beta para recopilar métricas (solo si depth <= 5
                // para no congelar la ejecución)
                long startTimeNoPruning = 0;
                long endTimeNoPruning = 0;
                double durationNoPruningMs = 0;
                boolean ranWithoutPruning = false;

                if (depth <= 5) {
                    startTimeNoPruning = System.nanoTime();
                    // Ejecutamos la búsqueda de la mejor jugada pero con usePruning = false
                    agent.getBestMove(board, depth, false);
                    endTimeNoPruning = System.nanoTime();
                    durationNoPruningMs = (endTimeNoPruning - startTimeNoPruning) / 1_000_000.0;
                    ranWithoutPruning = true;
                } else {
                    // Si la profundidad es 6 o más, ejecutar un minimax completo sin poda
                    // puede demorar minutos. Hacemos una aproximación a profundidad de comparación
                    // de 4 o 5
                    // o advertimos de la omisión para evitar frustración del usuario.
                    System.out.println(ANSI_YELLOW
                            + "Nota: Búsqueda sin poda omitida para este turno para mantener la fluidez (profundidad > 5)."
                            + ANSI_RESET);
                }

                if (bestMove != null) {
                    // Mostrar primero la posición elegida por la IA sobre el tablero actual
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA: DECISIÓN ===" + ANSI_RESET);
                    System.out.println("La IA jugará en " + ANSI_YELLOW + (char) ('A' + bestMove[1]) + (bestMove[0] + 1)
                            + ANSI_RESET);
                    board.printBoard(null);

                    // Pausa para que el usuario lea la coordenada que la IA va a colocar
                    try {
                        Thread.sleep(1800);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Ocurre la animación de colocación y volteo
                    board.makeMoveWithAnimation(bestMove[0], bestMove[1], Board.WHITE);

                    // Limpiar pantalla para mostrar resultados arriba y el tablero abajo
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    // Mostrar métricas comparativas
                    System.out.println(ANSI_GREEN + ANSI_BOLD
                            + "+-------------------------------------------------------+" + ANSI_RESET);
                    System.out.println(ANSI_GREEN + ANSI_BOLD
                            + "|       MÉTRICAS DE EFICIENCIA - BÚSQUEDA ADVERSARIAL    |" + ANSI_RESET);
                    System.out.println(ANSI_GREEN + ANSI_BOLD
                            + "+-------------------------------------------------------+" + ANSI_RESET);

                    long nodesWith = agent.getNodesEvaluatedWithPruning();
                    System.out.printf("  Nodos evaluados CON poda Alfa-Beta : %,d%n", nodesWith);
                    System.out.printf("  Tiempo empleado CON poda           : %.2f ms%n", durationPruningMs);

                    if (ranWithoutPruning) {
                        long nodesWithout = agent.getNodesEvaluatedWithoutPruning();
                        System.out.printf("  Nodos evaluados SIN poda Alfa-Beta : %,d%n", nodesWithout);
                        System.out.printf("  Tiempo empleado SIN poda           : %.2f ms%n", durationNoPruningMs);

                        double reduction = (1.0 - ((double) nodesWith / nodesWithout)) * 100.0;
                        System.out.printf(
                                "  " + ANSI_YELLOW + "Reducción de nodos                 : %.2f%%%n" + ANSI_RESET,
                                reduction);
                        System.out.printf("  " + ANSI_YELLOW + "Velocidad relativa                 : %.1fx más rápido%n"
                                + ANSI_RESET, (durationNoPruningMs / durationPruningMs));
                    } else {
                        System.out.println(
                                "  Nodos evaluados SIN poda (Est.): > " + String.format("%,d", nodesWith * 15));
                        System.out.println("  Reducción de nodos estimada        : > 90.00%");
                    }
                    System.out.println(ANSI_GREEN + ANSI_BOLD
                            + "+-------------------------------------------------------+" + ANSI_RESET);

                    System.out.println("La IA jugó en " + ANSI_YELLOW + (char) ('A' + bestMove[1]) + (bestMove[0] + 1)
                            + ANSI_RESET);
                    System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA: JUGADA REALIZADA ===" + ANSI_RESET);

                    // Renderizar el tablero actualizado debajo de las métricas
                    board.printBoard(null);
                } else {
                    // Limpiar pantalla si pasa de turno
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(ANSI_RED + "La IA decidió pasar." + ANSI_RESET);
                    board.printBoard(null);
                }

                System.out.print("Presiona ENTER para tu turno...");
                scanner.nextLine();
            }

            // Cambiar turno
            turn = Board.getOpponent(turn);
        }

        // Fin del juego
        System.out.print("\033[H\033[2J");
        System.out.flush();
        board.printBoard(null);
        System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + ANSI_BOLD + "               ¡FIN DE LA PARTIDA!             " + ANSI_RESET);
        System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);

        int finalBlack = board.getScore(Board.BLACK);
        int finalWhite = board.getScore(Board.WHITE);

        System.out.println("Puntaje Final:");
        System.out.println(
                ANSI_GRAY_TEXT + "█" + ANSI_RESET + " Humano (Negras): " + ANSI_BOLD + finalBlack + ANSI_RESET);
        System.out.println(
                ANSI_WHITE_TEXT + "█" + ANSI_RESET + " IA (Blancas)   : " + ANSI_BOLD + finalWhite + ANSI_RESET + "\n");

        if (finalBlack > finalWhite) {
            System.out.println(
                    ANSI_GREEN + ANSI_BOLD + "¡Felicidades! Has derrotado a la Inteligencia Artificial." + ANSI_RESET);
        } else if (finalBlack < finalWhite) {
            System.out.println(
                    ANSI_RED + ANSI_BOLD + "La Inteligencia Artificial ha ganado. ¡Sigue practicando!" + ANSI_RESET);
        } else {
            System.out
                    .println(ANSI_YELLOW + ANSI_BOLD + "¡Empate técnico! Ha sido una partida legendaria." + ANSI_RESET);
        }

        scanner.close();
    }
}
