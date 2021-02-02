package ghana7.trinketeering.registries;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.equipmentcores.EmptyRing;
import ghana7.trinketeering.item.equipmentcores.NecklaceString;
import ghana7.trinketeering.item.infuseables.bead.ClayBead;
import ghana7.trinketeering.item.infuseables.bead.GlassBead;
import ghana7.trinketeering.item.infuseables.bead.IronBead;
import ghana7.trinketeering.item.infuseables.gem.CutDiamond;
import ghana7.trinketeering.item.infuseables.gem.CutEmerald;
import ghana7.trinketeering.item.infuseables.gem.CutLapis;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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

    public static final RegistryObject<Item> NECKLACE_STRING = ITEMS.register("necklace_string", () ->
            new NecklaceString()
    );

    public static final RegistryObject<Item> EMPTY_RING = ITEMS.register("empty_ring", () ->
            new EmptyRing()
    );
    //blockitems
    public static final RegistryObject<Item> INFUSION_TABLE_BE = ITEMS.register("infusion_table", () ->
            new BlockItem(BlockRegistry.INFUSION_TABLE.get(),
                          new Item.Properties().group(ModItemGroup.TRINKETS))
    );

    public static final RegistryObject<Item> TRINKET_TABLE_BE = ITEMS.register("trinket_table", () ->
            new BlockItem(BlockRegistry.TRINKET_TABLE.get(),
                    new Item.Properties().group(ModItemGroup.TRINKETS))
    );
}
