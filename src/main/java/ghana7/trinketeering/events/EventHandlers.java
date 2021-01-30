package ghana7.trinketeering.events;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.infuseables.Infuseable;
import ghana7.trinketeering.item.infuseables.gem.CutDiamond;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventHandlers {

    public static <T> List<ItemStack> getInfuseablesOfType(PlayerEntity player, Class<T> type) {
        ArrayList<ItemStack> infs = new ArrayList<>();
        for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack stack = player.inventory.mainInventory.get(i);
            if(type.isInstance(stack.getItem())) {
                TrinketeeringMod.LOGGER.debug("found " + type);
                infs.add(stack);
            }
        }
        return infs;
    }

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        System.out.println("item picked up");
        getInfuseablesOfType(event.getPlayer(), CutDiamond.class);
    }
}
