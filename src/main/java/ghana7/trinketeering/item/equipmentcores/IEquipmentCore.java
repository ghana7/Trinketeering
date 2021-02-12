package ghana7.trinketeering.item.equipmentcores;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public interface IEquipmentCore {
    public abstract int getNumInfuseables();
    public abstract float getEffectChance();
    public abstract float getEffectModifier();
    public IItemHandler getInventory(ItemStack stack);
    public void saveInventory(ItemStack stack, IItemHandler itemHandler);
}
