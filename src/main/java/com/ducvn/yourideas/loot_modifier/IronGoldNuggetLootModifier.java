package com.ducvn.yourideas.loot_modifier;

import com.ducvn.yourideas.config.YourIdeasConfig;
import com.ducvn.yourideas.item.YourIdeasItemsRegister;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class IronGoldNuggetLootModifier extends LootModifier {
    private final int min;
    private final int max;
    protected IronGoldNuggetLootModifier(ILootCondition[] conditionsIn, int min, int max) {
        super(conditionsIn);
        this.min = min;
        this.max = max;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (YourIdeasConfig.gravel_drop_nuggets.get()){
            Random roll = new Random();
            boolean dropBoth = roll.nextBoolean();
            if (dropBoth){
                generatedLoot.add(new ItemStack(Items.IRON_NUGGET, roll.nextInt(max) + min));
                generatedLoot.add(new ItemStack(Items.GOLD_NUGGET, roll.nextInt(max) + min));
            }
            else {
                boolean dropIron = roll.nextBoolean();
                if (dropIron){
                    generatedLoot.add(new ItemStack(Items.IRON_NUGGET, roll.nextInt(max) + min));
                }
                else {
                    generatedLoot.add(new ItemStack(Items.GOLD_NUGGET, roll.nextInt(max) + min));
                }
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<IronGoldNuggetLootModifier> {

        @Override
        public IronGoldNuggetLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
            int min = JSONUtils.getAsInt(object, "min");
            int max = JSONUtils.getAsInt(object, "max");
            return new IronGoldNuggetLootModifier(conditionsIn, min, max);
        }

        @Override
        public JsonObject write(IronGoldNuggetLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("min", instance.min);
            json.addProperty("max", instance.max);
            return json;
        }
    }
}
