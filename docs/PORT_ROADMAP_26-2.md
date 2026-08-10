# Ruta de desarrollo — port a NeoForge 26.2.0.37-beta

> Estado: NeoForge/Minecraft 26.2.0.37-beta reescribió el pipeline de renderizado cliente (GUI, renderers de entidad/bloque, BEWLR, modelos custom). No es un fix mecánico de nombres — cada fase es un sub-sistema distinto. Este documento trackea el avance para decidir, fase a fase, si se hace aquí (Claude) o se delega (OpenCode).

## Cómo leer este documento

- **Estado**: ✅ hecho · 🔲 pendiente · 🔶 en curso
- **Errores**: recuento real de `./gradlew.bat compileJava --console=plain` en el momento de escribir esto (101 errores totales, 19 archivos)
- **Ejecutar**: decisión pendiente por fase — "aquí" (Claude directo) o "OpenCode" (delegado)

## Fase 1 — Platform / registro / coloreado ✅ HECHO

| Archivo | Errores antes |
|---|---|
| `platform/Platform.java` | — |
| `neoforge/NeoForgePlatform.java` | — |
| `VehicleryClient.java` | — |
| `neoforge/VehicleryClientNeoForge.java` | — |
| `neoforge/VehicleryNeoForge.java` | — |
| `neoforge/mixin/BlockColorsAccess.java` | — |

Cambios: `BlockColor`/`ItemColor` → `BlockTintSource`/`ItemTintSource` (basado en codecs), `RegisterClientReloadListenersEvent` → `AddClientReloadListenersEvent`, `ModelEvent.RegisterGeometryLoaders` → `RegisterLoaders`, ajustes de aridad genérica, `@EventBusSubscriber` sin bus explícito en `VehicleryNeoForge`.

Commit: `c02baa7`. Compila limpio (confirmado).

## Fase 2 — Entidad del automóvil (🔲 pendiente, 18 errores)

| Archivo | Errores |
|---|---|
| `entity/AutomobileEntity.java` | 17 |
| `neoforge/mixin/EntityRenderersMixin.java` | 1 |

Estos errores no existían en el diagnóstico original — aparecieron al arreglar la Fase 1 (javac ocultaba errores downstream mientras Platform.java no compilaba). Hay que investigar de cero qué símbolos rompió el cambio de entidad/render-state.

**Recomendación**: acotado (18 errores, 2 archivos) — buen candidato para hacer aquí directamente o una delegación corta y bien acotada.

## Fase 3 — Sistema de modelos de pendiente (slope) y geometry loader (🔲 pendiente, 40 errores — la más grande)

| Archivo | Errores |
|---|---|
| `neoforge/block/render/NeoForgeSlopeGeometryLoader.java` | 16 |
| `neoforge/block/render/NeoForgeSlopeBakedModel.java` | 13 |
| `block/model/SlopeBakedModel.java` | 4 |
| `block/model/SlopeUnbakedModel.java` | 2 |
| `neoforge/block/render/SlopeModelsProvider.java` | 4 |

El paquete `net.neoforged.neoforge.client.model.geometry` desapareció por completo; `BakedModel`/`ItemOverrides` también cambiaron. Es el sub-sistema más novedoso/desconocido — genera los modelos de bloque de pendiente (slopes) procedimentalmente. Requiere entender el nuevo mecanismo de geometry loaders + datagen de NeoForge 26.2 (referencia ya extraída en su momento: `UnbakedModelLoader`, `ExtendedModelTemplateBuilder`, `ConditionalModelLoader` — habría que re-extraer si se retoma, `temp/` es efímero).

**Recomendación**: la más compleja y con más riesgo de que se pierda comportamiento (visual) si se aproxima mal — candidata a delegar, pero con una sesión dedicada solo a esto, no mezclada con otras fases.

## Fase 4 — Pantallas GUI (🔲 pendiente, 17 errores)

| Archivo | Errores |
|---|---|
| `screen/AutoMechanicTableScreen.java` | 10 |
| `screen/AutomobileHud.java` | 4 |
| `screen/SingleSlotScreen.java` | 3 |

`GuiGraphics` → `GuiGraphicsExtractor`. Métodos renombrados: `render`→`extractRenderState`, `renderBg`→`extractBackground`, `renderLabels`→`extractLabels`, `renderTooltip`→`setTooltipForNextFrame`, `drawString`→`text`, `drawCenteredString`→`centeredText`, `blit` necesita `RenderPipeline`. Ya hay ejemplos reales funcionando en `ascendant_equipment` (mismo repo, mismo `neo_version`) — patrón bien establecido.

**Recomendación**: mecánico y con ejemplos reales disponibles — buen candidato para delegar en un lote corto, o hacerlo aquí con los ejemplos ya localizados.

## Fase 5 — Renderers (🔲 pendiente, 16 errores)

| Archivo | Errores |
|---|---|
| `automobile/render/attachment/front/AutopilotFrontAttachmentModel.java` | 3 |
| `automobile/render/BaseModel.java` | 3 |
| `block/entity/render/AutomobileAssemblerBlockEntityRenderer.java` | 3 |
| `entity/render/AutomobileEntityRenderer.java` | 3 |
| `mixin/EntityRenderDispatcherMixin.java` | 3 |
| `automobile/render/attachment/rear/BannerPostRearAttachmentModel.java` | 2 |
| `automobile/render/AutomobileRenderer.java` | (incluido en render pipeline general) |

`MultiBufferSource` → `SubmitNodeCollector`; `render(...)` → `submit(...)` + `createRenderState`/`extractRenderState`. Depende de que la Fase 2 (AutomobileEntity) esté resuelta primero, ya que estos renderers consumen esa entidad.

**Recomendación**: hacer después de Fase 2. Complejidad media.

## Fase 6 — BEWLR (renderizado 3D de items) (🔲 pendiente, 8 errores)

| Archivo | Errores |
|---|---|
| `neoforge/client/BEWLRs.java` | 4 |
| `neoforge/mixin/BlockEntityWithoutLevelRendererMixin.java` | 4 |

`BlockEntityWithoutLevelRenderer` (BEWLR) parece eliminado del todo, reemplazado por `SpecialModelRenderer`. Afecta al renderizado 3D de items de partes del vehículo en mano/inventario — no se debe perder ese comportamiento visual silenciosamente.

**Recomendación**: última fase, requiere entender `SpecialModelRenderer` desde cero — candidata a delegar con contexto dedicado.

## Orden recomendado

1. Fase 2 (Entidad) — desbloquea Fase 5
2. Fase 4 (GUI) — independiente, ejemplos ya disponibles
3. Fase 5 (Renderers) — depende de Fase 2
4. Fase 3 (Slope models) — la más grande, sesión dedicada
5. Fase 6 (BEWLR) — la más incierta

## Notas operativas para delegación (lecciones de hoy)

- El sandbox de OpenCode bloquea lectura fuera del directorio del proyecto (`external_directory`, auto-rechazado). Cualquier referencia externa (jars de Gradle, mods hermanos) debe copiarse dentro de `temp/` antes de delegar.
- Sesiones muy largas de investigación (`-c` resumido muchas veces) acumulan contexto hasta que la compactación falla contra algunos proveedores (visto con Nvidia). Mejor: sesiones nuevas y cortas por fase en vez de una sesión gigante recorriendo las 6 fases.
- Catálogo de modelos de Nvidia vía OpenCode poco fiable hoy (varios EOL, uno colgado, uno con error interno). Verificar disponibilidad real antes de asumir el listado de `opencode models`.
