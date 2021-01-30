package ghana7.trinketeering.item.infuseables;

import ghana7.trinketeering.TrinketeeringMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class InfusionEffects {
    public static void handleInfusionEffect(ItemStack stack, PlayerEntity player) {
        String translationKey = stack.getItem().getTranslationKey();
        TrinketeeringMod.LOGGER.debug("" + translationKey + " triggered on player " + player);
        switch(translationKey) {
            case "item.minecraft.snowball":
                player.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 0));
                break;
            case "item.minecraft.blaze_powder":
                player.setFire(2);
                break;
            default:
                TrinketeeringMod.LOGGER.debug("no infusion effect found for " + translationKey);
                break;
        }
    }
}
