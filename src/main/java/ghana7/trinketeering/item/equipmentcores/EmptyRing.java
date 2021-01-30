package ghana7.trinketeering.item.equipmentcores;

import ghana7.trinketeering.ModItemGroup;
import net.minecraft.item.Item;

public class EmptyRing extends EquipmentCore{
    public EmptyRing() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS));
    }

    @Override
    public int getNumInfuseables() {
        return 1;
    }

    @Override
    public float getEffectChance() {
        return 1;
    }

    @Override
    public float getEffectModifier() {
        return 1;
    }


}
