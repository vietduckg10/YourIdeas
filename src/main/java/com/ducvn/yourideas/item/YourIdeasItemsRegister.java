package com.ducvn.yourideas.item;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.armor.YourIdeasArmorMaterial;
import com.ducvn.yourideas.item.armor.YourIdeasEmeraldArmor;
import com.ducvn.yourideas.item.tools.YourIdeasItemTier;
import com.ducvn.yourideas.item.tools.emerald.*;
import com.ducvn.yourideas.item.utility.DragonChargeItem;
import com.ducvn.yourideas.item.utility.SpawnerPicker;
import com.ducvn.yourideas.item.tools.other.SpearOfSiphonItem;
import net.minecraft.inventory.EquipmentSlotType;
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
    public static final DeferredRegister<Item> EMERALD_GEARS = DeferredRegister.create(ForgeRegistries.ITEMS, YourIdeasMod.MODID);
    public static final DeferredRegister<Item> BEE_STAFF_ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        ITEMS.register(bus);
        if (YourIdeasConfig.edible_items.get()){
            VANILLA_ITEMS.register(bus);
        }
        if (YourIdeasConfig.spawner_picker.get()){
            SPAWNER_PICKER.register(bus);
        }
        if (YourIdeasConfig.emerald_gears.get()){
            EMERALD_GEARS.register(bus);
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

    public static final RegistryObject<Item> EMERALD_HELMET = EMERALD_GEARS.register("emerald_helmet", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = EMERALD_GEARS.register("emerald_chestplate", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_LEGGINGS = EMERALD_GEARS.register("emerald_leggings", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BOOTS = EMERALD_GEARS.register("emerald_boots", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD, EquipmentSlotType.FEET,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_SWORD = EMERALD_GEARS.register("emerald_sword", () ->
            new YourIdeasEmeraldSword(YourIdeasItemTier.EMERALD, 3, -2.4F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_PICKAXE = EMERALD_GEARS.register("emerald_pickaxe", () ->
            new YourIdeasEmeraldPickaxe(YourIdeasItemTier.EMERALD, 1, -2.8F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_AXE = EMERALD_GEARS.register("emerald_axe", () ->
            new YourIdeasEmeraldAxe(YourIdeasItemTier.EMERALD, 5, -3.0F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_SHOVEL = EMERALD_GEARS.register("emerald_shovel", () ->
            new YourIdeasEmeraldShovel(YourIdeasItemTier.EMERALD, 1.5F, -3.0F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_HOE = EMERALD_GEARS.register("emerald_hoe", () ->
            new YourIdeasEmeraldHoe(YourIdeasItemTier.EMERALD, -3, 0.0F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );

    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_HELMET = EMERALD_GEARS.register("emerald_base_netherite_helmet", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD_BASE_NETHERITE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_CHESTPLATE = EMERALD_GEARS.register("emerald_base_netherite_chestplate", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD_BASE_NETHERITE, EquipmentSlotType.CHEST,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_LEGGINGS = EMERALD_GEARS.register("emerald_base_netherite_leggings", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD_BASE_NETHERITE, EquipmentSlotType.LEGS,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_BOOTS = EMERALD_GEARS.register("emerald_base_netherite_boots", () ->
            new YourIdeasEmeraldArmor(YourIdeasArmorMaterial.EMERALD_BASE_NETHERITE, EquipmentSlotType.FEET,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_SWORD = EMERALD_GEARS.register("emerald_base_netherite_sword", () ->
            new YourIdeasEmeraldSword(YourIdeasItemTier.EMERALD_BASE_NETHERITE, 3, -2.4F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_PICKAXE = EMERALD_GEARS.register("emerald_base_netherite_pickaxe", () ->
            new YourIdeasEmeraldPickaxe(YourIdeasItemTier.EMERALD_BASE_NETHERITE, 1, -2.8F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_AXE = EMERALD_GEARS.register("emerald_base_netherite_axe", () ->
            new YourIdeasEmeraldAxe(YourIdeasItemTier.EMERALD_BASE_NETHERITE, 5, -3.0F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_SHOVEL = EMERALD_GEARS.register("emerald_base_netherite_shovel", () ->
            new YourIdeasEmeraldShovel(YourIdeasItemTier.EMERALD_BASE_NETHERITE, 1.5F, -3.0F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
    public static final RegistryObject<Item> EMERALD_BASE_NETHERITE_HOE = EMERALD_GEARS.register("emerald_base_netherite_hoe", () ->
            new YourIdeasEmeraldHoe(YourIdeasItemTier.EMERALD_BASE_NETHERITE, -4, 0.0F,
                    new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)
            )
    );
}
