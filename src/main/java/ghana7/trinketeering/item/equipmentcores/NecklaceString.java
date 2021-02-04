package ghana7.trinketeering.item.equipmentcores;

import ghana7.trinketeering.ModItemGroup;
import net.minecraft.item.Item;

public class NecklaceString extends EquipmentCore{


    @Override
    public int getNumInfuseables() {
        return 4;
    }

    @Override
    public float getEffectChance() {
        return 0.25f;
    }

    @Override
    public float getEffectModifier() {
        return 2;
    }
}
