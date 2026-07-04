UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

INSTRUCCIONES DEL PROYECTO GRUPAL (PG)
Asignatura: Inteligencia Artificial
Escuela Profesional de Ingeniería de Software — FISI — UNMSM
Semestre Académico 2026-I  ·  Ciclo VIII

1. Presentación y objetivos

El Proyecto Grupal (PG) es la evaluación integradora de la asignatura de Inteligencia
Artificial. Articula las cuatro unidades del sílabo y se compone de seis informes que
abarcan la búsqueda en el espacio de estados, los juegos adversariales y los modelos
de  aprendizaje  automático  supervisado  y  no  supervisado.  El  PG  forma  parte  de  la
Evaluación Continua (EC), donde EC = PG × 0.40 + (TA × (Porcentaje_Asistencia +
0.08)) × 0.60.

Estructura  de  informes  (según  el  sílabo):  Informe  1  —  Programa  en  Java  de
Búsqueda Informada y No Informada; Informe 2 — Programa en Java de Búsqueda
de Juegos con Poda Alfa-Beta; Informe 3 — Notebook con Modelos de Regresiones;
Informe 4 — Notebook con Modelo de Clasificación en Python; Informe 5 — Notebook
con  Modelo  de  Agrupamiento  en  Python;  Informe  6  —  Notebook  con  Modelo  de
Reducción de la Dimensionalidad.

1.1. Objetivo general

Desarrollar e integrar soluciones de Inteligencia Artificial que abarquen la búsqueda
en  el  espacio  de  estados,  los  juegos  adversariales  y  los  modelos  de  aprendizaje
automático supervisado y no supervisado, aplicando criterio técnico, ético e innovador
en un trabajo colaborativo.

1.2. Objetivos específicos

•  Implementar  algoritmos  de  búsqueda  no  informada  (BFS,  DFS)  e  informada
(Greedy  Best-First,  A*)  en  Java  para  resolver  un  problema  modelado  en  el
espacio de estados (CE1).

•  Implementar  un  agente  para  un  juego  adversarial  de  dos  jugadores  mediante

Minimax con poda Alfa-Beta en Java (CE1).

•  Construir y evaluar modelos de regresión para la predicción de valores continuos

sobre datos sintéticos (CE2).

•  Construir y evaluar modelos de clasificación supervisada, incluyendo técnicas de

ensamble y ajuste de hiperparámetros (CE2).

•  Aplicar  algoritmos  de  agrupamiento  no  supervisado  y  determinar  el  número

óptimo de grupos (CE3).

•  Aplicar

técnicas  de  reducción  de  dimensionalidad  (PCA,

t-SNE)  para

visualización y preprocesamiento (CE3).

•  Documentar de manera transparente el uso de herramientas de IA generativa

(prompts), incluyendo el detalle de cómo se generaron los datos sintéticos.

2. Conformación de los grupos

•  Tamaño: cada grupo tendrá un mínimo de 5 y un máximo de 7 integrantes.
•  Registro: los grupos se registran en el aula virtual dentro del plazo indicado por

el docente. No se aceptan integrantes sin grupo registrado.

Prof. Juan Gamarra Moreno

Pág 1 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

•  Responsabilidad  compartida:  todos  los  integrantes  deben  contribuir  y
cualquiera  debe  poder  sustentar  cualquier  parte  del  proyecto.  Se  aplicará
coevaluación entre pares.

Roles sugeridos (pueden combinarse según el tamaño del grupo):

Coordinador(a)

Responsable de Búsqueda
(Java)
Responsable de Juegos (Java)
Responsable de Datos
Sintéticos
Responsable de Modelos
Supervisados
Responsable de Modelos No
Supervisados
Responsable de
Documentación y Prompts

Organiza el trabajo, controla plazos y consolida la entrega
final.
Lidera el Informe 1 (BFS, DFS, Greedy, A*).

Lidera el Informe 2 (Minimax con poda Alfa-Beta).
Diseña, genera y documenta el dataset de la empresa ficticia.

Lidera los Informes 3 (regresión) y 4 (clasificación).

Lidera los Informes 5 (agrupamiento) y 6 (reducción de
dimensionalidad).
Consolida los informes, el anexo de prompts y la
sustentación.

3. Estructura general del proyecto

El PG se organiza en dos bloques. El Bloque A (Informes 1 y 2) se desarrolla en
Java.  El  Bloque  B  (Informes  3  a  6)  se  desarrolla  en  notebooks  de  Python,
articulados en torno a una sola empresa ficticia y un conjunto de datos sintéticos
propio del grupo.
Bloque
A

Informe
1

Herramienta

Unidad I

Unidad

Tema

Java

2
3
4
5
6

A
B
B
B
B

Búsqueda informada y no
informada
Juegos con poda Alfa-Beta
Modelos de regresión
Modelo de clasificación
Modelo de agrupamiento
Reducción de dimensionalidad

Java
Python / Jupyter
Python / Jupyter
Python / Jupyter
Python / Jupyter

Unidad I
Unidad II
Unidad III
Unidad IV
Unidad IV

Nota:  los  Informes  3  a  6  comparten  la  misma  empresa  ficticia  y  el  mismo  dataset
sintético, de modo que constituyan un caso analítico coherente.

4. Descripción detallada de los entregables

4.1. Bloque A — Programas en Java

Importante: el grupo debe elegir dos problemas diferentes: uno para el Informe 1
(problema de búsqueda en el espacio de estados) y otro distinto para el Informe 2
(juego adversarial de dos jugadores). No se acepta el mismo problema en ambos
informes.

4.1.1. Informe 1 — Búsqueda Informada y No Informada (Java)
Objetivo:  modelar  un  problema  como  búsqueda  en  el  espacio  de  estados  e
implementar algoritmos ciegos e informados.

•  Definir  formalmente  el  problema:  estados,  estado  inicial,  estado(s)  meta,

operadores/acciones y costos.

•  Implementar al menos BFS y DFS (no informada); Greedy Best-First Search y
A* (informada) con al menos una heurística admisible y consistente, justificada.
•  Comparar  los  algoritmos  en:  nodos  expandidos,  longitud/costo  de  la  solución,

tiempo de ejecución, completitud y optimalidad.

•  Permitir el ingreso o configuración del caso de prueba.

Prof. Juan Gamarra Moreno

Pág 2 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

Problemas sugeridos  (elegir uno o proponer otro con aprobación del docente): 8-
puzzle  /  15-puzzle;  búsqueda  de  rutas  en  un  mapa  de  ciudades  del  Perú  con
coordenadas y distancias; laberintos; problema de las jarras de agua; misioneros y
caníbales; ruta óptima de reparto en una cuadrícula.

Entregables:  código  fuente  Java  (.java),  informe  en  PDF,  tabla  comparativa  de
resultados, capturas de ejecución y anexo de prompts.

4.1.2. Informe 2 — Juegos con Poda Alfa-Beta (Java)
Objetivo:  implementar  un  agente  inteligente  para  un  juego  adversarial  de  dos
jugadores mediante Minimax con poda Alfa-Beta.

•  Modelar el juego: estados, jugadas legales, función terminal/utilidad y función de

evaluación heurística (si se usa profundidad limitada).

•  Implementar Minimax con poda Alfa-Beta.
•  Permitir el modo humano vs. máquina (opcionalmente máquina vs. máquina).
•  Reportar  el  número  de  nodos  evaluados  con  y  sin  poda,  para  evidenciar  la

eficiencia de la poda Alfa-Beta.

Juegos sugeridos (elegir uno, distinto del problema del Informe 1): Tic-Tac-Toe (3
en  raya);  Conecta  4;  Nim;  Gato  en  tablero  4×4;  Reversi/Otello  reducido;  Damas
simplificadas; Gomoku en tablero pequeño.

Entregables: código fuente Java (.java), informe en PDF, análisis de la eficiencia de
la poda, capturas de partidas y anexo de prompts.

4.2. Bloque B — Notebooks en Python: el caso de la empresa ficticia

4.2.1. Lineamientos del caso

•  Empresa  ficticia:  el  grupo  define  una  empresa  ficticia  con  nombre,  rubro,

contexto de negocio y problema analítico (todo inventado).

•  Cobertura:  el  rubro  debe  permitir  aplicar  las  cuatro  técnicas  (regresión,
clasificación,  agrupamiento  y  reducción  de  dimensionalidad)  sobre  un  mismo
conjunto de datos sintéticos coherente.

•  Rubros  sugeridos:  retail  /  e-commerce,  fintech  /  banca,  telecomunicaciones,
salud (healthtech), logística / delivery, educación (edtech), seguros o turismo.
•  Descripción del caso: debe explicar la empresa, el problema de negocio, las
preguntas analíticas que responde cada informe y por qué cada técnica aporta
valor.

•  Coherencia: los cuatro informes usan el mismo dataset (o datasets relacionados

derivados del mismo proceso generador).

4.2.2. Informe 3 — Modelos de Regresión
Objetivo: predecir una variable continua del negocio (p. ej., monto de venta, valor de
vida del cliente, tiempo de entrega o demanda).

•  Análisis  exploratorio

(EDA):  correlaciones,  valores

faltantes/atípicos  y

escalamiento.

•  Modelos: regresión lineal simple y múltiple, regresión polinómica, Ridge y Lasso,

y al menos un modelo avanzado (SVR o Gradient Boosting).

•  Evaluación:  MSE,  RMSE  y  R²  (MAE  opcional);  validación  train/test  y,  de  ser

posible, validación cruzada.

Prof. Juan Gamarra Moreno

Pág 3 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

•  Selección  y  justificación  del  mejor  modelo;  interpretación  de  coeficientes  o

importancias; visualizaciones (dispersión, residuos, predicho vs. real).

4.2.3. Informe 4 — Modelo de Clasificación
Objetivo:  predecir  una  variable  categórica  del  negocio,  binaria  o  multiclase  (p.  ej.,
abandono/churn, fraude, segmento, categoría de producto o aprobación de crédito).

•  Preprocesamiento: codificación de variables categóricas, balanceo de clases (si

aplica) y escalamiento.

•  Modelos: Regresión Logística y al menos tres de KNN, SVC, Naive Bayes, Árbol
de Decisión o Random Forest; e incluir al menos un modelo de ensamble (Voting,
Stacking o Bagging) o de boosting (XGBoost, AdaBoost o Gradient Boosting).
•  Ajuste  de  hiperparámetros  (GridSearchCV  o  RandomizedSearchCV)  con

validación cruzada.

•  Evaluación: Accuracy, Precisión, Recall, F1-Score y matriz de confusión (curva
ROC/AUC  opcional);  comparación  en  tabla  e  interpretación  (importancia  de
variables o visualización del árbol).

4.2.4. Informe 5 — Modelo de Agrupamiento
Objetivo:  segmentar  (agrupar)  registros  de
segmentación de clientes.

forma  no  supervisada,  p.  ej.,

•  Selección y justificación de variables para el agrupamiento; escalamiento.
•  Algoritmos: K-means y DBSCAN.
•  Número óptimo de clústeres: método del codo y coeficiente de silueta para K-

means; ajuste de eps y min_samples para DBSCAN.

•  Evaluación: coeficiente de silueta, índice Davies-Bouldin e inercia; visualización
de los clústeres (apoyándose, si es necesario, en la reducción del Informe 6) e
interpretación de negocio de cada segmento.

4.2.5. Informe 6 — Reducción de la Dimensionalidad
Objetivo:  reducir  la  dimensionalidad  del  conjunto  de  datos  para  visualización  y/o
preprocesamiento.

•  Aplicar  PCA:  varianza  explicada  por  componente,  varianza  acumulada  y

selección del número de componentes.
•  Aplicar t-SNE para visualización en 2D.
•  Comparar el espacio original con el reducido y relacionar los componentes con

las variables originales (cargas/loadings).

•  Mostrar utilidad: visualizar los grupos del Informe 5 en el espacio reducido y/o
comparar el desempeño de un modelo con y sin PCA (opcional); interpretar los
componentes principales.

5. Generación de datos sintéticos

•  Datos propios: cada grupo genera sus propios datos sintéticos. No se permite
usar  datasets  públicos  reales  como  fuente  principal  (pueden  servir  solo  de
inspiración).

•  Reproducibilidad:  el  proceso  generador  debe  estar  documentado  y  ser

reproducible (fijar una semilla / seed).

Prof. Juan Gamarra Moreno

Pág 4 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

•  Volumen  sugerido:  al  menos  1,000  registros  (filas)  y  al  menos  8  variables

(columnas), con mezcla de variables numéricas y categóricas.

•  Debe contener al menos una variable continua (para regresión) y al menos una

variable categórica objetivo (para clasificación).

•  Debe presentar estructura realista: correlaciones, ruido y patrones que permitan

el agrupamiento.

Herramientas  recomendadas:  numpy,  pandas,  Faker  y  funciones  de  scikit-learn
(make_regression,  make_classification,  make_blobs)  combinadas  para  construir  un
dataset coherente.

•  Número de registros y de variables.
•  Tipo, rango y/o distribución de cada variable.
•  Reglas  y  correlaciones  introducidas,  y  la  forma  de  generación  de  la  variable

objetivo.

•  Semilla utilizada para garantizar la reproducibilidad.

Entrega: guardar el dataset como archivo .csv e incluirlo en la entrega final.

6. Documentación de prompts (uso responsable de IA)

Es obligatorio incluir, en los resultados de cada informe y en un anexo consolidado,
los prompts que ayudaron a estructurar y dar respuesta al Proyecto Grupal. De forma
obligatoria  deben  incluirse  los  prompts  utilizados  para  generar  los  datos
sintéticos,  con  el  detalle  de  cómo  se  generaron  (estructura,  distribuciones,
correlaciones, semilla y volumen).

Para cada prompt, registrar:

N°
Objetivo del prompt

Numeración correlativa del prompt.
Qué se buscaba lograr (estructurar el modelado, generar datos,
depurar código, interpretar métricas, etc.).
Asistente de IA utilizado (indicar cuál).
Transcripción literal del prompt enviado.
Síntesis del resultado obtenido y cómo se incorporó al proyecto.
Verificación realizada y cambios introducidos por el equipo.

Herramienta / modelo de IA
Texto del prompt
Resultado / cómo se usó
Validación / ajuste del
equipo
Ejemplo de registro (prompt para la generación de datos sintéticos):

N°
Objetivo

Herramienta
Texto del prompt

Resultado / cómo se usó

Validación / ajuste

P-04
Generar el dataset sintético de la empresa ficticia para los
Informes 3 a 6.
Asistente de IA generativa.
“Genera código Python (numpy, pandas, Faker) para crear un
dataset sintético de 1500 clientes de la empresa ficticia
‘AndesMarket’ (e-commerce). Variables: edad (18–70, normal
μ=35), ingreso_mensual (correlacionado positivamente con
edad), categoria_preferida (5 categorías), frecuencia_compra
(Poisson), monto_promedio (continua, dependiente del ingreso),
antiguedad_meses, canal (web/app) y una variable objetivo
‘abandono’ (0/1) dependiente de la frecuencia y la antigüedad.
Introduce correlaciones realistas y 5% de ruido. Fija semilla=42.
Exporta a CSV.”
Se obtuvo el script generador; el equipo lo ejecutó, revisó las
distribuciones y ajustó los rangos.
Se verificó la coherencia de las correlaciones con un análisis
exploratorio y se corrigió el desbalance de la variable objetivo.

Prof. Juan Gamarra Moreno

Pág 5 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

Principios: el uso de IA debe ser transparente y crítico. La IA es un apoyo; el equipo
es  responsable  de  verificar,  corregir  y  comprender  todo  el  código  y  los  resultados.
Copiar sin comprender penaliza la nota.

7. Instrucciones paso a paso

1.  Conformar  el  grupo  (5  a  7  integrantes),  registrarlo  en  el aula  virtual  y  asignar

roles.

2.  Bloque  A:  elegir  el  problema  de  búsqueda  (Informe  1)  y  el  juego  (Informe  2),

distintos entre sí, y validarlos con el docente.

3.  Bloque  B:  definir  la  empresa  ficticia,  su  problema  de  negocio  y  las  preguntas

analíticas de los Informes 3 a 6.

4.  Diseñar y  generar  el  dataset  sintético  reproducible  (con  semilla);  guardarlo  en

CSV y documentar el proceso y los prompts de generación.

5.  Desarrollar  el  Informe  1  (Java):  modelar  el  problema,  implementar  BFS,  DFS,

Greedy y A*, comparar resultados y redactar.

6.  Desarrollar el Informe 2 (Java): modelar el juego, implementar Minimax con poda

Alfa-Beta, medir nodos con y sin poda y redactar.

7.  Desarrollar el Informe 3 (Regresión) sobre el dataset de la empresa ficticia.
8.  Desarrollar el Informe 4 (Clasificación) sobre el mismo dataset.
9.  Desarrollar el Informe 5 (Agrupamiento) sobre el mismo dataset.
10. Desarrollar el Informe 6 (Reducción de dimensionalidad) sobre el mismo dataset.
11. Consolidar  el  anexo  de  prompts  (estructuración  del  proyecto  y  generación  de

datos sintéticos).

12. Aplicar el cuadro de autoevaluación (sección 11) y ajustar lo que sea necesario.
13. Empaquetar la entrega final: informes en PDF, archivos .java, notebooks .ipynb,

dataset .csv, anexo de prompts y enlace al repositorio.

14. Preparar y realizar la sustentación oral del proyecto.

8. Formato y estructura de los entregables

8.1. Estructura de cada informe del Bloque A (Java)

•  Carátula (curso, integrantes y problema abordado).
•  Introducción y descripción/modelado del problema (estados, operadores, meta,

costos).

•  Diseño de la solución y algoritmos empleados.
•  Implementación (fragmentos de código comentados).
•  Resultados y análisis comparativo.
•  Conclusiones, anexo de prompts y referencias.

8.2. Estructura de cada notebook del Bloque B (Python)

•  Encabezado: empresa ficticia, integrantes y objetivo del informe.
•  Descripción  del  caso  y  del  dataset;  generación/carga  de  datos  sintéticos

documentada.

•  Análisis exploratorio (EDA) y preprocesamiento.
•  Modelado, evaluación de resultados y visualizaciones.

Prof. Juan Gamarra Moreno

Pág 6 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

•  Conclusiones e interpretación de negocio; celda o anexo de prompts.
•  Cada notebook debe ejecutarse sin errores de principio a fin (Restart & Run

All).

8.3. Convenciones de entrega

•  Nombres de archivo: PG_IA_GrupoXX_InformeN_descripción.
•  Formatos:  informes  en  PDF;  código  en  .java;  notebooks  en  .ipynb  (exportar

también a PDF/HTML); datos en .csv.

•  Repositorio: GitHub o Drive con todo el material y un archivo README; citar

fuentes y declarar el uso de IA.

9. Cronograma referencial de entregas

Entregable

Informe 1

Tema

Semana referencial
Semana 4

Búsqueda informada y no informada
(Java)
Juegos con poda Alfa-Beta (Java)
Modelos de regresión
Modelo de clasificación
Modelo de agrupamiento
Reducción de dimensionalidad
Consolidación de todo el PG

Informe 2
Informe 3
Informe 4
Informe 5
Informe 6
Entrega final +
Sustentación
Nota: las fechas exactas se confirman en el aula virtual. El cronograma se alinea con
el avance temático del sílabo.

Semana 5
Semana 7
Semana 12
Semana 14
Semana 15
Semanas 15–16

10. Rúbricas de evaluación

Escala de valoración por criterio: 4 = Logrado · 3 = Satisfactorio · 2 = En proceso ·
1 = En inicio · 0 = No presenta.

Cada rúbrica tiene cinco criterios; el puntaje máximo por componente es 20 (suma
directa  de  los  criterios).  La  nota  del  PG  (0–20)  se  obtiene  como  el  promedio
ponderado de las notas de cada componente, según los pesos de la tabla 10.1.

10.1. Distribución de pesos

Componente

Informe 1 — Búsqueda (Java)
Informe 2 — Juegos con poda Alfa-Beta (Java)
Informe 3 — Regresión
Informe 4 — Clasificación
Informe 5 — Agrupamiento
Informe 6 — Reducción de dimensionalidad
Aspectos transversales
Total

Peso (%)
15
15
15
15
10
10
20
100

Prof. Juan Gamarra Moreno

Pág 7 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

10.2. Rúbrica — Informe 1 (Búsqueda en Java)

Criterio
Modelado del
problema en el
espacio de
estados

Búsqueda no
informada (BFS y
DFS)

Logrado (4)

Define con
precisión estados,
estado inicial,
meta, operadores
y costos.
Implementa BFS y
DFS
correctamente y
sin errores.

Búsqueda
informada
(Greedy, A*) y
heurística

Análisis
comparativo de
algoritmos

Calidad del
código,
ejecución y
prompts

Implementa
Greedy y A* con
heurística
admisible y
consistente
justificada.
Compara nodos,
costo, tiempo,
completitud y
optimalidad con
conclusiones
sólidas.
Código limpio y
comentado,
ejecuta sin
errores; prompts
documentados y
críticos.

Satisfactorio (3)
Define los
elementos del
problema con
omisiones
menores.
Implementa
ambos con errores
menores que no
afectan el
resultado.
Implementa los
algoritmos;
heurística
adecuada con
justificación
parcial.
Compara la
mayoría de los
criterios con
análisis adecuado.

Código funcional
con
documentación o
prompts
incompletos.

En proceso (2)
Modelado incompleto o
con imprecisiones
relevantes.

En inicio (1)
Modelado ausente
o incorrecto.

Implementa solo uno o
con errores que afectan
los resultados.

No funcional o no
implementado.

Implementación parcial
o heurística no
admisible.

No implementa
búsqueda
informada.

Comparación
superficial o
incompleta.

Sin análisis
comparativo.

Código con fallas o
documentación/prompts
deficientes.

No ejecuta o sin
documentación ni
prompts.

10.3. Rúbrica — Informe 2 (Juegos con poda Alfa-Beta)
En proceso (2)
Modelado incompleto o
impreciso.

Criterio
Modelado del
juego adversarial

En inicio (1)
Modelado ausente
o incorrecto.

Satisfactorio (3)
Define los
elementos con
omisiones
menores.

Logrado (4)
Define estados,
jugadas legales,
función
terminal/utilidad y
evaluación con
claridad.
Minimax correcto
que selecciona
jugadas óptimas.
Poda
correctamente
implementada;
reduce el árbol sin
alterar la decisión.
Reporta nodos
con y sin poda y
ofrece modo
humano-máquina
funcional.
Código limpio y
comentado,
ejecuta sin
errores; prompts
documentados y
críticos.

Implementación
de Minimax

Poda Alfa-Beta

Eficiencia y
modalidad de
juego

Calidad del
código, ejecución
y prompts

Minimax con
errores menores.

Poda funcional
con detalles
menores por
mejorar.

Evidencia parcial
de eficiencia o
modo de juego
funcional.

Código funcional
con
documentación o
prompts
incompletos.

Implementación parcial
o con errores
relevantes.
Poda parcial o que
altera los resultados.

No funcional o
ausente.

No implementa la
poda.

Evidencia débil o juego
con fallas.

Sin evidencia de
eficiencia ni juego
funcional.

Código con fallas o
documentación/prompts
deficientes.

No ejecuta o sin
documentación ni
prompts.

Prof. Juan Gamarra Moreno

Pág 8 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

10.4. Rúbrica — Informe 3 (Regresión)

Criterio
Caso, dataset
sintético y EDA

Modelos de
regresión
implementados

Evaluación y
validación

Logrado (4)
Caso claro; dataset
sintético coherente y
EDA completo
(correlaciones, atípicos).
Implementa lineal,
polinómica, Ridge/Lasso
y un modelo avanzado
correctamente.
Usa MSE, RMSE y R²
con validación train/test o
cruzada bien aplicada.

Selección e
interpretación del
mejor modelo

Selecciona y justifica el
mejor modelo e interpreta
coeficientes/importancias.

Visualizaciones,
conclusiones y
prompts

Visualizaciones claras,
conclusiones de negocio
y prompts
documentados.

Satisfactorio (3)
Caso y EDA
adecuados con
omisiones
menores.
Implementa la
mayoría de los
modelos
requeridos.
Evalúa con la
mayoría de
métricas y
validación básica.
Selecciona el
modelo con
justificación
parcial.
Visualizaciones o
conclusiones
adecuadas;
prompts
incompletos.

10.5. Rúbrica — Informe 4 (Clasificación)

En proceso (2)
EDA superficial o
dataset poco
coherente.

En inicio (1)

Sin EDA o sin
dataset propio.

Implementa
pocos modelos o
con errores.

Modelos
ausentes o no
funcionales.

Evaluación
incompleta o mal
aplicada.

Sin evaluación
válida.

Selección sin
justificación clara.

No selecciona ni
interpreta.

Visualizaciones
pobres o
conclusiones
débiles.

Sin
visualizaciones ni
prompts.

Criterio
Variable objetivo
y
preprocesamiento

Modelos de
clasificación (incl.
ensamble)

Ajuste de
hiperparámetros
y validación

Evaluación de
clasificación

Logrado (4)

Define la variable objetivo y
aplica codificación, balanceo y
escalamiento pertinentes.

Implementa Regresión
Logística, varios clasificadores
y al menos un
ensamble/boosting.
Aplica
GridSearch/RandomizedSearch
con validación cruzada
correctamente.
Reporta Accuracy, Precisión,
Recall, F1 y matriz de
confusión con análisis.

Comparación,
interpretación y
prompts

Compara modelos, interpreta
resultados y documenta
prompts críticamente.

Satisfactorio (3)
Preprocesamiento
adecuado con
omisiones
menores.
Implementa la
mayoría de los
modelos
requeridos.
Aplica ajuste con
validación básica.

En proceso (2)
Preprocesamiento
incompleto.

Implementa
pocos modelos o
sin ensamble.

En inicio (1)

Sin
preprocesamiento
o variable mal
definida.
Modelos
ausentes o no
funcionales.

Ajuste limitado o
mal aplicado.

Sin ajuste ni
validación.

Métricas
incompletas o sin
análisis.

Sin evaluación
válida.

Comparación
superficial.

Sin comparación
ni prompts.

Reporta la
mayoría de
métricas con
análisis básico.
Comparación o
interpretación
adecuada;
prompts
incompletos.

Prof. Juan Gamarra Moreno

Pág 9 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

Criterio
Selección de
variables y
escalamiento

10.6. Rúbrica — Informe 5 (Agrupamiento)
Satisfactorio (3)
Logrado (4)
Selección y
escalamiento
adecuados con
omisiones
menores.
Implementa ambos
con detalles
menores.
Determina k o
ajusta DBSCAN
con justificación
parcial.

Implementación
de K-means y
DBSCAN
Número óptimo de
clústeres y ajuste

Selecciona y
justifica las
variables y escala
adecuadamente
para el clustering.
Implementa
correctamente
ambos algoritmos.
Determina k con
codo y silueta;
ajusta eps y
min_samples
justificadamente.
Usa silueta,
Davies-Bouldin e
inercia e interpreta
los resultados.
Interpreta y perfila
los segmentos para
el negocio; prompts
documentados.

Evaluación del
agrupamiento

Perfilamiento de
segmentos y
prompts

En proceso (2)

Selección o
escalamiento
deficiente.

En inicio (1)
Sin selección ni
escalamiento.

Implementa solo
uno o con errores.

Determinación
débil o sin ajuste.

Algoritmos
ausentes o no
funcionales.
No determina k ni
ajusta DBSCAN.

Usa la mayoría de
las métricas con
análisis básico.

Perfilamiento o
prompts adecuados
pero incompletos.

Evaluación
incompleta.

Sin evaluación
válida.

Interpretación
superficial.

Sin interpretación
ni prompts.

10.7. Rúbrica — Informe 6 (Reducción de dimensionalidad)

Criterio

Logrado (4)

Componentes-
variables e
interpretación

Aplicación de t-
SNE y
visualización

Aplicación de PCA  Aplica PCA, reporta
varianza explicada
y acumulada y
justifica el n° de
componentes.
Aplica t-SNE y
produce una
visualización 2D
clara e informativa.
Relaciona los
componentes con
las variables
(cargas) e
interpreta su
significado.
Demuestra utilidad
(visualiza grupos
del Informe 5 y/o
impacto en un
modelo).
Conclusiones
sólidas y prompts
documentados
críticamente.

Utilidad
demostrada de la
reducción

Conclusiones y
prompts

Satisfactorio (3)
Aplica PCA con
análisis de varianza
parcial.

En proceso (2)

En inicio (1)

Aplica PCA sin
análisis adecuado.

No aplica PCA
correctamente.

Aplica t-SNE con
visualización
adecuada.

Visualización pobre
o t-SNE mal
aplicado.

No aplica t-SNE.

Interpretación
parcial de los
componentes.

Interpretación débil.  Sin interpretación.

Demuestra utilidad
de forma parcial.

Utilidad poco
evidente.

No demuestra
utilidad.

Conclusiones o
prompts adecuados
pero incompletos.

Conclusiones
débiles.

Sin conclusiones ni
prompts.

Prof. Juan Gamarra Moreno

Pág 10 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

10.8. Rúbrica — Aspectos transversales

Criterio

Documentación
de prompts y
datos sintéticos

Integración del
caso de la
empresa ficticia

Redacción,
formato y
presentación

Logrado (4)
Documenta todos
los prompts
(estructuración y
generación de
datos) con detalle y
mirada crítica.
Los informes 3–6
comparten un caso
y un dataset
coherentes y bien
articulados.
Entregables claros,
ordenados y con el
formato solicitado.

Sustentación oral  Domina el tema,

Trabajo en equipo
y coevaluación

explica con claridad
y responde
correctamente a las
preguntas.
Distribución
equitativa,
evidencia de
colaboración y
coevaluación
coherente.

Satisfactorio (3)
Documenta la
mayoría de
prompts; detalle de
datos parcial.

En proceso (2)

En inicio (1)

Documentación
incompleta o sin
detalle de la
generación de
datos.

No documenta
prompts ni la
generación de
datos.

Integración
adecuada con
inconsistencias
menores.

Presentación
adecuada con
detalles por
mejorar.
Buena sustentación
con dudas
menores.

Colaboración
adecuada con
aportes desiguales
menores.

Integración débil
entre informes.

Informes inconexos
o sin caso común.

Presentación
descuidada o
formato incompleto.

Presentación
deficiente.

Sustentación
limitada o con
vacíos.

Colaboración
desigual.

No sustenta o no
domina el trabajo.

Trabajo
individualizado o
sin colaboración.

Prof. Juan Gamarra Moreno

Pág 11 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

11. Cuadro de autoevaluación

El  grupo  debe  completar  este  cuadro  antes  de  la  entrega  final,  valorando  con
honestidad cada criterio con la misma escala (0 a 4). Permite estimar la nota y detectar
brechas. Debe adjuntarse al final de la entrega.

Autoval. (0–
4)

Evidencia /
Comentario

Componente

Informe 1

Informe 2

Informe 3

Informe 4

Informe 5

Informe 6

Transversal

Criterio

Modelado del espacio de estados
Búsqueda no informada (BFS, DFS)
Búsqueda informada (Greedy, A*) y heurística
Análisis comparativo
Código, ejecución y prompts
Modelado del juego
Minimax
Poda Alfa-Beta
Eficiencia y modo de juego
Código, ejecución y prompts
Caso, dataset sintético y EDA
Modelos de regresión
Evaluación y validación
Selección e interpretación
Visualizaciones, conclusiones y prompts
Variable objetivo y preprocesamiento
Modelos (incl. ensamble)
Hiperparámetros y validación
Métricas de clasificación
Comparación, interpretación y prompts
Variables y escalamiento
K-means y DBSCAN
N° óptimo de clústeres y ajuste
Evaluación del agrupamiento
Perfilamiento y prompts
PCA y varianza
t-SNE y visualización
Componentes-variables e interpretación
Utilidad demostrada
Conclusiones y prompts
Prompts y datos sintéticos
Integración del caso ficticio
Redacción y presentación
Sustentación oral
Trabajo en equipo y coevaluación

Prof. Juan Gamarra Moreno

Pág 12 de 13

UNMSM - FISI - E.P. Ingeniería de Software
Asignatura: Inteligencia Artificial
Instrucciones del Proyecto Grupal

11.1. Resumen y estimación de la nota del PG

Sume la autovaloración de los 5 criterios de cada componente para obtener su nota
(0–20) y calcule el aporte ponderado. La suma de los aportes es la  nota estimada
del PG.

Componente

Informe 1
Informe 2
Informe 3
Informe 4
Informe 5
Informe 6
Aspectos transversales
Nota estimada del PG

Nota (0–20)

Peso
15%
15%
15%
15%
10%
10%
20%
100%

Aporte ponderado

12. Integridad académica y consideraciones finales

•  Los  datos  sintéticos  y  el  código  deben  ser  originales  del  grupo;  no  se  admite

plagio de otros grupos ni de fuentes externas sin cita.

•  El uso de IA debe ser transparente y crítico; entregar código o resultados que el

grupo no comprende será penalizado en la sustentación.

•  Todos los integrantes deben poder sustentar cualquier parte del proyecto.
•  Las  entregas  tardías  o  incompletas  se  evalúan  según  el  reglamento  de

evaluación de la UNMSM y las indicaciones del docente.

13. Referencias

•  Russell, S., & Norvig, P. (2022). Artificial Intelligence: A Modern Approach (4.ª

ed.). Pearson.

•  VanderPlas, J. (2016). Python Data Science Handbook. O’Reilly Media.
•  Raschka, S., Liu, Y., & Mirjalili, V. (2022). Machine Learning with PyTorch and

Scikit-Learn. Packt Publishing.

•  Documentación de scikit-learn: https://scikit-learn.org/stable/
•  Google Colab: https://colab.research.google.com/

Prof. Juan Gamarra Moreno

Pág 13 de 13

