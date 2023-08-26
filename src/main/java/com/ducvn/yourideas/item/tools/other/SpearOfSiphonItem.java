package com.ducvn.yourideas.item.tools.other;

import com.ducvn.yourideas.YourIdeasMod;
import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = YourIdeasMod.MODID)
public class SpearOfSiphonItem extends TieredItem implements IVanishable {
    private final double attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private float siphon;
    private static Set enchantmentsAllowed = ImmutableSet.of(Enchantments.SHARPNESS,
            Enchantments.MOB_LOOTING,
            Enchantments.UNBREAKING,
            Enchantments.FIRE_ASPECT,
            Enchantments.SMITE,
            Enchantments.BANE_OF_ARTHROPODS,
            Enchantments.KNOCKBACK,
            Enchantments.MENDING);

    public SpearOfSiphonItem(IItemTier p_i48459_1_, double baseAttackDamage, double attackSpeedModify, double siphon, Properties p_i48459_2_) {
        super(p_i48459_1_, p_i48459_2_);
        /* ItemTier - Iron: +3atk - Diamond: +4atk - Netherite: +5atk - */
        this.attackDamage = baseAttackDamage + (double) p_i48459_1_.getAttackDamageBonus();
        this.siphon = (float) siphon;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModify, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        super.appendHoverText(stack, world, tooltip, p_77624_4_);
        tooltip.add(new TranslationTextComponent(
                        "\u00A75Siphon: " + this.siphon + "hp\u00A7r"
                )
        );
        if (this.getItem() == YourIdeasItemsRegister.EMERALD_SPEAR_OF_SIPHON.get()){
            tooltip.add(new TranslationTextComponent(
                            "\u00A7a100% heal player on-hit (full charge)\u00A7r"
                    )
            );
        }
        else {
            tooltip.add(new TranslationTextComponent(
                            "\u00A7a75% heal player on-hit (full charge)\u00A7r"
                    )
            );
        }

    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantmentsAllowed.contains(enchantment);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        return p_150897_1_.is(Blocks.COBWEB);
    }

    public float getDestroySpeed(ItemStack p_150893_1_, BlockState p_150893_2_) {
        if (p_150893_2_.is(Blocks.COBWEB)) {
            return 12.0F;
        } else {
            Material material = p_150893_2_.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !p_150893_2_.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }

    public float getSiphon(){
        return this.siphon;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType p_111205_1_) {
        return p_111205_1_ == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_111205_1_);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        p_77644_1_.hurtAndBreak(1, p_77644_3_, (p_220045_0_) -> {
            p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack p_179218_1_, World p_179218_2_, BlockState p_179218_3_, BlockPos p_179218_4_, LivingEntity p_179218_5_) {
        if (p_179218_3_.getDestroySpeed(p_179218_2_, p_179218_4_) != 0.0F) {
            p_179218_1_.hurtAndBreak(2, p_179218_5_, (p_220044_0_) -> {
                p_220044_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    @SubscribeEvent
    public static void onHitEntity(AttackEntityEvent event){
        World world = event.getPlayer().level;
        if (!world.isClientSide && YourIdeasConfig.spear_of_siphon.get()){
            if (event.getTarget() instanceof LivingEntity){
                PlayerEntity player = event.getPlayer();
                int healChance = 75;
                if (player.getMainHandItem().getItem() == YourIdeasItemsRegister.EMERALD_SPEAR_OF_SIPHON.get()){
                    healChance = 100;
                }
                if ((player.getMainHandItem().getItem() instanceof SpearOfSiphonItem)
                && (player.getAttackStrengthScale(0.5F) > 0.9F)
                && (new Random().nextInt(100) < healChance)){
                    SpearOfSiphonItem spear = (SpearOfSiphonItem) player.getMainHandItem().getItem();
                    player.heal(spear.getSiphon());
                    System.out.println(spear.getMaxStackSize());
                }
            }
        }
    }
}
