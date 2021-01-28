package ghana7.trinketeering.item.bead;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.item.Infuseable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class Bead extends Infuseable {

    public Bead() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS));
    }


}
