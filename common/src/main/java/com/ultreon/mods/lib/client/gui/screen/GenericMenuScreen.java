package com.ultreon.mods.lib.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.TitleStyles;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;
import com.ultreon.mods.lib.client.gui.widget.*;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedReturnValue", "unused", "SameParameterValue"})
public abstract class GenericMenuScreen extends ULibScreen implements Stylized {
    protected final Minecraft mc = Minecraft.getInstance();

    @Nullable
    private final net.minecraft.client.gui.screens.Screen back;
    private final List<Row> rows = new ArrayList<>();
    private boolean frozen = false;

    private TitleStyle titleStyle;
    private int titleColor;
    private static final int TITLE_COLOR = 0xff606060;
    private static final int TITLE_COLOR_DARK = 0xffffffff;
    private final boolean panorama;
    private GlobalTheme globalTheme;
    private boolean initialized;
    private ResourceLocation contentSprite;
    private ResourceLocation titleSprite;

    protected GenericMenuScreen(Properties properties) {
        super(properties.title);
        this.globalTheme = properties.globalTheme;
        this.panorama = properties.panorama;

        this.titleColor = this.globalTheme.getTitleColor(WidgetPlacement.WINDOW).getRgb();
        this.titleStyle = properties.titleStyle;

        this.font = Minecraft.getInstance().font;
        this.minecraft = Minecraft.getInstance();

        this.back = properties.back;

        reloadTheme();
    }

    private static void emptyAction(PushButton btn) {
    }

    private static Tooltip nullTooltip(PushButton btn) {
        return null;
    }

    @NotNull
    private static Callback<PushButton> createCallbackWrapper(Runnable callback) {
        return (btn) -> callback.run();
    }

    protected final boolean initialized() {
        return initialized;
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Component componentR) {
        return addButtonRow(componentL, GenericMenuScreen::emptyAction, componentR, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Callback<PushButton> onPressL, Component componentR, Callback<PushButton> onPressR) {
        return addButtonRow(componentL, onPressL, GenericMenuScreen::nullTooltip, componentR, onPressR, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Runnable onPressL, Component componentR, Runnable onPressR) {
        return addButtonRow(componentL, createCallbackWrapper(onPressL), GenericMenuScreen::nullTooltip, componentR, createCallbackWrapper(onPressR), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Callback<PushButton> onPressL, TooltipFactory<PushButton> onTooltipL, Component componentR, Callback<PushButton> onPressR, TooltipFactory<PushButton> onTooltipR) {
        return addButtonRow(componentL, PushButton.Type.of(globalTheme.getContentTheme()), onPressL, onTooltipL, componentR, PushButton.Type.of(globalTheme.getContentTheme()), onPressR, onTooltipR);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Runnable onPressL, TooltipFactory<PushButton> onTooltipL, Component componentR, Runnable onPressR, TooltipFactory<PushButton> onTooltipR) {
        return addButtonRow(componentL, PushButton.Type.of(globalTheme.getContentTheme()), createCallbackWrapper(onPressL), onTooltipL, componentR, PushButton.Type.of(globalTheme.getContentTheme()), createCallbackWrapper(onPressR), onTooltipR);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Component componentR, PushButton.Type typeR) {
        return addButtonRow(componentL, typeL, GenericMenuScreen::emptyAction, componentR, typeR, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Callback<PushButton> onPressL, Component componentR, Callback<PushButton> onPressR) {
        return addButtonRow(componentL, typeL, onPressL, componentR, PushButton.Type.of(globalTheme.getContentTheme()), onPressR);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Runnable onPressL, Component componentR, Runnable onPressR) {
        return addButtonRow(componentL, typeL, createCallbackWrapper(onPressL), componentR, PushButton.Type.of(globalTheme.getContentTheme()), createCallbackWrapper(onPressR));
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Callback<PushButton> onPressL, Component componentR, PushButton.Type typeR, Callback<PushButton> onPressR) {
        return addButtonRow(componentL, PushButton.Type.of(globalTheme.getContentTheme()), onPressL, componentR, typeR, onPressR);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, Runnable onPressL, Component componentR, PushButton.Type typeR, Runnable onPressR) {
        return addButtonRow(componentL, PushButton.Type.of(globalTheme.getContentTheme()), createCallbackWrapper(onPressL), componentR, typeR, createCallbackWrapper(onPressR));
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Callback<PushButton> onPressL, Component componentR, PushButton.Type typeR, Callback<PushButton> onPressR) {
        return addButtonRow(componentL, typeL, onPressL, GenericMenuScreen::nullTooltip, componentR, typeR, onPressR, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Runnable onPressL, Component componentR, PushButton.Type typeR, Runnable onPressR) {
        return addButtonRow(componentL, typeL, createCallbackWrapper(onPressL), GenericMenuScreen::nullTooltip, componentR, typeR, createCallbackWrapper(onPressR), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Runnable onPressL, TooltipFactory<PushButton> onTooltipL, Component componentR, PushButton.Type typeR, Runnable onPressR, TooltipFactory<PushButton> onTooltipR) {
        return addButtonRow(componentL, typeL, createCallbackWrapper(onPressL), onTooltipL, componentR, typeR, createCallbackWrapper(onPressR), onTooltipR);
    }

    @SuppressWarnings("unused")
    public Pair<PushButton, PushButton> addButtonRow(Component componentL, PushButton.Type typeL, Callback<PushButton> onPressL, TooltipFactory<PushButton> onTooltipL, Component componentR, PushButton.Type typeR, Callback<PushButton> onPressR, TooltipFactory<PushButton> onTooltipR) {
        if (this.frozen) {
            return null;
        }

        PushButton left = PushButton.of(componentL, onPressL, onTooltipL).type(typeL);
        PushButton right = PushButton.of(componentR, onPressR, onTooltipR).type(typeR);

        left.setWidth((width() - 5 - 5) / 2 - 1);
        right.setWidth((width() - 5 - 5) / 2 - 1);
        this.rows.add(new Row(ImmutableList.of(left, right), 24, (width() - 5 - 5) / 2 - 3, 20, 6, 2, 4, 0, 77));
        add(left);
        add(right);
        return new Pair<>(left, right);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component) {
        return addButtonRow(component, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, Runnable onPress) {
        return addButtonRow(component, btn -> onPress.run(), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, Callback<PushButton> onPress) {
        return addButtonRow(component, onPress, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, Runnable onPress, TooltipFactory<PushButton> onTooltip) {
        return addButtonRow(component, PushButton.Type.of(globalTheme.getContentTheme()), btn -> onPress.run(), onTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, Callback<PushButton> onPress, TooltipFactory<PushButton> onTooltip) {
        return addButtonRow(component, PushButton.Type.of(globalTheme.getContentTheme()), onPress, onTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, PushButton.Type type) {
        return addButtonRow(component, type, GenericMenuScreen::emptyAction);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, PushButton.Type type, Callback<PushButton> onPress) {
        return addButtonRow(component, type, onPress, GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, PushButton.Type type, Runnable onPress) {
        return addButtonRow(component, type, btn -> onPress.run(), GenericMenuScreen::nullTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, PushButton.Type type, Runnable onPress, TooltipFactory<PushButton> onTooltip) {
        return addButtonRow(component, type, btn -> onPress.run(), onTooltip);
    }

    @SuppressWarnings("unused")
    public PushButton addButtonRow(Component component, PushButton.Type type, Callback<PushButton> onPress, TooltipFactory<PushButton> onTooltip) {
        if (this.frozen) {
            return null;
        }

        PushButton button = PushButton.of(component, onPress, onTooltip).type(type);

        button.setWidth(width() - 5 - 5);
        this.rows.add(new Row(ImmutableList.of(button), 24, width() - 6 - 6, 20, 6, 2, 4, 0, 53));
        add(button);
        return button;
    }

    @SuppressWarnings("unused")
    public GenericMenuScreen addInputRow(TextBox editBox) {
        if (this.frozen) {
            return this;
        }

        editBox.setBordered(false);

        editBox.setWidth(width() - 5 - 5);
        this.rows.add(new Row(ImmutableList.of(editBox), 16, width() - 5 - 5, 12, 7, 3, 4, 0, 37));
        add(editBox);
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

        Label label = new Label(0, 0, component);
        this.rows.add(new Row(ImmutableList.of(label), font.lineHeight + 4, width() - 5 - 5, font.lineHeight, 8, 2, 4, 0, 226));
        add(label);
        return label;
    }

    @Deprecated(forRemoval = true)
    @SuppressWarnings({"unused", "removal"})
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset));
        addRenderableOnly(widget);
        return widget;
    }

    @Deprecated(forRemoval = true)
    @SuppressWarnings({"unused", "removal"})
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset, u, v));
        addRenderableOnly(widget);
        return widget;
    }

    @Deprecated(forRemoval = true)
    @SuppressWarnings({"unused", "removal"})
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v));
        addRenderableOnly(widget);
        return widget;
    }

    @Deprecated(forRemoval = true)
    @SuppressWarnings({"unused", "removal"})
    public <T extends Renderable> T addStatic(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh));
        addRenderableOnly(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends UIWidget<?>> T addStatic(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh, int uw) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh, uw));
        add(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends UIWidget<?>> T add(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset));
        add(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends UIWidget<?>> T add(T widget, int height, int widgetWidth, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, deltaX, deltaY, widgetOffset, u, v));
        add(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends UIWidget<?>> T add(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v));
        add(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends UIWidget<?>> T add(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh));
        add(widget);
        return widget;
    }

    @SuppressWarnings("unused")
    public <T extends UIWidget<?>> T add(T widget, int height, int widgetWidth, int widgetHeight, int deltaX, int deltaY, int widgetOffset, int u, int v, int uh, int uw) {
        if (this.frozen) {
            return null;
        }

        this.rows.add(new Row(ImmutableList.of(widget), height, widgetWidth, widgetHeight, deltaX, deltaY, widgetOffset, u, v, uh, uw));
        add(widget);
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

        ListWidget widget = new ListWidget(this, count, hasSearch, title);
        widget.setWidth(width() - 6 - 6);
        this.rows.add(new Row(ImmutableList.of(widget), widget.getHeight() + 2, width() - 6 - 6, widget.getHeight(), 6, 0, 4, 0, 226, width(), 1));
        add(widget);
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
    @Override
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
    public final net.minecraft.client.gui.screens.Screen getBack() {
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
        return this.rowsHeight() + 4 + this.getRenderTitleBarHeight();
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

    @Override
    protected void initWidgets() {
        frozen = true;
        initialized = true;
    }

    @Override
    protected final void clearWidgets() {
        // Do nothing
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, int i, int j, float f) {
        if (!this.panorama) super.renderBackground(gfx, i, j, f);
    }

    public void onPreRender() {

    }

    public void onPostRender() {

    }

    @Override
    public final void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        // Pre rendering.
        onPreRender();

        ResourceLocation contentFrame = UltreonLib.getTheme().getContentTheme().getFrameSprite();
        ResourceLocation windowFrame = UltreonLib.getTheme().getWindowTheme().getFrameSprite();

        // Renders the background.
        if (this.panorama) renderPanorama(gfx, partialTicks);
        else renderBackground(gfx, mouseX, mouseY, partialTicks);

        int rowsHeight = rowsHeight();

        if (this.titleStyle.equals(TitleStyles.HIDDEN)) {
            gfx.blitSprite(this.contentSprite, left(), top(), width(), height());
        } else if (this.titleStyle.equals(TitleStyles.NORMAL)) {
            gfx.blitSprite(this.contentSprite, left(), top(), width(), height());
            gfx.drawString(this.font, this.title, (int) (left() + width() / 2f - this.font.width(this.title.getString()) / 2), top() + 6, this.titleColor, false);
        } else if (this.titleStyle.equals(TitleStyles.DETACHED)) {// Draw title bar frame.
            gfx.blitSprite(this.titleSprite, left(), top(), width(), getTitleBarHeight());
            gfx.blitSprite(this.contentSprite, left(), top() + getTitleBarHeight() + 1, width(), height() - getTitleBarHeight() - 1);
            gfx.drawString(this.font, this.title, (int) (left() + width() / 2f - this.font.width(this.title.getString()) / 2), top() + 6, this.titleColor, false);
        }

        int rowStartY = top() + this.getRenderTitleBarHeight();
        int index = 1;

        // Render the widget rows.
        this.renderRows(gfx, mouseX, mouseY, partialTicks, rowStartY, index);

        // Post rendering.
        super.render(gfx, mouseX, mouseY, partialTicks);
        this.onPostRender();
    }

    private int rowsHeight() {
        return this.rows.stream().mapToInt(Row::height).sum();
    }

    @Override
    public void onClose() {
        if (back != null) {
            Minecraft.getInstance().setScreen(back);
        } else {
            super.onClose();
        }
    }

    /**
     * Render the panorama background/
     *
     * @param gfx         pose stack.
     * @param partialTicks render frame time.
     */
    public void renderPanorama(GuiGraphics gfx, float partialTicks) {
        PanoramaScreen.PANORAMA.render(partialTicks, Mth.clamp(1.0f, 0.0f, 1.0f));
        RenderSystem.enableBlend();
        gfx.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gfx.blit(PanoramaScreen.PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0f, 0.0f, 16, 128, 16, 128);
        gfx.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderRows(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks, int y, int index) {
        if (!rows.isEmpty()) {
            for (Row row : rows.subList(0, rows.size() - 1)) {
                // Render the row widgets.
                renderSubWidgets(gfx, mouseX, mouseY, partialTicks, y, row);

                // Advance in index, and add the current row height to the current y coordinate.
                y += row.height();
                index++;
            }
        }

        if (!rows.isEmpty()) {
            // Get the last row if there were rows at least.
            Row row = rows.get(rows.size() - 1);

            // Render the row widgets.
            renderSubWidgets(gfx, mouseX, mouseY, partialTicks, y, row);
        }
    }

    private void renderSubWidgets(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks, int curY, Row row) {
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
                    gfx,
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
                        gfx,
                        mouseX, mouseY,
                        partialTicks);

                // Advance x position.
                x += row.widgetWidth() + row.widgetOffset();
            }
        }
    }

    private void repositionAndRender(Renderable widget, int x, int y, int width, int height, @NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        if (widget instanceof AbstractWidget absWidget) {
            absWidget.setX(x);
            absWidget.setY(y);
            absWidget.setWidth(width);
            absWidget.setHeight(height);
        } else if (widget instanceof AbstractSelectionList<?> absWidget) {
            absWidget.setX(x);
            absWidget.setY(y);
            absWidget.setWidth(width);
            absWidget.setHeight(height);
        } else if (widget instanceof Label label) {
            label.x = x;
            label.y = y;
        } else {
            onReposition(widget, x, y, width, height, gfx, mouseX, mouseY, partialTicks);
        }
        widget.render(gfx, mouseX, mouseY, partialTicks);
    }

    public void onReposition(Renderable widget, int x, int y, int width, int height, GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public final Vec2 getCloseButtonPos() {
        if (titleStyle.equals(TitleStyles.NORMAL)) {
            int iconX = right() - 9 - 5;
            int iconY = top() + (int) (5 + (21 / 2f - font.lineHeight / 2f) - 4);
            return new Vec2(iconX, iconY);
        } else if (titleStyle.equals(TitleStyles.DETACHED)) {
            int iconX = right() - 9 - 5;
            int iconY = top() + 1 + (int) ((25 - 6) / 2f - font.lineHeight / 2f);
            return new Vec2(iconX, iconY);
        }
        return null;
    }

    @Override
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

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void reloadTheme() {
        super.reloadTheme();
        this.globalTheme = UltreonLib.getTheme();
        this.titleColor = globalTheme.getTitleColor(WidgetPlacement.WINDOW).getRgb();
        this.titleStyle = UltreonLib.getTitleStyle();
        this.contentSprite = UltreonLib.getTheme().getContentTheme().getFrameSprite();
        this.titleSprite = UltreonLib.getTheme().getWindowTheme().getFrameSprite();

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
                if (widget instanceof Stylized abstractWidget) {
                    abstractWidget.reloadTheme();
                }
            }
        }
    }

    public static class Properties {
        private Component title;
        private TitleStyle titleStyle = UltreonLib.getTitleStyle();
        private final GlobalTheme globalTheme = UltreonLib.getTheme();
        private boolean panorama = Minecraft.getInstance().level == null;
        @Nullable
        private net.minecraft.client.gui.screens.Screen back = Minecraft.getInstance().screen;

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

        public Properties panorama() {
            this.panorama = true;
            return this;
        }

        public Properties back(@Nullable net.minecraft.client.gui.screens.Screen back) {
            this.back = back;
            return this;
        }
    }
}
