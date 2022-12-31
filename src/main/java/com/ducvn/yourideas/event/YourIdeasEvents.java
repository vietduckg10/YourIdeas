package com.ducvn.yourideas.event;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.entity.brick.ThrowableBrickEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YourIdeasMod.MODID)
public class YourIdeasEvents {

    @SubscribeEvent
    public static void ThrowBrickEvent(PlayerInteractEvent.RightClickItem event){
        World world = event.getEntity().level;
        if (!world.isClientSide){
            PlayerEntity player = event.getPlayer();
            if ((Items.BRICK == player.getOffhandItem().getItem()) || (Items.BRICK == player.getMainHandItem().getItem())){
                Hand brickHand;
                ThrowableBrickEntity brickEntity = new ThrowableBrickEntity(player, world);
                Vector3d playerLookAngle = player.getLookAngle();
                brickEntity.setDeltaMovement(playerLookAngle.x * 1.5D, playerLookAngle.y * 1.5D, playerLookAngle.z * 1.5D);
                if (Items.BRICK == player.getOffhandItem().getItem()){
                    brickHand = Hand.OFF_HAND;
                }
                else {
                    brickHand = Hand.MAIN_HAND;
                }
                if (!player.isCreative()){
                    player.getItemInHand(brickHand).shrink(1);
                }
                player.swing(brickHand, true);
                world.addFreshEntity(brickEntity);
            }
        }
    }
}
