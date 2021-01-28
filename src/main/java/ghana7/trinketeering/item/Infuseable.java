package ghana7.trinketeering.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class Infuseable extends Item {
    public Infuseable(Properties properties) {
        super(properties);
    }

    public IItemHandler getInfusionInventory(ItemStack stack) {
        ItemStackHandler stackHandler = createInfusionHandler();
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("InfusionInventory"));
        return stackHandler;
    }
    public void saveInfusionInventory(ItemStack stack, IItemHandler itemHandler) {
        if(itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("InfusionInventory", ((ItemStackHandler)itemHandler).serializeNBT());
        }
    }
    public static boolean canInfuseWith(Item item)
    {
        return item == Items.SNOWBALL || item == Items.BLAZE_POWDER;
    }

    private ItemStackHandler createInfusionHandler() {
        return new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return canInfuseWith(stack.getItem());
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }
        };
    }
}
