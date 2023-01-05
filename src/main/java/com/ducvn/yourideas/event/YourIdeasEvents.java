package com.ducvn.yourideas.event;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.entity.brick.ThrowableBrickEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = YourIdeasMod.MODID)
public class YourIdeasEvents {

    @SubscribeEvent
    public static void ThrowBrickEvent(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide){
            PlayerEntity player = event.getPlayer();
            if ((Items.BRICK == player.getOffhandItem().getItem()) || (Items.BRICK == player.getMainHandItem().getItem())
                    || (Items.NETHER_BRICK == player.getOffhandItem().getItem()) || (Items.NETHER_BRICK == player.getMainHandItem().getItem())){
                Hand brickHand;
                ThrowableBrickEntity brickEntity = new ThrowableBrickEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                brickEntity.setDeltaMovement(playerLookAngle.x * 1.5D, playerLookAngle.y * 1.5D, playerLookAngle.z * 1.5D);
                if (Items.BRICK == player.getOffhandItem().getItem()
                || Items.NETHER_BRICK == player.getOffhandItem().getItem()){
                    brickHand = Hand.OFF_HAND;
                }
                else {
                    brickHand = Hand.MAIN_HAND;
                }
                if (Items.NETHER_BRICK == player.getItemInHand(brickHand).getItem()){
                    brickEntity.isNetherBrick();
                }
                if (!player.isCreative()){
                    player.getItemInHand(brickHand).shrink(1);
                }
                player.swing(brickHand, true);
                world.addFreshEntity(brickEntity);
            }
        }
    }

    @SubscribeEvent
    public static void PlantPoisonousPotato(PlayerInteractEvent.RightClickBlock event){
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        if (!world.isClientSide){
            if ((Items.POISONOUS_POTATO == player.getOffhandItem().getItem()) || (Items.POISONOUS_POTATO == player.getMainHandItem().getItem())){
                BlockPos farmlandPos = event.getHitVec().getBlockPos();
                Block clickBlock = world.getBlockState(farmlandPos).getBlock();
                if (clickBlock instanceof FarmlandBlock && world.getBlockState(farmlandPos.above()).getBlock() instanceof AirBlock){
                    Hand potatoHand;
                    if (Items.POISONOUS_POTATO == player.getOffhandItem().getItem()){
                        potatoHand = Hand.OFF_HAND;
                    }
                    else {
                        potatoHand = Hand.MAIN_HAND;
                    }
                    PotatoBlock potatoBlock = (PotatoBlock) Blocks.POTATOES;
                    world.setBlock(farmlandPos.above(), potatoBlock.getStateForAge(3), 3);
                    if (!player.isCreative()){
                        player.getItemInHand(potatoHand).shrink(1);
                    }
                    world.playSound(null, farmlandPos.above(), SoundEvents.CROP_PLANTED, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    player.swing(potatoHand, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void CombinePotions(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide){
            PlayerEntity player = event.getPlayer();
            if ((Items.POTION == player.getOffhandItem().getItem()) && (Items.POTION == player.getMainHandItem().getItem())){
                CreateCustomPotion(player, Items.POTION);
            }
            if ((Items.SPLASH_POTION == player.getOffhandItem().getItem()) && (Items.SPLASH_POTION == player.getMainHandItem().getItem())){
                CreateCustomPotion(player, Items.SPLASH_POTION);
            }
            if ((Items.LINGERING_POTION == player.getOffhandItem().getItem()) && (Items.LINGERING_POTION == player.getMainHandItem().getItem())){
                CreateCustomPotion(player, Items.LINGERING_POTION);
            }
        }
    }

    private static void CreateCustomPotion(PlayerEntity player, Item potionType){
        ItemStack potion1 = player.getMainHandItem();
        ItemStack potion2 = player.getOffhandItem();
        List<EffectInstance> potion1Effects = PotionUtils.getAllEffects(potion1.getTag());
        List<EffectInstance> potion2Effects = PotionUtils.getAllEffects(potion2.getTag());
        boolean hasDuplicate = false;
        for (EffectInstance effect1 : potion1Effects){
            for (EffectInstance effect2 : potion2Effects){
                if (effect2.getEffect() == effect1.getEffect()){
                    hasDuplicate = true;
                }
            }
        }
        if (hasDuplicate){
            player.displayClientMessage(new TranslationTextComponent("Your potions have duplicate effect").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.LIGHT_PURPLE),true);
        }
        else {
            if (potion1Effects.size() + potion2Effects.size() > 3){
                player.displayClientMessage(new TranslationTextComponent("Mixed potion can only have 3 effects maximum").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.LIGHT_PURPLE),true);
            }
            else {
                if (player.totalExperience < 3){
                    player.displayClientMessage(new TranslationTextComponent("You don't have enough exp").withStyle(TextFormatting.BOLD).withStyle(TextFormatting.LIGHT_PURPLE),true);
                }
                else {
                    List<EffectInstance> mixedPotionEffects = Stream.concat(potion1Effects.stream(), potion2Effects.stream()).collect(Collectors.toList());
                    ItemStack stack = new ItemStack(potionType);
                    stack.setHoverName(new TranslationTextComponent("Mixed potion").withStyle(TextFormatting.AQUA));
                    PotionUtils.setCustomEffects(stack, mixedPotionEffects);
                    player.inventory.add(stack);
                    player.getItemInHand(Hand.MAIN_HAND).shrink(1);
                    player.getItemInHand(Hand.OFF_HAND).shrink(1);
                    player.giveExperiencePoints(-3);
                    player.level.playSound(null, player.blockPosition().above(), SoundEvents.BREWING_STAND_BREW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }
}
