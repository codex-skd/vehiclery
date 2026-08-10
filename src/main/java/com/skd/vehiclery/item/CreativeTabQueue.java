package com.skd.vehiclery.item;

import com.skd.vehiclery.util.Eventual;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class CreativeTabQueue implements CreativeModeTab.DisplayItemsGenerator {
    public final Identifier location;
    private final List<Eventual<? extends Item>> items = new ArrayList<>();

    public CreativeTabQueue(Identifier location) {
        this.location = location;
    }

    public void queue(Eventual<? extends Item> item) {
        this.items.add(item);
    }

    @Override
    public void accept(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        items.forEach(i -> {
            if (i.require() instanceof CustomCreativeOutput outputItem) {
                outputItem.provideCreativeOutput(output, params.holders());
            } else {
                output.accept(i.require());
            }
        });
    }
}
