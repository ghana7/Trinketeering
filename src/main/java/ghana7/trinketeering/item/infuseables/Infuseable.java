package ghana7.trinketeering.item.infuseables;

import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.equipmentcores.EquipmentCore;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class Infuseable extends Item {
    public Infuseable() {
        super(new Item.Properties().group(ModItemGroup.TRINKETS).maxStackSize(1));
    }
    private ItemStackHandler createInfusionHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return canInfuseWith(stack.getItem());
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }
        };
    }
    private ItemStackHandler createEquipmentCoreHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof EquipmentCore;
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }
        };
    }
    public IItemHandler getInfusionInventory(ItemStack stack) {
        ItemStackHandler stackHandler = createInfusionHandler();
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("InfusionInventory"));
        return stackHandler;
    }
    public void saveInfusionInventory(ItemStack stack, IItemHandler itemHandler) {
        if(itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("InfusionInventory", ((ItemStackHandler)itemHandler).serializeNBT());
        }
    }

    public ItemStack getEquipmentCoreParent(ItemStack stack) {
        ItemStackHandler stackHandler = createEquipmentCoreHandler();
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("EquipmentCoreParent"));
        return stackHandler.getStackInSlot(0);
    }

    public void saveEquipmentCoreParent(ItemStack stack, ItemStack equipmentCoreParent) {
        ItemStackHandler stackHandler = createEquipmentCoreHandler();
        stackHandler.insertItem(0, equipmentCoreParent, false);
        stack.getOrCreateTag().put("EquipmentCoreParent", stackHandler.serializeNBT());
    }
    public void trigger(ItemStack stack, PlayerEntity player) {
        IItemHandler infusionInventory = getInfusionInventory(stack);
        for (int i = 0; i < infusionInventory.getSlots(); i++) {
            if(!infusionInventory.getStackInSlot(i).isEmpty()) {
                ItemStack equipmentCoreParent = getEquipmentCoreParent(stack);
                float effectChance = 1;
                float effectModifier = 0;
                if(equipmentCoreParent.getItem() instanceof EquipmentCore) {
                    effectChance = ((EquipmentCore)equipmentCoreParent.getItem()).getEffectChance();
                    effectModifier = ((EquipmentCore)equipmentCoreParent.getItem()).getEffectModifier();
                    TrinketeeringMod.LOGGER.debug("applying chance " + effectChance +" and modifier " + effectModifier);
                } else {
                    TrinketeeringMod.LOGGER.warn("no parent core found - this is an error - using defaults of 1 and 0 for chance and modifier");
                }

                if(random.nextFloat() < effectChance) {
                    InfusionEffects.handleInfusionEffect(infusionInventory.getStackInSlot(i), player, effectModifier);
                }

            }
        }
    }
    public static boolean canInfuseWith(Item item)
    {
        return item == Items.SNOWBALL ||
                item == Items.BLAZE_POWDER ||
                item == Items.POISONOUS_POTATO ||
                item == Items.GOLDEN_APPLE ||
                item == Items.TNT ||
                item == Items.LEATHER_BOOTS ||
                item == Items.COOKED_BEEF ||
                item == Items.FIRE_CHARGE ||
                item == Items.GOLD_INGOT ||
                item == Items.SHIELD ||
                item == Items.ENDER_EYE
                ;
    }
    public abstract String getConditionText();

    public List<String> getInfoStrings(ItemStack stack) {
        List<String> infoStrings = new ArrayList<>();
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("InfusionInventory")) {
            IItemHandler stackHandler = getInfusionInventory(stack);
            for(int i = 0; i < stackHandler.getSlots(); i++) {
                if(!stackHandler.getStackInSlot(i).isEmpty()) {
                    infoStrings.add(stackHandler.getStackInSlot(i).getTranslationKey());
                }
            }
        }
        return infoStrings;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        tooltip.add(new StringTextComponent(" " + getConditionText()).setStyle(Style.EMPTY.setColor(Color.fromHex("#AA00AA"))));
        if(nbt.contains("InfusionInventory")) {
            List<String> strings = getInfoStrings(stack);
            for(int i = 0; i < strings.size(); i++) {
                tooltip.add(new StringTextComponent("  ").append(
                        new TranslationTextComponent(strings.get(i))
                                .setStyle(Style.EMPTY.setColor(Color.fromHex("#FFAA00")))
                ));
                tooltip.add(new StringTextComponent("   ").append(
                        new TranslationTextComponent(InfusionEffects.getResultText(strings.get(i)))
                            .setStyle(Style.EMPTY.setColor(Color.fromHex("#AA00AA")))
                ));
            }
        }
    }

    public void addIndentedInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        tooltip.add(new StringTextComponent("   " + getConditionText()).setStyle(Style.EMPTY.setColor(Color.fromHex("#AA00AA"))));
        if(nbt.contains("InfusionInventory")) {
            List<String> strings = getInfoStrings(stack);
            for(int i = 0; i < strings.size(); i++) {
                tooltip.add(new StringTextComponent("    ").append(
                        new TranslationTextComponent(strings.get(i))
                                .setStyle(Style.EMPTY.setColor(Color.fromHex("#FFAA00")))
                ));
                tooltip.add(new StringTextComponent("     ").append(
                        new TranslationTextComponent(InfusionEffects.getResultText(strings.get(i)))
                                .setStyle(Style.EMPTY.setColor(Color.fromHex("#AA00AA")))
                ));
            }
        }
    }
}
