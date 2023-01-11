package com.ducvn.yourideas.loot_modifier;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.potion.PotionsRegister;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class LevitationPotionLootModifier extends LootModifier {
    protected LevitationPotionLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (YourIdeasConfig.levitation_potion.get()){
            ItemStack stack = new ItemStack(Items.POTION);
            PotionUtils.setPotion(stack, PotionsRegister.LEVITATION_POTION.get());
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<LevitationPotionLootModifier> {

        @Override
        public LevitationPotionLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            return new LevitationPotionLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(LevitationPotionLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            return json;
        }
    }
}
