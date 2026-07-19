"""
=======================================================================
 GENERADOR DE DATASET - PROYECTO INTELIGENCIA ARTIFICIAL
 Empresa Ficticia : NovaConecta
 Rubro            : Telecomunicaciones (Internet / Telefonía móvil)
 Responsable      : Leonardo Vera - Ingeniero de Datos y Líder de Integración
=======================================================================

Se sigue un enfoque de "generadores estadísticos
aislados" por un enfoque de "reglas de negocio con ruido controlado":
cada variable se deriva explícitamente de las anteriores, replicando
cómo funcionaría realmente una empresa de telecomunicaciones. Esto
garantiza que los seis informes (EDA, regresión, clasificación,
clustering, reducción de dimensionalidad) cuenten la misma historia
analítica sobre los mismos clientes.
"""

import numpy as np
import pandas as pd
from faker import Faker

# -----------------------------------------------------------------
# 1. CONFIGURACIÓN Y SEMILLA FIJA (reproducibilidad)
# -----------------------------------------------------------------
SEED = 42
np.random.seed(SEED)
fake = Faker('es_ES')
Faker.seed(SEED)

N_SAMPLES = 1200
FECHA_CORTE = pd.to_datetime('2026-07-04')  # fecha de referencia del análisis

# -----------------------------------------------------------------
# 2. VARIABLES DEMOGRÁFICAS Y DE TEXTO (Faker)
# -----------------------------------------------------------------
customer_id = [f"NC-{str(i).zfill(5)}" for i in range(1, N_SAMPLES + 1)]
nombre_cliente = [fake.name() for _ in range(N_SAMPLES)]
ciudad = [fake.city() for _ in range(N_SAMPLES)]

edad = np.random.randint(18, 75, N_SAMPLES)
plan_contratado = np.random.choice(
    ['Basico', 'Estandar', 'Premium'], size=N_SAMPLES, p=[0.45, 0.35, 0.20]
)

# -----------------------------------------------------------------
# 3. ANTIGÜEDAD COHERENTE CON LA FECHA DE REGISTRO
# -----------------------------------------------------------------
fecha_registro = pd.Series(pd.to_datetime(
    [fake.date_between(start_date='-5y', end_date='today') for _ in range(N_SAMPLES)]
))
antiguedad_meses = ((FECHA_CORTE - fecha_registro).dt.days / 30.44).round().astype(int)
antiguedad_meses = np.clip(antiguedad_meses, 1, 60)

# -----------------------------------------------------------------
# 4. USO DE DATOS DEPENDIENTE DEL PLAN CONTRATADO
# -----------------------------------------------------------------
media_datos = {'Basico': 15, 'Estandar': 40, 'Premium': 90}
sd_datos = {'Basico': 4, 'Estandar': 10, 'Premium': 20}

uso_datos_gb = np.array([
    np.random.normal(media_datos[plan], sd_datos[plan]) for plan in plan_contratado
])
uso_datos_gb = np.clip(np.round(uso_datos_gb, 1), 1.0, 250.0)

# -----------------------------------------------------------------
# 5. CARGO MENSUAL DEPENDIENTE DEL PLAN Y DEL CONSUMO DE DATOS
# -----------------------------------------------------------------
cargo_base = {'Basico': 30.0, 'Estandar': 55.0, 'Premium': 85.0}
ruido_precio = np.random.normal(0, 5, N_SAMPLES)

cargo_mensual = np.array([
    cargo_base[plan] for plan in plan_contratado
]) + (uso_datos_gb * 0.1) + ruido_precio
cargo_mensual = np.clip(np.round(cargo_mensual, 2), 15.0, 200.0)

# -----------------------------------------------------------------
# 6. LLAMADAS A SOPORTE Y SATISFACCIÓN CORRELACIONADAS
# -----------------------------------------------------------------
num_llamadas_soporte = np.random.poisson(lam=2.0, size=N_SAMPLES)
llamadas_extra = np.random.choice([0, 1, 2, 3], size=N_SAMPLES, p=[0.7, 0.2, 0.08, 0.02])
num_llamadas_soporte = np.clip(num_llamadas_soporte + llamadas_extra, 0, 12)

# La satisfacción baja con más llamadas a soporte y con cargos altos
satisfaccion_cliente = (
    9.0
    - (num_llamadas_soporte * 0.8)
    - (cargo_mensual * 0.01)
    + np.random.normal(0, 1, N_SAMPLES)
)
satisfaccion_cliente = np.clip(np.round(satisfaccion_cliente), 1, 10).astype(int)

# -----------------------------------------------------------------
# 7. ABANDONO (CHURN) COMO FUNCIÓN LOGÍSTICA DE LAS VARIABLES ANTERIORES
# -----------------------------------------------------------------
log_odds = (
    5.0
    - 0.8 * satisfaccion_cliente
    + 0.02 * cargo_mensual
    - 0.05 * antiguedad_meses
)
p_churn = 1 / (1 + np.exp(-log_odds))
p_churn = np.clip(p_churn * 0.95 + 0.025, 0.0, 1.0)  # ruido para evitar separación perfecta
abandono = np.where(np.random.rand(N_SAMPLES) < p_churn, 'Si', 'No')

# -----------------------------------------------------------------
# 8. VALOR DE VIDA DEL CLIENTE (CLV) LIGADO A ANTIGÜEDAD, CARGO Y CHURN
# -----------------------------------------------------------------
proyeccion_meses = np.where(abandono == 'Si', 0, 12)  # si abandonó, no hay proyección futura
valor_vida_cliente = cargo_mensual * (antiguedad_meses + proyeccion_meses)
valor_vida_cliente += np.random.normal(0, 30, N_SAMPLES)
valor_vida_cliente = np.clip(np.round(valor_vida_cliente, 2), 50.0, None)

# -----------------------------------------------------------------
# 9. ENSAMBLAJE DEL DATASET FINAL (13 variables, 1200 filas)
# -----------------------------------------------------------------
df = pd.DataFrame({
    'id_cliente': customer_id,
    'nombre_cliente': nombre_cliente,
    'ciudad': ciudad,
    'fecha_registro': fecha_registro.dt.date,
    'edad': edad,
    'plan_contratado': plan_contratado,
    'antiguedad_meses': antiguedad_meses,
    'uso_datos_gb': uso_datos_gb,
    'num_llamadas_soporte': num_llamadas_soporte,
    'cargo_mensual': cargo_mensual,
    'satisfaccion_cliente': satisfaccion_cliente,
    'valor_vida_cliente': valor_vida_cliente,
    'abandono': abandono,
})

# -----------------------------------------------------------------
# 10. RUIDO REALISTA PARA EL EDA (Informe 3): nulos y outliers
# -----------------------------------------------------------------
null_idx = np.random.choice(df.index, size=25, replace=False)
df.loc[null_idx, 'uso_datos_gb'] = np.nan

outlier_idx = np.random.choice(df.index, size=8, replace=False)
df.loc[outlier_idx, 'cargo_mensual'] = df.loc[outlier_idx, 'cargo_mensual'] * 4

# -----------------------------------------------------------------
# 11. EXPORTACIÓN
# -----------------------------------------------------------------
OUTPUT_PATH = 'dataset_novaconecta.csv'
df.to_csv(OUTPUT_PATH, index=False)

print(f"Dataset generado: {df.shape[0]} filas x {df.shape[1]} columnas")
print(f"Guardado en: {OUTPUT_PATH}\n")

print("Distribución de 'abandono':")
print(df['abandono'].value_counts(normalize=True), "\n")

print("Uso de datos (GB) promedio por plan:")
print(df.groupby('plan_contratado')['uso_datos_gb'].mean(), "\n")

print("Cargo mensual promedio por plan:")
print(df.groupby('plan_contratado')['cargo_mensual'].mean(), "\n")

print("Correlación uso_datos_gb vs cargo_mensual:",
      round(df['uso_datos_gb'].corr(df['cargo_mensual']), 3))
print("Correlación num_llamadas_soporte vs satisfaccion_cliente:",
      round(df['num_llamadas_soporte'].corr(df['satisfaccion_cliente']), 3))

df_check = df.copy()
df_check['antiguedad_calculada'] = (
    (FECHA_CORTE - pd.to_datetime(df_check['fecha_registro'])).dt.days / 30.44
).round().clip(1, 60).astype(int)
inconsistentes = (df_check['antiguedad_meses'] != df_check['antiguedad_calculada']).sum()
print(f"Filas con antigüedad incoherente respecto a fecha_registro: {inconsistentes}")

clv_churn = df.groupby('abandono')['valor_vida_cliente'].mean()
print("\nCLV promedio por estado de abandono:")
print(clv_churn)
