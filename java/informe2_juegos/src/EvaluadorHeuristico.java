import java.util.List;

public class EvaluadorHeuristico {

    // Matriz de pesos posicionales clásicos para Othello (8x8)
    private static final int[][] PESOS_CASILLAS = {
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
     * Retorna el peso posicional dinámico de una celda.
     * Si es una casilla C (-20) o X (-50) y su esquina adyacente correspondiente
     * ya está ocupada, se elimina la penalización y se le asigna un valor positivo (+5).
     */
    private static int getPesoCasilla(Tablero tablero, int f, int c) {
        int peso = PESOS_CASILLAS[f][c];

        // Verificar casillas C y X
        if (peso == -20 || peso == -50) {
            int esquinaF = (f < 4) ? 0 : 7;
            int esquinaC = (c < 4) ? 0 : 7;
            
            if (tablero.getCasilla(esquinaF, esquinaC) != Tablero.VACIO) {
                // La esquina ya está ocupada, por lo que jugar aquí es seguro y ayuda a consolidar el borde.
                return 5;
            }
        }
        return peso;
    }

    /**
     * Evalúa el estado del tablero desde la perspectiva del jugador de IA.
     * Retorna una puntuación entera. Valores más altos indican ventaja para la IA.
     */
    public static int evaluar(Tablero tablero, char jugadorIA, char jugadorHumano) {
        // Si el estado es terminal, retornar utilidad absoluta (infinito / victoria o derrota)
        if (tablero.esTerminal()) {
            int puntajeIA = tablero.getPuntaje(jugadorIA);
            int puntajeHumano = tablero.getPuntaje(jugadorHumano);
            if (puntajeIA > puntajeHumano) {
                return 1000000 + (puntajeIA - puntajeHumano); // Victoria
            } else if (puntajeIA < puntajeHumano) {
                return -1000000 - (puntajeHumano - puntajeIA); // Derrota
            } else {
                return 0; // Empate
            }
        }

        int puntajePosicion = 0;
        int conteoIA = 0;
        int conteoHumano = 0;

        // 1. Evaluar posiciones y conteo de fichas
        for (int f = 0; f < Tablero.TAMANIO; f++) {
            for (int c = 0; c < Tablero.TAMANIO; c++) {
                char celda = tablero.getCasilla(f, c);
                if (celda == jugadorIA) {
                    puntajePosicion += getPesoCasilla(tablero, f, c);
                    conteoIA++;
                } else if (celda == jugadorHumano) {
                    puntajePosicion -= getPesoCasilla(tablero, f, c);
                    conteoHumano++;
                }
            }
        }

        // 2. Evaluar Movilidad (cantidad de jugadas legales disponibles)
        List<int[]> movimientosIA = tablero.getMovimientosLegales(jugadorIA);
        List<int[]> movimientosHumano = tablero.getMovimientosLegales(jugadorHumano);
        int puntajeMovilidad = movimientosIA.size() - movimientosHumano.size();

        // 3. Paridad de fichas (recuento relativo)
        int puntajeParidad = conteoIA - conteoHumano;

        // Ponderar componentes del score
        // La movilidad es crucial a mitad del juego para evitar quedarse sin opciones.
        // Los pesos posicionales (esquinas) tienen la prioridad más alta.
        int puntajeFinal = (puntajePosicion * 10) + (puntajeMovilidad * 15) + (puntajeParidad * 2);

        return puntajeFinal;
    }
}
