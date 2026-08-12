# Ruta de desarrollo — port a NeoForge 26.2.0.37-beta

> **Estado: ✅ COMPILA y ✅ CARGA en cliente real.** `./gradlew.bat build` compila limpio. Verificado además en runtime (instancia de pruebas CurseForge): el mod arranca, llega al menú principal, texturas/sonido OK. Las 6 fases del port están cerradas. Quedan regresiones funcionales puntuales documentadas al final, ya no solo TODOs de compilación sino bugs de runtime encontrados probando en cliente real.

## Sesión de pruebas en cliente real (2026-08-11/12)

Primera vez que se probó el mod en un cliente real (antes solo se había verificado con `./gradlew.bat build`, nunca ejecutado). Aparecieron **7 crashes/bugs de runtime en cadena** que el build no detectaba, todos corregidos salvo el último:

1. `VehicleryClientNeoForge#generateResources` suscrito a `GatherDataEvent` abstracto → debe ser `GatherDataEvent.Client`.
2. `VehicleryNeoForge#generateData` mismo problema → `GatherDataEvent.Server`.
3. Los 15 bloques de `VehicleryBlocks.java` construían `BlockBehaviour.Properties` sin `.setId(...)` → NPE "Block id not set" (`effectiveDrops()` lo requiere ahora). Igual para `Item.Properties` en `VehicleryItems.java` y los `BlockItem` de `VehicleryBlocks.java` → NPE "Item id not set".
4. `PlayerEnderChestContainerMixin`: `startOpen`/`stopOpen` de `PlayerEnderChestContainer` cambiaron su parámetro de `Player` a `ContainerUser`.
5. `SoundEngineMixin`: `tickNonPaused` renombrado a `tickInGameSound`; `play()` ahora devuelve `SoundEngine.PlayResult` → el `@Inject` necesita `CallbackInfoReturnable`, no `CallbackInfo`.
6. `VehicleryNeoForge#registerNetworking` registraba el mismo `CustomPacketPayload.Type` vía `playToClient`+`playToServer` por separado → ahora hace falta `playBidirectional(...)`.
7. Sistema `jsonem` (carga de los 32 modelos JSON de piezas del automóvil): `EntityModelSet` perdió su mecanismo de recarga (`ResourceManagerReloadListener`) — ahora `ModelManager.reload()` construye el `EntityModelSet` directamente vía `EntityModelSet.vanilla()` dentro de un `CompletableFuture`. Solución: nuevo mixin `ModelManagerMixin` con `@Redirect` sobre esa llamada (ordinal 0) + accessor `EntityModelSetAccess` para leer/fusionar el mapa `roots` privado. Además `CubeDefinitionAccess` apuntaba a campos `Vector3f` que ahora son `Vector3fc`.
8. Los blockstates de las pendientes (`slope.json`, `steep_slope.json`, `slope_with_dash_panel.json`, `steep_slope_with_dash_panel.json`) seguían con el esquema antiguo (`"model": "vehiclery:block/slope_bottom"`) en vez del nuevo (`"type": "vehiclery:slope", "represents": "..."`) — y había 9 archivos de modelo generados obsoletos (`src/generated/resources/.../slope*.json`) con `"loader": "vehiclery:slope"` (sistema eliminado) que rompían la carga de resource packs.

⚠️ **Lección importante**: en el paso 7, inicialmente se **eliminó por completo** el sistema `jsonem` asumiendo que no lo usaba nada (grep solo buscó referencias en código Java). Error: es un sistema *data-driven* — 32 archivos JSON bajo `models/entity/` que definen las piezas del automóvil (motor, chasis, ruedas, efecto de derrape) se cargan a través de él. Sin `jsonem`, ningún vehículo se puede renderizar. Se restauró y arregló correctamente. **Para verificar si un sistema data-driven está en uso, no basta con grep sobre el código Java — hay que comprobar si existen archivos de datos/recursos que dependen de él.**

### ✅ Resuelto: bug de recetas del Auto Mechanic Table

Al crear/cargar un mundo, `RecipeManager.prepare()` fallaba con `NullPointerException: Components not bound yet` en `AutoMechanicTableRecipeSerializer.java:33`, al construir un `ItemStack` por defecto para el resultado de la receta (uno de los items de componente de Vehiclery: chasis/rueda/motor).

**Causa raíz real** (descartada la hipótesis anterior sobre `RegistryQueue`/`Eventual`): `AUTO_COMPONENT_STACK` decodificaba el campo `"item"` con `Item.CODEC` (plano), que produce un `Holder<Item>` **sin garantía** de tener `DataComponentMap` enlazado. El propio `ItemStack.CODEC` de vanilla (verificado desensamblando `ItemStack.class` de `minecraft-client-patched-26.2.0.37-beta.jar`) usa en su lugar `Item.CODEC_WITH_BOUND_COMPONENTS` para ese mismo campo — un codec que sí valida que el holder esté enlazado antes de dejar construir el `ItemStack`. El mod original en 1.21 (`lib_ext/Automobility-1.21-rewrite`) evitaba el problema por otra vía (`ItemStack.ITEM_NON_AIR_CODEC` + `Item#getDefaultInstance()`, que en 1.21 no tenía este requisito); el port a 26.2 cambió a construir `new ItemStack(holder, count)` directamente con el holder "crudo" de `Item.CODEC`, lo cual sí dispara el NPE en esta versión.

Confirmado además que `Holder$Reference.bindComponents(...)` no aparece invocado en ningún punto del bytecode de `minecraft-client-patched` ni de `neoforge-universal` (10.963 clases revisadas) — es decir, el enlace de componentes para el holder "suelto" que produce `Item.CODEC` genuinamente nunca ocurre; solo el holder canónico (`Item#builtInRegistryHolder()`) y los holders producidos por `Item.CODEC_WITH_BOUND_COMPONENTS` quedan enlazados.

**Fix aplicado**: en `AutoMechanicTableRecipeSerializer.java`, cambiar `Item.CODEC.fieldOf("item")` → `Item.CODEC_WITH_BOUND_COMPONENTS.fieldOf("item")`.

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
