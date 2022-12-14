package com.korona.koronaswiat.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup MAGIC_ACCESSORIES = new ItemGroup("magicAccessoriesModTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.REGENERATION_BANNER.get());
        }
    };
}
