package com.ducvn.yourideas.entity.brick;

import com.ducvn.yourideas.entity.YourIdeasEntitiesRegister;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class ThrowableBrickEntity extends ProjectileItemEntity {
    public ThrowableBrickEntity(EntityType<? extends ProjectileItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrowableBrickEntity(double x, double y, double z, World world) {
        super(YourIdeasEntitiesRegister.THROWABLE_BRICK.get(), x, y, z, world);
    }

    public ThrowableBrickEntity(LivingEntity entity, World world) {
        super(YourIdeasEntitiesRegister.THROWABLE_BRICK.get(), entity, world);
    }

    private static boolean isNetherBrick = false;

    public ThrowableBrickEntity isNormalBrick(){
        this.isNetherBrick = false;
        return this;
    }

    public ThrowableBrickEntity isNetherBrick(){
        this.isNetherBrick = true;
        return this;
    }

    @Override
    protected Item getDefaultItem() {
        return isNetherBrick ? Items.NETHER_BRICK.asItem() : Items.BRICK.asItem();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult traceResult) {
        if (!level.isClientSide){
            if (traceResult.getEntity() instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity) traceResult.getEntity();
                DamageSource damageSource = DamageSource.playerAttack((PlayerEntity) this.getOwner());
                livingEntity.hurt(damageSource, 2.0F);
                Random effectChance = new Random();
                if (effectChance.nextInt(100) < 10){
                    if (isNetherBrick){
                        livingEntity.setSecondsOnFire(5);
                    }
                    else {
                        livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 200, 1));
                    }
                }
                this.remove();
            }
            if (traceResult.getEntity() instanceof EnderCrystalEntity){
                EnderCrystalEntity enderCrystalEntity = (EnderCrystalEntity) traceResult.getEntity();
                DamageSource damageSource = DamageSource.playerAttack((PlayerEntity) this.getOwner());
                enderCrystalEntity.hurt(damageSource, 2);
                this.remove();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult traceResult) {
        if (!level.isClientSide){
            if (RayTraceResult.Type.BLOCK == traceResult.getType()){
                Block hitBlock = level.getBlockState(traceResult.getBlockPos()).getBlock();
                if (hitBlock.getTags().contains(ResourceLocation.tryParse("forge:glass"))
                || hitBlock.getTags().contains(ResourceLocation.tryParse("forge:glass_panes"))){
                    level.destroyBlock(traceResult.getBlockPos(), true);
                }
                else {
                    Random roll = new Random();
                    if (roll.nextInt(100) < 1){
                        ThrowableBrickEntity brickEntity = new ThrowableBrickEntity((LivingEntity) this.getOwner(), this.level);
                        brickEntity.setPos(this.getX(), this.getY(), this.getZ());
                        switch (traceResult.getDirection()){
                            case EAST:
                            case WEST:
                                brickEntity.setDeltaMovement(this.getDeltaMovement().reverse().x * 0.5D, -0.1D, this.getDeltaMovement().z * 0.5D);
                                break;
                            case NORTH:
                            case SOUTH:
                                brickEntity.setDeltaMovement(this.getDeltaMovement().x * 0.5D, -0.1D, this.getDeltaMovement().reverse().z * 0.5D);
                                break;
                            case UP:
                            case DOWN:
                                brickEntity.setDeltaMovement(this.getDeltaMovement().x * 0.5D, this.getDeltaMovement().reverse().y * 0.5D, this.getDeltaMovement().z * 0.5D);
                                break;
                        }
                        if ((Math.abs(this.getDeltaMovement().x) > 0.001D) && (Math.abs(this.getDeltaMovement().z) > 0.001D)){
                            brickEntity.setOwner(this.getOwner());
                            this.level.addFreshEntity(brickEntity);
                        }
                        this.remove();
                    }
                    else {
                        this.remove();
                    }
                }
            }
        }
    }
}
