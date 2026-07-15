import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        // Códigos ANSI para dar estilo
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_WHITE = "\u001B[37m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BOLD = "\u001B[1m";
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
            String opcModo = teclado.nextLine().trim();

            if (opcModo.equals("3")) {
                System.out.println("\n¡Gracias por jugar! Saliendo del programa...");
                break;
            }

            int modoJuego = 1;
            if (opcModo.equals("2")) {
                modoJuego = 2;
            } else if (!opcModo.equals("1")) {
                System.out.println(ANSI_RED + "Opción no válida. Reintentando..." + ANSI_RESET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            int prof1 = 5; // Para Negras (IA 1 en modo 2)
            int prof2 = 5; // Para Blancas (IA en modo 1, IA 2 en modo 2)
            boolean esAuto = false; // Solo modo 2: true para simulación continua, false para manual-ENTER

            if (modoJuego == 1) {
                // Selección de dificultad Humano vs IA (Modo 1)
                System.out.println("\nSelecciona la dificultad de la IA (Profundidad de búsqueda):");
                System.out.println("1) Fácil (Profundidad 3) - Respuesta instantánea, juego casual.");
                System.out.println("2) Medio (Profundidad 5) - Reto moderado, excelente balance. [Recomendado]");
                System.out.println("3) Difícil (Profundidad 6) - Pensamiento profundo, más estratégico.");
                System.out.println("4) Personalizado");
                System.out.print("Opción (1-4): ");
                String opcStr = teclado.nextLine().trim();
                int profSeleccionada = 5;
                if (opcStr.equals("1")) {
                    profSeleccionada = 3;
                } else if (opcStr.equals("2")) {
                    profSeleccionada = 5;
                } else if (opcStr.equals("3")) {
                    profSeleccionada = 6;
                } else if (opcStr.equals("4")) {
                    System.out.print("Introduce la profundidad deseada (1-10): ");
                    try {
                        profSeleccionada = Integer.parseInt(teclado.nextLine().trim());
                        if (profSeleccionada < 1) profSeleccionada = 1;
                        if (profSeleccionada > 10) {
                            System.out.println(ANSI_RED + "Profundidad limitada a 8 para evitar sobrecarga de memoria." + ANSI_RESET);
                            profSeleccionada = 8;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Usando profundidad 5 por defecto.");
                        profSeleccionada = 5;
                    }
                } else {
                    System.out.println("Opción no válida. Usando profundidad 5 por defecto.");
                }
                prof2 = profSeleccionada; // IA es Blancas
            } else {
                // Configuración IA vs IA (Modo 2)
                System.out.println("\n--- CONFIGURACIÓN DEL MODO ESPECTADOR (IA vs. IA) ---");
                
                // IA 1 (Negras)
                System.out.println("Selecciona la dificultad de la IA 1 (Negras - █):");
                System.out.println("1) Fácil (Profundidad 3)");
                System.out.println("2) Medio (Profundidad 5) [Recomendado]");
                System.out.println("3) Difícil (Profundidad 6)");
                System.out.print("Opción (1-3): ");
                String opcIA1 = teclado.nextLine().trim();
                if (opcIA1.equals("1")) prof1 = 3;
                else if (opcIA1.equals("2")) prof1 = 5;
                else if (opcIA1.equals("3")) prof1 = 6;
                else System.out.println("Opción inválida. Usando profundidad 5 por defecto.");

                // IA 2 (Blancas)
                System.out.println("\nSelecciona la dificultad de la IA 2 (Blancas - ░):");
                System.out.println("1) Fácil (Profundidad 3)");
                System.out.println("2) Medio (Profundidad 5) [Recomendado]");
                System.out.println("3) Difícil (Profundidad 6)");
                System.out.print("Opción (1-3): ");
                String opcIA2 = teclado.nextLine().trim();
                if (opcIA2.equals("1")) prof2 = 3;
                else if (opcIA2.equals("2")) prof2 = 5;
                else if (opcIA2.equals("3")) prof2 = 6;
                else System.out.println("Opción inválida. Usando profundidad 5 por defecto.");

                // Flujo de avance
                System.out.println("\nSelecciona el avance de los turnos:");
                System.out.println("1) Manual (Paso a paso presionando ENTER)");
                System.out.println("2) Automático (Simulación continua con retardo de 1.8 segundos)");
                System.out.print("Opción (1-2): ");
                String opcFlujo = teclado.nextLine().trim();
                if (opcFlujo.equals("2")) {
                    esAuto = true;
                }
            }

            // Pantalla de inicio
            if (modoJuego == 1) {
                System.out.println("\nIniciando partida. Tú eres " + ANSI_GRAY_TEXT + "█" + ANSI_RESET + ANSI_BOLD + " Humano (Negras)" + ANSI_RESET + 
                                   ". La IA es " + ANSI_WHITE_TEXT + "█" + ANSI_RESET + ANSI_BOLD + " IA (Blancas)" + ANSI_RESET + ".");
                System.out.println("Las casillas marcadas con '" + ANSI_YELLOW_TEXT + "+" + ANSI_RESET + "' son tus movimientos válidos.");
            } else {
                System.out.println("\nIniciando partida IA vs. IA.");
                System.out.println("Fichas Negras (█): IA 1 (Profundidad " + prof1 + ")");
                System.out.println("Fichas Blancas (░): IA 2 (Profundidad " + prof2 + ")");
                System.out.println("Flujo: " + (esAuto ? "Automático (1.8s de retraso)" : "Manual (presiona ENTER para cada jugada)"));
            }
            System.out.print("Presiona ENTER para comenzar el juego...");
            teclado.nextLine();

            Tablero tablero = new Tablero();
            if (modoJuego == 2) {
                tablero.setEtiquetasJugadores("IA 1 (Negras)", "IA 2 (Blancas)");
            }
            AgenteMinimax agente = new AgenteMinimax();
            char turno = Tablero.NEGRO; // Comienza el jugador Negro

            while (!tablero.esTerminal()) {
                List<int[]> movimientosLegales = tablero.getMovimientosLegales(turno);

                if (movimientosLegales.isEmpty()) {
                    // Lógica de "Pass" en Othello
                    String jugadorStr;
                    if (modoJuego == 1) {
                        jugadorStr = (turno == Tablero.NEGRO) ? "Humano (Negras)" : "IA (Blancas)";
                    } else {
                        jugadorStr = (turno == Tablero.NEGRO) ? "IA 1 (Negras)" : "IA 2 (Blancas)";
                    }
                    System.out.println(ANSI_RED + "\n¡No hay movimientos legales para " + jugadorStr + "! Pasa de turno." + ANSI_RESET);
                    System.out.print("Presiona ENTER para continuar...");
                    teclado.nextLine();
                    turno = Tablero.getOponente(turno);
                    continue;
                }

                if (turno == Tablero.NEGRO) {
                    // Turno de las Negras
                    if (modoJuego == 1) {
                        // MODO 1: Humano
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        tablero.imprimirTablero(movimientosLegales); // Tablero siempre arriba
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TU TURNO: HUMANO (NEGRAS) ===" + ANSI_RESET);
                        int[] movSeleccionado = null;

                        while (movSeleccionado == null) {
                            System.out.print("Tu turno. Introduce tu coordenada (Ejemplo: F5 o C4): ");
                            String entrada = teclado.nextLine().trim().toUpperCase();

                            if (entrada.length() != 2) {
                                System.out.println(ANSI_RED + "Entrada inválida. Debe ser una letra (A-H) y un número (1-8)." + ANSI_RESET);
                                continue;
                            }

                            char colChar = entrada.charAt(0);
                            char rowChar = entrada.charAt(1);

                            int col = colChar - 'A';
                            int row = rowChar - '1';

                            if (!Tablero.esCoordenadaValida(row, col)) {
                                System.out.println(ANSI_RED + "Coordenadas fuera del tablero." + ANSI_RESET);
                                continue;
                            }

                            // Verificar si es un movimiento legal
                            boolean esLegal = false;
                            for (int[] mov : movimientosLegales) {
                                if (mov[0] == row && mov[1] == col) {
                                    esLegal = true;
                                    break;
                                }
                            }

                            if (!esLegal) {
                                System.out.println(ANSI_RED + "¡Ese no es un movimiento válido! Revisa las pistas (+)." + ANSI_RESET);
                                continue;
                            }

                            movSeleccionado = new int[]{row, col};
                        }

                        tablero.realizarMovimientoConAnimacion(movSeleccionado[0], movSeleccionado[1], Tablero.NEGRO);
                        System.out.println("\nJugaste en " + ANSI_YELLOW + (char)('A' + movSeleccionado[1]) + (movSeleccionado[0] + 1) + ANSI_RESET);
                        
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        // MODO 2: IA 1 (Negras)
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        tablero.imprimirTablero(null); // Tablero siempre arriba
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA 1: PENSANDO... ===" + ANSI_RESET);
                        System.out.println(ANSI_CYAN + "IA 1 pensando... (Profundidad: " + prof1 + ")" + ANSI_RESET);

                        agente.reiniciarContadores();
                        
                        // Calcular jugada con poda
                        long startTimePruning = System.nanoTime();
                        int[] bestMove = agente.getMejorMovimiento(tablero, prof1, true, Tablero.NEGRO);
                        long endTimePruning = System.nanoTime();
                        double durationPruningMs = (endTimePruning - startTimePruning) / 1_000_000.0;

                        // Calcular sin poda para métricas (si prof1 <= 5)
                        long startTimeNoPruning = 0;
                        long endTimeNoPruning = 0;
                        double durationNoPruningMs = 0;
                        boolean corrioSinPoda = false;

                        if (prof1 <= 5) {
                            startTimeNoPruning = System.nanoTime();
                            agente.getMejorMovimiento(tablero, prof1, false, Tablero.NEGRO);
                            endTimeNoPruning = System.nanoTime();
                            durationNoPruningMs = (endTimeNoPruning - startTimeNoPruning) / 1_000_000.0;
                            corrioSinPoda = true;
                        }

                        if (bestMove != null) {
                            // Anunciar jugada encima del tablero actual
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            tablero.imprimirTablero(null); // Tablero siempre arriba
                            System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA 1: DECISIÓN ===" + ANSI_RESET);
                            System.out.println("La IA 1 jugará en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                            
                            try {
                                Thread.sleep(1200);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                            // Animación de colocación
                            tablero.realizarMovimientoConAnimacion(bestMove[0], bestMove[1], Tablero.NEGRO);
                            
                            // Limpiar y mostrar métricas
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            tablero.imprimirTablero(null); // Tablero siempre arriba

                            System.out.println("La IA 1 jugó en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                            System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA IA 1: JUGADA REALIZADA ===" + ANSI_RESET);

                            System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                            System.out.println(ANSI_GREEN + ANSI_BOLD + "|   MÉTRICAS IA 1 (NEGRAS) - BÚSQUEDA ADVERSARIAL       |" + ANSI_RESET);
                            System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                            
                            long nodosCon = agente.getNodosEvaluadosConPoda();
                            System.out.printf("  Nodos evaluados CON poda Alfa-Beta : %,d%n", nodosCon);
                            System.out.printf("  Tiempo empleado CON poda           : %.2f ms%n", durationPruningMs);
                            
                            if (corrioSinPoda) {
                                long nodosSin = agente.getNodosEvaluadosSinPoda();
                                System.out.printf("  Nodos evaluados SIN poda Alfa-Beta : %,d%n", nodosSin);
                                System.out.printf("  Tiempo empleado SIN poda           : %.2f ms%n", durationNoPruningMs);
                                
                                double reduccion = (1.0 - ((double) nodosCon / nodosSin)) * 100.0;
                                System.out.printf("  " + ANSI_YELLOW + "Reducción de nodos                 : %.2f%%%n" + ANSI_RESET, reduccion);
                            } else {
                                System.out.println("  Nodos evaluados SIN poda (Est.): > " + String.format("%,d", nodosCon * 15));
                            }
                            System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                        }

                        if (esAuto) {
                            try {
                                Thread.sleep(1800);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        } else {
                            System.out.print("Presiona ENTER para el turno de la IA 2...");
                            teclado.nextLine();
                        }
                    }

                } else {
                    // Turno de las Blancas (IA en Modo 1, IA 2 en Modo 2)
                    String nombreIA = (modoJuego == 1) ? "IA" : "IA 2";
                    
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    tablero.imprimirTablero(null); // Tablero siempre arriba
                    System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA " + nombreIA + ": PENSANDO... ===" + ANSI_RESET);
                    System.out.println(ANSI_CYAN + nombreIA + " pensando... (Profundidad: " + prof2 + ")" + ANSI_RESET);

                    agente.reiniciarContadores();
                    
                    // Calcular jugada con poda
                    long startTimePruning = System.nanoTime();
                    int[] bestMove = agente.getMejorMovimiento(tablero, prof2, true, Tablero.BLANCO);
                    long endTimePruning = System.nanoTime();
                    double durationPruningMs = (endTimePruning - startTimePruning) / 1_000_000.0;

                    // Calcular sin poda para métricas (si prof2 <= 5)
                    long startTimeNoPruning = 0;
                    long endTimeNoPruning = 0;
                    double durationNoPruningMs = 0;
                    boolean corrioSinPoda = false;

                    if (prof2 <= 5) {
                        startTimeNoPruning = System.nanoTime();
                        agente.getMejorMovimiento(tablero, prof2, false, Tablero.BLANCO);
                        endTimeNoPruning = System.nanoTime();
                        durationNoPruningMs = (endTimeNoPruning - startTimeNoPruning) / 1_000_000.0;
                        corrioSinPoda = true;
                    }

                    if (bestMove != null) {
                        // Anunciar jugada encima del tablero actual
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        tablero.imprimirTablero(null); // Tablero siempre arriba
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA " + nombreIA + ": DECISIÓN ===" + ANSI_RESET);
                        System.out.println("La " + nombreIA + " jugará en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                        
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        // Animación de colocación
                        tablero.realizarMovimientoConAnimacion(bestMove[0], bestMove[1], Tablero.BLANCO);
                        
                        // Limpiar y mostrar métricas
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        tablero.imprimirTablero(null); // Tablero siempre arriba

                        System.out.println("La " + nombreIA + " jugó en " + ANSI_YELLOW + (char)('A' + bestMove[1]) + (bestMove[0] + 1) + ANSI_RESET);
                        System.out.println(ANSI_CYAN + ANSI_BOLD + "=== TURNO DE LA " + nombreIA + ": JUGADA REALIZADA ===" + ANSI_RESET);

                        System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + ANSI_BOLD + "|   MÉTRICAS " + nombreIA.toUpperCase() + " (BLANCAS) - BÚSQUEDA ADVERSARIAL   |" + ANSI_RESET);
                        System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                        
                        long nodosCon = agente.getNodosEvaluadosConPoda();
                        System.out.printf("  Nodos evaluados CON poda Alfa-Beta : %,d%n", nodosCon);
                        System.out.printf("  Tiempo empleado CON poda           : %.2f ms%n", durationPruningMs);
                        
                        if (corrioSinPoda) {
                            long nodosSin = agente.getNodosEvaluadosSinPoda();
                            System.out.printf("  Nodos evaluados SIN poda Alfa-Beta : %,d%n", nodosSin);
                            System.out.printf("  Tiempo empleado SIN poda           : %.2f ms%n", durationNoPruningMs);
                            
                            double reduccion = (1.0 - ((double) nodosCon / nodosSin)) * 100.0;
                            System.out.printf("  " + ANSI_YELLOW + "Reducción de nodos                 : %.2f%%%n" + ANSI_RESET, reduccion);
                            System.out.printf("  " + ANSI_YELLOW + "Velocidad relativa                 : %.1fx más rápido%n" + ANSI_RESET, (durationNoPruningMs / durationPruningMs));
                        } else {
                            System.out.println("  Nodos evaluados SIN poda (Est.): > " + String.format("%,d", nodosCon * 15));
                        }
                        System.out.println(ANSI_GREEN + ANSI_BOLD + "+-------------------------------------------------------+" + ANSI_RESET);
                    } else {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        tablero.imprimirTablero(null); // Tablero siempre arriba
                        System.out.println(ANSI_RED + "La " + nombreIA + " decidió pasar." + ANSI_RESET);
                    }

                    if (modoJuego == 2 && esAuto) {
                        try {
                            Thread.sleep(1800);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        String siguienteMsg = (modoJuego == 1) ? "tu turno..." : "el turno de la IA 1...";
                        System.out.print("Presiona ENTER para " + siguienteMsg);
                        teclado.nextLine();
                    }
                }

                // Cambiar turno
                turno = Tablero.getOponente(turno);
            }

            // Fin del juego
            System.out.print("\033[H\033[2J");
            System.out.flush();
            tablero.imprimirTablero(null); // Tablero siempre arriba
            System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "               ¡FIN DE LA PARTIDA!             " + ANSI_RESET);
            System.out.println(ANSI_CYAN + ANSI_BOLD + "===============================================" + ANSI_RESET);
            
            int finalNegro = tablero.getPuntaje(Tablero.NEGRO);
            int finalBlanco = tablero.getPuntaje(Tablero.BLANCO);

            System.out.println("Puntaje Final:");
            String nombreNegro = (modoJuego == 1) ? "Humano (Negras)" : "IA 1 (Negras)";
            String nombreBlanco = (modoJuego == 1) ? "IA (Blancas)" : "IA 2 (Blancas)";

            System.out.println(ANSI_GRAY_TEXT + "█" + ANSI_RESET + " " + nombreNegro + ": " + ANSI_BOLD + finalNegro + ANSI_RESET);
            System.out.println(ANSI_WHITE_TEXT + "█" + ANSI_RESET + " " + nombreBlanco + ": " + ANSI_BOLD + finalBlanco + ANSI_RESET + "\n");

            if (finalNegro > finalBlanco) {
                if (modoJuego == 1) {
                    System.out.println(ANSI_GREEN + ANSI_BOLD + "¡Felicidades! Has derrotado a la Inteligencia Artificial." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_GREEN + ANSI_BOLD + "¡Partida finalizada! La IA 1 (Negras) ha ganado el enfrentamiento." + ANSI_RESET);
                }
            } else if (finalNegro < finalBlanco) {
                if (modoJuego == 1) {
                    System.out.println(ANSI_RED + ANSI_BOLD + "La Inteligencia Artificial ha ganado. ¡Sigue practicando!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + ANSI_BOLD + "¡Partida finalizada! La IA 2 (Blancas) ha ganado el enfrentamiento." + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_YELLOW + ANSI_BOLD + "¡Empate técnico! Ha sido una batalla algorítmica legendaria." + ANSI_RESET);
            }

            System.out.print("\nPresiona ENTER para volver al menú principal...");
            teclado.nextLine();
        }

        teclado.close();
    }
}
