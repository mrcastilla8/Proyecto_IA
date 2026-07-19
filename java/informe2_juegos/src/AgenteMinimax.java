import java.util.List;

public class AgenteMinimax {
    private long nodosEvaluadosConPoda;
    private long nodosEvaluadosSinPoda;

    public AgenteMinimax() {
        reiniciarContadores();
    }

    public void reiniciarContadores() {
        nodosEvaluadosConPoda = 0;
        nodosEvaluadosSinPoda = 0;
    }

    public long getNodosEvaluadosConPoda() {
        return nodosEvaluadosConPoda;
    }

    public long getNodosEvaluadosSinPoda() {
        return nodosEvaluadosSinPoda;
    }

    /**
     * Retorna la coordenada de la mejor jugada [fila, columna] para el jugador IA especificado.
     */
    public int[] getMejorMovimiento(Tablero tablero, int profundidadMaxima, boolean usarPoda, char jugador) {
        char jugadorIA = jugador;
        
        List<int[]> movimientosLegales = tablero.getMovimientosLegales(jugadorIA);
        if (movimientosLegales.isEmpty()) {
            return null; // No hay jugadas posibles (debe pasar)
        }

        int[] mejorMovimiento = movimientosLegales.get(0);
        int mejorValor = Integer.MIN_VALUE;
        int alfa = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int[] mov : movimientosLegales) {
            Tablero tableroTemporal = tablero.getClon();
            tableroTemporal.realizarMovimiento(mov[0], mov[1], jugadorIA);

            // Iniciar búsqueda Minimax recursiva (el siguiente turno es del oponente, que minimiza)
            int valorMovimiento = minimax(tableroTemporal, profundidadMaxima - 1, false, alfa, beta, usarPoda, jugadorIA);

            if (valorMovimiento > mejorValor) {
                mejorValor = valorMovimiento;
                mejorMovimiento = mov;
            }

            if (usarPoda) {
                alfa = Math.max(alfa, valorMovimiento);
            }
        }

        return mejorMovimiento;
    }

    /**
     * Algoritmo Minimax unificado con opción de Poda Alfa-Beta, parametrizado por el jugador maximizante.
     */
    public int minimax(Tablero tablero, int profundidad, boolean esMaximizador, int alfa, int beta, boolean usarPoda, char jugadorMaximizador) {
        // Incrementar el contador de nodos evaluados
        if (usarPoda) {
            nodosEvaluadosConPoda++;
        } else {
            nodosEvaluadosSinPoda++;
        }

        // Caso base: profundidad límite alcanzada o fin del juego
        if (profundidad == 0 || tablero.esTerminal()) {
            return EvaluadorHeuristico.evaluar(tablero, jugadorMaximizador, Tablero.getOponente(jugadorMaximizador));
        }

        char jugadorActual = esMaximizador ? jugadorMaximizador : Tablero.getOponente(jugadorMaximizador);
        List<int[]> movimientos = tablero.getMovimientosLegales(jugadorActual);

        // Si el jugador actual no tiene movimientos, pero el juego no ha terminado (paso de turno)
        if (movimientos.isEmpty()) {
            return minimax(tablero, profundidad - 1, !esMaximizador, alfa, beta, usarPoda, jugadorMaximizador);
        }

        if (esMaximizador) {
            int evaluacionMax = Integer.MIN_VALUE;
            for (int[] mov : movimientos) {
                Tablero hijo = tablero.getClon();
                hijo.realizarMovimiento(mov[0], mov[1], jugadorActual);
                
                int eval = minimax(hijo, profundidad - 1, false, alfa, beta, usarPoda, jugadorMaximizador);
                evaluacionMax = Math.max(evaluacionMax, eval);

                if (usarPoda) {
                    alfa = Math.max(alfa, eval);
                    if (beta <= alfa) {
                        break; // Poda Beta
                    }
                }
            }
            return evaluacionMax;
        } else {
            int evaluacionMin = Integer.MAX_VALUE;
            for (int[] mov : movimientos) {
                Tablero hijo = tablero.getClon();
                hijo.realizarMovimiento(mov[0], mov[1], jugadorActual);
                
                int eval = minimax(hijo, profundidad - 1, true, alfa, beta, usarPoda, jugadorMaximizador);
                evaluacionMin = Math.min(evaluacionMin, eval);

                if (usarPoda) {
                    beta = Math.min(beta, eval);
                    if (beta <= alfa) {
                        break; // Poda Alfa
                    }
                }
            }
            return evaluacionMin;
        }
    }
}
