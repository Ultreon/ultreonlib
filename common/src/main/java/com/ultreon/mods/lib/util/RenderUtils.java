package com.ultreon.mods.lib.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RenderUtils {
    /**
     * Renders an entity in the GUI or HUD.
     *
     * @param gfx the gui graphics provided by the GUI or HUD.
     * @param posX the x position to render the entity.
     * @param posY the y position to render the entity.
     * @param xRot the x rotation override for the entity.
     * @param yRot the y rotation override for the entity.
     * @param scale the scale to render the entity in.
     * @param entity the entity to render.
     */
    public static void renderEntityInGui(GuiGraphics gfx, int posX, int posY, float xRot, float yRot, float scale, Entity entity) {
        renderEntityInGui(gfx, posX, posY, xRot, yRot, scale, 0.0625f, entity);
    }

    /**
     * Renders a living entity in the GUI or HUD.
     *
     * @param gfx the gui graphics provided by the GUI or HUD.
     * @param posX the x position to render the entity.
     * @param posY the y position to render the entity.
     * @param xRot the x rotation override for the entity.
     * @param yRot the y rotation override for the entity.
     * @param scale the scale to render the entity in.
     * @param entity the living entity to render.
     */
    public static void renderEntityInGui(GuiGraphics gfx, int posX, int posY, float xRot, float yRot, float scale, LivingEntity entity) {
        renderEntityInGui(gfx, posX, posY, xRot, yRot, scale, 0.0625f, entity);
    }

    /**
     * Renders an entity in the GUI or HUD, this method also uses GL Scissor to only render the entity within bounds.
     *
     * @param gfx the gui graphics provided by the GUI or HUD.
     * @param posX the x position to render the entity.
     * @param posY the y position to render the entity.
     * @param xRot the x rotation override for the entity.
     * @param yRot the y rotation override for the entity.
     * @param scale the scale to render the entity in.
     * @param cutX the x position of the cut.
     * @param cutY the x position of the cut.
     * @param cutWidth the width of the cut.
     * @param cutHeight the height of the cut.
     * @param entity the entity to render.
     */
    public static void renderEntityInGui(GuiGraphics gfx, int posX, int posY, float xRot, float yRot, float scale, int cutX, int cutY, int cutWidth, int cutHeight, Entity entity) {
        if (ScissorStack.pushScissorTranslated(gfx, cutX, cutY, cutWidth, cutHeight)) {
            renderEntityInGui(gfx, posX, posY, xRot, yRot, scale, entity);
            ScissorStack.popScissor();
        }
    }

    /**
     * Renders an entity in the GUI or HUD.
     *
     * @param gfx the gui graphics provided by the GUI or HUD.
     * @param posX the x position to render the entity.
     * @param posY the y position to render the entity.
     * @param xRot the x rotation override for the entity.
     * @param yRot the y rotation override for the entity.
     * @param scale the scale to render the entity in.
     * @param offset the y-offset of the entity.
     * @param entity the entity to render.
     */
    public static void renderEntityInGui(GuiGraphics gfx, int posX, int posY, float xRot, float yRot, float scale, float offset, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            renderEntityInGui(gfx, posX, posY, xRot, yRot, scale, livingEntity);
            return;
        }

        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf cameraOrientation = new Quaternionf().rotateX(yRot * 20.0f * ((float) Math.PI / 180));
        rotation.mul(cameraOrientation);

        float entityYRot = entity.getYRot();
        float entityXRot = entity.getXRot();
        float yHeadRot = entity.getYHeadRot();

        entity.setYBodyRot(180.0f + xRot * 20.0f);
        entity.setYRot(180.0f + yRot * 40.0f);
        entity.setXRot(-yRot * 20.0f);
        entity.setYHeadRot(entity.getYRot());

        Vector3f position3D = new Vector3f(0.0f, entity.getBbHeight() / 2.0f + offset, 0.0f);
        RenderUtils.renderEntityInInventory(gfx, posX, posY, (int) scale, position3D, rotation, cameraOrientation, entity);

        entity.setYBodyRot(entityYRot);
        entity.setYRot(entityYRot);
        entity.setXRot(entityXRot);
        entity.setYHeadRot(yHeadRot);
    }

    /**
     * Renders a living entity in the GUI or HUD.
     *
     * @param gfx the gui graphics provided by the GUI or HUD.
     * @param posX the x position to render the entity.
     * @param posY the y position to render the entity.
     * @param xRot the x rotation override for the entity.
     * @param yRot the y rotation override for the entity.
     * @param scale the scale to render the entity in.
     * @param offset the y-offset of the entity.
     * @param entity the living entity to render.
     */
    public static void renderEntityInGui(GuiGraphics gfx, int posX, int posY, float xRot, float yRot, float scale, float offset, LivingEntity entity) {
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf cameraOrientation = new Quaternionf().rotateX(xRot * 20.0f * ((float) Math.PI / 180));
        rotation.mul(cameraOrientation);

        float yBodyRot = entity.yBodyRot;
        float entityYRot = entity.getYRot();
        float entityXRot = entity.getXRot();
        float yHeadRotO = entity.yHeadRotO;
        float yHeadRot = entity.yHeadRot;

        entity.yBodyRot = 180.0f + yRot * 20.0f;
        entity.setYRot(180.0f + yRot * 40.0f);
        entity.setXRot(-xRot * 20.0f);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();

        Vector3f vector3f = new Vector3f(0.0f, entity.getBbHeight() / 2.0f + offset, 0.0f);
        RenderUtils.renderEntityInInventory(gfx, posX, posY, scale, vector3f, rotation, cameraOrientation, entity);

        entity.yBodyRot = yBodyRot;
        entity.setYRot(entityYRot);
        entity.setXRot(entityXRot);
        entity.yHeadRotO = yHeadRotO;
        entity.yHeadRot = yHeadRot;
    }

    @SuppressWarnings("deprecation")
    private static void renderEntityInInventory(GuiGraphics gfx, float posX, float posY, float scale, Vector3f position3D, Quaternionf rotation, @Nullable Quaternionf cameraOrientation, Entity entity) {
        gfx.pose().pushPose();
        gfx.pose().translate(posX, posY, 50.0);
        gfx.pose().mulPoseMatrix(new Matrix4f().scaling(scale, scale, -scale));
        gfx.pose().translate(position3D.x, position3D.y, position3D.z);
        gfx.pose().mulPose(rotation);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (cameraOrientation != null) {
            cameraOrientation.conjugate();
            dispatcher.overrideCameraOrientation(cameraOrientation);
        }
        dispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> dispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, gfx.pose(), gfx.bufferSource(), 0xF000F0));
        gfx.flush();
        dispatcher.setRenderShadow(true);
        gfx.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
