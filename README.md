# 🏋️ RxTracker — Backend

> **⚠️ Proyecto en desarrollo activo — Work in Progress**

Backend de una aplicación de gestión de entrenamientos CrossFit con planificación asistida por IA (Gemini). El atleta diseña su semana con Gemini, el plan se persiste en MongoDB y Flutter lo consume para mostrar sesiones y registrar resultados.

---

## ✨ ¿Qué hace?

- Recibe el **JSON semanal generado por IA** y lo valida, transforma y persiste en MongoDB
- Genera automáticamente **7 sesiones diarias** por cada plan cargado
- Permite **registrar resultados reales** de cada sesión
- Calcula **1RM automáticamente** (fórmula de Epley) y detecta **PRs**
- Expone una **API REST** lista para ser consumida por cualquier frontal

---

## 🧱 Stack técnico

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.x |
| MongoDB | 7.x (Docker) |
| Maven | 3.x |

---

## 🗂️ Estructura del proyecto

```
src/main/java/com/diego/iatrainig/
├── controller/     # REST controllers
├── service/        # Lógica de negocio
├── repository/     # MongoRepository interfaces
├── domain/         # Documentos MongoDB + bloques polimórficos
├── dto/            # DTOs de entrada/salida (Records Java)
├── aspect/         # AOP — logging dinámico
├── config/         # Configuración MongoDB
└── exception/      # Manejo global de errores
```

---

## 🔗 Endpoints principales

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/v1/planes` | Carga el JSON semanal de Gemini |
| `GET` | `/api/v1/planes` | Lista todos los planes |
| `GET` | `/api/v1/planes/{id}` | Plan completo con bloques |
| `DELETE` | `/api/v1/planes/{id}` | Elimina plan y sus sesiones |
| `GET` | `/api/v1/sesiones/hoy` | Sesión del día actual |
| `GET` | `/api/v1/sesiones/{id}` | Sesión por ID |
| `GET` | `/api/v1/sesiones?semana={n}` | Sesiones de una semana |
| `PATCH` | `/api/v1/sesiones/{id}/resultado` | Registrar resultado real |
| `PATCH` | `/api/v1/sesiones/{id}/estado` | Cambiar estado de sesión |
| `GET` | `/api/v1/progreso/1rm/{ejercicio}` | Historial de 1RM |
| `GET` | `/api/v1/progreso/wod/{nombre}` | Historial de WODs |
| `GET` | `/api/v1/progreso/prs` | Últimos PRs registrados |

---

## 🧩 Bloques de entrenamiento soportados

El JSON de Gemini usa un campo `block_type` como discriminador para el polimorfismo:

| `block_type` | Descripción |
|---|---|
| `warmup` | Calentamiento |
| `strength` | Bloque de fuerza (sentadilla, press, etc.) |
| `wod_for_time` | WOD a tiempo |
| `wod_amrap` | AMRAP |
| `wod_emom` | EMOM |
| `wod_intervals` | Intervalos |
| `wod_competition` | WOD de competición |
| `wod_team` | WOD en equipo |
| `accessory` | Trabajo accesorio |
| `recovery` | Recuperación activa |
| `cooldown` | Vuelta a la calma |
| `rest` | Día de descanso |

---

## 🚀 Cómo levantar el proyecto

### Requisitos
- Java 21
- Docker + Docker Compose
- Maven

### 1. Configurar variables de entorno

Crea un archivo `.env` en la raíz del proyecto. Las variables necesarias son:

```env
# Spring Boot
MONGODB_URI=

# MongoDB
MONGO_INITDB_ROOT_USERNAME=
MONGO_INITDB_ROOT_PASSWORD=

# Mongo Express
ME_CONFIG_MONGODB_ADMINUSERNAME=
ME_CONFIG_MONGODB_ADMINPASSWORD=
ME_CONFIG_MONGODB_URL=
ME_CONFIG_BASICAUTH_USERNAME=
ME_CONFIG_BASICAUTH_PASSWORD=
```

> El `.env` es leído automáticamente por Spring Boot (via `spring-dotenv`) y por Docker Compose.

### 2. Levantar MongoDB con Docker Compose

```bash
docker compose -f docker/docker-compose.yml up -d
```

Esto levanta dos servicios:
- **MongoDB** en `localhost:27017`
- **Mongo Express** (UI web) en `http://localhost:8081`

Para detenerlos:
```bash
docker compose -f docker/docker-compose.yml down
```

### 3. Arrancar el servidor

```bash
./mvnw spring-boot:run
```

El servidor arranca en `http://localhost:8080`.

---

## 📐 Decisiones de arquitectura

- **Polimorfismo de bloques** — `@JsonTypeInfo` + `@JsonSubTypes` sobre `block_type`
- **DTOs inmutables** — Records de Java 21
- **Sin lógica en controllers** — toda la lógica va en services
- **Bean Validation** — `@Valid` en todos los DTOs de entrada
- **Errores consistentes** — siempre devuelve `{ "error": "...", "detalle": "..." }`
- **AOP Logging** — trazas automáticas por aspecto sin contaminar el código de negocio

---

## 📊 Colecciones MongoDB

| Colección | Descripción |
|---|---|
| `weekly_plan` | Plan semanal generado por Gemini (inmutable tras carga) |
| `day_session` | Un documento por día con plan + resultado real |

---

## 🛣️ Roadmap

- [x] Modelo de dominio completo con bloques polimórficos
- [x] Carga del plan semanal (POST /planes)
- [x] Gestión de sesiones diarias
- [ ] Registro de resultados y cálculo de 1RM
- [ ] Detección automática de PRs
- [ ] Endpoints de progreso histórico
- [ ] Autenticación JWT
- [ ] Tests de integración

---

## 📁 Documentación interna

| Fichero | Contenido |
|---|---|
| [`docs/01_arquitectura.md`](docs/01_arquitectura.md) | Decisiones de arquitectura y patrones |
| [`docs/02_mongodb.md`](docs/02_mongodb.md) | Modelo de datos con índices |
| [`docs/03_api.md`](docs/03_api.md) | Todos los endpoints con request/response |
| [`docs/04_json_schema.json`](docs/04_json_schema.json) | JSON de referencia completo de Gemini |
