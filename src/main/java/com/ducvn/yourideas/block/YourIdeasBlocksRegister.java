package com.ducvn.yourideas.block;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemGroup;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.ducvn.yourideas.item.armor.SunglassesItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class YourIdeasBlocksRegister {
    public static final DeferredRegister<Block> SLIME_BALL_BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, YourIdeasMod.MODID);
    public static final DeferredRegister<Block> SUNGLASSES = DeferredRegister.create(ForgeRegistries.BLOCKS, YourIdeasMod.MODID);
    public static final DeferredRegister<Block> BREEDER = DeferredRegister.create(ForgeRegistries.BLOCKS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        if (YourIdeasConfig.throwable_slimeball.get()){
            SLIME_BALL_BLOCK.register(bus);
        }
        if (YourIdeasConfig.sunglasses.get()){
            SUNGLASSES.register(bus);
        }
        if (YourIdeasConfig.animal_breeder.get()){
            BREEDER.register(bus);
        }
    }
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        if (name.equals("sunglasses") && YourIdeasConfig.sunglasses.get()){
            RegistryObject<T> returnBlock = SUNGLASSES.register(name, block);
            registerBlockItem(name, returnBlock);
            return returnBlock;
        }
        else {
            if (name.equals("animal_breeder") && YourIdeasConfig.animal_breeder.get()){
                RegistryObject<T> returnBlock = BREEDER.register(name, block);
                registerBlockItem(name, returnBlock);
                return returnBlock;
            }
            else {
                return null;
            }
        }
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        if (name.equals("sunglasses")){
            if (YourIdeasConfig.sunglasses.get()){
                YourIdeasItemsRegister.ITEMS.register(name, () ->
                        new SunglassesItem(block.get(),
                                new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP).stacksTo(1)));
            }
        }
        else {
            YourIdeasItemsRegister.ITEMS.register(name, () ->
                    new BlockItem(block.get(),
                            new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)));
        }
    }

    public static final RegistryObject<Block> SLIME_SPLASH_BLOCK = SLIME_BALL_BLOCK.register("slime_splash_block", () ->
            new SlimeSplashBlock(AbstractBlock.Properties.copy(Blocks.SLIME_BLOCK)
                    .noCollission()
                    .noOcclusion()
                    .noDrops()
                    .randomTicks()
                    .instabreak())
    );

    public static final RegistryObject<Block> SUNGLASSES_BLOCK = registerBlock("sunglasses", () ->
            new SunglassesBlock(AbstractBlock.Properties.of(Material.GLASS)
                    .noDrops())
    );

    public static final RegistryObject<Block> BREEDER_BLOCK = registerBlock("animal_breeder", () ->
            new AnimalBreederBlock(AbstractBlock.Properties.of(Material.GLASS)
                    .noOcclusion()
                    .randomTicks())
    );
}