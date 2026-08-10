# CurseForge — Variables del proyecto

## Proyecto

| Variable | Valor |
|----------|-------|
| `curseforge_project_id` | *(pendiente — se rellena tras crear el proyecto en CurseForge)* |
| `mod_id` | `vehiclery` |
| `display_name` | `Vehiclery` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | *(mismo token de cuenta que otros mods — ver `equivalent_legacy/neoforge/26.2/docs/curseforge/project_vars.md` u otros)* | Subir archivos JAR |
| Core (GET) | *(mismo token de cuenta, ver referencia anterior)* | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

## Variables para script (lectura automática)

project_id = *(pendiente)*
api_token = *(pendiente — copiar el token de cuenta)*
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
- *(sin subidas todavía — proyecto pendiente de dar de alta)*
