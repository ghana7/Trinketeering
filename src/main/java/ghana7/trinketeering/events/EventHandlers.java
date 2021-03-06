package ghana7.trinketeering.events;

import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.equipmentcores.EquipmentCore;
import ghana7.trinketeering.item.infuseables.Infuseable;
import ghana7.trinketeering.item.infuseables.bead.ClayBead;
import ghana7.trinketeering.item.infuseables.bead.CoralBead;
import ghana7.trinketeering.item.infuseables.bead.QuartzBead;
import ghana7.trinketeering.item.infuseables.bead.ShellBead;
import ghana7.trinketeering.item.infuseables.gem.CutDiamond;
import ghana7.trinketeering.item.infuseables.gem.CutLapis;
import ghana7.trinketeering.item.infuseables.other.*;
import ghana7.trinketeering.item.infuseables.plates.IronPlates;
import ghana7.trinketeering.item.infuseables.plates.StonePlates;
import ghana7.trinketeering.item.infuseables.plates.WovenPlates;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class EventHandlers {

    public static <T> List<ItemStack> getInfuseablesOfType(PlayerEntity player, Class<T> type) {
        ArrayList<ItemStack> infs = new ArrayList<>();
        for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
            ItemStack stack = player.inventory.mainInventory.get(i);
            if(stack.getItem() instanceof EquipmentCore) {
                EquipmentCore equipmentCore = (EquipmentCore) stack.getItem();
                IItemHandler equipmentInventory = equipmentCore.getInventory(stack);
                for(int j = 0; j < equipmentInventory.getSlots(); j++) {
                    ItemStack infuseableStack = equipmentInventory.getStackInSlot(j);
                    if(type.isInstance(infuseableStack.getItem())) {
                        TrinketeeringMod.LOGGER.debug("found " + type + " in " + equipmentCore);
                        infs.add(infuseableStack);
                    }
                }
            }

        }
        return infs;
    }

    private <T> void triggerType(PlayerEntity player, Class<T> type) {
        List<ItemStack> triggeredInfuseables = getInfuseablesOfType(player, type);
        for (ItemStack stack : triggeredInfuseables) {
            if(!stack.isEmpty()) {
                ((Infuseable)stack.getItem()).trigger(stack, player);
            }
        }
    }
    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        if(!event.getPlayer().world.isRemote()) {
            System.out.println("item picked up");
            triggerType(event.getPlayer(), CutLapis.class);
        }
    }
    @SubscribeEvent
    public void onArrowImpact(ProjectileImpactEvent.Arrow event) {
        if(!event.getEntity().world.isRemote()) {
            TrinketeeringMod.LOGGER.debug("arrow hit");
            ProjectileImpactEvent.Arrow arrowEvent = (ProjectileImpactEvent.Arrow) event;
            if(arrowEvent.getArrow().func_234616_v_() instanceof PlayerEntity) {
                TrinketeeringMod.LOGGER.debug("arrow shot by player");
                triggerType((PlayerEntity) event.getArrow().func_234616_v_(), FeatherTrinket.class);
            }
        }
    }

    @SubscribeEvent
    public void onExplosionDetonate(ExplosionEvent.Detonate event)
    {
        if(!event.getWorld().isRemote()) {
            TrinketeeringMod.LOGGER.debug("explosion");
            Vector3d p = event.getExplosion().getPosition();
            List<Entity> affectedEntities = event.getAffectedEntities();
            for(int i = 0; i < affectedEntities.size(); i++) {
                if(affectedEntities.get(i) instanceof PlayerEntity) {
                    TrinketeeringMod.LOGGER.debug("explosion hit player");
                    triggerType((PlayerEntity) affectedEntities.get(i), IronPlates.class);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent breakEvent) {
        if(!breakEvent.getWorld().isRemote()) {
            if(breakEvent.getState().getBlock().isIn(Tags.Blocks.ORES)) {
                TrinketeeringMod.LOGGER.debug("ore mined by player");
                triggerType(breakEvent.getPlayer(), CutDiamond.class);
            }

            if(breakEvent.getState().getBlock().isIn(BlockTags.LOGS)) {
                TrinketeeringMod.LOGGER.debug("wood chopped by player");
                triggerType(breakEvent.getPlayer(), CarvedBark.class);
            }

            if(breakEvent.getState().getBlock().isIn(BlockTags.FLOWERS) ||
                    breakEvent.getState().getBlock() instanceof TallGrassBlock ||
                    breakEvent.getState().getBlock() instanceof DoublePlantBlock) {
                TrinketeeringMod.LOGGER.debug("grass/flower foraged by player");

                triggerType(breakEvent.getPlayer(), GatheredForage.class);
            }
        }
    }

    @SubscribeEvent
    public void onFishCatch(ItemFishedEvent itemFishedEvent) {
        if(!itemFishedEvent.getEntity().world.isRemote()) {
            TrinketeeringMod.LOGGER.debug("item fished by player");
            triggerType(itemFishedEvent.getPlayer(), ShellBead.class);
        }
    }

    @SubscribeEvent
    public void onDamageTaken(LivingDamageEvent livingDamageEvent) {
        if(!livingDamageEvent.getEntity().world.isRemote()) {
            if(livingDamageEvent.getEntity() instanceof PlayerEntity) {
                TrinketeeringMod.LOGGER.debug("player took damage");
                triggerType((PlayerEntity)livingDamageEvent.getEntity(), CarvedTooth.class);
                if(livingDamageEvent.getSource().isFireDamage()) {
                    TrinketeeringMod.LOGGER.debug("player took fire damage");
                    triggerType((PlayerEntity)livingDamageEvent.getEntity(), ClayBead.class);
                }
                if(livingDamageEvent.getSource().getDamageType() == "drown") {
                    TrinketeeringMod.LOGGER.debug("player took drowning damage");
                    triggerType((PlayerEntity)livingDamageEvent.getEntity(), CoralBead.class);
                }
                if(livingDamageEvent.getSource().getTrueSource() instanceof LivingEntity) {
                    double playerYaw = ((PlayerEntity)livingDamageEvent.getEntity()).rotationYaw;
                    Vector3d displacement = livingDamageEvent.getSource().getDamageLocation().subtract(livingDamageEvent.getEntity().getPositionVec());
                    Vector3d direction = displacement.normalize();
                    double dirYaw = Math.toDegrees(Math.asin(direction.getX()));
                    double modDirYaw = 180 - dirYaw; //if equal to player yaw, mob is directly behind
                    double diff = (modDirYaw - playerYaw + 720) % 360;
                    if(diff < 90 || diff > 270) {
                        TrinketeeringMod.LOGGER.debug("player hit from behind");
                        triggerType((PlayerEntity)livingDamageEvent.getEntity(), StonePlates.class);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onFallDamage(LivingFallEvent livingFallEvent) {
        if(!livingFallEvent.getEntity().world.isRemote()) {
            if(livingFallEvent.getEntity() instanceof PlayerEntity) {
                if(livingFallEvent.getDistance() > 3.0f) {
                    TrinketeeringMod.LOGGER.debug("player took fall damage - dist: " + livingFallEvent.getDistance());

                    triggerType((PlayerEntity)livingFallEvent.getEntity(), WovenPlates.class);
                }

            }
        }
    }

    @SubscribeEvent
    public void onBlockInteract(PlayerInteractEvent.RightClickBlock playerInteractEvent) {
        if(!playerInteractEvent.getWorld().isRemote()) {
            BlockState blockState = playerInteractEvent.getWorld().getBlockState(playerInteractEvent.getPos());
            if(blockState.getBlock() == Blocks.CAKE && blockState.get(BlockStateProperties.BITES_0_6) == 6) {
                TrinketeeringMod.LOGGER.debug("cake finished by player");
                triggerType(playerInteractEvent.getPlayer(), Leftovers.class);
            }
        }
    }

    @SubscribeEvent
    public void onEnemyKill(LivingDeathEvent event) {
        if(!event.getEntity().world.isRemote()) {
            if(event.getSource().getTrueSource() instanceof PlayerEntity && event.getEntity() instanceof LivingEntity) {
                TrinketeeringMod.LOGGER.debug("living entity killed by player");
                triggerType((PlayerEntity) event.getSource().getTrueSource(), BloodyGiblets.class);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCrit(CriticalHitEvent event) {
        if(!event.getTarget().world.isRemote()) {
            if(event.isVanillaCritical()) {
                TrinketeeringMod.LOGGER.debug("player crit");
                triggerType(event.getPlayer(), QuartzBead.class);
            }
        }
    }
}
