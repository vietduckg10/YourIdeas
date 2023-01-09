package com.ducvn.yourideas.item;

import com.ducvn.yourideas.YourIdeasMod;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        ITEMS.register(bus);
    }
}
