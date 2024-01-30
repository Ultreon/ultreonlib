package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.window.TitleBarAccess;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;
import com.ultreon.mods.lib.client.gui.widget.ContainerWidget;
import com.ultreon.mods.lib.client.gui.widget.UIWidget;
import com.ultreon.mods.lib.client.gui.widget.ULibWidget;
import com.ultreon.mods.lib.client.gui.widget.WidgetsContainer;
import com.ultreon.mods.lib.client.gui.widget.menu.ButtonMenuItem;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import com.ultreon.mods.lib.client.input.MouseButton;
import com.ultreon.mods.lib.client.theme.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ULibScreen extends Screen implements Stylized, WidgetsContainer, TabPage {
    private static final String CLOSE_ICON = "×";
    private static final String CLOSE_ICON_HOVER = ChatFormatting.RED + CLOSE_ICON;

    protected static final int BORDER_SIZE = 7;

    private ContextMenu contextMenu = null;
    protected GlobalTheme globalTheme;
    private Screen back;
    private TitleBar titleBar;
    private boolean initialized = false;
    private final List<? extends ULibWidget> widgets = new ArrayList<>();

    protected ULibScreen(Component title) {
        this(title, Minecraft.getInstance().screen);
        this.minecraft = Minecraft.getInstance();
        this.titleBar = new TitleBar(this, title);
    }

    protected ULibScreen(Component title, Screen back) {
        super(title);
        this.globalTheme = UltreonLib.getTheme();
        this.back = back;
    }

    @Override
    protected final void init() {
        super.init();

        if (!this.initialized) {
            this.initialized = true;
            this.initWidgets();
        }

        children().stream()
                .filter(listener -> listener instanceof ULibWidget)
                .map(listener -> (ULibWidget) listener)
                .forEach(ULibWidget::revalidate);
    }

    @Override
    public @NotNull List<? extends ULibWidget> children() {
        return super.children().stream().map(guiEventListener -> (ULibWidget) guiEventListener).toList();
    }

    public void initWidgets() {

    }

    @Override
    public Component getMessage() {
        return this.titleBar.getMessage();
    }

    @Override
    public void setMessage(Component message) {
        this.titleBar.setMessage(message);
    }

    @Override
    protected void clearWidgets() {

    }

    public void show() {
        show(this);
    }

    @SuppressWarnings("unused")
    private static void show(ULibScreen screen) {
        throw new AssertionError();
    }

    @Override
    public ThemeComponent getPlacement() {
        return WidgetPlacement.WINDOW;
    }

    @Override
    public void reloadTheme() {
        this.globalTheme = UltreonLib.getTheme();
    }

    /**
     * @deprecated Use {@link #render(GuiRenderer, int, int, float)} instead, this method will be set to final in the future
     */
    @Override
    @Deprecated
    public /*final*/ void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        GuiRenderer renderer = new GuiRenderer(guiGraphics, GlobalTheme.get(), TitleStyle.get());
        this.renderBackground(renderer, mouseX, mouseY, partialTicks);
        this.render(renderer, mouseX, mouseY, partialTicks);

        this.renderCloseButton(renderer, mouseX, mouseY);

//        GuiRenderer renderer = new GuiRenderer(guiGraphics, GlobalTheme.get(), TitleStyle.get());
//
//        this.renderBackground(renderer, mouseX, mouseY, partialTicks);
//
//        if (this.titleBar.getCurrentTab() == this) this.render(renderer, mouseX, mouseY, partialTicks);
//        else this.titleBar.getCurrentTab().render(renderer, mouseX, mouseY, partialTicks);
//
//        titleBar.renderWidget(renderer, mouseX, mouseY, partialTicks);
    }

    public void setWidth(int width) {
        this.titleBar.setWindowWidth(width);
        for (ULibWidget widget : children()) {
            widget.revalidate();
        }
    }

    public void setHeight(int height) {
        this.titleBar.setWindowHeight(height);
        for (ULibWidget widget : children()) {
            widget.revalidate();
        }
    }

    public void resize(int width, int height) {
        this.titleBar.setWindowWidth(width);
        this.titleBar.setWindowHeight(height);

        for (ULibWidget widget : children()) {
            widget.revalidate();
        }
    }

    public Vector2i getSize(Vector2i tmp) {
        return tmp.set(this.titleBar.getWindowWidth(), this.titleBar.getWindowHeight());
    }

    public void render(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        this.renderWidget(renderer, mouseX, mouseY, partialTicks);

        boolean flag = contextMenu != null && contextMenu.isMouseOver(mouseX, mouseY);

        int mx = flag ? Integer.MIN_VALUE : mouseX;
        int my = flag ? Integer.MIN_VALUE : mouseY;
        for (GuiEventListener widget : children()) {
            if (widget instanceof UIWidget<?> uiWidget)
                uiWidget.render(renderer, mx, my, partialTicks);
            else if (widget instanceof Renderable renderable)
                renderable.render(renderer.gfx(), mx, my, partialTicks);
        }

        renderContextMenu(renderer, mouseX, mouseY, partialTicks);
    }

    public void renderWidget(GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * @deprecated Use {@link #renderDirtBackground(GuiRenderer)} instead, this method will be set to final in the future
     */
    @Override
    @Deprecated
    public /*final*/ void renderDirtBackground(@NotNull GuiGraphics gfx) {
        super.renderDirtBackground(gfx);
    }

    public void renderDirtBackground(@NotNull GuiRenderer renderer) {
        super.renderDirtBackground(renderer.gfx());
    }

    /**
     * @deprecated Use {@link #renderTransparentBackground(GuiRenderer)} instead, this method will be set to final in the future
     */
    @Deprecated
    @Override
    public /*final*/ void renderTransparentBackground(@NotNull GuiGraphics gfx) {
        super.renderTransparentBackground(gfx);
    }

    public void renderTransparentBackground(@NotNull GuiRenderer renderer) {
        super.renderTransparentBackground(renderer.gfx());
    }

    /**
     * @deprecated Use {@link #renderBackground(GuiRenderer, int, int, float)} instead, this method will be set to final in the future
     */
    @Override
    @Deprecated
    public /*final*/ void renderBackground(@NotNull GuiGraphics gfx, int i, int j, float f) {
        super.renderBackground(gfx, i, j, f);
    }

    public void renderBackground(GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(renderer.gfx(), mouseX, mouseY, partialTicks);
    }

    private void renderContextMenu(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        ContextMenu menu = contextMenu;
        if (menu != null) {
            RenderSystem.disableDepthTest();
            menu.render(renderer, mouseX, mouseY, partialTicks);
            RenderSystem.enableDepthTest();
        }
    }

    @Nullable
    public Vec2 getCloseButtonPos() {
        return this.titleBar.getCloseButtonPos();
    }

    protected final boolean isPointBetween(int mouseX, int mouseY, int x, int y, int w, int h) {
        final int x1 = x + w;
        final int y1 = y + h;

        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }

    protected final void renderCloseButton(@NotNull GuiRenderer renderer, int mouseX, int mouseY) {
        if (!this.shouldCloseOnEsc()) {
            return;
        }

        Vec2 iconPos = getCloseButtonPos();
        if (iconPos != null) {
            this.renderCloseButton(renderer, mouseX, mouseY, (int) iconPos.x, (int) iconPos.y);
        }
    }


    public void renderCloseButton(GuiRenderer renderer, int mouseX, int mouseY, int x, int y) {
        if (isPointBetween(mouseX, mouseY, x, y, 6, 6))
            renderer.textCenter(CLOSE_ICON_HOVER, x + 3, y, TitleStyle.get().getTitleColor().getRgb(), false);
        else
            renderer.textCenter(CLOSE_ICON, x + 3, y, TitleStyle.get().getTitleColor().getRgb(), false);
    }

    private boolean isHoveringContextMenu(int mouseX, int mouseY) {
        return contextMenu != null &&
                mouseX >= contextMenu.getX() && mouseX <= contextMenu.getX() + contextMenu.getWidth() &&
                mouseY >= contextMenu.getY() && mouseY <= contextMenu.getY() + contextMenu.getHeight();
    }

    /**
     * Event handler for mouse motion.
     *
     * @param mouseX the X-position of the mouse it moved to.
     * @param mouseY the Y-position of the mouse it moved to.
     */
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (isHoveringContextMenu((int) mouseX, (int) mouseY)) {
            contextMenu.mouseMoved(mouseX, mouseY);
            return;
        }

        super.mouseMoved(mouseX, mouseY);
    }

    /**
     * Event handler for mouse scrolling.
     *
     * @param mouseX  the X-position of the mouse it scrolled at.
     * @param mouseY  the Y-position of the mouse it scrolled at.
     * @param amountX the amount of partial clicks it scrolled in the X-axis.
     * @param amountY the amount of partial clicks it scrolled in the Y-axis.
     */
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        return super.mouseScrolled(mouseX, mouseY, amountX, amountY);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    /**
     * Event handler for mouse releasing.
     *
     * @param mouseX the X-position of the mouse it released the {@code button} at.
     * @param mouseY the Y-position of the mouse it released the {@code button} at.
     * @param button the mouse button the mouse released.
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isHoveringContextMenu((int) mouseX, (int) mouseY) && contextMenu.mouseReleased(mouseX, mouseY, button)) {
            return true;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    /**
     * Event handler for mouse clicking.
     *
     * @param mouseX the X-position of the mouse it clicked at.
     * @param mouseY the Y-position of the mouse it clicked at.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isAtCloseButton(mouseX, mouseY)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            close();
            return true;
        }

        if (isHoveringContextMenu((int) mouseX, (int) mouseY) && contextMenu.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (button == MouseButton.RIGHT && isAtTitleBar(mouseX, mouseY)) {
            ContextMenu menu = new ContextMenu(null);
            menu.addItem(new ButtonMenuItem(menu, Component.literal("Close"), menuItem -> closeScreen()));
            placeContextMenu(menu);
            return true;
        }
        if (button == MouseButton.RIGHT) {
            ContextMenu menu = createContextMenu((int) mouseX, (int) mouseY);
            GuiEventListener it = getChildAt(mouseX, mouseY).orElse(null);
            if (it instanceof HasContextMenu contextMenuHolder) {
                contextMenuHolder.contextMenu((int) mouseX, (int) mouseY, button);
            }
            if (menu != null) {
                placeContextMenu(menu);
                return true;
            }
        }
        if (button == MouseButton.LEFT && contextMenu != null && !contextMenu.isMouseOver(mouseX, mouseY)) {
            contextMenu.onClose();
            contextMenu = null;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Closes the screen.
     */
    public final void closeScreen() {
        this.onClose();
    }

    /**
     * Event handler for mouse clicking.
     *
     * @param x the start X-position of the context menu.
     * @param y the start Y-position of the context menu.
     */
    protected @Nullable ContextMenu createContextMenu(int x, int y) {
        ULibWidget exactWidgetAt = this.getExactWidgetAt(x, y);
        if (exactWidgetAt != null) {
            return exactWidgetAt.createContextMenu(x, y);
        }
        return null;
    }

    private ULibWidget getExactWidgetAt(int x, int y) {
        for (GuiEventListener e : children()) {
            if (e.isMouseOver(x, y)) {
                if (e instanceof ContainerWidget elem) {
                    ULibWidget at = elem.getExactWidgetAt(x, y);
                    return at == null ? elem : at;
                } else if (e instanceof ULibWidget widget) {
                    return widget;
                }
            }
        }

        return null;
    }


    protected boolean isAtTitleBar(double mouseX, double mouseY) {
        return false;
    }

    public void placeContextMenu(@NotNull ContextMenu menu) {
        this.contextMenu = Objects.requireNonNull(menu);
    }

    public void closeContextMenu() {
        this.contextMenu = null;
    }

    /**
     * @deprecated Use {@link #close()} instead
     */
    @Deprecated(forRemoval = true)
    protected void back() {
        this.close();
    }

    protected void close() {
        Minecraft.getInstance().setScreen(back);
    }

    @Override
    public void onClose() {
        this.close();
        super.clearWidgets();
    }

    protected final boolean isAtCloseButton(int mouseX, int mouseY) {
        Vec2 iconPos = getCloseButtonPos();
        if (iconPos == null) {
            return false;
        }

        return isPointBetween(mouseX, mouseY, (int) iconPos.x, (int) iconPos.y, 6, 6);
    }

    protected final boolean isAtCloseButton(double mouseX, double mouseY) {
        return isAtCloseButton((int) mouseX, (int) mouseY);
    }

    public static void renderFrame(GuiGraphics gfx, int x, int y, int width, int height, Theme theme) {
        renderFrame(gfx, x, y, width, height, theme, FrameType.NORMAL);
    }

    public static void renderFrame(GuiGraphics gfx, int x, int y, int width, int height, Theme theme, FrameType type) {
        var tex = theme.getFrameSprite();
        gfx.blitSprite(type.mapSprite(tex), x, y, width, height);
    }

    public static void renderTitleFrame(GuiGraphics gfx, int x, int y, int width, int height, GlobalTheme globalTheme) {
        renderTitleFrame(gfx, x, y, width, height, globalTheme, FrameType.NORMAL);
    }

    public static void renderTitleFrame(GuiGraphics gfx, int x, int y, int width, int height, GlobalTheme globalTheme, FrameType type) {
        var tex = globalTheme.getWindowTheme().getFrameSprite();
        gfx.blitSprite(type.mapSprite(tex), x, y, width, height);
    }

    public void open() {
        this.back = Minecraft.getInstance().screen;
        Minecraft.getInstance().setScreen(this);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, String text, int x, int y, int color) {
        gfx.drawString(font, text, x - font.width(text) / 2, y, color);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font, formattedCharSequence, x - font.width(formattedCharSequence) / 2, y, color);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, FormattedCharSequence text, int x, int y, int color) {
        gfx.drawString(font, text, x - font.width(text) / 2, y, color);
    }

    @Override
    @Deprecated(forRemoval = true)
    protected <T extends GuiEventListener & Renderable & NarratableEntry> @NotNull T addRenderableWidget(@NotNull T widget) {
        return super.addRenderableWidget(widget);
    }

    @Override
    @Deprecated(forRemoval = true)
    protected <T extends Renderable> @NotNull T addRenderableOnly(@NotNull T renderable) {
        return super.addRenderableOnly(renderable);
    }

    @Override
    protected <T extends GuiEventListener & NarratableEntry> @NotNull T addWidget(@NotNull T listener) {
        return super.addWidget(listener);
    }

    public <T extends ULibWidget> T add(@NotNull T widget) {
        widget.setPlacement(WidgetPlacement.CONTENT);
        return super.addRenderableWidget(widget);
    }

    public <T extends ULibWidget> @NotNull T addTitle(@NotNull T widget) {
        widget.setPlacement(WidgetPlacement.WINDOW);
        this.titleBar.add(widget);
        return super.addRenderableWidget(widget);
    }

    protected TitleBarAccess titleBarAccess() {
        return this.titleBar;
    }

    public void setFullScreen(boolean fullScreen) {
        this.titleBar.setFullScreen(fullScreen);
    }

    public boolean isFullScreen() {
        return this.titleBar.isFullScreen();
    }

    public Vector2i getContentOffset(Vector2i tmp) {
        return tmp.set(this.titleBar.getContentOffset());
    }
}
