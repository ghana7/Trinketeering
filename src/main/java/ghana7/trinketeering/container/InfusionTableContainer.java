package ghana7.trinketeering.container;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.Infuseable;
import ghana7.trinketeering.registries.BlockRegistry;
import ghana7.trinketeering.registries.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class InfusionTableContainer extends Container {
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private CraftingInventory infusionSlots = new CraftingInventory(this, 5, 1);
    private final IWorldPosCallable worldPosCallable;

    public InfusionTableContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ContainerRegistry.INFUSION_TABLE_CONTAINER.get(), windowId);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.worldPosCallable = IWorldPosCallable.of(world, pos);
        this.addSlot(new Slot(infusionSlots, 0, 35, 35));
        for(int i = 1; i < infusionSlots.getWidth(); i++) {
            this.addSlot(new Slot(infusionSlots, i, 53 + 18 * i, 35));
        }
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerEntity, BlockRegistry.INFUSION_TABLE.get());
    }

    private boolean isItemValid(int index, ItemStack stack) {
        if(index == 0) {
            return stack.getItem() instanceof Infuseable;
        } else {
            return Infuseable.canInfuseWith(stack.getItem());
        }

    }
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        //IItemHandler handler = tileEntity.getGUICapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        //unhardcode this
        int numSlots = 5;
        if(slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if(index < numSlots) {
                if(!this.mergeItemStack(stack, numSlots, numSlots + 36, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if(isItemValid(0, stack)) {
                    if(!this.mergeItemStack(stack, 0, numSlots, false)) {
                        return ItemStack.EMPTY;
                    } else if(index < 27 + numSlots) {
                        if(!this.mergeItemStack(stack, 27 + numSlots, 36 + numSlots, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if(index < 36 + numSlots && !this.mergeItemStack(stack, numSlots, 27+numSlots, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if(stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if(stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);

        }
        return itemstack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for(int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }
    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for(int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        //Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        //Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
