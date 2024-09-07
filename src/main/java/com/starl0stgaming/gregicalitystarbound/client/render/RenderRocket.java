package com.starl0stgaming.gregicalitystarbound.client.render;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@SideOnly(Side.CLIENT)
public class RenderRocket extends Render<EntityRocket> {
    private AxisAlignedBB blocks;

    public RenderRocket(RenderManager manager) {
        super(manager);
        this.blocks = new AxisAlignedBB(-252, 5, 100, -254, 11, 98);
    }

    @Override
    public void doRender(EntityRocket entity, double x, double y, double z, float entityYaw, float partialTicks) {
        for(BlockPos pos : BlockPos.getAllInBox((int) blocks.minX, (int) blocks.minY, (int) blocks.minZ,
                (int) blocks.maxX, (int) blocks.maxY, (int) blocks.maxZ)) {

            GlStateManager.pushMatrix();
            IBlockState state = entity.world.getBlockState(pos);

            Tessellator tessellator = Tessellator.getInstance();

            Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                    entity.world,
                    Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state),
                    state,
                    entity.getPosition(),
                    tessellator.getBuffer(),
                    true
            );

            tessellator.draw();
            GlStateManager.popMatrix();
        }
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRocket entity) {
        return null;
    }
}
