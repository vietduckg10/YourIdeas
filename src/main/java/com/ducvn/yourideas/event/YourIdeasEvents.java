package com.ducvn.yourideas.event;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.entity.brick.ThrowableBrickEntity;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.ducvn.yourideas.potion.YourIdeasPotionsRegister;
import net.minecraft.block.*;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = YourIdeasMod.MODID)
public class YourIdeasEvents {

    @SubscribeEvent
    public static void ThrowBrickEvent(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide && YourIdeasConfig.throwable_brick.get()){
            PlayerEntity player = event.getPlayer();
            if ((Items.BRICK == player.getOffhandItem().getItem()) || (Items.BRICK == player.getMainHandItem().getItem())
                    || (Items.NETHER_BRICK == player.getOffhandItem().getItem()) || (Items.NETHER_BRICK == player.getMainHandItem().getItem())){
                Hand brickHand;
                ThrowableBrickEntity brickEntity = new ThrowableBrickEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                brickEntity.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
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
                else {
                    brickEntity.isNormalBrick();
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
        if (!world.isClientSide && YourIdeasConfig.plantable_poisonous_potato.get()){
            PlayerEntity player = event.getPlayer();
            if (Items.POISONOUS_POTATO == player.getItemInHand(event.getHand()).getItem()){
                BlockPos farmlandPos = event.getHitVec().getBlockPos();
                Block clickBlock = world.getBlockState(farmlandPos).getBlock();
                if (clickBlock instanceof FarmlandBlock && world.getBlockState(farmlandPos.above()).getBlock() instanceof AirBlock){
                    Hand potatoHand = event.getHand();
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
    public static void MixingPotions(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide && YourIdeasConfig.mixing_potions.get()){
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
                    stack.getOrCreateTag().putInt("CustomPotionColor", PotionUtils.getColor(potion1) + PotionUtils.getColor(potion2));
                    player.inventory.add(stack);
                    player.getItemInHand(Hand.MAIN_HAND).shrink(1);
                    player.getItemInHand(Hand.OFF_HAND).shrink(1);
                    player.giveExperiencePoints(-3);
                    player.level.playSound(null, player.blockPosition().above(), SoundEvents.BREWING_STAND_BREW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void ThrowTNTEvent(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide && YourIdeasConfig.throw_tnt_from_hand.get()){
            PlayerEntity player = event.getPlayer();
            if ((Items.TNT == player.getOffhandItem().getItem()) && (Items.FLINT_AND_STEEL == player.getMainHandItem().getItem())
                    || (Items.FLINT_AND_STEEL == player.getOffhandItem().getItem()) && (Items.TNT == player.getMainHandItem().getItem())){
                Hand tntHand;
                Hand flintAndSteelHand;
                TNTEntity tntEntity = new TNTEntity(world,player.getX(),player.getY() + 1D, player.getZ(), player);
                Vector3d playerLookAngle = player.getLookAngle();
                if (!player.isCrouching()){
                    tntEntity.setDeltaMovement(playerLookAngle.x, playerLookAngle.y , playerLookAngle.z);
                }
                if (Items.TNT == player.getOffhandItem().getItem()){
                    tntHand = Hand.OFF_HAND;
                    flintAndSteelHand = Hand.MAIN_HAND;
                }
                else {
                    tntHand = Hand.MAIN_HAND;
                    flintAndSteelHand = Hand.OFF_HAND;
                }
                if (!player.isCreative()){
                    player.getItemInHand(tntHand).shrink(1);
                    player.getItemInHand(flintAndSteelHand).setDamageValue(player.getItemInHand(flintAndSteelHand).getDamageValue() + 1);
                }
                player.swing(tntHand, true);
                world.addFreshEntity(tntEntity);
                player.getCooldowns().addCooldown(player.getItemInHand(tntHand).getItem(), 10);
                player.getCooldowns().addCooldown(player.getItemInHand(flintAndSteelHand).getItem(), 10);
            }
        }
    }

    @SubscribeEvent
    public static void GetLevitatePotion(PlayerInteractEvent.EntityInteract event){
        World world = event.getEntity().level;
        if (!world.isClientSide && YourIdeasConfig.levitation_potion.get()){
            PlayerEntity player = event.getPlayer();
            if (Items.GLASS_BOTTLE == player.getItemInHand(event.getHand()).getItem() && event.getTarget() instanceof ShulkerBulletEntity){
                Hand bottleHand = event.getHand();
                System.out.println(bottleHand);
                if (!player.isCreative()){
                    player.getItemInHand(bottleHand).shrink(1);
                }
                ItemStack stack = new ItemStack(Items.POTION);
                PotionUtils.setPotion(stack, YourIdeasPotionsRegister.LEVITATION_POTION.get());
                player.inventory.add(stack);
                event.getTarget().remove();
                player.level.playSound(null, player.blockPosition().above(), SoundEvents.BREWING_STAND_BREW, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent
    public static void ShootDragonChargeEvent(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide && YourIdeasConfig.dragon_charge.get()){
            PlayerEntity player = event.getPlayer();
            if ((Items.DRAGON_HEAD == player.getOffhandItem().getItem()) || (Items.DRAGON_HEAD == player.getMainHandItem().getItem())){
                int slot = player.inventory.findSlotMatchingItem(YourIdeasItemsRegister.DRAGON_CHARGE.get().getDefaultInstance());
                if (slot >= 0){
                    Hand dragonHand;
                    Vector3d playerLookAngle = player.getLookAngle();
                    DragonFireballEntity dragonFireball = new DragonFireballEntity(world,
                            player, playerLookAngle.x, playerLookAngle.y, playerLookAngle.z);
                    Vector3d dragonFireballPos = dragonFireball.position();
                    dragonFireball.setPos(dragonFireballPos.x, dragonFireballPos.y + 1D, dragonFireballPos.z);
                    if (Items.DRAGON_HEAD == player.getOffhandItem().getItem()){
                        dragonHand = Hand.OFF_HAND;
                    }
                    else {
                        dragonHand = Hand.MAIN_HAND;
                    }
                    if (!player.isCreative()){
                        ItemStack stack = player.inventory.getItem(slot);
                        stack.setCount(stack.getCount() -1);
                        System.out.println(slot);
                        player.inventory.setItem(slot, stack);
                    }
                    player.swing(dragonHand, true);
                    world.addFreshEntity(dragonFireball);
                    player.getCooldowns().addCooldown(player.getItemInHand(dragonHand).getItem(), 20);
                }
            }
        }
    }
}
