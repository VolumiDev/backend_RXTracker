# API REST — Endpoints

Base URL: `/api/v1`
Content-Type: `application/json`

---

## Planes

### POST /api/v1/planes
Carga el JSON semanal generado por Gemini. Crea un documento en `plan_semana`
y 7 documentos en `sesion_dia` (uno por día).

**Request body:** JSON completo de Gemini (ver `docs/04_json_schema.md`)

**Response 201:**
```json
{
  "plan_semana_id": "objectId",
  "semana": 10,
  "sesiones_creadas": [
    { "dia": "Lunes",    "sesion_id": "objectId", "fecha": "2026-03-09" },
    { "dia": "Martes",   "sesion_id": "objectId", "fecha": "2026-03-10" },
    { "dia": "Miercoles","sesion_id": "objectId", "fecha": "2026-03-11" },
    { "dia": "Jueves",   "sesion_id": "objectId", "fecha": "2026-03-12" },
    { "dia": "Viernes",  "sesion_id": "objectId", "fecha": "2026-03-13" },
    { "dia": "Sabado",   "sesion_id": "objectId", "fecha": "2026-03-14" },
    { "dia": "Domingo",  "sesion_id": "objectId", "fecha": "2026-03-15" }
  ]
}
```

**Response 400:** JSON inválido o faltan campos obligatorios.

---

### GET /api/v1/planes
Lista todos los planes cargados (resumen, sin bloques).

**Response 200:**
```json
[
  {
    "id"          : "objectId",
    "semana"      : 10,
    "mesociclo"   : 3,
    "fase"        : "Realizacion",
    "fecha_inicio": "2026-03-09",
    "fecha_fin"   : "2026-03-15"
  }
]
```

---

### GET /api/v1/planes/{id}
Devuelve el plan completo con todos los entrenamientos y bloques.

**Response 200:** Documento completo de `plan_semana`.
**Response 404:** Plan no encontrado.

---

### GET /api/v1/planes/semana/{num}
Devuelve el plan de una semana concreta por número de semana.

**Response 200:** Documento completo de `plan_semana`.
**Response 404:** No existe plan para esa semana.

---

### DELETE /api/v1/planes/{id}
Elimina el plan y sus 7 sesiones asociadas.

**Response 204:** Eliminado correctamente.
**Response 404:** Plan no encontrado.

---

## Sesiones

### GET /api/v1/sesiones/hoy
Devuelve la `sesion_dia` cuya fecha coincide con la fecha actual del servidor.

**Response 200:** Documento completo de `sesion_dia`.
**Response 404:** No hay sesión planificada para hoy.

---

### GET /api/v1/sesiones/{id}
Devuelve una sesión por su ID.

**Response 200:** Documento completo de `sesion_dia`.
**Response 404:** Sesión no encontrada.

---

### GET /api/v1/sesiones?semana={n}
Devuelve las 7 sesiones de una semana concreta.

**Response 200:**
```json
[
  { "dia": "Lunes", "fecha": "2026-03-09", "estado": "completada", "...": "..." },
  { "dia": "Martes", "..." : "..." }
]
```

---

### PATCH /api/v1/sesiones/{id}/resultado
Registra o actualiza el resultado real de una sesión completa.
Actualiza el campo `resultado` de cada bloque y calcula métricas (1RM, PRs).

**Request body:**
```json
{
  "rpe_real"   : 8,
  "notas_post" : "Buena sesión, el snatch va mejorando",
  "bloques": [
    {
      "orden"     : 2,
      "resultado" : {
        "completado": true,
        "series": [
          { "set_num": 1, "reps_real": 5, "peso_kg": 100 },
          { "set_num": 2, "reps_real": 5, "peso_kg": 102.5 }
        ]
      }
    },
    {
      "orden"     : 3,
      "resultado" : {
        "completado" : true,
        "tiempo_seg" : 245,
        "tiempo_cap" : false,
        "escalado"   : "Rx"
      }
    }
  ]
}
```

**Response 200:** Sesión actualizada con `es_pr` y `1rm_calc` calculados.
**Response 400:** Estructura de resultado incompatible con el block_type.
**Response 404:** Sesión no encontrada.

---

### PATCH /api/v1/sesiones/{id}/estado
Cambia el estado de una sesión.

**Request body:**
```json
{ "estado": "completada | saltada | pendiente" }
```

**Response 200:** `{ "id": "...", "estado": "completada" }`

---

## Progreso

### GET /api/v1/progreso/1rm/{ejercicio}
Historial de 1RM de un ejercicio ordenado por fecha ascendente.
El nombre del ejercicio va URL-encoded (ej: `Back%20Squat`).

**Response 200:**
```json
[
  { "fecha": "2026-01-06", "peso_kg": 95,  "1rm_calc": 110.6, "es_pr": false },
  { "fecha": "2026-01-20", "peso_kg": 100, "1rm_calc": 116.7, "es_pr": true  },
  { "fecha": "2026-02-03", "peso_kg": 102, "1rm_calc": 118.9, "es_pr": true  }
]
```

---

### GET /api/v1/progreso/wod/{nombre}
Historial de resultados de un WOD por nombre (ej: `Fran`, `Open%2025.1`).

**Response 200:**
```json
[
  { "fecha": "2025-10-01", "tiempo_seg": 310, "escalado": "Scaled", "es_pr": false },
  { "fecha": "2026-01-15", "tiempo_seg": 267, "escalado": "Rx",     "es_pr": true  },
  { "fecha": "2026-03-13", "tiempo_seg": 245, "escalado": "Rx",     "es_pr": true  }
]
```

---

### GET /api/v1/progreso/prs
Últimos PRs registrados, para el dashboard de Flutter.
Devuelve los N más recientes (default: 10).

**Query params:** `?limit=10`

**Response 200:**
```json
[
  {
    "ejercicio"  : "Back Squat",
    "tipo"       : "1rm",
    "valor"      : 118.9,
    "unidad"     : "kg",
    "fecha"      : "2026-02-03",
    "sesion_id"  : "objectId"
  },
  {
    "ejercicio"  : "Fran",
    "tipo"       : "tiempo",
    "valor"      : 245,
    "unidad"     : "seg",
    "fecha"      : "2026-03-13",
    "sesion_id"  : "objectId"
  }
]
```
