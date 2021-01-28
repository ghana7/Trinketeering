package ghana7.trinketeering;

import ghana7.trinketeering.registries.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ModItemGroup extends ItemGroup {
    private Supplier<ItemStack> displayStack;

    public static final ModItemGroup TRINKETS = new ModItemGroup("trinkets", () -> new ItemStack(ItemRegistry.IRON_BEAD.get()));

    private ModItemGroup(String label, Supplier<ItemStack> displayStack) {
        super(label);
        this.displayStack = displayStack;
    }

    @Override
    public ItemStack createIcon() {return displayStack.get();}
}
