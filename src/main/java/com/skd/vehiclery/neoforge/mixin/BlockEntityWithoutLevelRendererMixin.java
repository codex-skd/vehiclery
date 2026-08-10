package com.skd.vehiclery.neoforge.mixin;

// TODO(port): BlockEntityWithoutLevelRenderer (BEWLR) -- the vanilla hook this mixin used to inject
// into for custom 3D item rendering (automobile/component items in hand/inventory) -- no longer
// exists at all in this Minecraft version. The replacement is a codec-based
// SpecialModelRenderer<T>/SpecialModelRenderer.Unbaked<T> system registered via
// RegisterSpecialModelRendererEvent and referenced from the item's model JSON (similar in spirit to
// how the slope block model system moved to CustomUnbakedBlockStateModel, see
// SlopeUnbakedModel/NeoForgeSlopeGeometryLoader), which needs real design work: a MapCodec for our
// Unbaked type, a way to bake it into a SpecialModelRenderer using the existing BEWLRs registry
// (com.skd.vehiclery.neoforge.client.BEWLRs, still intact and SubmitNodeCollector-based), and the
// corresponding "special_render_id"-style model JSON declarations for each item that used a BEWLR.
// Until that's implemented, affected items render with their flat 2D icon instead of the custom 3D
// model. This mixin is no longer registered in vehiclery_neoforge.mixins.json (its target class is
// gone) -- kept as an inert placeholder file rather than deleted.
public class BlockEntityWithoutLevelRendererMixin {
}
