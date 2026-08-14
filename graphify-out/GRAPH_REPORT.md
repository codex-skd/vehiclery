# Graph Report - 26.2  (2026-08-15)

## Corpus Check
- 469 files · ~166,095 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 2605 nodes · 5816 edges · 125 communities (118 shown, 7 thin omitted)
- Extraction: 98% EXTRACTED · 2% INFERRED · 0% AMBIGUOUS · INFERRED: 133 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `6b661c59`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- NeoForgePlatform.java
- SlopeBakedModel
- JsonEMCodecs.java
- AutomobileEntity
- AutopilotSignBlock.java
- LocalPlayerMixin.java
- BannerPostRearAttachment
- RearAttachmentType
- AutoMechanicTableBlock.java
- AutopilotSignBlockItem.java
- AutomobileEntity.java
- ChestRearAttachment
- HitboxEntity
- AutoMechanicTableScreen
- BaseChestRearAttachment
- Vec3
- AutomobileAssemblerBlock.java
- DashPanelBlock.java
- AutomobileItem.java
- AutomobileModels
- AutomobileSoundInstance
- AutoMechanicTableScreenHandler
- RearAttachment
- AutomobileAssemblerBlockEntity
- ModelDefinition
- AutomobileEngine
- DriftSmokeParticle.java
- SlopeBlock.java
- AutomobileComponentItem.java
- JsonEntityModelUtil.java
- SteepSlopeBlock.java
- AutomobileAssemblerBlockEntity.java
- FrontAttachmentType
- RenderableAutomobile
- Vehiclery.java
- Eventual
- AutopilotFrontAttachment
- SimpleMapContentRegistry
- AutomobileFrame
- VehicleryItems.java
- .interactAutomobile
- AutomobileWheel
- SimpleRenderableAutomobile
- VehicleryNeoForge.java
- AutoMechanicTableRecipe
- BaseAttachment
- SlopeWithDashPanelBlock.java
- SteepSlopeWithDashPanelBlock.java
- AUtils.java
- VehicleryClientNeoForge.java
- ObjModel.java
- BaseModel
- CollisionArea
- AutomobileHud.java
- AutomobileAssemblerBlockEntityRenderer.java
- FrontAttachment
- .rl
- SlicedLoopingAutomobileSoundInstance.java
- SoundEngineMixin.java
- ObjLoader.java
- BasePlowRearAttachment
- BannerPostRearAttachmentModel.java
- Platform
- AutoMechanicTableRecipeSerializer.java
- AutomobileComponentItem
- WheelBase.java
- AutomobileEntityRenderer.java
- VehicleryClientResourceDumper.java
- LazyTypeUnboundedMapCodec
- BaseHarvesterFrontAttachment
- SaddledBarrelRearAttachment.java
- EntityRenderersMixin.java
- Platform.java
- Flujo de trabajo — Vehiclery (NeoForge)
- VehicleryClient.java
- AutomobileStats
- HornSoundDefinition
- AutopilotFrontAttachmentModel.java
- FrontAttachmentRenderModel
- ContainerRecipeInput
- MenuScreenRegistrar.java
- AutomobileController
- SingleSlotScreenHandler
- CreativeTabQueue
- Ruta de desarrollo — port a NeoForge 26.2.0.37-beta
- BackhoeRearAttachment
- RearAttachmentRenderModel
- CurseForge — Variables del proyecto
- HarvesterFrontAttachmentModel.java
- ChestRearAttachmentModel.java
- GrindstoneRearAttachmentModel.java
- PlowRearAttachmentModel.java
- StonecutterRearAttachmentModel.java
- VehicleryBlocks.java
- .blockColor
- DefaultRegistrar
- SlopePlacementContext.java
- SlopeModelsProvider.java
- CropHarvesterFrontAttachment.java
- .interactAutomobile
- ClientPackets.java
- AutopilotSignBlockEntity.java
- StatContainer
- AABBMixin
- GrassCutterFrontAttachment.java
- AutomobileInteractable.java
- ShovelItemAccess.java
- .entityType
- Vehiclery
- ExhaustFumesModel.java
- SkidEffectModel.java
- AutomobileController.java
- EntityMixin.java
- SoundChannelAccess
- Changelog
- gradlew
- EntityRenderDispatcherMixin.java
- MaterialDefinitionAccess.java
- .from
- .getUpdateTag

## God Nodes (most connected - your core abstractions)
1. `AutomobileEntity` - 258 edges
2. `RearAttachmentType` - 64 edges
3. `RearAttachment` - 51 edges
4. `AutomobileAssemblerBlockEntity` - 49 edges
5. `AutomobileFrame` - 46 edges
6. `AutomobileEngine` - 40 edges
7. `FrontAttachmentType` - 39 edges
8. `AutomobileWheel` - 38 edges
9. `FrontAttachment` - 33 edges
10. `Platform` - 33 edges

## Surprising Connections (you probably didn't know these)
- `Vehiclery` --references--> `CreativeTabQueue`  [EXTRACTED]
  src/main/java/com/skd/vehiclery/Vehiclery.java → src/main/java/com/skd/vehiclery/item/CreativeTabQueue.java
- `Vehiclery` --references--> `AutoMechanicTableScreenHandler`  [EXTRACTED]
  src/main/java/com/skd/vehiclery/Vehiclery.java → src/main/java/com/skd/vehiclery/screen/AutoMechanicTableScreenHandler.java
- `Vehiclery` --references--> `SingleSlotScreenHandler`  [EXTRACTED]
  src/main/java/com/skd/vehiclery/Vehiclery.java → src/main/java/com/skd/vehiclery/screen/SingleSlotScreenHandler.java
- `Vehiclery` --references--> `Eventual`  [EXTRACTED]
  src/main/java/com/skd/vehiclery/Vehiclery.java → src/main/java/com/skd/vehiclery/util/Eventual.java
- `RearAttachmentType` --implements--> `AutomobileComponent`  [EXTRACTED]
  src/main/java/com/skd/vehiclery/automobile/attachment/RearAttachmentType.java → src/main/java/com/skd/vehiclery/automobile/AutomobileComponent.java

## Import Cycles
- None detected.

## Communities (125 total, 7 thin omitted)

### Community 0 - "NeoForgePlatform.java"
Cohesion: 0.05
Nodes (52): CustomPacketPayload, IPayloadContext, BEWLRs, Item, ItemDisplayContext, ItemStack, PoseStack, SubmitNodeCollector (+44 more)

### Community 1 - "SlopeBakedModel"
Cohesion: 0.05
Nodes (48): Baked, BlockStateModelPart, CustomUnbakedBlockStateModel, DynamicBlockStateModel, MaterialBaker, MaterialFlags, Matrix4fc, ModelBaker (+40 more)

### Community 2 - "JsonEMCodecs.java"
Cohesion: 0.22
Nodes (9): CubeDefinitionAccess, Accessor, CubeDefinition, CubeDeformation, Direction, Invoker, Mixin, UVPair (+1 more)

### Community 4 - "AutopilotSignBlock.java"
Cohesion: 0.07
Nodes (38): BaseEntityBlock, BlockEntityTicker, RenderShape, AutopilotSignBlock, getSerializedName(), Heading, Block, BlockEntity (+30 more)

### Community 5 - "LocalPlayerMixin.java"
Cohesion: 0.05
Nodes (41): ClientInput, ContainerLevelAccess, ServerboundPlayerCommandPacket, Shadow, EntityWithInventory, Player, AbstractContainerMenuMixin, Block (+33 more)

### Community 6 - "BannerPostRearAttachment"
Cohesion: 0.06
Nodes (41): ItemTintSource, MinecraftServer, ExtendableRearAttachment, CompoundTag, Override, Provider, ServerPlayer, ClientPackets (+33 more)

### Community 7 - "RearAttachmentType"
Cohesion: 0.09
Nodes (18): BlockRearAttachment, BlockState, Component, ContainerLevelAccess, MenuProvider, Nullable, Override, Player (+10 more)

### Community 8 - "AutoMechanicTableBlock.java"
Cohesion: 0.08
Nodes (39): Block, AutoMechanicTableBlock, BlockHitResult, BlockPos, BlockState, Component, InteractionResult, Level (+31 more)

### Community 9 - "AutopilotSignBlockItem.java"
Cohesion: 0.06
Nodes (39): BlockItem, ItemUseAnimation, AutopilotSignBlockItem, Block, InteractionHand, InteractionResult, ItemStack, Level (+31 more)

### Community 10 - "AutomobileEntity.java"
Cohesion: 0.06
Nodes (13): Entity, MoveFunction, AutomobileEntity, AABB, Builder, EntityDataAccessor, Holder, Nullable (+5 more)

### Community 11 - "ChestRearAttachment"
Cohesion: 0.09
Nodes (21): Container, MenuProvider, NonNullList, ChestRearAttachment, AbstractContainerMenu, BlockState, Component, CompoundTag (+13 more)

### Community 12 - "HitboxEntity"
Cohesion: 0.07
Nodes (28): PressurePlateBlock, AutomobilePressurePlateBlock, BlockPos, Level, MapCodec, Override, HitboxEntity, Builder (+20 more)

### Community 13 - "AutoMechanicTableScreen"
Cohesion: 0.09
Nodes (23): AbstractContainerScreen, FormattedCharSequence, MenuAccess, MouseButtonEvent, AutoMechanicTableScreen, Component, GuiGraphicsExtractor, Identifier (+15 more)

### Community 14 - "BaseChestRearAttachment"
Cohesion: 0.10
Nodes (22): ChestLidController, ContainerOpenersCounter, PlayerEnderChestContainer, BaseChestRearAttachment, BlockPos, BlockState, Component, ContainerLevelAccess (+14 more)

### Community 15 - "Vec3"
Cohesion: 0.12
Nodes (8): Quaternionf, RemovalReason, Displacement, IncomingCollision, LivingEntity, Vec3, Vector3d, Vec3

### Community 16 - "AutomobileAssemblerBlock.java"
Cohesion: 0.13
Nodes (25): EntityBlock, AutomobileAssemblerBlock, Block, BlockEntity, BlockGetter, BlockHitResult, BlockPlaceContext, BlockPos (+17 more)

### Community 17 - "DashPanelBlock.java"
Cohesion: 0.14
Nodes (22): ScheduledTickAccess, DashPanelBlock, Block, BlockGetter, BlockPlaceContext, BlockPos, BlockState, BooleanProperty (+14 more)

### Community 18 - "AutomobileItem.java"
Cohesion: 0.15
Nodes (13): Item, AutomobileItem, Component, InteractionResult, ItemStack, Output, Override, Provider (+5 more)

### Community 19 - "AutomobileModels"
Cohesion: 0.12
Nodes (16): Context, AutomobileModels, Context, Gson, Identifier, Override, ResourceManager, AutomobileRenderer (+8 more)

### Community 20 - "AutomobileSoundInstance"
Cohesion: 0.13
Nodes (9): AbstractTickableSoundInstance, AutomobileSoundInstance, EngineSound, IntConsumer, Minecraft, Override, SoundEvent, Vec3 (+1 more)

### Community 21 - "AutoMechanicTableScreenHandler"
Cohesion: 0.15
Nodes (14): DataSlot, Slot, AutoMechanicTableScreenHandler, InputSlot, Container, ContainerLevelAccess, Ingredient, Inventory (+6 more)

### Community 22 - "RearAttachment"
Cohesion: 0.09
Nodes (12): CompoundTag, ContainerLevelAccess, MenuProvider, Nullable, Override, Player, Provider, Vec3 (+4 more)

### Community 24 - "ModelDefinition"
Cohesion: 0.11
Nodes (24): getSerializedName(), Codec, Identifier, ModelLayerLocation, Override, RenderType, Vector3fc, ModelDefinition (+16 more)

### Community 25 - "AutomobileEngine"
Cohesion: 0.14
Nodes (16): AutomobileEngine, EngineModel, ExhaustPos, Codec, EntityDataSerializer, Holder, Identifier, Override (+8 more)

### Community 26 - "DriftSmokeParticle.java"
Cohesion: 0.20
Nodes (12): ClientLevel, Layer, Particle, ParticleProvider, SingleQuadParticle, SpriteSet, DriftSmokeParticle, Factory (+4 more)

### Community 27 - "SlopeBlock.java"
Cohesion: 0.15
Nodes (20): HorizontalDirectionalBlock, SimpleWaterloggedBlock, Block, BlockGetter, BlockPlaceContext, BlockPos, BlockState, BooleanProperty (+12 more)

### Community 28 - "AutomobileComponentItem.java"
Cohesion: 0.06
Nodes (30): DataComponentType, FunctionalInterface, AutomobileComponentItem, Builtin, Dynamic, Component, Holder, Identifier (+22 more)

### Community 29 - "JsonEntityModelUtil.java"
Cohesion: 0.13
Nodes (17): EntityModelSet, Redirect, EntityModelSetAccess, Accessor, LayerDefinition, Mixin, ModelLayerLocation, Mixin (+9 more)

### Community 30 - "SteepSlopeBlock.java"
Cohesion: 0.18
Nodes (15): Block, BlockGetter, BlockPlaceContext, BlockPos, BlockState, BooleanProperty, Builder, CollisionContext (+7 more)

### Community 31 - "AutomobileAssemblerBlockEntity.java"
Cohesion: 0.18
Nodes (12): BlockPos, BlockState, ClientGamePacketListener, Component, Holder, InteractionHand, InteractionResult, ItemStack (+4 more)

### Community 32 - "FrontAttachmentType"
Cohesion: 0.26
Nodes (7): EmptyFrontAttachment, FrontAttachmentModel, FrontAttachmentType, Codec, Identifier, Override, AutomobileComponent

### Community 33 - "RenderableAutomobile"
Cohesion: 0.10
Nodes (3): Nullable, Vector3f, RenderableAutomobile

### Community 34 - "Vehiclery.java"
Cohesion: 0.17
Nodes (11): Identifier, NeoForgeSlopeGeometryLoader, InitlessConstants, Block, Codec, Identifier, Logger, MenuType (+3 more)

### Community 35 - "Eventual"
Cohesion: 0.33
Nodes (7): SoundEvent, VehiclerySounds, Entry, Identifier, Registry, SuppressWarnings, RegistryQueue

### Community 36 - "AutopilotFrontAttachment"
Cohesion: 0.14
Nodes (11): AutopilotFrontAttachment, CompoundTag, Entity, EntityType, Override, Provider, Vec3, State (+3 more)

### Community 37 - "SimpleMapContentRegistry"
Cohesion: 0.15
Nodes (14): BannerPostRearAttachment, BannerPatternLayers, Component, CompoundTag, ContainerLevelAccess, DyeColor, ItemStack, MenuProvider (+6 more)

### Community 38 - "AutomobileFrame"
Cohesion: 0.06
Nodes (44): AutomobileFrame, FrameModel, Hitbox, ByteBuf, Codec, EntityDataSerializer, EntityDimensions, Holder (+36 more)

### Community 39 - "VehicleryItems.java"
Cohesion: 0.23
Nodes (11): DataComponentGetter, AutomobileData, Codec, Component, Identifier, ItemStack, Override, ResourceKey (+3 more)

### Community 40 - ".interactAutomobile"
Cohesion: 0.15
Nodes (10): Container, InteractionHand, InteractionResult, Player, InteractionHand, InteractionResult, ItemStack, Override (+2 more)

### Community 41 - "AutomobileWheel"
Cohesion: 0.19
Nodes (12): AutomobileWheel, Codec, EntityDataSerializer, Holder, Identifier, Override, Registry, RegistryFriendlyByteBuf (+4 more)

### Community 42 - "SimpleRenderableAutomobile"
Cohesion: 0.17
Nodes (4): Nullable, Override, Vector3f, SimpleRenderableAutomobile

### Community 43 - "VehicleryNeoForge.java"
Cohesion: 0.20
Nodes (11): Mod, NewRegistry, RegisterEvent, RegisterPayloadHandlersEvent, Server, EventBusSubscriber, Registry, SubscribeEvent (+3 more)

### Community 44 - "AutoMechanicTableRecipe"
Cohesion: 0.20
Nodes (10): PlacementInfo, Recipe, RecipeBookCategory, RecipeType, AutoMechanicTableRecipe, Identifier, Ingredient, ItemStack (+2 more)

### Community 45 - "BaseAttachment"
Cohesion: 0.15
Nodes (7): BaseAttachment, BlockPos, CompoundTag, Level, Provider, ServerPlayer, Vec3

### Community 46 - "SlopeWithDashPanelBlock.java"
Cohesion: 0.20
Nodes (14): Block, BlockPos, BlockState, Builder, Entity, HorizontalDirectionalBlock, InsideBlockEffectApplier, ItemStack (+6 more)

### Community 47 - "SteepSlopeWithDashPanelBlock.java"
Cohesion: 0.20
Nodes (14): Block, BlockPos, BlockState, Builder, Entity, HorizontalDirectionalBlock, InsideBlockEffectApplier, ItemStack (+6 more)

### Community 48 - "AUtils.java"
Cohesion: 0.08
Nodes (31): Key, KeyMapping, Options, Accessor, Mixin, KeyMappingAccess, AutomobileHud, ControlHint (+23 more)

### Community 49 - "VehicleryClientNeoForge.java"
Cohesion: 0.17
Nodes (11): AddClientReloadListenersEvent, BlockTintSources, Client, FMLClientSetupEvent, ItemTintSources, RegisterBlockStateModels, RegisterParticleProvidersEvent, RegisterSpecialModelRendererEvent (+3 more)

### Community 50 - "ObjModel.java"
Cohesion: 0.16
Nodes (14): Model, EmptyModel, ModelPart, Override, PoseStack, VertexConsumer, Context, ModelLayerLocation (+6 more)

### Community 51 - "BaseModel"
Cohesion: 0.23
Nodes (10): BaseModel, Context, Identifier, ModelLayerLocation, ModelPart, Override, PoseStack, SubmitNodeCollector (+2 more)

### Community 52 - "CollisionArea"
Cohesion: 0.16
Nodes (9): BlockPos, BlockState, Level, SpecialAutomobileColliderBlock, CollisionArea, AABB, Entity, Override (+1 more)

### Community 53 - "AutomobileHud.java"
Cohesion: 0.11
Nodes (11): ClientboundAddEntityPacket, MoverType, ServerEntity, DeployableRearAttachment, ClientGamePacketListener, DamageSource, EntityDimensions, ItemStack (+3 more)

### Community 54 - "AutomobileAssemblerBlockEntityRenderer.java"
Cohesion: 0.19
Nodes (12): BlockEntityRenderer, BlockEntityRenderState, Font, @Nullable CrumblingOverlay, AutomobileAssemblerBlockEntityRenderer, CameraRenderState, Context, Override (+4 more)

### Community 55 - "FrontAttachment"
Cohesion: 0.18
Nodes (7): FrontAttachment, CompoundTag, Entity, ItemStack, Override, Provider, Vec3

### Community 56 - ".rl"
Cohesion: 0.32
Nodes (11): Codec, CubeDefinition, CubeDeformation, Direction, LayerDefinition, MaterialDefinition, PartDefinition, PartPose (+3 more)

### Community 57 - "SlicedLoopingAutomobileSoundInstance.java"
Cohesion: 0.23
Nodes (6): HornSound, IntConsumer, Minecraft, Override, SoundEvent, SlicedLoopingAutomobileSoundInstance

### Community 58 - "SoundEngineMixin.java"
Cohesion: 0.19
Nodes (11): ChannelHandle, PlayResult, SoundInstance, CallbackInfo, CallbackInfoReturnable, Inject, Mixin, SoundEngineMixin (+3 more)

### Community 59 - "ObjLoader.java"
Cohesion: 0.24
Nodes (10): Obj, ResourceManagerReloadListener, BakedObj, EventualObj, Identifier, Logger, ModelLayerLocation, Override (+2 more)

### Community 60 - "BasePlowRearAttachment"
Cohesion: 0.21
Nodes (8): BasePlowRearAttachment, BlockPos, BlockState, MutableBlockPos, Override, ServerLevel, SoundEvent, Vec3

### Community 61 - "BannerPostRearAttachmentModel.java"
Cohesion: 0.21
Nodes (11): BannerPostRearAttachmentModel, BannerPatternLayers, Context, DyeColor, ModelLayerLocation, ModelPart, Override, PoseStack (+3 more)

### Community 62 - "Platform"
Cohesion: 0.15
Nodes (9): GlobalPlatformInstance, CreativeModeTab, DisplayItemsGenerator, EntityDataSerializer, FriendlyByteBuf, Identifier, ServerPlayer, SimpleParticleType (+1 more)

### Community 63 - "AutoMechanicTableRecipeSerializer.java"
Cohesion: 0.21
Nodes (11): AutoMechanicTableRecipeSerializer, Codec, Holder, Identifier, Item, ItemStack, MapCodec, RecipeSerializer (+3 more)

### Community 64 - "AutomobileComponentItem"
Cohesion: 0.33
Nodes (7): Accessor, CubeDefinition, Invoker, Mixin, PartDefinition, PartPose, PartDefinitionAccess

### Community 65 - "WheelBase.java"
Cohesion: 0.35
Nodes (7): MeshDefinition, Accessor, Invoker, LayerDefinition, MaterialDefinition, Mixin, LayerDefinitionAccess

### Community 66 - "AutomobileEntityRenderer.java"
Cohesion: 0.23
Nodes (9): EntityRenderer, EntityRenderState, AutomobileEntityRenderer, CameraRenderState, Context, Override, PoseStack, SubmitNodeCollector (+1 more)

### Community 67 - "VehicleryClientResourceDumper.java"
Cohesion: 0.38
Nodes (7): BakingContext, SpecialModelRenderer, ItemStack, MapCodec, Override, VehiclerySpecialModelRenderer, Unbaked

### Community 68 - "LazyTypeUnboundedMapCodec"
Cohesion: 0.31
Nodes (7): BaseMapCodec, Codec, DataResult, DynamicOps, Override, Pair, LazyTypeUnboundedMapCodec

### Community 69 - "BaseHarvesterFrontAttachment"
Cohesion: 0.25
Nodes (8): BaseHarvesterFrontAttachment, BlockPos, BlockState, ItemStack, MutableBlockPos, Override, ServerLevel, Vec3

### Community 70 - "SaddledBarrelRearAttachment.java"
Cohesion: 0.25
Nodes (7): BlockState, Component, ContainerLevelAccess, MenuProvider, Override, SoundEvent, SaddledBarrelRearAttachment

### Community 71 - "EntityRenderersMixin.java"
Cohesion: 0.21
Nodes (11): EntityRenderersMixin, CallbackInfoReturnable, Context, EntityRenderer, EntityType, Inject, Mixin, EntityRenderHelper (+3 more)

### Community 72 - "Platform.java"
Cohesion: 0.22
Nodes (10): CommandBuildContext, CommandDispatcher, Inventory, Item, ItemDisplayContext, ItemStack, MenuType, PoseStack (+2 more)

### Community 73 - "Flujo de trabajo — Vehiclery (NeoForge)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Vehiclery (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 74 - "VehicleryClient.java"
Cohesion: 0.33
Nodes (7): Component, ItemStack, Override, TooltipContext, TooltipDisplay, TooltipFlag, TooltipItem

### Community 75 - "AutomobileStats"
Cohesion: 0.15
Nodes (8): AutomobileStats, Identifier, Override, DisplayStat, Component, Component, Identifier, StatContainer

### Community 77 - "AutopilotFrontAttachmentModel.java"
Cohesion: 0.26
Nodes (8): AutopilotFrontAttachmentModel, Context, ModelLayerLocation, ModelPart, Override, PoseStack, SubmitNodeCollector, Vector3fc

### Community 78 - "FrontAttachmentRenderModel"
Cohesion: 0.23
Nodes (8): FrontAttachmentRenderModel, Context, ModelLayerLocation, ModelPart, Override, PoseStack, Vector3fc, VertexConsumer

### Community 79 - "ContainerRecipeInput"
Cohesion: 0.30
Nodes (6): RecipeInput, Level, ContainerRecipeInput, ItemStack, Override, SimpleContainer

### Community 80 - "MenuScreenRegistrar.java"
Cohesion: 0.26
Nodes (6): RegisterMenuScreensEvent, Component, Inventory, MenuType, MenuScreenRegistrar, TriFunc

### Community 82 - "SingleSlotScreenHandler"
Cohesion: 0.31
Nodes (7): AbstractContainerMenu, Container, Inventory, ItemStack, Override, Player, SingleSlotScreenHandler

### Community 83 - "CreativeTabQueue"
Cohesion: 0.14
Nodes (13): DisplayItemsGenerator, ItemDisplayParameters, CreativeTabQueue, Identifier, Item, Output, Override, itemId() (+5 more)

### Community 84 - "Ruta de desarrollo — port a NeoForge 26.2.0.37-beta"
Cohesion: 0.18
Nodes (10): Cambios de arquitectura más relevantes (para referencia futura), Notas operativas para delegación (lecciones de la sesión de port), Regresiones funcionales conocidas (TODOs en el código, no bloquean compilación), ✅ Resuelto: bug de recetas del Auto Mechanic Table, ✅ Resuelto: formato antiguo de ingredientes en las 75 recetas, ✅ Resuelto: iconos/modelos de ítem faltantes ("Missing item model") + traducción al español, ✅ Resuelto: renderizado 3D en blanco de las 6 piezas del automóvil (`automobile`, `automobile_frame/wheel/engine`, `front/rear_attachment`), Resumen de las 6 fases (+2 more)

### Community 85 - "BackhoeRearAttachment"
Cohesion: 0.29
Nodes (6): BackhoeRearAttachment, Block, BlockPos, BlockState, Override, SoundEvent

### Community 86 - "RearAttachmentRenderModel"
Cohesion: 0.27
Nodes (6): Context, ModelLayerLocation, ModelPart, Override, Vector3fc, RearAttachmentRenderModel

### Community 87 - "CurseForge — Variables del proyecto"
Cohesion: 0.20
Nodes (9): CurseForge — Variables del proyecto, Nota, Nota: nombre de jar no coincide con el script de subida, Nota post-subida, Proyecto, Rama, Tag, Tokens (+1 more)

### Community 88 - "HarvesterFrontAttachmentModel.java"
Cohesion: 0.31
Nodes (6): HarvesterFrontAttachmentModel, Context, ModelLayerLocation, ModelPart, Override, Vector3fc

### Community 89 - "ChestRearAttachmentModel.java"
Cohesion: 0.31
Nodes (6): ChestRearAttachmentModel, Context, ModelLayerLocation, ModelPart, Override, Vector3fc

### Community 90 - "GrindstoneRearAttachmentModel.java"
Cohesion: 0.31
Nodes (6): GrindstoneRearAttachmentModel, Context, ModelLayerLocation, ModelPart, Override, Vector3fc

### Community 91 - "PlowRearAttachmentModel.java"
Cohesion: 0.31
Nodes (6): Context, ModelLayerLocation, ModelPart, Override, Vector3fc, PlowRearAttachmentModel

### Community 92 - "StonecutterRearAttachmentModel.java"
Cohesion: 0.31
Nodes (6): Context, ModelLayerLocation, ModelPart, Override, Vector3fc, StonecutterRearAttachmentModel

### Community 93 - "VehicleryBlocks.java"
Cohesion: 0.36
Nodes (8): id(), itemId(), Block, BlockItem, Item, ResourceKey, register(), VehicleryBlocks

### Community 94 - ".blockColor"
Cohesion: 0.29
Nodes (5): BlockEntityType, BlockPos, BlockState, BlockTintSource, Nullable

### Community 95 - "DefaultRegistrar"
Cohesion: 0.32
Nodes (5): automobileDamageSource(), DamageSource, Level, VehicleryEntities, TagKey

### Community 96 - "SlopePlacementContext.java"
Cohesion: 0.44
Nodes (5): BlockPlaceContext, Direction, Half, UseOnContext, SlopePlacementContext

### Community 97 - "SlopeModelsProvider.java"
Cohesion: 0.33
Nodes (5): CachedOutput, DataProvider, PackOutput, Override, SlopeModelsProvider

### Community 98 - "CropHarvesterFrontAttachment.java"
Cohesion: 0.36
Nodes (5): CropHarvesterFrontAttachment, BlockPos, BlockState, ItemStack, Override

### Community 99 - ".interactAutomobile"
Cohesion: 0.19
Nodes (11): AutomobileInteractable, InteractionHand, InteractionResult, ItemStack, Player, FrontAttachmentItem, InteractionHand, InteractionResult (+3 more)

### Community 100 - "ClientPackets.java"
Cohesion: 0.43
Nodes (3): Entity, Override, MobControllerFrontAttachment

### Community 101 - "AutopilotSignBlockEntity.java"
Cohesion: 0.46
Nodes (5): BlockEntity, AutopilotSignBlockEntity, BlockPos, BlockState, Level

### Community 102 - "StatContainer"
Cohesion: 0.48
Nodes (3): CubeDeformationAccess, Accessor, Mixin

### Community 103 - "AABBMixin"
Cohesion: 0.39
Nodes (4): AABBMixin, AABB, Mixin, Override

### Community 104 - "GrassCutterFrontAttachment.java"
Cohesion: 0.36
Nodes (5): GrassCutterFrontAttachment, BlockPos, BlockState, ItemStack, Override

### Community 106 - "ShovelItemAccess.java"
Cohesion: 0.20
Nodes (9): BlockPos, BlockState, Override, SoundEvent, Accessor, Block, BlockState, Mixin (+1 more)

### Community 107 - ".entityType"
Cohesion: 0.20
Nodes (7): BlockEntityRenderer, Context, EntityDimensions, EntityRenderer, EntityType, Level, MobCategory

### Community 108 - "Vehiclery"
Cohesion: 0.33
Nodes (5): Credit: Audio, Driving, Features, Getting Started, Vehiclery

### Community 109 - "ExhaustFumesModel.java"
Cohesion: 0.47
Nodes (4): ExhaustFumesModel, Context, Identifier, ModelLayerLocation

### Community 110 - "SkidEffectModel.java"
Cohesion: 0.47
Nodes (4): Context, Identifier, ModelLayerLocation, SkidEffectModel

### Community 111 - "AutomobileController.java"
Cohesion: 0.53
Nodes (5): accelerating(), braking(), drifting(), inControllerMode(), Override

### Community 112 - "EntityMixin.java"
Cohesion: 0.53
Nodes (4): EntityMixin, CallbackInfo, Inject, Mixin

### Community 113 - "SoundChannelAccess"
Cohesion: 0.53
Nodes (3): Accessor, Mixin, SoundChannelAccess

### Community 114 - "Changelog"
Cohesion: 0.50
Nodes (3): 0.0.0-beta.1, 0.0.0-beta.6, Changelog

### Community 115 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 122 - "MaterialDefinitionAccess.java"
Cohesion: 0.53
Nodes (3): Accessor, Mixin, MaterialDefinitionAccess

### Community 124 - ".getUpdateTag"
Cohesion: 0.40
Nodes (3): CompoundTag, Provider, ValueOutput

## Knowledge Gaps
- **68 isolated node(s):** `LEFT`, `CENTER`, `RIGHT`, `FRONT`, `BACK` (+63 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `AutomobileEntity` connect `AutomobileEntity.java` to `AutomobileEntity`, `LocalPlayerMixin.java`, `BannerPostRearAttachment`, `RearAttachmentType`, `ChestRearAttachment`, `HitboxEntity`, `BaseChestRearAttachment`, `Vec3`, `AutomobileSoundInstance`, `RearAttachment`, `AutomobileEngine`, `FrontAttachmentType`, `RenderableAutomobile`, `AutopilotFrontAttachment`, `SimpleMapContentRegistry`, `AutomobileFrame`, `.interactAutomobile`, `AutomobileWheel`, `BaseAttachment`, `AUtils.java`, `AutomobileHud.java`, `FrontAttachment`, `SlicedLoopingAutomobileSoundInstance.java`, `BasePlowRearAttachment`, `AutomobileEntityRenderer.java`, `BaseHarvesterFrontAttachment`, `SaddledBarrelRearAttachment.java`, `AutomobileStats`, `BackhoeRearAttachment`, `CropHarvesterFrontAttachment.java`, `.interactAutomobile`, `ClientPackets.java`, `GrassCutterFrontAttachment.java`, `ShovelItemAccess.java`?**
  _High betweenness centrality (0.261) - this node is a cross-community bridge._
- **Why does `RearAttachmentType` connect `RearAttachmentType` to `FrontAttachmentType`, `RenderableAutomobile`, `SimpleMapContentRegistry`, `BannerPostRearAttachment`, `SaddledBarrelRearAttachment.java`, `.interactAutomobile`, `ShovelItemAccess.java`, `ChestRearAttachment`, `AutomobileEntity.java`, `AutomobileComponentItem.java`, `BaseChestRearAttachment`, `BackhoeRearAttachment`, `AutomobileHud.java`, `RearAttachment`, `FrontAttachment`, `BasePlowRearAttachment`?**
  _High betweenness centrality (0.052) - this node is a cross-community bridge._
- **Why does `FrontAttachment` connect `FrontAttachment` to `FrontAttachmentType`, `RenderableAutomobile`, `ClientPackets.java`, `AutopilotFrontAttachment`, `BaseHarvesterFrontAttachment`, `SimpleRenderableAutomobile`, `AutomobileEntity.java`, `BaseAttachment`, `AutopilotFrontAttachmentModel.java`, `FrontAttachmentRenderModel`, `AutomobileHud.java`, `AutomobileAssemblerBlockEntity`, `HarvesterFrontAttachmentModel.java`, `AutomobileAssemblerBlockEntity.java`?**
  _High betweenness centrality (0.046) - this node is a cross-community bridge._
- **What connects `LEFT`, `CENTER`, `RIGHT` to the rest of the system?**
  _68 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `NeoForgePlatform.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05246913580246913 - nodes in this community are weakly interconnected._
- **Should `SlopeBakedModel` be split into smaller, more focused modules?**
  _Cohesion score 0.05365686944634313 - nodes in this community are weakly interconnected._
- **Should `AutomobileEntity` be split into smaller, more focused modules?**
  _Cohesion score 0.08205128205128205 - nodes in this community are weakly interconnected._