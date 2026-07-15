# Proyecto Grupal - Inteligencia Artificial (UNMSM)

Este repositorio contiene el desarrollo del Proyecto Grupal para la asignatura de **Inteligencia Artificial** de la Escuela Profesional de Ingeniería de Software (FISI - UNMSM), Semestre Académico 2026-I (Ciclo VIII).

El proyecto está estructurado en dos bloques principales:
* **Bloque A (Java):** Algoritmos de búsqueda en espacios de estados e implementación de agentes inteligentes para juegos adversariales.
* **Bloque B (Python):** Casos analíticos aplicados a una empresa ficticia utilizando modelos de aprendizaje supervisado, no supervisado y reducción de dimensionalidad.

---

## 👥 Integrantes y Roles

| Integrante | Rol | Responsabilidad Principal |
| :--- | :--- | :--- |
| **Angel Luis Kallpa De La Cruz Meza** | Especialista en Búsqueda (Java) | **Informe 1:** Modelado de búsqueda ciega (BFS, DFS) e informada (Greedy, A*). |
| **Marco Renato Castilla Huanca** | Especialista en Juegos y Poda (Java) | **Informe 2:** Implementación de Minimax con Poda Alfa-Beta (modo Humano vs. Máquina). |
| **Edu Sanchez Gotea** | Especialista en Regresión y EDA (Python) | **Informe 3:** EDA, preprocesamiento y entrenamiento de modelos de regresión. |
| **David Joel Aldana Chavez** | Especialista en Clasificación Avanzada (Python) | **Informe 4:** Clasificación binaria/multiclase y ajuste de hiperparámetros (GridSearchCV). |
| **Marcos Luis Basualdo Ale** | Especialista No Supervisado y Reducción (Python) | **Informes 5 y 6:** Agrupamiento (K-means, DBSCAN) y reducción de dimensionalidad (PCA, t-SNE). |
| **Leonardo Vera Rodriguez** | Ingeniero de Datos y Líder de Integración | **Transversal:** Generación del dataset sintético y consistencia analítica de la empresa. |
| **Gabriel Poma Gutierrez** | Ingeniero de QA, Prompts y Negocio | **Transversal:** Testing técnico (QA), consolidación de prompts de IA y autoevaluación. |

---

## 📂 Estructura del Repositorio

El repositorio está organizado de la siguiente manera:

```text
Proyecto_IA/
├── README.md                           # Presentación del proyecto, roles y guías de ejecución
├── .gitignore                          # Exclusión de binarios de Java y entornos virtuales de Python
│
├── bloque_a_java/                      # Bloque A: Algoritmos de búsqueda y juegos en Java
│   ├── informe_1_busqueda/             # Desarrollo del Informe 1 (Búsqueda en Espacio de Estados)
│   │   ├── src/                        # Código fuente .java (BFS, DFS, Greedy, A*)
│   │   ├── IA_Informe1.docx            # Informe escrito (Word)
│   │   └── diagnostico_informe1.md     # Diagnóstico del solucionador 8-puzzle
│   └── informe_2_juegos/               # Desarrollo del Informe 2 (Juegos Adversariales y Poda)
│       ├── src/                        # Código fuente .java (Minimax, Poda Alfa-Beta, Tablero)
│       ├── Informe 2.docx              # Documento del Informe 2 (Word)
│       └── Informe 2.pdf               # Documento del Informe 2 (PDF)
│
├── bloque_b_python/                    # Bloque B: Aprendizaje Automático sobre la Empresa Ficticia
│   ├── data/                           # Dataset y scripts de generación
│   │   ├── dataset_novaconecta.csv     # Dataset sintético utilizado por el equipo
│   │   └── generar_dataset.py          # Script reproducible de generación de datos
│   ├── notebooks/                      # Notebooks de Jupyter (.ipynb)
│   │   ├── Informe_3_Regresion.ipynb
│   │   ├── Informe_4_Clasificacion.ipynb
│   │   ├── Informe_5_Clustering.ipynb  # Algoritmos de clustering (K-Means, DBSCAN)
│   │   └── Informe_6_Reduccion.ipynb   # Reducción de dimensionalidad (PCA, t-SNE)
│   └── requirements.txt                # Dependencias requeridas del proyecto
│
└── entregables_finales/                 # Archivos finales del proyecto
    ├── informes_pdf/                   # Carpeta para informes consolidados
    ├── Anexo de Prompts - Generación del Dataset  (NovaConecta).pdf # Registro detallado de prompts de IA
    └── DefinicionEmpresaFicticia_NovaConecta.pdf                    # Documento de definición de la empresa
```

---

## 🛠️ Requisitos e Instalación

### ☕ Bloque A (Java)
* Requiere **Java Development Kit (JDK) 11** o superior instalado en el sistema.

* **Módulo de Búsqueda (Informe 1 - Solucionador de 8-Puzzle)**:
  Este módulo está diseñado intencionalmente de forma portátil utilizando estrictamente caracteres ASCII plano (evitando tildes y símbolos Unicode) para funcionar sin dependencias en cualquier codificación del terminal. Aun así, se recomienda compilar indicando el charset por uniformidad:
  ```bash
  cd bloque_a_java/informe_1_busqueda/src
  
  # Compilar forzando codificación UTF-8 (opcional pero recomendado)
  javac -encoding UTF-8 Main.java State.java SearchNode.java SearchAlgorithms.java
  
  # Ejecutar la aplicación interactiva
  java Main
  ```

* **Módulo de Juegos (Informe 2 - Othello con Poda Alfa-Beta)**:
  > [!IMPORTANT]
  > Este módulo **requiere obligatoriamente** compilarse con la bandera `-encoding UTF-8`. El archivo `Tablero.java` contiene caracteres especiales Unicode (`███` y `▒▒▒`) para pintar y animar el tablero y las fichas a color en consola. Si estás en Windows y compilas sin esta bandera, el compilador `javac` interpretará el código usando el charset por defecto de la plataforma (ej. Windows-1252) arrojando errores de compilación por caracteres no asignables (*unmappable character*).
  
  Navega hasta la carpeta del código fuente de juegos, compila forzando UTF-8 y ejecuta:
  ```bash
  cd bloque_a_java/informe_2_juegos/src
  
  # Compilar forzando codificación UTF-8 para admitir símbolos Unicode
  javac -encoding UTF-8 Main.java Tablero.java AgenteMinimax.java EvaluadorHeuristico.java
  
  # Ejecutar el simulador interactivo de Othello
  java Main
  ```
  *Características del Othello en consola:*
  - Agente de inteligencia artificial basado en Minimax optimizado con Poda Alfa-Beta.
  - Modos de juego interactivos: Humano vs. IA, IA vs. IA (demostración automática).
  - Interfaz de consola dinámica coloreada con códigos ANSI para mostrar el tablero y simular la animación del volteo de fichas.

---

### 🐍 Bloque B (Python)
* Requiere **Python 3.8 o superior** instalado en el sistema.

#### 1. Configuración del Entorno Virtual e Instalación de Dependencias
Para asegurar que los cuadernos corran correctamente sin conflictos de dependencias, se recomienda crear y activar un entorno virtual e instalar las librerías necesarias ejecutando los siguientes comandos desde la raíz del proyecto:

* **En Windows (PowerShell/CMD):**
  ```powershell
  # Crear entorno virtual en la carpeta venv
  python -m venv venv
  
  # Activar el entorno virtual
  .\venv\Scripts\activate
  
  # Instalar librerías de cálculo científico, machine learning, visualización y el servidor de Jupyter
  pip install numpy pandas matplotlib seaborn scikit-learn imbalanced-learn joblib faker notebook
  ```

* **En Unix / macOS:**
  ```bash
  # Crear entorno virtual en la carpeta venv
  python3 -m venv venv
  
  # Activar el entorno virtual
  source venv/bin/activate
  
  # Instalar librerías necesarias
  pip install numpy pandas matplotlib seaborn scikit-learn imbalanced-learn joblib faker notebook
  ```

#### 2. Generación del Dataset (Opcional)
Si deseas volver a generar el dataset sintético de la empresa ficticia **NovaConecta** con las reglas analíticas predefinidas:
```bash
cd bloque_b_python/data
python generar_dataset.py
```
*Esto actualizará el archivo `dataset_novaconecta.csv` respetando la semilla fija para reproducibilidad.*

#### 3. Ejecución de los Cuadernos de Jupyter (Notebooks)
Para revisar y ejecutar paso a paso los modelos y análisis de cada informe del Bloque B:
1. Activa tu entorno virtual (si no lo está).
2. Ejecuta el servidor de Jupyter Notebook:
   ```bash
   jupyter notebook
   ```
3. Tu navegador web se abrirá automáticamente. Navega a la carpeta `bloque_b_python/notebooks/` y abre cualquiera de los cuadernos de interés:
   - **Informe 3 (Modelos de Regresión)**: [Informe_3_Regresion.ipynb](file:///c:/Users/marec/Desktop/Proyecto_gamarra/bloque_b_python/notebooks/Informe_3_Regresion.ipynb) (Modelado y evaluación de consumo y costos).
   - **Informe 4 (Modelos de Clasificación)**: [Informe_4_Clasificacion.ipynb](file:///c:/Users/marec/Desktop/Proyecto_gamarra/bloque_b_python/notebooks/Informe_4_Clasificacion.ipynb) (Predicción de fuga de clientes con optimización de hiperparámetros e imbalanced-learn).
   - **Informe 5 (Modelado No Supervisado)**: [Informe_5_Clustering.ipynb](file:///c:/Users/marec/Desktop/Proyecto_gamarra/bloque_b_python/notebooks/Informe_5_Clustering.ipynb) (Segmentación de clientes vía K-Means y DBSCAN).
   - **Informe 6 (Reducción de Dimensionalidad)**: [Informe_6_Reduccion.ipynb](file:///c:/Users/marec/Desktop/Proyecto_gamarra/bloque_b_python/notebooks/Informe_6_Reduccion.ipynb) (Visualización de clústeres mediante PCA y t-SNE).
