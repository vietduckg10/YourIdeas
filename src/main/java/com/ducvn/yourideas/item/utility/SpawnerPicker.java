package com.ducvn.yourideas.item.utility;

import com.ducvn.yourideas.config.YourIdeasConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.List;

public class SpawnerPicker extends Item {
    public SpawnerPicker(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        super.appendHoverText(stack, world, tooltip, p_77624_4_);
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.get("hasSpawner") != null){
            tooltip.add(new TranslationTextComponent(
                            "\u00A7bMob spawner: \u00A7r" + "\u00A75"
                                    + WordUtils.capitalize(nbt.getString("entity").replace("minecraft:", ""))
                                    +  "\u00A7r"
                    )
            );
        }
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getLevel();
        if (!world.isClientSide && YourIdeasConfig.spawner_picker.get()){
            BlockState state = world.getBlockState(context.getClickedPos());
            BlockPos clickedPos = context.getClickedPos();
            CompoundNBT nbt = stack.getOrCreateTag();
            if (state.getBlock() == Blocks.SPAWNER && nbt.get("hasSpawner") == null){
                nbt.putBoolean("hasSpawner", true);
                nbt.putString("entity",
                        ((MobSpawnerTileEntity)world.getBlockEntity(clickedPos))
                                .getUpdateTag().getCompound("SpawnData").getString("id"));
                world.setBlockAndUpdate(clickedPos, Blocks.AIR.defaultBlockState());
                return ActionResultType.CONSUME;
            }
            else {
                if (nbt.get("hasSpawner") != null
                        && !world.getBlockState(context.getClickedPos().relative(context.getClickedFace())).getMaterial().isSolid()){
                    world.setBlock(context.getClickedPos().relative(context.getClickedFace()), Blocks.SPAWNER.defaultBlockState(), 3);
                    BlockState spawnerState = world.getBlockState(context.getClickedPos().relative(context.getClickedFace()));
                    TileEntity tileentity = world.getBlockEntity(context.getClickedPos().relative(context.getClickedFace()));
                    AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawner();
                    EntityType<?> entitytype = EntityType.byString(nbt.getString("entity")).get();
                    abstractspawner.setEntityId(entitytype);
                    tileentity.setChanged();
                    world.sendBlockUpdated(context.getClickedPos().above(), spawnerState, spawnerState, 3);
                    if (!context.getPlayer().isCreative()){
                        stack.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.get("hasSpawner") == null || !nbt.getBoolean("hasSpawner")){
            return false;
        }
        else {
            return true;
        }
    }
}
