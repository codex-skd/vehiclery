# CurseForge — Variables del proyecto

## Proyecto

| Variable | Valor |
|----------|-------|
| `curseforge_project_id` | `1646967` |
| `mod_id` | `vehiclery` |
| `display_name` | `Vehiclery` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

> Token de cuenta (mismo para todos los mods, ver `equivalent_legacy/neoforge/26.2/docs/curseforge/project_vars.md` u otros).

## Variables para script (lectura automática)

project_id = 1646967
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
release_type = beta
game_versions = 9638,9639,16498,10150
relations = *(ninguna dependencia obligatoria conocida por ahora)*

## Nota

La **primera subida a CurseForge se hace manual** (proyecto recién creado, sin archivos previos que verificar por API). A partir de la segunda subida se puede usar el script `codex-docs/scripts/curseforge-upload.ps1`.

## Rama

```
minecraft/26.2/neoforge-26.2.0.37-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`

## Nota post-subida (manual, obligatorio)

El API de subida de CurseForge no expone el campo de lado (client/server). Tras **cada** subida de archivo hay que entrar en la web de CurseForge → pestaña "Files" → editar el archivo → marcar el entorno como **Client & Server** (vehículos, block entities y recetas se procesan en servidor). Sin este paso, el file queda etiquetado como solo cliente.

**Historial:**
- Proyecto creado en CurseForge (ID `1646967`). Primera subida de archivo aún pendiente (manual).
