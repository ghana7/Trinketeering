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
        addBoxFromBlockBench(-1, -1, -1, 10, 2, 2);
        addBoxFromBlockBench(-1, -1, 7, 10, 2, 2);
        addBoxFromBlockBench(-1, -1, 1, 2, 2, 6);
        addBoxFromBlockBench(7, -1, 1, 2, 2, 6);

        addBoxFromBlockBench(-1,1,-1,2,2,2);
        addBoxFromBlockBench(7,1,-1,2,2,2);
        addBoxFromBlockBench(7,1,7,2,2,2);
        addBoxFromBlockBench(-1,1,7,2,2,2);
        addBoxFromBlockBench(-1,1,3,2,2,2);
        addBoxFromBlockBench(7,1,3,2,2,2);
        addBoxFromBlockBench(3,1,-1,2,2,2);
        addBoxFromBlockBench(3,1,7,2,2,2);
    }

    private void addBoxFromBlockBench(float x, float y, float z, float width, float height, float depth) {
        armorHead.addBox(x - 4, -8 - height - y, z - 4, width, height, depth);
    }
}
