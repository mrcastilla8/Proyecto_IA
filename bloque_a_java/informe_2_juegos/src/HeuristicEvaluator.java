
import java.util.List;

public class HeuristicEvaluator {

    // Matriz de pesos posicionales clásicos para Othello (8x8)
    private static final int[][] CELL_WEIGHTS = {
        { 100, -20,  10,   5,   5,  10, -20, 100 },
        { -20, -50,  -2,  -2,  -2,  -2, -50, -20 },
        {  10,  -2,   3,   1,   1,   3,  -2,  10 },
        {   5,  -2,   1,   1,   1,   1,  -2,   5 },
        {   5,  -2,   1,   1,   1,   1,  -2,   5 },
        {  10,  -2,   3,   1,   1,   3,  -2,  10 },
        { -20, -50,  -2,  -2,  -2,  -2, -50, -20 },
        { 100, -20,  10,   5,   5,  10, -20, 100 }
    };

    /**
     * Evalúa el estado del tablero desde la perspectiva del jugador de IA.
     * Retorna una puntuación entera. Valores más altos indican ventaja para la IA.
     */
    public static int evaluate(Board board, char aiPlayer, char humanPlayer) {
        // Si el estado es terminal, retornar utilidad absoluta (infinito / victoria o derrota)
        if (board.isTerminal()) {
            int aiScore = board.getScore(aiPlayer);
            int humanScore = board.getScore(humanPlayer);
            if (aiScore > humanScore) {
                return 1000000 + (aiScore - humanScore); // Victoria
            } else if (aiScore < humanScore) {
                return -1000000 - (humanScore - aiScore); // Derrota
            } else {
                return 0; // Empate
            }
        }

        int positionScore = 0;
        int aiCount = 0;
        int humanCount = 0;

        // 1. Evaluar posiciones y conteo de fichas
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                char cell = board.getCell(r, c);
                if (cell == aiPlayer) {
                    positionScore += CELL_WEIGHTS[r][c];
                    aiCount++;
                } else if (cell == humanPlayer) {
                    positionScore -= CELL_WEIGHTS[r][c];
                    humanCount++;
                }
            }
        }

        // 2. Evaluar Movilidad (cantidad de jugadas legales disponibles)
        List<int[]> aiMoves = board.getLegalMoves(aiPlayer);
        List<int[]> humanMoves = board.getLegalMoves(humanPlayer);
        int mobilityScore = aiMoves.size() - humanMoves.size();

        // 3. Paridad de fichas (recuento relativo)
        int parityScore = aiCount - humanCount;

        // Ponderar componentes del score
        // La movilidad es crucial a mitad del juego para evitar quedarse sin opciones.
        // Los pesos posicionales (esquinas) tienen la prioridad más alta.
        int finalScore = (positionScore * 10) + (mobilityScore * 15) + (parityScore * 2);

        return finalScore;
    }
}
