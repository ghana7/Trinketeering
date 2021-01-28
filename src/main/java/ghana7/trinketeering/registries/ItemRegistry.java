package ghana7.trinketeering.registries;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.bead.ClayBead;
import ghana7.trinketeering.item.bead.GlassBead;
import ghana7.trinketeering.item.bead.IronBead;
import ghana7.trinketeering.item.gem.CutDiamond;
import ghana7.trinketeering.item.gem.CutEmerald;
import ghana7.trinketeering.item.gem.CutLapis;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.rmi.registry.Registry;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TrinketeeringMod.MODID);

    public static final RegistryObject<Item> IRON_BEAD = ITEMS.register("iron_bead", () ->
            new IronBead()
    );

    public static final RegistryObject<Item> CLAY_BEAD = ITEMS.register("clay_bead", () ->
            new ClayBead()
    );

    public static final RegistryObject<Item> GLASS_BEAD = ITEMS.register("glass_bead", () ->
            new GlassBead()
    );

    public static final RegistryObject<Item> CUT_DIAMOND = ITEMS.register("cut_diamond", () ->
            new CutDiamond()
    );

    public static final RegistryObject<Item> CUT_EMERALD = ITEMS.register("cut_emerald", () ->
            new CutEmerald()
    );

    public static final RegistryObject<Item> CUT_LAPIS = ITEMS.register("cut_lapis", () ->
            new CutLapis()
    );
}
