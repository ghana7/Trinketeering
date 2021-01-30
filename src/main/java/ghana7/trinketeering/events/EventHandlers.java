package ghana7.trinketeering.events;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.equipmentcores.EquipmentCore;
import ghana7.trinketeering.item.infuseables.Infuseable;
import ghana7.trinketeering.item.infuseables.gem.CutDiamond;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventHandlers {

    public static <T> List<ItemStack> getInfuseablesOfType(PlayerEntity player, Class<T> type) {
        ArrayList<ItemStack> infs = new ArrayList<>();
        for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack stack = player.inventory.mainInventory.get(i);
            if(stack.getItem() instanceof EquipmentCore) {
                EquipmentCore equipmentCore = (EquipmentCore) stack.getItem();
                IItemHandler equipmentInventory = equipmentCore.getInventory(stack);
                for(int j = 0; j < equipmentInventory.getSlots(); j++) {
                    ItemStack infuseableStack = equipmentInventory.getStackInSlot(j);
                    if(type.isInstance(infuseableStack.getItem())) {
                        TrinketeeringMod.LOGGER.debug("found " + type + " in " + equipmentCore);
                        infs.add(infuseableStack);
                    }
                }
            }

        }
        return infs;
    }

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        System.out.println("item picked up");
        List<ItemStack> triggeredInfuseables = getInfuseablesOfType(event.getPlayer(), CutDiamond.class);
        for (ItemStack stack : triggeredInfuseables) {
            if(!stack.isEmpty()) {
                ((Infuseable)stack.getItem()).trigger(stack, event.getPlayer());
            }
        }
    }
}
