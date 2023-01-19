package com.ducvn.yourideas.block;

import com.ducvn.yourideas.YourIdeasMod;
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
    public static final DeferredRegister<Block> STAIR_SLAB_WALL_FENCE = DeferredRegister.create(ForgeRegistries.BLOCKS, YourIdeasMod.MODID);
    public static void init(IEventBus bus){
        STAIR_SLAB_WALL_FENCE.register(bus);
    }
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> returnBlock = STAIR_SLAB_WALL_FENCE.register(name, block);
        registerBlockItem(name, returnBlock);
        return returnBlock;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        YourIdeasItemsRegister.ITEMS.register(name, () ->
                new BlockItem(block.get(),
                        new Item.Properties().tab(YourIdeasItemGroup.YOUR_IDEAS_ITEM_GROUP)));
    }

    public static final RegistryObject<Block> GRASS_SLAB = registerBlock("grass_slab", () ->
            new SlabBlock(AbstractBlock.Properties.copy(Blocks.GRASS_BLOCK))
    );
    public static final RegistryObject<Block> GRASS_STAIRS = registerBlock("grass_stairs", () ->
            new StairsBlock(() -> Blocks.GRASS_BLOCK.defaultBlockState(),
                    AbstractBlock.Properties.copy(Blocks.GRASS_BLOCK))
    );
}