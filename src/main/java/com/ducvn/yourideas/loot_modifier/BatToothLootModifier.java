package com.ducvn.yourideas.loot_modifier;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class BatToothLootModifier extends LootModifier {
    protected BatToothLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (YourIdeasConfig.spear_of_siphon.get()){
            ItemStack stack = new ItemStack(YourIdeasItemsRegister.BAT_TOOTH.get());
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BatToothLootModifier> {

        @Override
        public BatToothLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            return new BatToothLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(BatToothLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            return json;
        }
    }
}
