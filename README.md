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
│   │   ├── src/                        # Código fuente .java
│   │   └── README.md                   # Guía de ejecución del módulo de búsqueda
│   └── informe_2_juegos/               # Desarrollo del Informe 2 (Juegos Adversariales y Poda)
│       ├── src/                        # Código fuente .java (Minimax, Poda Alfa-Beta)
│       └── README.md                   # Guía de juego y explicación del contador de nodos
│
├── bloque_b_python/                    # Bloque B: Aprendizaje Automático sobre la Empresa Ficticia
│   ├── data/                           # Dataset y scripts de generación
│   │   ├── dataset_novaconecta.csv     # Dataset sintético utilizado por el equipo
│   │   └── generar_dataset.py          # Script reproducible de generación de datos
│   ├── notebooks/                      # Notebooks de Jupyter (.ipynb)
│   │   ├── Informe_3_Regresion.ipynb
│   │   ├── Informe_4_Clasificacion.ipynb
│   │   ├── Informe_5_Agrupamiento.ipynb
│   │   └── Informe_6_Reduccion.ipynb
│   └── requirements.txt                # Dependencias requeridas del proyecto
│
└── entregables_finales/                 # PDF consolidados del proyecto
    ├── informes_pdf/                   # Informes técnicos en PDF (1 al 6)
    ├── Anexo_Prompts.pdf               # Bitácora detallada de prompts de IA
    └── Autoevaluacion.pdf              # Cuadro de autoevaluación (Sección 11)
```

---

## 🛠️ Requisitos e Instalación

### Bloque A (Java)
* Requiere **Java Development Kit (JDK) 11** o superior.
* Para compilar y ejecutar cualquiera de los módulos, navega a la carpeta correspondiente e inicia la clase `Main`:
  ```bash
  cd bloque_a_java/informe_1_busqueda/src
  javac Main.java
  java Main
  ```

### Bloque B (Python)
* Requiere **Python 3.8+**.
* Se recomienda crear un entorno virtual para instalar las dependencias:
  ```bash
  python -m venv venv
  # En Windows:
  .\venv\Scripts\activate
  # Instalar dependencias:
  pip install -r bloque_b_python/requirements.txt
  ```
