package com.ducvn.yourideas.loot_modifier;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class WitherWitherSkullLootModifier extends LootModifier {
    protected WitherWitherSkullLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (YourIdeasConfig.wither_drop_wither_skull.get()){
            Random roll = new Random();
            ItemStack stack = new ItemStack(Items.WITHER_SKELETON_SKULL);
            if (roll.nextInt(1000) < 125){
                if (roll.nextInt(1000) < 125){
                    stack.setCount(3);
                }
                else {
                    stack.setCount(2);
                }
            }
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<WitherWitherSkullLootModifier> {

        @Override
        public WitherWitherSkullLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            return new WitherWitherSkullLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(WitherWitherSkullLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            return json;
        }
    }
}
