package com.ducvn.yourideas.item;

import com.ducvn.yourideas.YourIdeasMod;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YourIdeasItemsRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        ITEMS.register(bus);
    }

    public static final RegistryObject<Item> DRAGON_CHARGE = ITEMS.register("dragon_charge", () ->
            new DragonChargeItem(
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
}
