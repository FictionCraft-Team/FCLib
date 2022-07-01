package fictioncraft.wintersteve25.fclib.utils;

import fictioncraft.wintersteve25.fclib.content.base.blocks.FCLibInvBE;
import fictioncraft.wintersteve25.fclib.content.base.interfaces.IHasValidItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

public class ONIInventoryHandler extends ItemStackHandler {
    private final FCLibInvBE tile;

    public ONIInventoryHandler(FCLibInvBE inv) {
        super(inv.getInvSize());
        tile = inv;
    }

    @Override
    public void onContentsChanged(int slot) {
        tile.updateBlock();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (!(tile instanceof IHasValidItems validItems)) {
            return true;
        }
        BiPredicate<ItemStack, Integer> valids = validItems.validItemsPredicate();
        return valids.test(stack, slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (!(tile instanceof IHasValidItems validItems)) {
            return super.insertItem(slot, stack, simulate);
        }
        BiPredicate<ItemStack, Integer> valids = validItems.validItemsPredicate();
        return valids.test(stack, slot) ? super.insertItem(slot, stack, simulate) : stack;
    }
}
