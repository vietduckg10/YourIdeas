package com.ducvn.yourideas.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class YourIdeasItemGroup {
    public static final ItemGroup YOUR_IDEAS_ITEM_GROUP = new ItemGroup("yourideas") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.LANTERN);
        }
    };
}
