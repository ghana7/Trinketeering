package ghana7.trinketeering.item;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.registries.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CarvingKnife extends Item {
    public CarvingKnife() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS).maxStackSize(1));
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ItemRegistry.CARVING_KNIFE.get(), 1);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
