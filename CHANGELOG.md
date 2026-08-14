# Changelog

## 0.0.0-beta.6

First update published to CurseForge since `0.0.0-beta.1` (betas 2-5 were internal
development-only builds, never released). Consolidates all fixes since beta.1:

- **Runtime crash fixes** (block/item ids, `GatherDataEvent` subscriptions, container/sound
  mixins, `jsonem` entity-model loading, networking payload registration, slope blockstates)
  found in the first real-client test of the 26.2 port.
- **Missing item icons fixed**: all 20 items now have proper `assets/vehiclery/items/*.json`
  definitions (26.2 split the old single item-model file into a definition + geometry pair).
- **Spanish translation** (`es_es.json`) added, full key parity with English.
- **All 75 Auto Mechanic Table recipes fixed**: migrated from the pre-26.2 ingredient object
  shorthand (`{"item": "..."}`) to the new plain-string format (`"..."` / `"#..."` for tags)
  required by `Ingredient.CODEC` in 26.2.
- **Automobile/component 3D item rendering fixed**: `VehiclerySpecialModelRenderer` now reports
  a real bounding box from `getExtents()`, so the 6 automobile/component items no longer render
  blank in hand/inventory/ground.
- **Auto Mechanic Table recipes actually craftable now**: root-caused and fixed a
  registration-timing bug where `AutoMechanicTableRecipeSerializer` built the result `ItemStack`
  eagerly during recipe JSON decode — which runs *before* the datapack reload binds item
  `DataComponentMap`s for that cycle — causing every frame/wheel/engine/attachment recipe to
  fail to parse (`Item ... does not have components yet`). The result is now decoded into a
  lightweight spec and the `ItemStack` is built lazily on first use, after components are bound.
- **Automobile item hand-render/placement hardening**: guarded against a `NullPointerException`
  in `AutomobileItem#useOn` when placing an `automobile` item stack with no crafted component
  data (e.g. obtained via search/`/give` instead of a prefab), and clamped the item-render scale
  calculation against degenerate frame dimensions.

## 0.0.0-beta.1

- Initial port of Automobility (1.21.1) to Minecraft 26.2 / NeoForge 26.2.0.37-beta as Vehiclery.
- Single-module NeoForge project layout (Fabric module dropped).
- Fully renamed to `vehiclery` (mod_id, package `com.skd.vehiclery`, assets/data namespaces).
- First compiling build: ported the full mod through NeoForge 26.2.0.37-beta's client
  rendering API overhaul (block/entity model baking, GUI, item/vehicle renderers, entity
  data serialization). See `docs/PORT_ROADMAP_26-2.md` for details and known temporary
  regressions (banner pattern rendering, vehicle passenger tilt animation, 3D item
  rendering for automobile/component items, and client-side Auto Mechanic Table recipe
  listing).
