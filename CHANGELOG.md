# Changelog

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
