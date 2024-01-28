package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;
import com.ultreon.mods.lib.client.gui.widget.ContainerWidget;
import com.ultreon.mods.lib.client.gui.widget.ULibWidget;
import com.ultreon.mods.lib.client.gui.widget.WidgetsContainer;
import com.ultreon.mods.lib.client.gui.widget.menu.ButtonMenuItem;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import com.ultreon.mods.lib.client.input.MouseButton;
import com.ultreon.mods.lib.client.theme.*;
import com.ultreon.mods.lib.mixin.common.ScreenAccess;
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

import java.util.Objects;

public abstract class ULibScreen extends Screen implements Stylized, WidgetsContainer {
    private static final String CLOSE_ICON = "×";
    private static final String CLOSE_ICON_HOVER = ChatFormatting.RED + CLOSE_ICON;

    protected static final int BORDER_SIZE = 7;

    private ContextMenu contextMenu = null;
    protected GlobalTheme globalTheme;
    private Screen back;
    private TitleBar titleBar;
    private boolean initialized = false;

    protected ULibScreen(Component title) {
        this(title, Minecraft.getInstance().screen);
        this.minecraft = Minecraft.getInstance();
        this.titleBar = new TitleBar(title);
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

    protected void initWidgets() {

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

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        renderBackground(gfx, mouseX, mouseY, partialTicks);

        renderCloseButton(gfx, mouseX, mouseY);

        boolean flag = contextMenu != null && contextMenu.isMouseOver(mouseX, mouseY);

        int mx = flag ? Integer.MIN_VALUE : mouseX;
        int my = flag ? Integer.MIN_VALUE : mouseY;
        for (GuiEventListener widget : children()) {
            if (widget instanceof Renderable renderable) {
                renderable.render(gfx, mx, my, partialTicks);
            }
        }

        renderContextMenu(gfx, mouseX, mouseY, partialTicks);
    }

    private void renderContextMenu(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        ContextMenu menu = contextMenu;
        if (menu != null) {
            RenderSystem.disableDepthTest();
            menu.renderWidget(gfx, mouseX, mouseY, partialTicks);
            RenderSystem.enableDepthTest();
        }
    }

    @Nullable
    public abstract Vec2 getCloseButtonPos();

    protected final boolean isPointBetween(int mouseX, int mouseY, int x, int y, int w, int h) {
        final int x1 = x + w;
        final int y1 = y + h;

        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }

    protected final void renderCloseButton(GuiGraphics gfx, int mouseX, int mouseY) {
        if (!this.shouldCloseOnEsc()) {
            return;
        }

        Vec2 iconPos = getCloseButtonPos();
        if (iconPos != null) {
            int iconX = (int) iconPos.x;
            int iconY = (int) iconPos.y;
            if (isPointBetween(mouseX, mouseY, iconX, iconY, 6, 6)) {
                gfx.drawString(this.font, CLOSE_ICON_HOVER, iconX, iconY, TitleStyle.get().getTitleColor().getRgb(), false);
            } else {
                gfx.drawString(this.font, CLOSE_ICON, iconX, iconY, TitleStyle.get().getTitleColor().getRgb(), false);
            }
        }
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
     * @param mouseX the X-position of the mouse it scrolled at.
     * @param mouseY the Y-position of the mouse it scrolled at.
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
            back();
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

    protected void back() {
        Minecraft.getInstance().setScreen(back);
    }

    @Override
    public void onClose() {
        this.back();
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

    public <T extends ULibWidget> @NotNull T add(@NotNull T widget) {
        widget.setPlacement(WidgetPlacement.CONTENT);
        return super.addRenderableWidget(widget);
    }

    public <T extends ULibWidget> @NotNull T addTitle(@NotNull T widget) {
        widget.setPlacement(WidgetPlacement.WINDOW);
        this.titleBar.add(widget);
        return super.addRenderableWidget(widget);
    }
}
