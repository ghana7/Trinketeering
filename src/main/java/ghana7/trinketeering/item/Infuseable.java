package ghana7.trinketeering.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class Infuseable extends Item {
    public Infuseable(Properties properties) {
        super(properties);
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

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("InfusionInventory")) {
            IItemHandler stackHandler = getInfusionInventory(stack);
            for(int i = 0; i < stackHandler.getSlots(); i++) {
                if(!stackHandler.getStackInSlot(i).isEmpty()) {
                    tooltip.add(new TranslationTextComponent(stackHandler.getStackInSlot(i).getTranslationKey()));
                }
            }
        }
    }

}
