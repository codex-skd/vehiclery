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

El script `codex-docs/scripts/curseforge-upload.ps1` no depende de archivos previos existentes en el proyecto — la primera subida (v0.0.0-beta.1) se hizo directamente con el script, sin paso manual.

## Rama

```
minecraft/26.2/neoforge-26.2.0.37-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`

## Nota post-subida

`game_versions` incluye tanto el ID Client (`9638`) como Server (`9639`) junto a MC (`16498`) y NeoForge (`10150`), por lo que la API infiere el entorno **Client & Server** automáticamente al subir — no hace falta editar el archivo en la web tras la subida (confirmado en la subida de v0.0.0-beta.1: `Environment 'Client & Server': NO manual step needed.`).

**Historial:**
- Proyecto creado en CurseForge (ID `1646967`).
- v0.0.0-beta.1 subida vía `curseforge-upload.ps1` — File ID `8620309`, HTTP 200, entorno Client & Server inferido automáticamente.
- v0.0.0-beta.6 subida vía `curseforge-upload.ps1` — File ID `8649386`, HTTP 200. Primera actualización real desde beta.1 (betas 2-5 fueron builds de desarrollo internas, nunca publicadas).

## Nota: nombre de jar no coincide con el script de subida

`build.gradle` define `archivesName = "${mod_id}-${minecraft_version}-neoforge"` (sin `neo_version`), así que el jar real es `vehiclery-26.2-neoforge-<mod_version>.jar`. El script genérico `codex-docs/scripts/curseforge-upload.ps1` construye el nombre esperado como `${modId}-${mcVersion}-${modFramework}-${loaderVersion}-${modVersion}.jar` (incluye `neo_version` como segmento), por lo que falla con "JAR not found" salvo que se copie/renombre el jar real a ese nombre antes de ejecutar el script. Workaround usado en la subida de beta.6: copiar `build/libs/vehiclery-26.2-neoforge-<version>.jar` a `build/libs/vehiclery-26.2-neoforge-26.2.0.37-beta-<version>.jar`, ejecutar el script, y borrar la copia después (no se versiona, `build/` está en `.gitignore`). No se ha tocado el script compartido para no afectar a otros mods que sí podrían depender de ese segmento.
