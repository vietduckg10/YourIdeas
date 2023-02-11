package com.ducvn.yourideas.block;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemGroup;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class YourIdeasBlocksRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        if (YourIdeasConfig.throwable_slimeball.get()){
            BLOCKS.register(bus);
        }
    }
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> returnBlock = BLOCKS.register(name, block);
        registerBlockItem(name, returnBlock);
        return returnBlock;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        YourIdeasItemsRegister.ITEMS.register(name, () ->
                new BlockItem(block.get(),
                        new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)));
    }

    public static final RegistryObject<Block> SLIME_SPLASH_BLOCK = BLOCKS.register("slime_splash_block", () ->
            new SlimeSplashBlock(AbstractBlock.Properties.copy(Blocks.SLIME_BLOCK)
                    .noCollission()
                    .noOcclusion()
                    .noDrops()
                    .randomTicks()
                    .instabreak())
    );

//    public static final RegistryObject<Block> IRON_INGOT_BLOCK = BLOCKS.register("iron_ingot_block", () ->
//            new IronIngotBlock(AbstractBlock.Properties.copy(Blocks.IRON_BLOCK)
//                    .noOcclusion())
//    );

}