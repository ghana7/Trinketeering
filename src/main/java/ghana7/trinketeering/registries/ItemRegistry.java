package ghana7.trinketeering.registries;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.CarvingKnife;
import ghana7.trinketeering.item.equipmentcores.*;
import ghana7.trinketeering.item.infuseables.bead.*;
import ghana7.trinketeering.item.infuseables.gem.CutDiamond;
import ghana7.trinketeering.item.infuseables.gem.CutLapis;
import ghana7.trinketeering.item.infuseables.other.*;
import ghana7.trinketeering.item.infuseables.plates.IronPlates;
import ghana7.trinketeering.item.infuseables.plates.StonePlates;
import ghana7.trinketeering.item.infuseables.plates.WovenPlates;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TrinketeeringMod.MODID);

    public static final RegistryObject<Item> CARVING_KNIFE = ITEMS.register("carving_knife", () ->
            new CarvingKnife()
    );

    public static final RegistryObject<Item> CLAY_BEAD = ITEMS.register("clay_bead", () ->
            new ClayBead()
    );

    //public static final RegistryObject<Item> GLASS_BEAD = ITEMS.register("glass_bead", () ->
    //        new GlassBead()
    //);

    public static final RegistryObject<Item> QUARTZ_BEAD = ITEMS.register("quartz_bead", () ->
            new QuartzBead()
    );

    public static final RegistryObject<Item> CORAL_BEAD = ITEMS.register("coral_bead", () ->
            new CoralBead()
    );

    public static final RegistryObject<Item> SHELL_BEAD = ITEMS.register("shell_bead", () ->
            new ShellBead()
    );

    public static final RegistryObject<Item> CUT_DIAMOND = ITEMS.register("cut_diamond", () ->
            new CutDiamond()
    );

    public static final RegistryObject<Item> CUT_LAPIS = ITEMS.register("cut_lapis", () ->
            new CutLapis()
    );

    public static final RegistryObject<Item> IRON_PLATES = ITEMS.register("iron_plates", () ->
            new IronPlates()
    );

    public static final RegistryObject<Item> WOVEN_PLATES = ITEMS.register("woven_plates", () ->
            new WovenPlates()
    );

    public static final RegistryObject<Item> STONE_PLATES = ITEMS.register("stone_plates", () ->
            new StonePlates()
    );

    public static final RegistryObject<Item> BLOODY_GIBLETS = ITEMS.register("bloody_giblets", () ->
            new BloodyGiblets()
    );

    public static final RegistryObject<Item> CARVED_BARK = ITEMS.register("carved_bark", () ->
            new CarvedBark()
    );

    public static final RegistryObject<Item> CARVED_TOOTH = ITEMS.register("carved_tooth", () ->
            new CarvedTooth()
    );

    public static final RegistryObject<Item> FEATHER_TRINKET = ITEMS.register("feather_trinket", () ->
            new FeatherTrinket()
    );

    public static final RegistryObject<Item> GATHERED_FORAGE = ITEMS.register("gathered_forage", () ->
            new GatheredForage()
    );

    public static final RegistryObject<Item> LEFTOVERS = ITEMS.register("leftovers", () ->
            new Leftovers()
    );

    public static final RegistryObject<Item> NECKLACE_STRING = ITEMS.register("necklace_string", () ->
            new NecklaceString()
    );

    public static final RegistryObject<Item> EMPTY_RING = ITEMS.register("empty_ring", () ->
            new EmptyRing()
    );

    public static final RegistryObject<Item> CROWN = ITEMS.register("crown", () ->
            new Crown()
    );

    public static final RegistryObject<Item> BRACELET_STRING = ITEMS.register("bracelet_string", () ->
            new BraceletString()
    );

    public static final RegistryObject<Item> AMULET_CORE = ITEMS.register("amulet_core", () ->
            new AmuletCore()
    );

    public static final RegistryObject<Item> EARRING_HOOKS = ITEMS.register("earring_hooks", () ->
            new EarringHooks()
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
