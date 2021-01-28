package ghana7.trinketeering.item.gem;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.item.Infuseable;
import net.minecraft.item.Item;

public abstract class CutGem extends Infuseable {
    public CutGem() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS));
    }
}
