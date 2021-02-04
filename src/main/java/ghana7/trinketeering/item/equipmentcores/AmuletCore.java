package ghana7.trinketeering.item.equipmentcores;

public class AmuletCore extends EquipmentCore{

    @Override
    public int getNumInfuseables() {
        return 1;
    }

    @Override
    public float getEffectChance() {
        return 0.1f;
    }

    @Override
    public float getEffectModifier() {
        return 10f;
    }


}
