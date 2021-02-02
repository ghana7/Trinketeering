package ghana7.trinketeering.container;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.equipmentcores.EquipmentCore;
import ghana7.trinketeering.item.infuseables.Infuseable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TrinketTableSlot extends SlotItemHandler {
    private final TrinketTableContainer parentContainer;
    public TrinketTableSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition, TrinketTableContainer parent) {
        super(inventoryIn, index, xPosition, yPosition);
        parentContainer = parent;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof EquipmentCore;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    //update infuseable when removed
    @Override
    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        if(!player.getEntityWorld().isRemote) {
            //TrinketeeringMod.LOGGER.debug("removing equipment core");
            EquipmentCore equipmentCore = (EquipmentCore)stack.getItem();
            IItemHandler inventory = equipmentCore.getInventory(stack);
            IItemHandler parentContainerInventory = parentContainer.getInfuseableSlots();
            for (int i = 1; i < parentContainerInventory.getSlots(); i++) {
                if(i - 1 < inventory.getSlots()) {
                    //TrinketeeringMod.LOGGER.debug(parentContainerInventory.getStackInSlot(i).getStack());
                    inventory.extractItem(i - 1, 64, false);
                    inventory.insertItem(i - 1, parentContainerInventory.getStackInSlot(i).getStack(), false);
                    if (!parentContainerInventory.getStackInSlot(i).isEmpty()) {
                        ((Infuseable) parentContainerInventory.getStackInSlot(i).getItem()).saveEquipmentCoreParent(parentContainerInventory.getStackInSlot(i), stack);
                    }
                }
                parentContainerInventory.extractItem(i, 64, false);
            }
            equipmentCore.saveInventory(stack, inventory);
            parentContainer.detectAndSendChanges();
        }
        return super.onTake(player, stack);
    }

    //update child slots
    @Override
    public void putStack(ItemStack stack) {
        if(Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
            if(stack.getItem() instanceof EquipmentCore) {
                //TrinketeeringMod.LOGGER.debug("inserting equipment core");
                EquipmentCore equipmentCore = (EquipmentCore)stack.getItem();
                IItemHandler inventory = equipmentCore.getInventory(stack);
                for (int i = 1; i < parentContainer.getInfuseableSlots().getSlots(); i++) {
                    //TrinketeeringMod.LOGGER.debug(parentContainer.getInfuseableSlots().getStackInSlot(i).getStack());
                    if(i - 1 < inventory.getSlots()) {
                        parentContainer.getInfuseableSlots().extractItem(i, 64, false);
                        parentContainer.getInfuseableSlots().insertItem(i, inventory.extractItem(i - 1, 64, false), false);
                    }
                }
                equipmentCore.saveInventory(stack, inventory);
                parentContainer.detectAndSendChanges();
            }
        }
        super.putStack(stack);
    }
}
