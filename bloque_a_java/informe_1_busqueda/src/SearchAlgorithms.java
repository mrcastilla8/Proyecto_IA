import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Motor de Inteligencia Artificial: implementa BFS, DFS, Greedy Best-First
 * Search y A* sobre el espacio de estados del 8-Puzzle.
 */
public class SearchAlgorithms {

    /**
     * Resultado estandarizado de una busqueda: nodo meta encontrado (o null
     * si no hubo solucion), cantidad de nodos expandidos y costo de la
     * solucion (longitud de la ruta, ya que cada accion cuesta 1).
     */
    public static class SearchResult {
        public final SearchNode goalNode;
        public final int nodesExpanded;
        public final int solutionCost;
        public final boolean found;

        public SearchResult(SearchNode goalNode, int nodesExpanded, int solutionCost, boolean found) {
            this.goalNode = goalNode;
            this.nodesExpanded = nodesExpanded;
            this.solutionCost = solutionCost;
            this.found = found;
        }
    }

    /**
     * Heuristica de Distancia de Manhattan: para cada ficha (excepto el
     * espacio en blanco) suma la distancia (en filas + columnas) entre su
     * posicion actual y su posicion objetivo en el estado meta.
     */
    public static int manhattanDistance(State state) {
        int[] board = state.getBoard();
        int distance = 0;
        for (int i = 0; i < board.length; i++) {
            int value = board[i];
            if (value == 0) {
                continue;
            }
            int targetIndex = value - 1; // posicion del valor "value" dentro de GOAL
            int currentRow = i / State.SIZE;
            int currentCol = i % State.SIZE;
            int targetRow = targetIndex / State.SIZE;
            int targetCol = targetIndex % State.SIZE;
            distance += Math.abs(currentRow - targetRow) + Math.abs(currentCol - targetCol);
        }
        return distance;
    }

    /**
     * BFS (Busqueda a lo Ancho): explora nivel por nivel usando una Queue
     * (FIFO). Al no tener costos negativos ni variables, garantiza la
     * solucion optima (minimo numero de movimientos).
     */
    public static SearchResult bfs(State initial) {
        int nodosExpandidos = 0;
        SearchNode root = new SearchNode(initial, null, null, 0, 0);

        Queue<SearchNode> frontier = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        frontier.add(root);
        visited.add(initial);

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.poll();

            if (current.getState().isGoal()) {
                return new SearchResult(current, nodosExpandidos, current.getGCost(), true);
            }

            nodosExpandidos++;
            for (State.Successor succ : current.getState().getSuccessors()) {
                if (!visited.contains(succ.state)) {
                    visited.add(succ.state);
                    SearchNode child = new SearchNode(
                            succ.state, current, succ.action, current.getGCost() + succ.cost, 0);
                    frontier.add(child);
                }
            }
        }
        return new SearchResult(null, nodosExpandidos, -1, false);
    }

    /**
     * DFS (Busqueda en Profundidad): explora usando una Stack (LIFO), con
     * control estricto de visitados (HashSet) para evitar ciclos infinitos
     * en el grafo de estados. No garantiza la solucion optima.
     */
    public static SearchResult dfs(State initial) {
        int nodosExpandidos = 0;
        SearchNode root = new SearchNode(initial, null, null, 0, 0);

        Deque<SearchNode> frontier = new ArrayDeque<>(); // usada como Stack: push()/pop()
        Set<State> visited = new HashSet<>();
        frontier.push(root);
        visited.add(initial);

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.pop();

            if (current.getState().isGoal()) {
                return new SearchResult(current, nodosExpandidos, current.getGCost(), true);
            }

            nodosExpandidos++;
            for (State.Successor succ : current.getState().getSuccessors()) {
                if (!visited.contains(succ.state)) {
                    visited.add(succ.state); // marcar visitado al generar, evita ciclos/explosion
                    SearchNode child = new SearchNode(
                            succ.state, current, succ.action, current.getGCost() + succ.cost, 0);
                    frontier.push(child);
                }
            }
        }
        return new SearchResult(null, nodosExpandidos, -1, false);
    }

    /**
     * Greedy Best-First Search: usa una PriorityQueue ordenada unicamente
     * por h(n) (Distancia de Manhattan). Es rapida pero no garantiza la
     * solucion optima.
     */
    public static SearchResult greedy(State initial) {
        int nodosExpandidos = 0;
        SearchNode root = new SearchNode(initial, null, null, 0, manhattanDistance(initial));

        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(Comparator.comparingInt(SearchNode::getHCost));
        Set<State> visited = new HashSet<>();
        frontier.add(root);
        visited.add(initial);

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.poll();

            if (current.getState().isGoal()) {
                return new SearchResult(current, nodosExpandidos, current.getGCost(), true);
            }

            nodosExpandidos++;
            for (State.Successor succ : current.getState().getSuccessors()) {
                if (!visited.contains(succ.state)) {
                    visited.add(succ.state);
                    int h = manhattanDistance(succ.state);
                    SearchNode child = new SearchNode(
                            succ.state, current, succ.action, current.getGCost() + succ.cost, h);
                    frontier.add(child);
                }
            }
        }
        return new SearchResult(null, nodosExpandidos, -1, false);
    }

    /**
     * A*: usa una PriorityQueue ordenada por f(n) = g(n) + h(n), con h(n)
     * = Distancia de Manhattan (admisible y consistente para el 8-Puzzle),
     * lo que garantiza encontrar la solucion optima.
     */
    public static SearchResult aStar(State initial) {
        int nodosExpandidos = 0;
        SearchNode root = new SearchNode(initial, null, null, 0, manhattanDistance(initial));

        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(Comparator.comparingInt(SearchNode::getF));
        Set<State> visited = new HashSet<>();
        frontier.add(root);
        visited.add(initial);

        while (!frontier.isEmpty()) {
            SearchNode current = frontier.poll();

            if (current.getState().isGoal()) {
                return new SearchResult(current, nodosExpandidos, current.getGCost(), true);
            }

            nodosExpandidos++;
            for (State.Successor succ : current.getState().getSuccessors()) {
                if (!visited.contains(succ.state)) {
                    visited.add(succ.state);
                    int g = current.getGCost() + succ.cost;
                    int h = manhattanDistance(succ.state);
                    SearchNode child = new SearchNode(succ.state, current, succ.action, g, h);
                    frontier.add(child);
                }
            }
        }
        return new SearchResult(null, nodosExpandidos, -1, false);
    }
}
