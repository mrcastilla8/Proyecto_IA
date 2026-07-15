

## Diagnóstico de Lógica y Código (Fallas y Hallazgos)

Se identificó un **error conceptual clásico en IA** que compromete la garantía de optimalidad teórica de A* y Greedy Best-First Search en grafos:

### Anomalía Crítica: Uso del Cerrado (`visited`) en Tiempo de Generación para A*

* **Detalle del código:** En `SearchAlgorithms.java` dentro del método `aStar` (líneas 183-191):
  ```java
  for (State.Successor succ : current.getState().getSuccessors()) {
      if (!visited.contains(succ.state)) {
          visited.add(succ.state); // <- ERROR TEÓRICO
          int g = current.getGCost() + succ.cost;
          int h = manhattanDistance(succ.state);
          SearchNode child = new SearchNode(succ.state, current, succ.action, g, h);
          frontier.add(child);
      }
  }
  ```

* **Explicación del Bug Teórico:** 
  Para garantizar que A* encuentre la ruta óptima (corta) en un grafo donde se pueden alcanzar los mismos estados por diferentes caminos (como el 8-puzzle), un nodo **solo debe ser marcado como visitado (cerrado) cuando es EXPANDIDO (retirado con `poll()` de la PriorityQueue)**, no cuando es generado (agregado a la frontera).
  
  Si marcas un estado como visitado al generarlo, y en el futuro encuentras ese mismo estado a través de un **camino más corto** (menor costo $g$), el condicional `!visited.contains(succ.state)` descartará la ruta óptima porque el estado ya existe en la lista de visitados (asociado a la ruta más larga/subóptima).

* **Por qué funciona en los casos del código:**
  En los casos de prueba incluidos (Fácil, Medio, Difícil), el algoritmo funciona porque la heurística Manhattan es consistente y la diferencia de ordenación no provocó colisiones que descartaran la ruta óptima. Sin embargo, para tableros más complejos o si se usara una heurística más débil, este detalle provocaría que A* devuelva soluciones subóptimas, lo cual contradice la definición matemática de A*.

* **Cómo solucionarlo (si deseas corregir el código):**
  Para que sea teóricamente correcto y 100% óptimo, debes:
  1. No agregar a `visited` durante la generación.
  2. Agregar el estado a `visited` inmediatamente después de hacer el `frontier.poll()` (al expandir el nodo).
  3. Si un nodo expandido ya está en `visited`, ignorarlo (`continue`) porque significa que ya fue expandido previamente por un camino más corto (debido al ordenamiento del $f(n)$ en la PriorityQueue).

