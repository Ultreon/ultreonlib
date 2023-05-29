/*
 * Copyright (c) 2022. - Qboi SMP Development Team
 * Do NOT redistribute, or copy in any way, and do NOT modify in any way.
 * It is not allowed to hack into the code, use cheats against the code and/or compiled form.
 * And it is not allowed to decompile, modify or/and patch parts of code or classes or in full form.
 * Sharing this file isn't allowed either, and is hereby strictly forbidden.
 * Sharing decompiled code on social media or an online platform will cause in a report on that account.
 *
 * ONLY the owner can bypass these rules.
 */

package com.ultreon.mods.lib.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.Themed;
import com.ultreon.mods.lib.client.gui.widget.BaseButton;
import com.ultreon.mods.lib.client.gui.widget.Button;
import com.ultreon.mods.lib.client.gui.widget.Label;
import com.ultreon.mods.lib.client.gui.widget.ListWidget;
import com.ultreon.mods.lib.mixin.common.AbstractSelectionListAccessor;
import com.ultreon.mods.lib.mixin.common.AbstractWidgetAccessor;
import com.ultreon.mods.lib.mixin.common.TitleScreenAccessor;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings({"UnusedReturnValue", "unused", "SameParameterValue"})
public abstract class GenericMenuScreen extends BaseScreen implements Themed {
    public ResourceLocation background;

    protected final Minecraft mc = Minecraft.getInstance();

    @Nullable
    private final Screen back;
    private final java.util.List<Row> rows = new ArrayList<>();
    private boolean frozen = false;

    private TitleStyle titleStyle;
    private int titleColor;
    private static final int TITLE_COLOR = 0xff606060;
    private static final int TITLE_COLOR_DARK = 0xffffffff;
    private final boolean panorama;
    private Theme theme;
    private boolean initialized;
    private ResourceLocation titleBackground;

    protected GenericMenuScreen(Properties properties) {
        super(properties.title);
        this.theme = properties.theme;
        this.panorama = properties.panorama;

        this.titleColor = theme.getTitleColor();
        this.titleStyle = properties.titleStyle;

        this.font = Minecraft.getInstance().font;
        this.minecraft = Minecraft.getInstance();

        this.back = properties.back;

        reloadTheme();
    }

    private static void emptyAction(BaseButton btn) {
    }

    private static Tooltip nullTooltip(BaseButton btn) {
        return null;
    }

    @NotNull
    private static BaseButton.CommandCallback createCallbackWrapper(Runnable callback) {
        return (btn) -> callback.run();
    }

    protected final boolean initialized() {
        return initialized;
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Component componentR) {
        return addButtonRow(componentL, GenericMenuScreen::emptyAction, componentR, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, BaseButton.CommandCallback onPressL, Component componentR, BaseButton.CommandCallback onPressR) {
        return addButtonRow(componentL, onPressL, GenericMenuScreen::nullTooltip, componentR, onPressR, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Runnable onPressL, Component componentR, Runnable onPressR) {
        return addButtonRow(componentL, createCallbackWrapper(onPressL), GenericMenuScreen::nullTooltip, componentR, createCallbackWrapper(onPressR), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, BaseButton.CommandCallback onPressL, BaseButton.TooltipFactory onTooltipL, Component componentR, BaseButton.CommandCallback onPressR, BaseButton.TooltipFactory onTooltipR) {
        return addButtonRow(componentL, Button.Type.of(theme), onPressL, onTooltipL, componentR, Button.Type.of(theme), onPressR, onTooltipR);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Runnable onPressL, BaseButton.TooltipFactory onTooltipL, Component componentR, Runnable onPressR, BaseButton.TooltipFactory onTooltipR) {
        return addButtonRow(componentL, Button.Type.of(theme), createCallbackWrapper(onPressL), onTooltipL, componentR, Button.Type.of(theme), createCallbackWrapper(onPressR), onTooltipR);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, Component componentR, Button.Type typeR) {
        return addButtonRow(componentL, typeL, GenericMenuScreen::emptyAction, componentR, typeR, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, BaseButton.CommandCallback onPressL, Component componentR, BaseButton.CommandCallback onPressR) {
        return addButtonRow(componentL, typeL, onPressL, componentR, Button.Type.of(theme), onPressR);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, Runnable onPressL, Component componentR, Runnable onPressR) {
        return addButtonRow(componentL, typeL, createCallbackWrapper(onPressL), componentR, Button.Type.of(theme), createCallbackWrapper(onPressR));
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, BaseButton.CommandCallback onPressL, Component componentR, Button.Type typeR, BaseButton.CommandCallback onPressR) {
        return addButtonRow(componentL, Button.Type.of(theme), onPressL, componentR, typeR, onPressR);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Runnable onPressL, Component componentR, Button.Type typeR, Runnable onPressR) {
        return addButtonRow(componentL, Button.Type.of(theme), createCallbackWrapper(onPressL), componentR, typeR, createCallbackWrapper(onPressR));
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, BaseButton.CommandCallback onPressL, Component componentR, Button.Type typeR, BaseButton.CommandCallback onPressR) {
        return addButtonRow(componentL, typeL, onPressL, GenericMenuScreen::nullTooltip, componentR, typeR, onPressR, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, Runnable onPressL, Component componentR, Button.Type typeR, Runnable onPressR) {
        return addButtonRow(componentL, typeL, createCallbackWrapper(onPressL), GenericMenuScreen::nullTooltip, componentR, typeR, createCallbackWrapper(onPressR), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, Runnable onPressL, BaseButton.TooltipFactory onTooltipL, Component componentR, Button.Type typeR, Runnable onPressR, BaseButton.TooltipFactory onTooltipR) {
        return addButtonRow(componentL, typeL, createCallbackWrapper(onPressL), onTooltipL, componentR, typeR, createCallbackWrapper(onPressR), onTooltipR);
    }

    @SuppressWarnings("unused")
    public Pair<BaseButton, BaseButton> addButtonRow(Component componentL, Button.Type typeL, BaseButton.CommandCallback onPressL, BaseButton.TooltipFactory onTooltipL, Component componentR, Button.Type typeR, BaseButton.CommandCallback onPressR, BaseButton.TooltipFactory onTooltipR) {
        if (this.frozen) {
            return null;
        }

        BaseButton left = new Button(0, 0, 0, 0, componentL, onPressL, onTooltipL, typeL);
        BaseButton right = new Button(0, 0, 0, 0, componentR, onPressR, onTooltipR, typeR);

        left.setWidth((width() - 5 - 5) / 2 - 1);
        right.setWidth((width() - 5 - 5) / 2 - 1);
        this.rows.add(new Row(ImmutableList.of(left, right), 24, (width() - 5 - 5) / 2 - 3, 20, 6, 2, 4, 0, 77));
        addRenderableWidget(left);
        addRenderableWidget(right);
        return new Pair<>(left, right);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component) {
        return addButtonRow(component, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Runnable onPress) {
        return addButtonRow(component, btn -> onPress.run(), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, BaseButton.CommandCallback onPress) {
        return addButtonRow(component, onPress, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Runnable onPress, BaseButton.TooltipFactory onTooltip) {
        return addButtonRow(component, Button.Type.of(theme), btn -> onPress.run(), onTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, BaseButton.CommandCallback onPress, BaseButton.TooltipFactory onTooltip) {
        return addButtonRow(component, Button.Type.of(theme), onPress, onTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Button.Type type) {
        return addButtonRow(component, type, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Button.Type type, BaseButton.CommandCallback onPress) {
        return addButtonRow(component, type, onPress, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Button.Type type, Runnable onPress) {
        return addButtonRow(component, type, btn -> onPress.run(), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Button.Type type, Runnable onPress, BaseButton.TooltipFactory onTooltip) {
        return addButtonRow(component, type, btn -> onPress.run(), onTooltip);
    }

    @SuppressWarnings("unused")
    public BaseButton addButtonRow(Component component, Button.Type type, BaseButton.CommandCallback onPress, BaseButton.TooltipFactory onTooltip) {
        if (this.frozen) {
            return null;
        }

        BaseButton button = new Button(0, 0, 0, 0, component, onPress, onTooltip, type);

        button.setWidth(width() - 5 - 5);
        this.rows.add(new Row(ImmutableList.of(button), 24, width() - 6 - 6, 20, 6, 2, 4, 0, 53));
        addRenderableWidget(button);
        return button;
    }

    @SuppressWarnings("unused")
    public GenericMenuScreen addInputRow(EditBox editBox) {
        if (this.frozen) {
            return this;
        }

        editBox.setBordered(false);

        editBox.setWidth(width() - 5 - 5);
        this.rows.add(new Row(ImmutableList.of(editBox), 16, width() - 5 - 5, 12, 7, 3, 4, 0, 37));
        addRenderableWidget(editBox);
        return this;
    }

    @SuppressWarnings("unused")
    public Label addLabel() {
        return addLabel("");
    }

    @SuppressWarnings("unused")
    public Label addLabel(String text) {
        return addLabel(Component.literal(text));
    }

    @SuppressWarnings("unused")
    public Label addLabel(Component component) {
        if (this.frozen) {
            return null;
        }

        Label userWidget = new Label(0, 0, component, theme);
        this.rows.add(new Row(ImmutableList.of(userWidget), font.lineHeight + 4, width() - 5 - 5, font.lineHeight, 8, 2, 4, 0, 226));
        addRenderableOnly(userWidget);
        return userWidget;
    }

    @SuppressWarnings("unused")
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset));
        addRenderableOnly(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset, u, v));
        addRenderableOnly(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v));
        addRenderableOnly(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh));
        addRenderableOnly(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh, int uw) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh, uw));
        addRenderableOnly(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset));
        addRenderableWidget(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset, u, v));
        addRenderableWidget(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v));
        addRenderableWidget(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh));
        addRenderableWidget(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh, int uw) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh, uw));
        addRenderableWidget(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public ListWidget addListRow(int count) {
        return addListRow(count, false);
    }

    @SuppressWarnings("unused")
    public ListWidget addListRow() {
        return addListRow(false);
    }

    @SuppressWarnings("unused")
    public ListWidget addListRow(boolean hasSearch) {
        return addListRow(3, hasSearch);
    }

    @SuppressWarnings("unused")
    public ListWidget addListRow(int count, boolean hasSearch) {
        if (this.frozen) {
            return null;
        }

        ListWidget widget = new ListWidget(this, 0, 0, width() - 6 - 7, count, hasSearch, title);
        widget.setWidth(width() - 6 - 6);
        this.rows.add(new Row(ImmutableList.of(widget), widget.getHeight() + 2, width() - 6 - 6, widget.getHeight(), 6, 0, 4, 0, 226, width(), 1));
        addRenderableWidget(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    private void back(@Nullable net.minecraft.client.gui.components.Button button) {
        back();
    }

    /**
     * Go back to previous screen.
     *
     * @see #onPreBack()
     * @see #onPostBack()
     */
    public final void back() {
        if (onPreBack()) return;

        if (back != null) {
            mc.setScreen(back);
        } else {
            popGuiLayer(this);
        }

        onPostBack();
    }

    @Nullable
    @ApiStatus.Internal
    public final Screen getBack() {
        return back;
    }

    @ExpectPlatform
    @ApiStatus.Internal
    private static void popGuiLayer(GenericMenuScreen screen) {
        throw new AssertionError();
    }

    /**
     * Handle things after going back to the previous screen.
     */
    protected void onPostBack() {

    }

    /**
     * Handle things before going back to the previous screen.
     *
     * @return true to cancel the back event, false to let the screen go back.
     */
    protected boolean onPreBack() {
        return false;
    }

    /**
     * @return the title color.
     */
    public int getTitleColor() {
        return titleColor;
    }

    /**
     * @param titleColor the title color to set.
     */
    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    /**
     * @return gui width.
     */
    protected final int width() {
        return 176;
    }

    /**
     * @return gui height.
     */
    protected final int height() {
        return rows.stream().mapToInt(Row::height).sum() + 4 + getRenderTitleBarHeight();
    }

    @Override
    protected boolean isAtTitleBar(double mouseX, double mouseY) {
        return isPointBetween((int) mouseX, (int) mouseY, left(), top(), width(), getTitleBarHeight());
    }

    private int getRenderTitleBarHeight() {
        if (titleStyle == null) {
            return 0;
        } else {
            return titleStyle.renderHeight;
        }
    }

    private int getTitleBarHeight() {
        if (titleStyle == null) {
            return 0;
        } else {
            return titleStyle.height;
        }
    }

    protected final int left() {
        return (this.width - width()) / 2;
    }

    protected final int right() {
        return left() + width();
    }

    protected final int top() {
        return (this.height - height()) / 2;
    }

    protected final int bottom() {
        return top() + height();
    }

    protected void init() {
        frozen = true;
        initialized = true;
    }

    @Override
    protected void clearWidgets() {

    }

    public void removed() {
        
    }

    @Override
    public void renderBackground(@NotNull PoseStack pose) {
        if (!this.panorama) super.renderBackground(pose);
    }

    public void onPreRender() {

    }

    public void onPostRender() {

    }

    public final void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        // Pre rendering.
        onPreRender();

        // Renders the background.
        if (this.panorama) renderPanorama(pose, partialTicks);
        else renderBackground(pose);

        int curY = top();
        int index = 0;

        // Title rendering (if present).
        switch (this.titleStyle) {
            // Render no title. Only the top border.
            case HIDDEN -> {
                RenderSystem.setShaderTexture(0, background);

                // Draw the top part of the frame.
                blit(pose, left(), top(), 0, 21, width(), 4);
                index++;
                curY += 4;
            }
            // Render normal title and the top border.
            case NORMAL -> {
                RenderSystem.setShaderTexture(0, titleBackground);

                // Draw the title background.
                blit(pose, left(), top(), 0, 21, width(), 16);

                // Draw the title.
                font.draw(pose, this.title, (int) (left() + width() / 2f - this.font.width(this.title.getString()) / 2), top() + 6, this.titleColor);

                index++;
                curY += 16;
            }
            // Render detached title and the top border.
            case DETACHED -> {
                RenderSystem.setShaderTexture(0, titleBackground);

                // Detached part of frame, where the title is.
                blit(pose, left(), top(), 0, 0, width(), 20);

                // Render the frame part (attached part) with the normal background (fixes issues with mixed theme).
                RenderSystem.setShaderTexture(0, background);
                blit(pose, left(), top() + 21, 0, 21, width(), 16);

                // Draw the title.
                font.draw(pose, this.title, (int) (left() + width() / 2f - this.font.width(this.title.getString()) / 2), top() + 6, this.titleColor);

                index++;
                curY += 25;
            }
            default -> throw new IllegalStateException("Unexpected value: " + titleStyle);
        }

        // Title rendering (if present).
        RenderSystem.setShaderTexture(0, background);

        // Render the widget rows.
        renderRows(pose, mouseX, mouseY, partialTicks, curY, index);

        // Post rendering.
        super.render(pose, mouseX, mouseY, partialTicks);
        onPostRender();
    }

    @Override
    public void onClose() {
        if (back != null) {
            Minecraft.getInstance().setScreen(back);
        } else {
            super.onClose();
        }
    }

    @SuppressWarnings({"DuplicatedCode"})
    public void renderPanorama(PoseStack pose, float partialTicks) {
        // Non-null requirements.
        Objects.requireNonNull(this.minecraft, "The screen's Minecraft instance cannot be null!");

        PanoramaScreen.panorama.render(partialTicks, minecraft.screen == this ? 1f : 0f);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TitleScreenAccessor.getPanoramaOverlay());
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        blit(pose, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
    }

    private void renderRows(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks, int y, int index) {
        if (rows.size() > 0) {
            for (Row row : rows.subList(0, rows.size() - 1)) {
                // Render the row background and widgets.
                renderRowBackground(pose, y, row);
                renderSubWidgets(pose, mouseX, mouseY, partialTicks, y, row);

                // Advance in index, and add the current row height to the current y coordinate.
                y += row.height();
                index++;
            }
        }

        if (!rows.isEmpty()) {
            // Get the last row if there were rows at least.
            Row row = rows.get(rows.size() - 1);

            // Render the row background and widgets.
            renderRowBackground(pose, y, row);
            renderSubWidgets(pose, mouseX, mouseY, partialTicks, y, row);

            y += row.height();
        }

        // Render bottom border.
        RenderSystem.setShaderTexture(0, background);
        this.blit(pose, left(), y, 0, 252, width(), 4);
    }

    private void renderSubWidgets(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks, int curY, Row row) {
        // Render row.
        if (row.widgets.size() == 1) {
            // Render row with single widget.
            Renderable widget = row.widgets.get(0);
            repositionAndRender(
                    widget,
                    left() + row.deltaX(),
                    curY + row.deltaY(),
                    row.widgetWidth(),
                    row.widgetHeight(),
                    pose,
                    mouseX, mouseY,
                    partialTicks
            );
        } else {
            // Render row with multiple widgets.
            int x = left() + row.deltaX();
            for (Renderable widget : row.widgets()) {
                repositionAndRender(widget,
                        x,
                        curY + row.deltaY(),
                        row.widgetWidth(),
                        row.widgetHeight(),
                        pose,
                        mouseX, mouseY,
                        partialTicks);

                // Advance x position.
                x += row.widgetWidth() + row.widgetOffset();
            }
        }
    }

    private void repositionAndRender(Renderable widget, int x, int y, int width, int height, @NotNull PoseStack matrices, int mouseX, int mouseY, float partialTicks) {
        if (widget instanceof AbstractWidget absWidget && absWidget instanceof AbstractWidgetAccessor accessor) {
            absWidget.setX(x);
            absWidget.setY(y);
            absWidget.setWidth(width);
            accessor.setHeight(height);
        } else if (widget instanceof AbstractSelectionList<?> absWidget && absWidget instanceof AbstractSelectionListAccessor accessor) {
            accessor.setX0(x);
            accessor.setY0(y);
            accessor.setX1(x + width);
            accessor.setY1(y + height);
            accessor.setWidth(width);
            accessor.setHeight(height);
        } else if (widget instanceof Label label) {
            label.x = x;
            label.y = y;
        } else {
            onReposition(widget, x, y, width, height, matrices, mouseX, mouseY, partialTicks);
        }
        widget.render(matrices, mouseX, mouseY, partialTicks);
    }

    public void onReposition(Renderable widget, int x, int y, int width, int height, PoseStack matrices, int mouseX, int mouseY, float partialTicks) {

    }

    private void renderRowBackground(@NotNull PoseStack pose, int y, Row row) {
        // Render row background.
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, background);
        blit(pose, left(), y, width(), row.height(), row.u(), row.v(), row.uw(), row.vh(), 256, 256);
    }

    @Override
    public void tick() {
        for (Row row : rows) {
            for (Renderable widget : row.widgets()) {
                if (widget instanceof EditBox editBox) {
                    editBox.tick();
                }
            }
        }
    }

    @Override
    public final Vec2 getCloseButtonPos() {
        switch (titleStyle) {
            case NORMAL -> {
                int iconX = right() - 9 - 5;
                int iconY = top() + (int) (5 + (21 / 2f - font.lineHeight / 2f) - 4);
                return new Vec2(iconX, iconY);
            }
            case DETACHED -> {
                int iconX = right() - 9 - 5;
                int iconY = top() + 1 + (int) ((25 - 6) / 2f - font.lineHeight / 2f);
                return new Vec2(iconX, iconY);
            }
        }
        return null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Row row : rows) {
            for (Renderable widget : row.widgets()) {
                if (widget instanceof EditBox editBox) {
                    if (editBox.isFocused()) {
                        editBox.mouseClicked(mouseX, mouseY, button);
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button)/* || this.filterList.mouseClicked(mouseX, mouseY, button)*/;
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void reloadTheme() {
        super.reloadTheme();
        this.theme = UltreonLib.getTheme();
        this.titleColor = theme.getTitleColor();
        this.titleStyle = UltreonLib.getTitleStyle();
        this.background = UltreonLib.res(switch (theme) {
            case DARK -> "textures/gui/menu/generic/generic_menu_dark.png";
            case LIGHT, MIX -> "textures/gui/menu/generic/generic_menu_light.png";
            default -> "textures/gui/menu/generic/generic_menu.png";
        });
        this.titleBackground = UltreonLib.res(switch (theme) {
            case DARK, MIX -> "textures/gui/menu/generic/generic_menu_dark.png";
            case LIGHT -> "textures/gui/menu/generic/generic_menu_light.png";
            default -> "textures/gui/menu/generic/generic_menu.png";
        });
        for (Row row : rows) {
            row.reloadTheme();
        }
    }

    private record Row(ImmutableList<Renderable> widgets, int height, int widgetWidth, int widgetHeight,
                       int deltaX, int deltaY, int widgetOffset, int u, int v, int uw, int vh) {

        public Row(ImmutableList<Renderable> widgets, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset) {
            this(widgets, height, widgetWidth, deltaX, deltaY, widgetOffset, 0, 216);
        }

        public Row(ImmutableList<Renderable> widgets, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset, int u, int v) {
            this(widgets, height, widgetWidth, height, deltaX, deltaY, widgetOffset, u, v);
        }

        public Row(ImmutableList<Renderable> widgets, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v) {
            this(widgets, height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, height);
        }

        public Row(ImmutableList<Renderable> widgets, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh) {
            this(widgets, height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, 176, uh);
        }

        public void reloadTheme() {
            for (Renderable widget : widgets) {
                if (widget instanceof Themed abstractWidget) {
                    abstractWidget.reloadTheme();
                }
            }
        }
    }

    public static class Properties {
        private Component title;
        private TitleStyle titleStyle = UltreonLibConfig.TITLE_STYLE.get();
        private final Theme theme = UltreonLibConfig.THEME.get();
        @Deprecated
        @SuppressWarnings("FieldCanBeLocal")
        private boolean darkMode = theme == Theme.DARK;
        private boolean panorama = Minecraft.getInstance().level == null;
        @Nullable
        private Screen back = null;

        public Properties title(Component title) {
            this.title = title;
            return this;
        }

        public Properties titleText(String text) {
            this.title = Component.literal(text);
            return this;
        }

        public Properties titleLang(String text) {
            this.title = Component.translatable(text);
            return this;
        }

        public Properties titleStyle(TitleStyle titleStyle) {
            this.titleStyle = titleStyle;
            return this;
        }

        @Deprecated
        public Properties darkMode(boolean darkMode) {
            this.darkMode = darkMode;
            return this;
        }

        public Properties panorama() {
            this.panorama = true;
            return this;
        }

        public Properties back(Screen back) {
            this.back = back;
            return this;
        }
    }
}
