# Arquitectura y Decisiones Técnicas

## Patrón general
API REST stateless. El cliente (Flutter) envía JSON, el backend procesa y responde.
No hay sesiones ni estado en servidor.

## Decisión clave: polimorfismo de bloques
Cada entrenamiento tiene un array de `bloques`. Cada bloque tiene un `block_type` que
determina su estructura. En Java esto se modela con herencia:

```
Bloque (abstracta)
├── BloqueWarmup
├── BloqueStrength
├── BloqueWodForTime
├── BloqueWodAmrap
├── BloqueWodEmom
├── BloqueWodIntervalos
├── BloqueWodCompeticion
├── BloqueWodTeam
├── BloqueAccessory
├── BloqueRecovery
├── BloqueCooldown
└── BloqueRest
```

La deserialización usa `@JsonTypeInfo` + `@JsonSubTypes` sobre el campo `block_type`.
MongoDB almacena el tipo concreto gracias a `@Document` en la clase base.

## Flujo de carga del JSON (POST /api/v1/planes)
Cuando llega el JSON de Gemini, el backend ejecuta estos pasos en orden:

1. Deserializar y validar el DTO de entrada (@Valid)
2. Calcular fecha_inicio y fecha_fin a partir del número de semana
3. Insertar documento en colección `plan_semana`
4. Para cada día: crear documento en `sesion_dia` con estado="pendiente" y resultado=null
5. Enlazar referencias: plan_semana.entrenamientos[].sesion_dia_id y sesion_dia.plan_semana_id
6. Responder 201 con los IDs generados

Si cualquier paso falla → rollback completo (sesión MongoDB).

## Cálculo de 1RM
Fórmula de Epley: `1RM = peso_kg * (1 + reps / 30)`
Se calcula en `ProgresoService` cada vez que se registra un resultado de tipo strength.
Se marca `es_pr = true` si el 1RM calculado supera el máximo histórico del atleta
para ese ejercicio.

## Manejo de errores
Clase global `GlobalExceptionHandler` con `@RestControllerAdvice`.
Todos los errores devuelven:
```json
{
  "error": "Descripción corta",
  "detalle": "Mensaje técnico o de validación",
  "timestamp": "2026-03-09T07:00:00Z"
}
```
Códigos HTTP usados:
- 200 OK — Lectura correcta
- 201 Created — Recurso creado
- 400 Bad Request — JSON inválido o validación fallida
- 404 Not Found — Recurso no encontrado
- 500 Internal Server Error — Error no controlado

## Configuración MongoDB (application.properties)
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/rxtracker
spring.data.mongodb.database=rxtracker
```

## Variables de entorno esperadas
```
MONGO_URI        # URI de conexión a MongoDB (sobreescribe application.properties en producción)
SERVER_PORT      # Puerto del servidor (default: 8080)
```
