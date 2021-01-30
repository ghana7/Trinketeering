package ghana7.trinketeering.registries;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.block.InfusionTableBlock;
import ghana7.trinketeering.block.TrinketTableBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TrinketeeringMod.MODID);

    public static final RegistryObject<Block> INFUSION_TABLE = BLOCKS.register("infusion_table", () ->
            new InfusionTableBlock()
    );

    public static final RegistryObject<Block> TRINKET_TABLE = BLOCKS.register("trinket_table", () ->
            new TrinketTableBlock()
    );
}
