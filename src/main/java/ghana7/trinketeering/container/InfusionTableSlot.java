package ghana7.trinketeering.container;

import ghana7.trinketeering.item.infuseables.Infuseable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InfusionTableSlot extends SlotItemHandler {
    private final InfusionTableContainer parentContainer;
    public InfusionTableSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition, InfusionTableContainer parent) {
        super(inventoryIn, index, xPosition, yPosition);
        parentContainer = parent;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof Infuseable;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    //update infuseable when removed
    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        if(!player.getEntityWorld().isRemote) {
            //TrinketeeringMod.LOGGER.debug("removing infuseable");
            Infuseable infuseable = (Infuseable)stack.getItem();
            IItemHandler inventory = infuseable.getInfusionInventory(stack);
            for (int i = 1; i < parentContainer.getInfusionSlots().getSlots(); i++) {
                //TrinketeeringMod.LOGGER.debug(parentContainer.getInfusionSlots().getStackInSlot(i).getStack());
                inventory.extractItem(i - 1, 64, false);
                inventory.insertItem(i - 1, parentContainer.getInfusionSlots().getStackInSlot(i).getStack(), false);
                parentContainer.getInfusionSlots().extractItem(i, 64, false);
                parentContainer.sendUpdatePacket(i, ItemStack.EMPTY);
            }
            infuseable.saveInfusionInventory(stack, inventory);
            parentContainer.detectAndSendChanges();
        }
        return super.onTake(player, stack);
    }

    //update child slots
    @Override
    public void putStack(ItemStack stack) {
        if(Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            if(stack.getItem() instanceof Infuseable) {
                //TrinketeeringMod.LOGGER.debug("inserting infuseable");
                Infuseable infuseable = (Infuseable)stack.getItem();
                IItemHandler inventory = infuseable.getInfusionInventory(stack);
                for (int i = 1; i < parentContainer.getInfusionSlots().getSlots(); i++) {
                    //TrinketeeringMod.LOGGER.debug(parentContainer.getInfusionSlots().getStackInSlot(i).getStack());
                    parentContainer.getInfusionSlots().extractItem(i, 64, false);
                    ItemStack itemToInsert = inventory.extractItem(i - 1, 64, false);
                    parentContainer.getInfusionSlots().insertItem(i, itemToInsert, false);
                    parentContainer.sendUpdatePacket(i, itemToInsert);
                }
                infuseable.saveInfusionInventory(stack, inventory);
                parentContainer.detectAndSendChanges();
            }
        }
        super.putStack(stack);
    }

}
