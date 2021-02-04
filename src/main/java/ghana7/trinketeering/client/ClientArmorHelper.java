package ghana7.trinketeering.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientArmorHelper{

    private final static ArmorBaseModel CROWN_ARMOR = new CrownModel();

    public static ArmorBaseModel getCrownArmor(){
        return CROWN_ARMOR;
    }
}
