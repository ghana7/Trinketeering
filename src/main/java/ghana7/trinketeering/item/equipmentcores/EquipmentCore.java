package ghana7.trinketeering.item.equipmentcores;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.item.infuseables.Infuseable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public abstract class EquipmentCore extends Item implements IEquipmentCore {
    public EquipmentCore() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS));
    }
    public abstract int getNumInfuseables();
    public abstract float getEffectChance();
    public abstract float getEffectModifier();

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(getNumInfuseables()) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof Infuseable;
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
    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = createItemHandler();
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }
    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if(itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler)itemHandler).serializeNBT());
        }
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        EquipmentCore coreItem = (EquipmentCore) stack.getItem();
        tooltip.add(
                new StringTextComponent("Slots: " + coreItem.getNumInfuseables())
                        .setStyle(Style.EMPTY.setColor(Color.fromHex("#FF5555"))).append(
                new StringTextComponent(" Chance: " + coreItem.getEffectChance())
                        .setStyle(Style.EMPTY.setColor(Color.fromHex("#55FF55")))).append(
                new StringTextComponent(" Modifier: " + coreItem.getEffectModifier())
                        .setStyle(Style.EMPTY.setColor(Color.fromHex("#5555FF"))))
        );

        if(nbt.contains("Inventory")) {
            IItemHandler stackHandler = getInventory(stack);
            for(int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack itemStack = stackHandler.getStackInSlot(i);
                if(!itemStack.isEmpty()) {
                    tooltip.add(new StringTextComponent(" ").append(
                            new TranslationTextComponent(itemStack.getTranslationKey())
                                    .setStyle(Style.EMPTY.setColor(Color.fromHex("#00AAAA")))
                    ));

                    if(itemStack.getItem() instanceof Infuseable) {
                        ((Infuseable)itemStack.getItem()).addIndentedInformation(itemStack, worldIn, tooltip, flagIn);
                    }
                    /*
                    List<String> infusionStrings = ((Infuseable)itemStack.getItem()).getInfoStrings(itemStack);
                    for(int j = 0; j < infusionStrings.size(); j++) {
                        tooltip.add(new TranslationTextComponent(infusionStrings.get(j)).setStyle(Style.EMPTY.setColor(Color.fromHex("#FFAA00"))));
                    }*/
                }
            }
        }
    }
}
