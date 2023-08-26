package com.ducvn.yourideas.event;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.entity.brick.ThrowableBrickEntity;
import com.ducvn.yourideas.entity.slimeball.ThrowableSlimeBallEntity;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.ducvn.yourideas.potion.YourIdeasPotionsRegister;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = YourIdeasMod.MODID)
public class YourIdeasEvents {

    private static List<String> playerList = new ArrayList<>();
    private static List<Integer> totemUsedList = new ArrayList<>();
    private static List<Integer> campfireTick = new ArrayList<>();
    private static List<ResourceLocation> leatherBootBiomes = new ArrayList<>(
            Arrays.asList(
                    Biomes.SWAMP.location(), Biomes.SWAMP_HILLS.location(), Biomes.DESERT.location(),
                    Biomes.DESERT_HILLS.location(), Biomes.BEACH.location(), Biomes.TAIGA.location(),
                    Biomes.MOUNTAINS.location(), Biomes.MOUNTAIN_EDGE.location(), Biomes.GRAVELLY_MOUNTAINS.location(),
                    Biomes.SNOWY_TAIGA_MOUNTAINS.location(), Biomes.SNOWY_MOUNTAINS.location(), Biomes.TAIGA_MOUNTAINS.location(),
                    Biomes.WOODED_MOUNTAINS.location(), Biomes.MODIFIED_GRAVELLY_MOUNTAINS.location(), Biomes.WOODED_HILLS.location(),
                    Biomes.TAIGA_HILLS.location(), Biomes.JUNGLE_HILLS.location(), Biomes.BIRCH_FOREST_HILLS.location(),
                    Biomes.SNOWY_TAIGA_HILLS.location(), Biomes.GIANT_TREE_TAIGA_HILLS.location(), Biomes.TALL_BIRCH_HILLS.location(),
                    Biomes.DARK_FOREST_HILLS.location(), Biomes.GIANT_SPRUCE_TAIGA_HILLS.location(), Biomes.BAMBOO_JUNGLE_HILLS.location(),
                    Biomes.BADLANDS.location(), Biomes.WOODED_BADLANDS_PLATEAU.location(), Biomes.BADLANDS_PLATEAU.location(),
                    Biomes.ERODED_BADLANDS.location(), Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU.location(), Biomes.MODIFIED_BADLANDS_PLATEAU.location()
            )
    );

    @SubscribeEvent
    public static void setPlayerDataOnJoin(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        World world = event.getWorld();
        if (!world.isClientSide){
            if (entity instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) entity;
                if (!playerList.contains(player.getScoreboardName())){
                    playerList.add(player.getScoreboardName());
                    int timeUseTotem = ((ServerPlayerEntity) player).getStats().getValue(Stats.ITEM_USED, Items.TOTEM_OF_UNDYING);
                    totemUsedList.add(timeUseTotem);
                    campfireTick.add(0);
                }
                checkPlayerTag(player);
            }
        }
    }

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
                    if (Items.POTION == potionType){
                        player.inventory.add(new ItemStack(Items.GLASS_BOTTLE, 1));
                    }
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
        if (!world.isClientSide && (YourIdeasConfig.dragon_charge.get() || YourIdeasConfig.shoot_fire_charge.get())){
            PlayerEntity player = event.getPlayer();
            if ((Items.DRAGON_HEAD == player.getOffhandItem().getItem()) || (Items.DRAGON_HEAD == player.getMainHandItem().getItem())){
                int slot = player.inventory.findSlotMatchingItem(YourIdeasItemsRegister.DRAGON_CHARGE.get().getDefaultInstance());
                if (slot >= 0 && YourIdeasConfig.dragon_charge.get()){
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
                        stack.shrink(1);
                        player.inventory.setItem(slot, stack);
                    }
                    world.addFreshEntity(dragonFireball);
                    player.getCooldowns().addCooldown(player.getItemInHand(dragonHand).getItem(), 20);
                }
                else {
                    slot = player.inventory.findSlotMatchingItem(Items.FIRE_CHARGE.getDefaultInstance());
                    if (slot >= 0 && YourIdeasConfig.shoot_fire_charge.get()){
                        Hand dragonHand;
                        Vector3d playerLookAngle = player.getLookAngle();
                        FireballEntity fireballEntity = new FireballEntity(world,
                                player, playerLookAngle.x, playerLookAngle.y, playerLookAngle.z);
                        fireballEntity.explosionPower = 3;
                        Vector3d fireballPos = fireballEntity.position();
                        fireballEntity.setPos(fireballPos.x, fireballPos.y + 1D, fireballPos.z);
                        if (Items.DRAGON_HEAD == player.getOffhandItem().getItem()){
                            dragonHand = Hand.OFF_HAND;
                        }
                        else {
                            dragonHand = Hand.MAIN_HAND;
                        }
                        if (!player.isCreative()){
                            ItemStack stack = player.inventory.getItem(slot);
                            stack.shrink(1);
                            player.inventory.setItem(slot, stack);
                        }
                        world.addFreshEntity(fireballEntity);
                        player.getCooldowns().addCooldown(player.getItemInHand(dragonHand).getItem(), 20);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void AutoReplaceTotemEvent(TickEvent.PlayerTickEvent event){
        World world = event.player.level;
        if (!world.isClientSide && YourIdeasConfig.auto_replace_totem.get()
                && playerList.indexOf(event.player.getScoreboardName()) >= 0
                && totemUsedList.get(playerList.indexOf(event.player.getScoreboardName())) >= 0){
            PlayerEntity player = event.player;
            int playerIndex = playerList.indexOf(player.getScoreboardName());
            int timeUseTotem = ((ServerPlayerEntity) player).getStats().getValue(Stats.ITEM_USED, Items.TOTEM_OF_UNDYING);
            if (timeUseTotem > totemUsedList.get(playerIndex)){
                ReplaceTotem(player);
                totemUsedList.set(playerIndex, timeUseTotem);
            }
        }
    }

    private static void ReplaceTotem(PlayerEntity player){
        int slot = player.inventory.findSlotMatchingItem(Items.TOTEM_OF_UNDYING.getDefaultInstance());
        if (player.getMainHandItem().getItem() != Items.TOTEM_OF_UNDYING
        && player.getOffhandItem().getItem() != Items.TOTEM_OF_UNDYING
        && slot >= 0){
            ItemStack stack = player.inventory.getItem(slot);
            if (player.getMainHandItem().getItem() == Items.AIR
                    || player.getOffhandItem().getItem() == Items.AIR){
                Hand emptyHand;
                if (player.getMainHandItem().getItem() == Items.AIR){
                    emptyHand = Hand.OFF_HAND;
                }
                else {
                    emptyHand = Hand.MAIN_HAND;
                }
                player.inventory.removeItem(stack);
                player.setItemInHand(emptyHand, stack);
            }
        }
    }

    @SubscribeEvent
    public static void CampfireHeal(TickEvent.PlayerTickEvent event){
        World world = event.player.level;
        if (!world.isClientSide && YourIdeasConfig.campfire_heal.get()){
            PlayerEntity player = event.player;
            NearCampfire(player.blockPosition(), world, player);
        }
    }

    private static void NearCampfire(BlockPos playerPos, World world, PlayerEntity player){
        AxisAlignedBB checkArea = new AxisAlignedBB(playerPos.getX() - 4D, playerPos.getY() - 1D, playerPos.getZ() - 4D,
                playerPos.getX() + 4D, playerPos.getY() + 1D, playerPos.getZ() + 4D);
        List<BlockState> blocksInArea = world.getBlockStates(checkArea).collect(Collectors.toList());
        int stateIndex = 0;
        boolean isNearCampfire = false;
        for (BlockState state : blocksInArea){
            if (state.getBlock() instanceof CampfireBlock && state.getValue(CampfireBlock.LIT)){
                stateIndex = blocksInArea.indexOf(state);
                isNearCampfire = true;
                break;
            }
        }
        int campfireIndex = playerList.indexOf(player.getScoreboardName());
        int campfireValue = campfireTick.get(campfireIndex);
        if (isNearCampfire){
            campfireTick.set(campfireIndex, campfireValue + 1);
            if ((campfireTick.get(campfireIndex) != 0) && (campfireTick.get(campfireIndex) % 200 == 0)){
                player.heal(1F);
                Random roll = new Random();
                if (roll.nextInt(100) < 15){
                    if (blocksInArea.get(stateIndex).getBlock() == Blocks.CAMPFIRE
                    && !player.hasEffect(Effects.DAMAGE_RESISTANCE)){
                        player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 300, 0));
                    }
                    else {
                        if (blocksInArea.get(stateIndex).getBlock() == Blocks.SOUL_CAMPFIRE
                                && !player.hasEffect(Effects.FIRE_RESISTANCE)){
                            player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0));
                        }
                    }
                }
                campfireTick.set(campfireIndex, 0);
            }
        }
        if (!isNearCampfire){
            campfireTick.set(campfireIndex, 0);
        }
    }

    @SubscribeEvent
    public static void ThrowSlimeBallEvent(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide && YourIdeasConfig.throwable_slimeball.get()){
            PlayerEntity player = event.getPlayer();
            if ((Items.SLIME_BALL == player.getOffhandItem().getItem()) || (Items.SLIME_BALL == player.getMainHandItem().getItem())){
                Hand slimeHand;
                ThrowableSlimeBallEntity slimeBallEntity = new ThrowableSlimeBallEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                slimeBallEntity.setDeltaMovement(playerLookAngle.x * 1.2D, playerLookAngle.y * 1.2D, playerLookAngle.z * 1.2D);
                if (Items.SLIME_BALL == player.getOffhandItem().getItem()){
                    slimeHand = Hand.OFF_HAND;
                }
                else {
                    slimeHand = Hand.MAIN_HAND;
                }
                if (!player.isCreative()){
                    player.getItemInHand(slimeHand).shrink(1);
                }
                player.swing(slimeHand, true);
                world.addFreshEntity(slimeBallEntity);
            }
        }
    }

    @SubscribeEvent
    public static void LeatherBootsIncreaseSpeed(TickEvent.PlayerTickEvent event){
        World world = event.player.level;
        if (!world.isClientSide && YourIdeasConfig.leather_boots_speed.get()){
            PlayerEntity player = event.player;
            if (player.getItemBySlot(EquipmentSlotType.FEET).getItem() == Items.LEATHER_BOOTS
            && leatherBootBiomes.contains(world.getBiome(player.blockPosition()).getRegistryName())){
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED));
            }
        }
    }

    @SubscribeEvent
    public static void RegenerateDragonEgg(EntityLeaveWorldEvent event){
        World world = event.getEntity().level;
        if (!world.isClientSide && event.getEntity() instanceof EnderDragonEntity && YourIdeasConfig.regenerate_dragon_egg.get()){
            ServerWorld serverWorld = (ServerWorld) world;
            CompoundNBT nbt = serverWorld.dragonFight().saveData();
            if (nbt.getBoolean("PreviouslyKilled")){
                world.setBlockAndUpdate(world.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING,
                        EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.defaultBlockState());
            }
        }
    }

    private static void checkPlayerTag(PlayerEntity player){
        if (player.getPersistentData().get(YourIdeasMod.MODID + "negative_sight") == null
                || player.getPersistentData().get(YourIdeasMod.MODID + "division_sight") == null
                || player.getPersistentData().get(YourIdeasMod.MODID + "verdant_sight") == null) {
            player.getPersistentData().putBoolean(YourIdeasMod.MODID + "negative_sight", false);
            player.getPersistentData().putBoolean(YourIdeasMod.MODID + "division_sight", false);
            player.getPersistentData().putBoolean(YourIdeasMod.MODID + "verdant_sight", false);
        }
    }

    @SubscribeEvent
    public static void TurnObsidiantToCryingObsidiant(EntityJoinWorldEvent event){
        if (!event.getWorld().isClientSide && YourIdeasConfig.lightning_convert_obsidian.get()
                && event.getEntity() instanceof LightningBoltEntity){
            World world = event.getWorld();
            BlockPos lightningPos = event.getEntity().blockPosition();
            for (int i = -2; i <= 2; i++){
                for (int j = -1; j <= 3; j++){
                    for (int k = -2; k <= 2; k++){
                        BlockPos checkPos = new BlockPos(
                                lightningPos.getX() + i ,
                                lightningPos.getY() + j,
                                lightningPos.getZ() + k);
                        if (world.getBlockState(checkPos).getBlock() == Blocks.OBSIDIAN){
                            world.setBlock(checkPos, Blocks.CRYING_OBSIDIAN.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    public static void TestingEvent(PlayerInteractEvent.RightClickBlock event){
//        if (!event.getWorld().isClientSide && event.getPlayer().isShiftKeyDown()){
//            LightningBoltEntity lightningBolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, event.getWorld());
//            lightningBolt.setPos(
//                    event.getHitVec().getBlockPos().getX(),
//                    event.getHitVec().getBlockPos().getY(),
//                    event.getHitVec().getBlockPos().getZ());
//            System.out.println(event.getHitVec().getBlockPos());
//            event.getWorld().addFreshEntity(lightningBolt);
//        }
//    }
}