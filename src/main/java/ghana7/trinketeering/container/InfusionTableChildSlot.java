package ghana7.trinketeering.container;

import ghana7.trinketeering.item.infuseables.Infuseable;
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
