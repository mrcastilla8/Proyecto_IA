import java.util.List;

public class MinimaxAgent {
    private long nodesEvaluatedWithPruning;
    private long nodesEvaluatedWithoutPruning;

    public MinimaxAgent() {
        resetCounters();
    }

    public void resetCounters() {
        nodesEvaluatedWithPruning = 0;
        nodesEvaluatedWithoutPruning = 0;
    }

    public long getNodesEvaluatedWithPruning() {
        return nodesEvaluatedWithPruning;
    }

    public long getNodesEvaluatedWithoutPruning() {
        return nodesEvaluatedWithoutPruning;
    }

    /**
     * Retorna la coordenada de la mejor jugada [fila, columna] para el jugador IA especificado.
     */
    public int[] getBestMove(Board board, int maxDepth, boolean usePruning, char player) {
        char aiPlayer = player;
        
        List<int[]> legalMoves = board.getLegalMoves(aiPlayer);
        if (legalMoves.isEmpty()) {
            return null; // No hay jugadas posibles (debe pasar)
        }

        int[] bestMove = legalMoves.get(0);
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int[] move : legalMoves) {
            Board tempBoard = board.getClone();
            tempBoard.makeMove(move[0], move[1], aiPlayer);

            // Iniciar búsqueda Minimax recursiva (el siguiente turno es del oponente, que minimiza)
            int moveValue = minimax(tempBoard, maxDepth - 1, false, alpha, beta, usePruning, aiPlayer);

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }

            if (usePruning) {
                alpha = Math.max(alpha, moveValue);
            }
        }

        return bestMove;
    }

    /**
     * Algoritmo Minimax unificado con opción de Poda Alfa-Beta, parametrizado por el jugador maximizante.
     */
    public int minimax(Board board, int depth, boolean isMax, int alpha, int beta, boolean usePruning, char maximizingPlayer) {
        // Incrementar el contador de nodos evaluados
        if (usePruning) {
            nodesEvaluatedWithPruning++;
        } else {
            nodesEvaluatedWithoutPruning++;
        }

        // Caso base: profundidad límite alcanzada o fin del juego
        if (depth == 0 || board.isTerminal()) {
            return HeuristicEvaluator.evaluate(board, maximizingPlayer, Board.getOpponent(maximizingPlayer));
        }

        char currentPlayer = isMax ? maximizingPlayer : Board.getOpponent(maximizingPlayer);
        List<int[]> moves = board.getLegalMoves(currentPlayer);

        // Si el jugador actual no tiene movimientos, pero el juego no ha terminado (paso de turno)
        if (moves.isEmpty()) {
            return minimax(board, depth - 1, !isMax, alpha, beta, usePruning, maximizingPlayer);
        }

        if (isMax) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : moves) {
                Board child = board.getClone();
                child.makeMove(move[0], move[1], currentPlayer);
                
                int eval = minimax(child, depth - 1, false, alpha, beta, usePruning, maximizingPlayer);
                maxEval = Math.max(maxEval, eval);

                if (usePruning) {
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break; // Poda Beta
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : moves) {
                Board child = board.getClone();
                child.makeMove(move[0], move[1], currentPlayer);
                
                int eval = minimax(child, depth - 1, true, alpha, beta, usePruning, maximizingPlayer);
                minEval = Math.min(minEval, eval);

                if (usePruning) {
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break; // Poda Alfa
                    }
                }
            }
            return minEval;
        }
    }
}
