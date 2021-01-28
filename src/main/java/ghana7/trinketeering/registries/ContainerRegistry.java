package ghana7.trinketeering.registries;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.container.InfusionTableContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

public class ContainerRegistry {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TrinketeeringMod.MODID);

    public static final RegistryObject<ContainerType<InfusionTableContainer>> INFUSION_TABLE_CONTAINER = CONTAINERS.register(
            "infusion_table", () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new InfusionTableContainer(windowId, world, pos, inv, inv.player);
            }))
    );
}
