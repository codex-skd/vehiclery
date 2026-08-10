# Ruta de desarrollo — port a NeoForge 26.2.0.37-beta

> **Estado: ✅ COMPLETO.** `./gradlew.bat build` compila limpio y genera `vehiclery-26.2-neoforge-0.0.0-beta.1.jar` (verificado). Las 6 fases del port están cerradas. Quedan regresiones funcionales puntuales documentadas al final (TODOs en el código), no errores de compilación.

## Resumen de las 6 fases

| Fase | Descripción | Estado |
|---|---|---|
| 1 | Platform / registro / coloreado | ✅ |
| 2 | Entidad del automóvil / datos / attachments | ✅ |
| 3 | Modelos de pendiente (slope) + geometry loader | ✅ |
| 4 | Pantallas GUI (`GuiGraphicsExtractor`) | ✅ |
| 5 | Renderers (`SubmitNodeCollector`, state-extraction) | ✅ |
| 6 | BEWLR (renderizado 3D de items) | ✅ (stub, ver regresiones) |

Progreso real de errores de compilación durante el port: **101 → 88 → 69 → 65 → 57 → 45 → 16 → 0**.

## Cambios de arquitectura más relevantes (para referencia futura)

- **Coloreado**: `BlockColor`/`ItemColor` → `BlockTintSource`/`ItemTintSource` (basado en codecs).
- **NBT**: `CompoundTag` con getters `Optional`-based (`getFloat` → `getFloatOr`); `Entity`/`BlockEntity` migraron de `CompoundTag` a `ValueInput`/`ValueOutput`.
- **Registries**: `RegistryAccess.registryOrThrow` → `lookupOrThrow`; `Registry.getHolder` → `get` (ahora `Optional`); `ResourceKey.location()` → `identifier()`.
- **Modelos de bloque**: `BakedModel`/`ItemOverrides`/`IGeometryLoader` desaparecieron. Nuevo sistema: `CustomUnbakedBlockStateModel` (codec-based, evento `RegisterBlockStateModels`) + `DynamicBlockStateModel#collectParts` + `QuadCollection`/`BakedQuad` + `QuadBakingVertexConsumer`.
- **Renderizado de modelos custom (`Model<S>`)**: `renderToBuffer` ahora `final` (solo root). Se creó la interfaz propia `RenderableModel` para el hook de transform+extras que antes vivía en el override.
- **Renderizado diferido**: `MultiBufferSource` (inmediato) → `SubmitNodeCollector` (comandos diferidos: `submitModelPart`, `submitCustomGeometry`, `submitText`...). Bridge usado: `submitCustomGeometry` capturando el `PoseStack` vivo (no el `Pose` congelado del callback).
- **Entity/BlockEntity renderers**: ahora `EntityRenderer<T, S extends EntityRenderState>` / `BlockEntityRenderer<T, S extends BlockEntityRenderState>`, con `createRenderState()`/`extractRenderState()`/`submit()` en vez de un único `render()`.
- **GUI**: `GuiGraphics` → `GuiGraphicsExtractor`; `render`→`extractRenderState`, `renderBg`→`extractBackground`, `renderLabels`→`extractLabels`, `renderTooltip`→`setTooltipForNextFrame`/`extractTooltip`, `drawString`→`text`, `drawCenteredString`→`centeredText`; `blit` necesita `RenderPipeline` + tamaño de atlas explícito; `imageWidth`/`imageHeight` ahora `final` (constructor).
- **BEWLR**: `BlockEntityWithoutLevelRenderer` eliminado del todo. Reemplazo: `SpecialModelRenderer<T>` (codec-based, evento `RegisterSpecialModelRendererEvent`, declarado en el JSON del item) — **no implementado**, ver regresiones.
- **Recipe**: `Recipe<T>` perdió `getResultItem(HolderLookup.Provider)`/`canCraftInDimensions`; ganó `showNotification()`/`group()`/`placementInfo()`/`recipeBookCategory()`. `Level#getRecipeManager()` desapareció (solo `MinecraftServer`).

## Regresiones funcionales conocidas (TODOs en el código, no bloquean compilación)

| Archivo | Qué falta |
|---|---|
| `automobile/render/attachment/rear/BannerPostRearAttachmentModel.java` | El patrón de color del banner en el mástil trasero no se dibuja (el mástil sí); `BannerRenderer.submitPatterns` necesita `SpriteGetter` + `Model<S>` propio, sin investigar aún |
| `mixin/EntityRenderDispatcherMixin.java` | Los pasajeros ya no se inclinan visualmente con el vehículo (el punto de inyección original ya no existe) |
| `neoforge/mixin/BlockEntityWithoutLevelRendererMixin.java` + `neoforge/client/BEWLRs.java` | Los items de automóvil/componentes no tienen renderizado 3D custom en mano/inventario (icono 2D plano); requiere implementar `SpecialModelRenderer<T>` |
| `screen/AutoMechanicTableScreenHandler.java` | La lista de recetas del Auto Mechanic Table solo se puebla en servidor; el cliente la ve vacía (`Level#getRecipeManager()` ya no sincroniza recetas completas al cliente, solo `RecipePropertySet`/`RecipeDisplay`) — necesita paquete de sync propio |
| `screen/AutomobileHud.java` | El HUD del velocímetro no respeta F1 (ocultar interfaz) — `Options#hideGui` ya no existe públicamente |

## Notas operativas para delegación (lecciones de la sesión de port)

- El sandbox de OpenCode bloquea lectura fuera del directorio del proyecto (`external_directory`, auto-rechazado). Cualquier referencia externa (jars de Gradle, mods hermanos) debe copiarse dentro de `temp/` antes de delegar.
- Sesiones muy largas de investigación (`-c` resumido muchas veces) acumulan contexto hasta que la compactación falla contra algunos proveedores (visto con Nvidia). Mejor: sesiones nuevas y cortas por fase en vez de una sesión gigante recorriendo varias fases.
- Catálogo de modelos de Nvidia vía OpenCode poco fiable (varios EOL, uno colgado, uno con error interno). Verificar disponibilidad real antes de asumir el listado de `opencode models`.
- Las fases más "arquitectónicas" (3, 5, 6) se resolvieron mejor investigando directamente en los jars de fuentes (`neoforge-*-sources.jar`, `mergeWithSources_*_output.jar` en la caché de Gradle) y en mods hermanos ya migrados a NeoForge 26.2 (`ascendant_equipment`, `armor_cosmetic`) que delegando ciegamente.
