package com.skd.vehiclery.item;

import com.skd.vehiclery.automobile.AutomobileData;
import com.skd.vehiclery.automobile.AutomobileEngine;
import com.skd.vehiclery.automobile.AutomobileFrame;
import com.skd.vehiclery.automobile.AutomobileWheel;
import com.skd.vehiclery.entity.AutomobileEntity;
import com.skd.vehiclery.entity.VehicleryEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutomobileItem extends Item implements CustomCreativeOutput {
    public static final List<AutomobileData> PREFABS = new ArrayList<>();

    public AutomobileItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide()) {
            var stack = context.getItemInHand();
            var data = stack.get(VehicleryItems.COMPONENT_AUTOMOBILE_DATA.require());
            if (data == null) {
                // No component data, fall back to empty components
                var e = new AutomobileEntity(VehicleryEntities.AUTOMOBILE.require(), context.getLevel());
                var pos = context.getClickLocation();
                e.snapTo(pos.x, pos.y, pos.z, context.getHorizontalDirection().toYRot(), 0);
                e.setComponents(
                        Holder.direct(AutomobileFrame.EMPTY),
                        Holder.direct(AutomobileWheel.EMPTY),
                        Holder.direct(AutomobileEngine.EMPTY)
                );
                boolean added = context.getLevel().addFreshEntity(e);
                // TODO(debug): see matching note below -- remove once the root cause is confirmed and fixed.
                com.skd.vehiclery.Vehiclery.LOG.info(
                        "[DEBUG placement] addFreshEntity (empty-data fallback) returned {} at {}", added, pos);
                stack.shrink(1);
                return InteractionResult.PASS;
            }
            var e = new AutomobileEntity(VehicleryEntities.AUTOMOBILE.require(), context.getLevel());
            var pos = context.getClickLocation();
            e.snapTo(pos.x, pos.y, pos.z, context.getHorizontalDirection().toYRot(), 0);

            var frame = context.getLevel().registryAccess().lookupOrThrow(AutomobileFrame.REGISTRY).get(data.frame())
                    .map(r -> (Holder<AutomobileFrame>)r).orElseGet(() -> Holder.direct(AutomobileFrame.EMPTY));
            var wheel = context.getLevel().registryAccess().lookupOrThrow(AutomobileWheel.REGISTRY).get(data.wheel())
                    .map(r -> (Holder<AutomobileWheel>)r).orElseGet(() -> Holder.direct(AutomobileWheel.EMPTY));
            var engine = context.getLevel().registryAccess().lookupOrThrow(AutomobileEngine.REGISTRY).get(data.engine())
                    .map(r -> (Holder<AutomobileEngine>)r).orElseGet(() -> Holder.direct(AutomobileEngine.EMPTY));
            e.setComponents(frame, wheel, engine);

            boolean added = context.getLevel().addFreshEntity(e);
            // TODO(debug): temporary diagnostic logging for the "nothing appears when placing"
            // bug reported on a real client -- remove once the root cause is confirmed and fixed.
            com.skd.vehiclery.Vehiclery.LOG.info(
                    "[DEBUG placement] addFreshEntity returned {} for automobile at {} frame={} wheel={} engine={}",
                    added, pos, data.frame().identifier(), data.wheel().identifier(), data.engine().identifier());
            stack.shrink(1);
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    public static void addPrefabs(AutomobileData ... prefabs) {
        PREFABS.addAll(Arrays.asList(prefabs));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.get(VehicleryItems.COMPONENT_AUTOMOBILE_DATA.require());

        if (data != null) {
            data.addToTooltip(context, tooltipComponents, tooltipFlag, stack);
        }

        super.appendHoverText(stack, context, display, tooltipComponents, tooltipFlag);
    }

    @Override
    public void provideCreativeOutput(CreativeModeTab.Output output, HolderLookup.Provider registries) {
        for (var prefab : PREFABS) {
            output.accept(prefab.asStack());
        }
    }
}
