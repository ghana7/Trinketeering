package ghana7.trinketeering;

import ghana7.trinketeering.container.InfusionTableScreen;
import ghana7.trinketeering.container.TrinketTableScreen;
import ghana7.trinketeering.events.EventHandlers;
import ghana7.trinketeering.registries.BlockRegistry;
import ghana7.trinketeering.registries.ContainerRegistry;
import ghana7.trinketeering.registries.ItemRegistry;
import ghana7.trinketeering.registries.TileEntityRegistry;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TrinketeeringMod.MODID)
public class TrinketeeringMod {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "trinketeering";

    public TrinketeeringMod() {

        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityRegistry.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ContainerRegistry.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventHandlers());


    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        ScreenManager.registerFactory(ContainerRegistry.INFUSION_TABLE_CONTAINER.get(), InfusionTableScreen::new);
        ScreenManager.registerFactory(ContainerRegistry.TRINKET_TABLE_CONTAINER.get(), TrinketTableScreen::new);
    }
}
