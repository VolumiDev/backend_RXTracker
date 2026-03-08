# RxTracker — Backend Context

## Qué es este proyecto
App de gestión de entrenamientos CrossFit. El flujo central es:
1. El atleta diseña su semana con Gemini → Gemini genera un JSON estructurado
2. Ese JSON se envía al backend via POST
3. El backend valida, transforma y persiste en MongoDB
4. Flutter consume la API REST para mostrar el plan y registrar resultados

## Stack
- **Java 21** + **Spring Boot 3.x**
- **MongoDB** (Docker) via Spring Data MongoDB
- **Maven** como gestor de dependencias

## Estructura del proyecto
```
src/main/java/com/rxtracker/
├── controller/     # REST controllers
├── service/        # Lógica de negocio
├── repository/     # MongoRepository interfaces
├── model/          # Documentos MongoDB (colecciones)
├── dto/            # DTOs de entrada/salida
├── validation/     # Validadores personalizados
└── exception/      # Manejo global de errores
```

## Reglas de desarrollo
- Usar **Records** de Java para DTOs inmutables
- Usar **@JsonSubTypes** para deserializar bloques polimórficos por `block_type`
- Todo endpoint devuelve **ResponseEntity<?>** con códigos HTTP explícitos
- Validación con **Bean Validation (@Valid)** en los DTOs de entrada
- Los errores devuelven siempre `{ "error": "...", "detalle": "..." }`
- Sin lógica de negocio en los controllers — todo va en services
- Nombres en **camelCase** en Java, **snake_case** en MongoDB y JSON
- Sigue principios SOLID
- Las mejores prácticas para desarrollo Java
*- RECUERDA QUE LOS NOMBRE DE LAS VARIABLES, CLASES, ETC TIENE QUE SE EN INGLES*

## Colecciones MongoDB
Hay dos colecciones principales. Ver detalle completo en `docs/02_mongodb.md`:
- `plan_semana` — El plan generado por Gemini (inmutable tras la carga)
- `sesion_dia` — Un documento por día, con plan + resultado real

## Endpoints principales
Ver lista completa en `docs/03_api.md`.
- `POST /api/v1/planes` — Carga el JSON de Gemini (endpoint más importante)
- `GET  /api/v1/sesiones/hoy` — Sesión del día actual
- `PATCH /api/v1/sesiones/{id}/resultado` — Registrar resultado real

## JSON de entrada
El JSON que genera Gemini tiene una estructura fija con `block_type` como discriminador.
Ver esquema completo y todos los valores posibles en `docs/04_json_schema.md`.

## Ficheros de contexto detallado
- `docs/01_arquitectura.md` — Decisiones de arquitectura y patrones usados
- `docs/02_mongodb.md` — Modelo de datos completo con índices
- `docs/03_api.md` — Todos los endpoints con request/response
- `docs/04_json_schema.md` — JSON de referencia completo con todos los block_type
