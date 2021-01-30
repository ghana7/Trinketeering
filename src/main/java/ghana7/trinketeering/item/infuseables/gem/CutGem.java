package ghana7.trinketeering.item.infuseables.gem;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.item.infuseables.Infuseable;
import net.minecraft.item.Item;

public abstract class CutGem extends Infuseable {
    public CutGem() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS));
    }
}
