package ghana7.trinketeering.container;

import ghana7.trinketeering.item.Infuseable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InfusionTableChildSlot extends SlotItemHandler {
    public InfusionTableChildSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return Infuseable.canInfuseWith(stack.getItem());
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
