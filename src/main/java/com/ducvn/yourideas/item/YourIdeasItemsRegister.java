package com.ducvn.yourideas.item;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.item.utility.DragonChargeItem;
import com.ducvn.yourideas.item.weapon.SpearOfSiphonItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
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

    public static final RegistryObject<Item> BAT_TOOTH = ITEMS.register("bat_tooth", () ->
            new Item(
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> IRON_SPEAR_OF_SIPHON = ITEMS.register("iron_spear_of_siphon", () ->
            new SpearOfSiphonItem(ItemTier.IRON, 2D, -2.8D, 1.0D,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)));
    public static final RegistryObject<Item> DIAMOND_SPEAR_OF_SIPHON = ITEMS.register("diamond_spear_of_siphon", () ->
            new SpearOfSiphonItem(ItemTier.DIAMOND, 2D, -2.8D, 1.0D,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)));
    public static final RegistryObject<Item> NETHERITE_SPEAR_OF_SIPHON = ITEMS.register("netherite_spear_of_siphon", () ->
            new SpearOfSiphonItem(ItemTier.NETHERITE, 2D, -2.8D, 2.0D,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP).fireResistant()));
    public static final RegistryObject<Item> EMERALD_SPEAR_OF_SIPHON = ITEMS.register("emerald_spear_of_siphon", () ->
            new SpearOfSiphonItem(ItemTier.IRON, 2D, -2.8D, 3.0D,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)));
}
