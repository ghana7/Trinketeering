package ghana7.trinketeering.item.infuseables;

import ghana7.trinketeering.TrinketeeringMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfusionEffects {
    public static void handleInfusionEffect(ItemStack stack, PlayerEntity player, float effectModifier) {
        String translationKey = stack.getItem().getTranslationKey();
        TrinketeeringMod.LOGGER.debug("" + translationKey + " triggered on player " + player);
        switch(translationKey) {
            case "item.minecraft.snowball":
                if(getRandomNearbyEntity(player) != null)
                    getRandomNearbyEntity(player).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, (int)(1 + effectModifier)));
                break;
            case "item.minecraft.blaze_powder":
                if(getRandomNearbyEntity(player) != null)
                    getRandomNearbyEntity(player).setFire((int)(2*effectModifier));
                break;
            case "item.minecraft.poisonous_potato":
                if(getRandomNearbyEntity(player) != null)
                    getRandomNearbyEntity(player).addPotionEffect(new EffectInstance(Effects.POISON, 200, (int)(1+effectModifier)));
                break;
            case "item.minecraft.shield":
                getAllNearbyEntities(player).forEach((LivingEntity entity) -> {entity.setMotion(entity.getPositionVec().subtract(player.getPositionVec()).normalize().mul(2.0f * effectModifier,2.0f * effectModifier,2.0f * effectModifier));});
                break;
            case "item.minecraft.golden_apple":
                player.heal(2.0f * effectModifier);
                break;
            case "block.minecraft.tnt":
                player.getEntityWorld().createExplosion(player, player.getPosX(), player.getPosY(), player.getPosZ(), 2.0f * effectModifier, Explosion.Mode.BREAK);
                break;
            case "item.minecraft.leather_boots":
                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 200, (int)(1+effectModifier)));
                break;
            case "item.minecraft.cooked_beef":
                player.addPotionEffect(new EffectInstance(Effects.SATURATION, 200, (int)(1+effectModifier)));
                break;
            case "item.minecraft.fire_charge":
                player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 200, (int)(1+effectModifier)));
                break;
            case "item.minecraft.gold_ingot":
                player.addPotionEffect(new EffectInstance(Effects.HASTE, 200, (int)(1+effectModifier)));
                break;
            default:
                TrinketeeringMod.LOGGER.debug("no infusion effect found for " + translationKey);
                break;
        }
    }

    @Nullable
    private static LivingEntity getRandomNearbyEntity(PlayerEntity player) {
        World world = player.getEntityWorld();
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(10.0));

        TrinketeeringMod.LOGGER.debug("num nearby entities: " + entities.size());
        List<LivingEntity> livingEntities = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i) instanceof LivingEntity) {
                livingEntities.add((LivingEntity) entities.get(i));
            }
        }
        TrinketeeringMod.LOGGER.debug("num nearby livingentities: " + livingEntities.size());
        if(livingEntities.size() > 0) {
            Random r = new Random();
            return livingEntities.get(r.nextInt(livingEntities.size()));
        } else {
            return null;
        }

    }

    private static List<LivingEntity> getAllNearbyEntities(PlayerEntity player) {
        World world = player.getEntityWorld();
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().grow(10.0));
        List<LivingEntity> livingEntities = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            if(entities.get(i) instanceof LivingEntity) {
                livingEntities.add((LivingEntity) entities.get(i));
            }
        }
        return livingEntities;
    }
}
