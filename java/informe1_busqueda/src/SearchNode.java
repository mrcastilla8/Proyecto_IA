import java.util.LinkedList;
import java.util.List;

/**
 * Envuelve un State dentro de un nodo del arbol de busqueda, manteniendo
 * la referencia al nodo padre (para reconstruir la ruta), la accion que
 * genero el nodo y los costos g (camino recorrido) y h (heuristica).
 */
public class SearchNode {

    private final State state;
    private final SearchNode parent;
    private final String action;
    private final int gCost;
    private final int hCost;

    public SearchNode(State state, SearchNode parent, String action, int gCost, int hCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public State getState() {
        return state;
    }

    public SearchNode getParent() {
        return parent;
    }

    public String getAction() {
        return action;
    }

    public int getGCost() {
        return gCost;
    }

    public int getHCost() {
        return hCost;
    }

    /**
     * Costo total estimado f(n) = g(n) + h(n), usado por A*.
     */
    public int getF() {
        return gCost + hCost;
    }

    /**
     * Reconstruye la secuencia de acciones desde la raiz hasta este nodo,
     * recorriendo los punteros "parent" hacia atras.
     */
    public List<String> reconstructPath() {
        LinkedList<String> path = new LinkedList<>();
        SearchNode current = this;
        while (current.parent != null) {
            path.addFirst(current.action);
            current = current.parent;
        }
        return path;
    }

    @Override
    public String toString() {
        return "SearchNode{state=" + state + ", g=" + gCost + ", h=" + hCost + ", f=" + getF() + "}";
    }
}
