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

        while (true) {
            // Limpiar pantalla al iniciar la ejecución y al volver al menú principal
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "          OTHELLO (REVERSI) EN JAVA            " + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "     Búsqueda Adversarial con Poda Alfa-Beta   " + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
            System.out.println("Responsable: " + ANSI_YELLOW + "Marco Renato Castilla Huanca" + ANSI_RESET + "\n");

            // 1. Selección de Modo de Juego
            System.out.println("Selecciona el Modo de Juego:");
            System.out.println("1) Humano vs. Inteligencia Artificial (Modo Clásico)");
            System.out.println("2) Inteligencia Artificial vs. Inteligencia Artificial (Modo Espectador)");
            System.out.println("3) Salir");
            System.out.print("Opción (1-3): ");
            String modeOpt = scanner.nextLine().trim();

            if (modeOpt.equals("3")) {
                System.out.println("\n¡Gracias por jugar! Saliendo del programa...");
                break;
            }

            int gameMode = 1;
            if (modeOpt.equals("2")) {
                gameMode = 2;
            } else if (!modeOpt.equals("1")) {
                System.out.println(ANSI_RED + "Opción no válida. Reintentando..." + ANSI_RESET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            int depth1 = 5; // Para Negras (IA 1 en modo 2)
            int depth2 = 5; // Para Blancas (IA en modo 1, IA 2 en modo 2)
            boolean isAutoPlay = false; // Solo modo 2: true para simulación continua, false para manual-ENTER

            if (gameMode == 1) {
                // Selección de dificultad Humano vs IA (Modo 1)
                System.out.println("\nSelecciona la dificultad de la IA (Profundidad de búsqueda):");
                System.out.println("1) Fácil (Profundidad 3) - Respuesta instantánea, juego casual.");
                System.out.println("2) Medio (Profundidad 5) - Reto moderado, excelente balance. [Recomendado]");
                System.out.println("3) Difícil (Profundidad 6) - Pensamiento profundo, más estratégico.");
                System.out.println("4) Personalizado");
                System.out.print("Opción (1-4): ");
                String optStr = scanner.nextLine().trim();
                int selectedDepth = 5;
                if (optStr.equals("1")) {
                    selectedDepth = 3;
                } else if (optStr.equals("2")) {
                    selectedDepth = 5;
                } else if (optStr.equals("3")) {
                    selectedDepth = 6;
                } else if (optStr.equals("4")) {
                    System.out.print("Introduce la profundidad deseada (1-10): ");
                    try {
                        selectedDepth = Integer.parseInt(scanner.nextLine().trim());
                        if (selectedDepth < 1) selectedDepth = 1;
                        if (selectedDepth > 10) {
                            System.out.println(ANSI_RED + "Profundidad limitada a 8 para evitar sobrecarga de memoria." + ANSI_RESET);
                            selectedDepth = 8;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Usando profundidad 5 por defecto.");
                        selectedDepth = 5;
                    }
                } else {
                    System.out.println("Opción no válida. Usando profundidad 5 por defecto.");
                }
                depth2 = selectedDepth; // IA es Blancas
            } else {
                // Configuración IA vs IA (Modo 2)
                System.out.println("\n--- CONFIGURACIÓN DEL MODO ESPECTADOR (IA vs. IA) ---");
                
                // IA 1 (Negras)
                System.out.println("Selecciona la dificultad de la IA 1 (Negras - █):");
                System.out.println("1) Fácil (Profundidad 3)");
                System.out.println("2) Medio (Profundidad 5) [Recomendado]");
                System.out.println("3) Difícil (Profundidad 6)");
                System.out.print("Opción (1-3): ");
                String opt1 = scanner.nextLine().trim();
                if (opt1.equals("1")) depth1 = 3;
                else if (opt1.equals("2")) depth1 = 5;
                else if (opt1.equals("3")) depth1 = 6;
                else System.out.println("Opción inválida. Usando profundidad 5 por defecto.");

                // IA 2 (Blancas)
                System.out.println("\nSelecciona la dificultad de la IA 2 (Blancas - ░):");
                System.out.println("1) Fácil (Profundidad 3)");
                System.out.println("2) Medio (Profundidad 5) [Recomendado]");
                System.out.println("3) Difícil (Profundidad 6)");
                System.out.print("Opción (1-3): ");
                String opt2 = scanner.nextLine().trim();
                if (opt2.equals("1")) depth2 = 3;
                else if (opt2.equals("2")) depth2 = 5;
                else if (opt2.equals("3")) depth2 = 6;
                else System.out.println("Opción inválida. Usando profundidad 5 por defecto.");

                // Flujo de avance
                System.out.println("\nSelecciona el avance de los turnos:");
                System.out.println("1) Manual (Paso a paso presionando ENTER)");
                System.out.println("2) Automático (Simulación continua con retardo de 1.8 segundos)");
                System.out.print("Opción (1-2): ");
                String optFlow = scanner.nextLine().trim();
                if (optFlow.equals("2")) {
                    isAutoPlay = true;
                }
            }

            // Pantalla de inicio
            if (gameMode == 1) {
                System.out.println("\nIniciando partida. Tú eres " + ANSI_GRAY_TEXT + "█" + ANSI_RESET + ANSI_BOLD + " Humano (Negras)" + ANSI_RESET + 
                                   ". La IA es " + ANSI_WHITE_TEXT + "█" + ANSI_RESET + ANSI_BOLD + " IA (Blancas)" + ANSI_RESET + ".");
                System.out.println("Las casillas marcadas con '" + ANSI_YELLOW_TEXT + "+" + ANSI_RESET + "' son tus movimientos válidos.");
            } else {
                System.out.println("\nIniciando partida IA vs. IA.");
                System.out.println("Fichas Negras (█): IA 1 (Profundidad " + depth1 + ")");
                System.out.println("Fichas Blancas (░): IA 2 (Profundidad " + depth2 + ")");
                System.out.println("Flujo: " + (isAutoPlay ? "Automático (1.8s de retraso)" : "Manual (presiona ENTER para cada jugada)"));
            }
            System.out.print("Presiona ENTER para comenzar el juego...");
            scanner.nextLine();

            Board board = new Board();
            if (gameMode == 2) {
                board.setPlayerLabels("IA 1 (Negras)", "IA 2 (Blancas)");
            }
            MinimaxAgent agent = new MinimaxAgent();
            char turn = Board.BLACK; // Comienza el jugador Negro

            while (!board.isTerminal()) {
                List<int[]> legalMoves = board.getLegalMoves(turn);

                if (legalMoves.isEmpty()) {
                    // Lógica de "Pass" en Othello
                    String playerStr;
                    if (gameMode == 1) {
                        playerStr = (turn == Board.BLACK) ? "Humano (Negras)" : "IA (Blancas)";
                    } else {
                        playerStr = (turn == Board.BLACK) ? "IA 1 (Negras)" : "IA 2 (Blancas)";
                    }
                    System.out.println(ANSI_RED + "\n¡No hay movimientos legales para " + playerStr + "! Pasa de turno." + ANSI_RESET);
                    System.out.print("Presiona ENTER para continuar...");
                    scanner.nextLine();
                    turn = Board.getOpponent(turn);
                    continue;
                }

                if (turn == Board.BLACK) {
                    // Turno de las Negras
                    if (gameMode == 1) {
                        // MODO 1: Humano
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        board.printBoard(legalMoves); // Tablero siempre arriba
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TU TURNO: HUMANO (NEGRAS) ===" + ANSI_RESET);
                        int[] selectedMove = null;

                        while (selectedMove == null) {
                            System.out.print("Tu turno. Introduce tu coordenada (Ejemplo: F5 o C4): ");
                            String input = scanner.nextLine().trim().toUpperCase();

                            if (input.length() != 2) {
                                System.out.println(ANSI_RED + "Entrada inválida. Debe ser una letra (A-H) y un número (1-8)." + ANSI_RESET);
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
                                System.out.println(ANSI_RED + "¡Ese no es un movimiento válido! Revisa las pistas (+)." + ANSI_RESET);
                                continue;
                            }

                            selectedMove = new int[]{row, col};
                        }

                        board.makeMoveWithAnimation(selectedMove[0], selectedMove[1], Board.BLACK);
                        System.out.println("\nJugaste en " + ANSI_YELLOW + (char)('A' + selectedMove[1]) + (selectedMove[0] + 1) + ANSI_RESET);
                        
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        // MODO 2: IA 1 (Negras)
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        board.printBoard(null); // Tablero siempre arriba
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA 1: PENSANDO... ===" + ANSI_RESET);
                        System.out.println(ANSI_CYAN + "IA 1 pensando... (Profundidad: " + depth1 + ")" + ANSI_RESET);

                        agent.resetCounters();
                        
                        // Calcular jugada con poda
                        long startTimePruning = System.nanoTime();
                        int[] bestMove = agent.getBestMove(board, depth1, true, Board.BLACK);
                        long endTimePruning = System.nanoTime();
                        double durationPruningMs = (endTimePruning - startTimePruning) / 1_000_000.0;

                        // Calcular sin poda para métricas (si depth1 <= 5)
                        long startTimeNoPruning = 0;
                        long endTimeNoPruning = 0;
                        double durationNoPruningMs = 0;
                        boolean ranWithoutPruning = false;

                        if (depth1 <= 5) {
                            startTimeNoPruning = System.nanoTime();
                            agent.getBestMove(board, depth1, false, Board.BLACK);
                            endTimeNoPruning = System.nanoTime();
                            durationNoPruningMs = (endTimeNoPruning - startTimeNoPruning) / 1_000_000.0;
                            ranWithoutPruning = true;
                        }

                        if (bestMove != null) {
                            // Anunciar jugada encima del tablero actual
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            board.printBoard(null); // Tablero siempre arriba
                            System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA 1: DECISIÓN ===" + ANSI_RESET);
                            System.out.println("La IA 1 jugará en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                            
                            try {
                                Thread.sleep(1200);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                            // Animación de colocación (coloca la ficha en la misma posición de pantalla)
                            board.makeMoveWithAnimation(bestMove[0], bestMove[1], Board.BLACK);
                            
                            // Limpiar y mostrar métricas
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            board.printBoard(null); // Tablero siempre arriba

                            System.out.println("La IA 1 jugó en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                            System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA 1: JUGADA REALIZADA ===" + ANSI_RESET);

                            System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                            System.out.println(ANSI_GREEN + ANSI_BOLD + "|   MÉTRICAS IA 1 (NEGRAS) - BÚSQUEDA ADVERSARIAL       |" + ANSI_RESET);
                            System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                            
                            long nodesWith = agent.getNodesEvaluatedWithPruning();
                            System.out.printf("  Nodos evaluados CON poda Alfa-Beta : %,d%n", nodesWith);
                            System.out.printf("  Tiempo empleado CON poda           : %.2f ms%n", durationPruningMs);
                            
                            if (ranWithoutPruning) {
                                long nodesWithout = agent.getNodesEvaluatedWithoutPruning();
                                System.out.printf("  Nodos evaluados SIN poda Alfa-Beta : %,d%n", nodesWithout);
                                System.out.printf("  Tiempo empleado SIN poda           : %.2f ms%n", durationNoPruningMs);
                                
                                double reduction = (1.0 - ((double) nodesWith / nodesWithout)) * 100.0;
                                System.out.printf("  " + ANSI_YELLOW + "Reducción de nodos                 : %.2f%%%n" + ANSI_RESET, reduction);
                            } else {
                                System.out.println("  Nodos evaluados SIN poda (Est.): > " + String.format("%,d", nodesWith * 15));
                            }
                            System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                        }

                        if (isAutoPlay) {
                            try {
                                Thread.sleep(1800);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        } else {
                            System.out.print("Presiona ENTER para el turno de la IA 2...");
                            scanner.nextLine();
                        }
                    }

                } else {
                    // Turno de las Blancas (IA en Modo 1, IA 2 en Modo 2)
                    String iaName = (gameMode == 1) ? "IA" : "IA 2";
                    
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    board.printBoard(null); // Tablero siempre arriba
                    System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA " + iaName + ": PENSANDO... ===" + ANSI_RESET);
                    System.out.println(ANSI_CYAN + iaName + " pensando... (Profundidad: " + depth2 + ")" + ANSI_RESET);

                    agent.resetCounters();
                    
                    // Calcular jugada con poda
                    long startTimePruning = System.nanoTime();
                    int[] bestMove = agent.getBestMove(board, depth2, true, Board.WHITE);
                    long endTimePruning = System.nanoTime();
                    double durationPruningMs = (endTimePruning - startTimePruning) / 1_000_000.0;

                    // Calcular sin poda para métricas (si depth2 <= 5)
                    long startTimeNoPruning = 0;
                    long endTimeNoPruning = 0;
                    double durationNoPruningMs = 0;
                    boolean ranWithoutPruning = false;

                    if (depth2 <= 5) {
                        startTimeNoPruning = System.nanoTime();
                        agent.getBestMove(board, depth2, false, Board.WHITE);
                        endTimeNoPruning = System.nanoTime();
                        durationNoPruningMs = (endTimeNoPruning - startTimeNoPruning) / 1_000_000.0;
                        ranWithoutPruning = true;
                    }

                    if (bestMove != null) {
                        // Anunciar jugada encima del tablero actual
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        board.printBoard(null); // Tablero siempre arriba
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA " + iaName + ": DECISIÓN ===" + ANSI_RESET);
                        System.out.println("La " + iaName + " jugará en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                        
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        // Animación de colocación
                        board.makeMoveWithAnimation(bestMove[0], bestMove[1], Board.WHITE);
                        
                        // Limpiar y mostrar métricas
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        board.printBoard(null); // Tablero siempre arriba

                        System.out.println("La " + iaName + " jugó en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA " + iaName + ": JUGADA REALIZADA ===" + ANSI_RESET);

                        System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + ANSI_BOLD + "|   MÉTRICAS " + iaName.toUpperCase() + " (BLANCAS) - BÚSQUEDA ADVERSARIAL   |" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                        
                        long nodesWith = agent.getNodesEvaluatedWithPruning();
                        System.out.printf("  Nodos evaluados CON poda Alfa-Beta : %,d%n", nodesWith);
                        System.out.printf("  Tiempo empleado CON poda           : %.2f ms%n", durationPruningMs);
                        
                        if (ranWithoutPruning) {
                            long nodesWithout = agent.getNodesEvaluatedWithoutPruning();
                            System.out.printf("  Nodos evaluados SIN poda Alfa-Beta : %,d%n", nodesWithout);
                            System.out.printf("  Tiempo empleado SIN poda           : %.2f ms%n", durationNoPruningMs);
                            
                            double reduction = (1.0 - ((double) nodesWith / nodesWithout)) * 100.0;
                            System.out.printf("  " + ANSI_YELLOW + "Reducción de nodos                 : %.2f%%%n" + ANSI_RESET, reduction);
                            System.out.printf("  " + ANSI_YELLOW + "Velocidad relativa                 : %.1fx más rápido%n" + ANSI_RESET, (durationNoPruningMs / durationPruningMs));
                        } else {
                            System.out.println("  Nodos evaluados SIN poda (Est.): > " + String.format("%,d", nodesWith * 15));
                        }
                        System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                    } else {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        board.printBoard(null); // Tablero siempre arriba
                        System.out.println(ANSI_RED + "La " + iaName + " decidió pasar." + ANSI_RESET);
                    }

                    if (gameMode == 2 && isAutoPlay) {
                        try {
                            Thread.sleep(1800);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        String nextTurnMsg = (gameMode == 1) ? "tu turno..." : "el turno de la IA 1...";
                        System.out.print("Presiona ENTER para " + nextTurnMsg);
                        scanner.nextLine();
                    }
                }

                // Cambiar turno
                turn = Board.getOpponent(turn);
            }

            // Fin del juego
            System.out.print("\033[H\033[2J");
            System.out.flush();
            board.printBoard(null); // Tablero siempre arriba
            System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "               ¡FIN DE LA PARTIDA!             " + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
            
            int finalBlack = board.getScore(Board.BLACK);
            int finalWhite = board.getScore(Board.WHITE);

            System.out.println("Puntaje Final:");
            String nameBlack = (gameMode == 1) ? "Humano (Negras)" : "IA 1 (Negras)";
            String nameWhite = (gameMode == 1) ? "IA (Blancas)" : "IA 2 (Blancas)";

            System.out.println(ANSI_GRAY_TEXT + "█" + ANSI_RESET + " " + nameBlack + ": " + ANSI_BOLD + finalBlack + ANSI_RESET);
            System.out.println(ANSI_WHITE_TEXT + "█" + ANSI_RESET + " " + nameWhite + ": " + ANSI_BOLD + finalWhite + ANSI_RESET + "\n");

            if (finalBlack > finalWhite) {
                if (gameMode == 1) {
                    System.out.println(ANSI_GREEN + ANSI_BOLD + "¡Felicidades! Has derrotado a la Inteligencia Artificial." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_GREEN + ANSI_BOLD + "¡Partida finalizada! La IA 1 (Negras) ha ganado el enfrentamiento." + ANSI_RESET);
                }
            } else if (finalBlack < finalWhite) {
                if (gameMode == 1) {
                    System.out.println(ANSI_RED + ANSI_BOLD + "La Inteligencia Artificial ha ganado. ¡Sigue practicando!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + ANSI_BOLD + "¡Partida finalizada! La IA 2 (Blancas) ha ganado el enfrentamiento." + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_YELLOW + ANSI_BOLD + "¡Empate técnico! Ha sido una batalla algorítmica legendaria." + ANSI_RESET);
            }

            System.out.print("\nPresiona ENTER para volver al menú principal...");
            scanner.nextLine();
        }

        scanner.close();
    }
}
