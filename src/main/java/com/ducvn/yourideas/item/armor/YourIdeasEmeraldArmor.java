package com.ducvn.yourideas.item.armor;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.ducvn.yourideas.item.tools.emerald.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class YourIdeasEmeraldArmor extends ArmorItem {
    public YourIdeasEmeraldArmor(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (this.material.getName().equals("yourideas:emerald_base_netherite")){
            tooltip.add(new TranslationTextComponent(
                            "\u00A7aEmerald\u00A7r"
                    )
            );
        }
        tooltip.add(new TranslationTextComponent(
                        "\u00A7b-Full set bonus-\u00A7r"
                )
        );
        tooltip.add(new TranslationTextComponent(
                        "\u00A7aGain Luck 1 (Luck 2 if main hand have emerald tool)\u00A7r"
                )
        );
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if(!world.isClientSide() && YourIdeasConfig.emerald_gears.get()) {
            if(hasFullSetArmor(player)) {
                EffectInstance effectInstance = new EffectInstance(Effects.LUCK);
                if (player.getMainHandItem().getItem() instanceof YourIdeasEmeraldSword
                        || player.getMainHandItem().getItem() instanceof YourIdeasEmeraldAxe
                        || player.getMainHandItem().getItem() instanceof YourIdeasEmeraldPickaxe
                        || player.getMainHandItem().getItem() instanceof YourIdeasEmeraldShovel
                        || player.getMainHandItem().getItem() instanceof YourIdeasEmeraldHoe){
                    effectInstance = new EffectInstance(Effects.LUCK, 0, 1);
                }
                if (YourIdeasConfig.spear_of_siphon.get()
                        && player.getMainHandItem().getItem() == YourIdeasItemsRegister.EMERALD_SPEAR_OF_SIPHON.get())
                {
                    effectInstance = new EffectInstance(Effects.LUCK, 0, 1);
                }
                player.addEffect(effectInstance);
            }
        }
        super.onArmorTick(stack, world, player);
    }

    private boolean hasFullSetArmor(PlayerEntity player) {
        ItemStack boots = player.inventory.getArmor(0);
        ItemStack leggings = player.inventory.getArmor(1);
        ItemStack chestplate = player.inventory.getArmor(2);
        ItemStack helmet = player.inventory.getArmor(3);

        if (helmet.getItem() instanceof YourIdeasEmeraldArmor
                && chestplate.getItem() instanceof YourIdeasEmeraldArmor
                && leggings.getItem() instanceof YourIdeasEmeraldArmor
                && boots.getItem() instanceof YourIdeasEmeraldArmor){
            return true;
        }
        else {
            return false;
        }
    }
}
