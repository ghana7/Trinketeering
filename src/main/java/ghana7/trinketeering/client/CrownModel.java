package ghana7.trinketeering.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class CrownModel extends ArmorBaseModel {
    public CrownModel() {
        super(16, 16, new ResourceLocation("trinketeering:textures/armor/gold.png"));
    }

    @Override
    public void setupArmorParts() {
        //You need to cut out some parts from Blockbenchs exported model, and paste them here.
        //You can see 2 examples of how this may look, adding a cube to the head
        //and a rotated cube on the chest
        //Export the unedited armor template to see which parts you should **not** copy over
        addBoxFromBlockBench(0, 0, 0, 8, 2, 2);
        addBoxFromBlockBench(0, 0, 6, 8, 2, 2);
        addBoxFromBlockBench(0, 0, 2, 2, 2, 4);
        addBoxFromBlockBench(6, 0, 2, 2, 2, 4);

        addBoxFromBlockBench(0,2,0,2,2,2);
        addBoxFromBlockBench(6,2,0,2,2,2);
        addBoxFromBlockBench(6,2,6,2,2,2);
        addBoxFromBlockBench(0,2,6,2,2,2);
        addBoxFromBlockBench(0,2,3,2,2,2);
        addBoxFromBlockBench(6,2,3,2,2,2);
        addBoxFromBlockBench(3,2,0,2,2,2);
        addBoxFromBlockBench(3,2,6,2,2,2);
    }

    private void addBoxFromBlockBench(float x, float y, float z, float width, float height, float depth) {
        armorHead.addBox(x - 4, -8 - height - y, z - 4, width, height, depth);
    }
}
