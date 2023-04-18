package net.mod;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.mod.item.ModItems;

public class ModCreativeModeTab {
    public static final CreativeModeTab TIMURLIFE_TAB = new CreativeModeTab("timurlifetab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SOBRANIE_CIGARETTE.get());
        }
    };
}
