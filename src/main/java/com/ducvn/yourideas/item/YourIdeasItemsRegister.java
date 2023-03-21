package com.ducvn.yourideas.item;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.utility.DragonChargeItem;
import com.ducvn.yourideas.item.utility.SpawnerPicker;
import com.ducvn.yourideas.item.weapon.SpearOfSiphonItem;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YourIdeasItemsRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YourIdeasMod.MODID);
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    public static final DeferredRegister<Item> SPAWNER_PICKER = DeferredRegister.create(ForgeRegistries.ITEMS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        ITEMS.register(bus);
        if (YourIdeasConfig.edible_items.get()){
            VANILLA_ITEMS.register(bus);
        }
        if (YourIdeasConfig.spawner_picker.get()){
            SPAWNER_PICKER.register(bus);
        }
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

    public static final RegistryObject<Item> SUGAR = VANILLA_ITEMS.register("sugar", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_FOOD)
                            .food(new Food.Builder()
                                    .nutrition(1)
                                    .saturationMod(0.5F)
                                    .fast()
                                    .build()
                            )
            )
    );

    public static final RegistryObject<Item> HONEY_COMB = VANILLA_ITEMS.register("honeycomb", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_FOOD)
                            .food(new Food.Builder()
                                    .nutrition(2)
                                    .saturationMod(0.6F)
                                    .build()
                            )
            )
    );

    public static final RegistryObject<Item> POPPED_CHORUS_FRUIT = VANILLA_ITEMS.register("popped_chorus_fruit", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_FOOD)
                            .food(new Food.Builder()
                                    .nutrition(5)
                                    .saturationMod(0.4F)
                                    .build()
                            )
            )
    );

    public static final RegistryObject<Item> GLISTERING_MELON_SLICE = VANILLA_ITEMS.register("glistering_melon_slice", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_FOOD)
                            .food(new Food.Builder()
                                    .nutrition(4)
                                    .saturationMod(1.2F)
                                    .fast()
                                    .build()
                            )
            )
    );

    public static final RegistryObject<Item> NETHER_WART = VANILLA_ITEMS.register("nether_wart", () ->
            new Item(
                    new Item.Properties().tab(ItemGroup.TAB_FOOD)
                            .food(new Food.Builder()
                                    .nutrition(2)
                                    .saturationMod(0.2F)
                                    .fast()
                                    .effect(new EffectInstance(Effects.CONFUSION, 200, 2), 0.1F)
                                    .build()
                            )
            )
    );

    public static final RegistryObject<Item> SPAWNER_PICKER_ITEM = SPAWNER_PICKER.register("spawner_picker", () ->
            new SpawnerPicker(
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
                            .stacksTo(1)
            )
    );
}
