package ghana7.trinketeering.item.equipmentcores;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import ghana7.trinketeering.ModItemGroup;
import ghana7.trinketeering.client.ArmorBaseModel;
import ghana7.trinketeering.client.ClientArmorHelper;
import ghana7.trinketeering.item.infuseables.Infuseable;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class Crown extends ArmorItem implements IEquipmentCore{
    public Crown() {
        super(ArmorMaterial.GOLD, EquipmentSlotType.HEAD, (new Item.Properties()).group(ModItemGroup.TRINKETS));

    }

    @Override
    public int getNumInfuseables() {
        return 4;
    }

    @Override
    public float getEffectChance() {
        return 1;
    }

    @Override
    public float getEffectModifier() {
        return 2;
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(getNumInfuseables()) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof Infuseable;
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
    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = createItemHandler();
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }
    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if(itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler)itemHandler).serializeNBT());
        }
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains("Inventory")) {
            IItemHandler stackHandler = getInventory(stack);
            for(int i = 0; i < stackHandler.getSlots(); i++) {
                ItemStack itemStack = stackHandler.getStackInSlot(i);
                if(!itemStack.isEmpty()) {
                    tooltip.add(new TranslationTextComponent(itemStack.getTranslationKey()).setStyle(Style.EMPTY.setColor(Color.fromHex("#00AAAA"))));
                    List<String> infusionStrings = ((Infuseable)itemStack.getItem()).getInfoStrings(itemStack);
                    for(int j = 0; j < infusionStrings.size(); j++) {
                        tooltip.add(new TranslationTextComponent(infusionStrings.get(j)).setStyle(Style.EMPTY.setColor(Color.fromHex("#FFAA00"))));
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return ClientArmorHelper.getCrownArmor().getTexture();
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        return ClientArmorHelper.getCrownArmor().applyEntityStats(_default).applySlot(armorSlot);
    }


}
