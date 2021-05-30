package ghana7.trinketeering.container;

import ghana7.trinketeering.item.equipmentcores.Crown;
import ghana7.trinketeering.item.equipmentcores.EquipmentCore;
import ghana7.trinketeering.item.infuseables.Infuseable;
import ghana7.trinketeering.registries.BlockRegistry;
import ghana7.trinketeering.registries.ContainerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class TrinketTableContainer extends Container {
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private IItemHandler infuseableSlots = createHandler();
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(5) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
                super.onContentsChanged(slot);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return TrinketTableContainer.isItemValid(slot, stack);
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
    private final IWorldPosCallable worldPosCallable;

    public IItemHandler getInfuseableSlots() {
        return infuseableSlots;
    }
    public TrinketTableContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ContainerRegistry.TRINKET_TABLE_CONTAINER.get(), windowId);

        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.worldPosCallable = IWorldPosCallable.of(world, pos);

        TrinketTableSlot slot = (TrinketTableSlot) this.addSlot(new TrinketTableSlot(infuseableSlots, 0, 27, 7, this));
        NonNullList<Slot> childSlots = NonNullList.create();
        for(int i = 1; i < 3; i++) {
            childSlots.add(this.addSlot(new TrinketTableChildSlot(infuseableSlots, i,  18 * i, 42)));
        }
        for(int i = 3; i < 5; i++) {
            childSlots.add(this.addSlot(new TrinketTableChildSlot(infuseableSlots, i, 18 - 54 + 18 * i, 60)));
        }
        layoutPlayerInventorySlots(36, 84);
    }

    public void sendUpdatePacket(int slot, ItemStack itemStack){
        ((ServerPlayerEntity)playerEntity).connection.sendPacket(new SSetSlotPacket(windowId, slot, itemStack));
    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerEntity, BlockRegistry.TRINKET_TABLE.get());
    }

    public static boolean isItemValid(int index, ItemStack stack) {
        if(index == 0) {
            return stack.getItem() instanceof EquipmentCore || stack.getItem() instanceof Crown;
        } else {
            return stack.getItem() instanceof Infuseable;
        }

    }


    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        if(isItemValid(slotID, stack)) {
            super.putStackInSlot(slotID, stack);
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
                for(int i = 0; i < numSlots; i++) {
                    if(isItemValid(i, stack)) {
                        if(!this.mergeItemStack(stack, i, numSlots, false)) {
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
