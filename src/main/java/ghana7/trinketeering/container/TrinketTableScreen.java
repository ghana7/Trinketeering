package ghana7.trinketeering.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import ghana7.trinketeering.TrinketeeringMod;
import ghana7.trinketeering.item.equipmentcores.IEquipmentCore;
import ghana7.trinketeering.item.infuseables.Infuseable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class TrinketTableScreen extends ContainerScreen<TrinketTableContainer> {
    private ResourceLocation GUI = new ResourceLocation(TrinketeeringMod.MODID, "textures/gui/trinket_table_gui.png");

    public TrinketTableScreen(TrinketTableContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.xSize = 237;
        this.ySize = 166;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        List<ITextComponent> dummyTooltip = new ArrayList<ITextComponent>();
        if(!getContainer().getSlot(0).getHasStack()) {
            return;
        }
        ItemStack stack = getContainer().getSlot(0).getStack();
        (stack.getItem()).addInformation(stack, null, dummyTooltip, ITooltipFlag.TooltipFlags.ADVANCED);
        for(int i = 0; i < dummyTooltip.size(); i++) {
            drawString(matrixStack, minecraft.getInstance().fontRenderer, dummyTooltip.get(i), 70, 7 + 12 * i, 0xffffff);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);
    }
}
