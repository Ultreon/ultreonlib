package com.ultreon.mods.lib.client.gui.screen;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.screen.window.TitleBarAccess;
import com.ultreon.mods.lib.client.gui.widget.ContainerWidget;
import com.ultreon.mods.lib.client.gui.widget.ULibWidget;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Theme;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TitleBar extends ContainerWidget implements TitleBarAccess {
    private static final int PADDING = 4;
    private static final int TITLE_SPACE = 8;
    private static final int CONTROL_WIDTH = 5 + PADDING * 2;
    private final ULibScreen screen;
    private GlobalTheme globalTheme = UltreonLib.getTheme();

    private boolean fullScreen = true;
    private final Vector2i contentOffset = new Vector2i();
    private int titleBarHeight;
    private @Range(from = 0, to = Integer.MAX_VALUE) int curTabPage = 0;
    private final List<TabPage> tabs = new ArrayList<>();
    private int windowWidth;
    private int windowHeight;

    public TitleBar(ULibScreen screen, Component message) {
        super(message);
        this.screen = screen;
        tabs.add(screen);
    }

    @Override
    public void reloadTheme() {
        super.reloadTheme();

        this.globalTheme = UltreonLib.getTheme();
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        super.renderWidget(renderer, mouseX, mouseY, partialTicks);

        if (fullScreen) {
            this.renderFullScreen(renderer, mouseX, mouseY, partialTicks);
        } else {
            this.renderWindowed(renderer);
        }
    }

    private void renderWindowed(@NotNull GuiRenderer renderer) {
        renderer.renderWindow(this.getWindowX(), this.getWindowY(), this.getWindowWidth(), this.getWindowHeight(), this.getTitle());
    }

    int getWindowX() {
        return this.screen.width / 2 - this.windowWidth / 2;
    }

    int getWindowY() {
        return this.screen.height / 2 - this.windowHeight / 2;
    }

    int getWindowWidth() {
        return this.windowWidth;
    }

    int getWindowHeight() {
        return this.windowHeight;
    }

    void setWindowWidth(int width) {
        if (fullScreen) return;
        if (width < 0) width = 0;

        this.windowWidth = width;
    }

    void setWindowHeight(int height) {
        if (fullScreen) return;
        if (height < 0) height = 0;

        this.windowHeight = height;
    }

    private void renderFullScreen(GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        renderer.renderBumpLine(this.getX(), this.getY() + this.getHeight());

        screen.renderCloseButton(renderer, mouseX, mouseY, this.getWidth() - CONTROL_WIDTH + PADDING, PADDING);

        int x = this.getX() + PADDING + this.font.width(this.getMessage()) + TITLE_SPACE;
        int width = this.getWidth() - x - PADDING - CONTROL_WIDTH;
        int height = this.getHeight() - PADDING * 2;
        renderer.scissor(x, PADDING, width, height, () -> this.renderTitleWidgets(renderer, x, width, mouseX, mouseY, partialTicks));
    }

    private void renderTitleWidgets(GuiRenderer renderer, int x, int width, int mouseX, int mouseY, float partialTicks) {
        for (ULibWidget widget : widgets) {
            widget.setX(x);
            widget.setY(PADDING);
            widget.render(renderer, mouseX, mouseY, partialTicks);

            x += widget.getWidth() + PADDING;

            if (x > width) {
                break;
            }
        }
    }

    @Override
    public int getHeight() {
        return titleBarHeight;
    }

    @Override
    protected void revalidateWidgets() {
        int height = 0;
        for (ULibWidget widget : widgets) {
            widget.revalidate();
            int widgetHeight = widget.getHeight();
            if (widgetHeight > height) {
                height = widgetHeight;
            }
        }

        this.titleBarHeight = height + PADDING * 2;
    }

    Vector2i getContentOffset() {
        return contentOffset;
    }

    @Override
    public Theme getTheme() {
        return this.globalTheme.getWindowTheme();
    }

    @Override
    public void setTitle(Component title) {
        this.setMessage(title);
    }

    @Override
    public TitleBarAccess title(Component title) {
        this.setMessage(title);
        return this;
    }

    @Override
    public Component getTitle() {
        return this.getMessage();
    }

    boolean isFullScreen() {
        return fullScreen;
    }

    void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    @Override
    public void addTab(TabPage page) {
        if (page instanceof ULibScreen)
            throw new IllegalArgumentException("Page cannot be a " + ULibScreen.class.getSimpleName());
        tabs.add(page);
    }

    @Override
    public void removeTab(TabPage page) {
        if (page instanceof ULibScreen)
            throw new IllegalArgumentException("Page cannot be a " + ULibScreen.class.getSimpleName());

        int i = tabs.indexOf(page);
        if (i == -1)
            return;
        if (tabs.remove(page) && curTabPage > i)
            curTabPage = curTabPage - 1;
        
        if (tabs.isEmpty())
            screen.close();
    }

    @Override
    public TabPage getCurrentTab() {
        return tabs.get(curTabPage);
    }

    List<TabPage> getTabs() {
        return Collections.unmodifiableList(tabs);
    }

    @Override
    public int getTabCount() {
        if (tabs.isEmpty())
            throw new IllegalStateException("Tabs not initialized!");
        return tabs.size();
    }

    @Override
    public void nextTab() {
        curTabPage = (curTabPage + 1) % tabs.size();
    }

    @Override
    public void prevTab() {
        curTabPage = (curTabPage - 1 + tabs.size()) % tabs.size();
    }

    @Override
    public ULibScreen getScreen() {
        return screen;
    }

    public Vec2 getCloseButtonPos() {
        return new Vec2(this.getWidth() - CONTROL_WIDTH + PADDING, PADDING);
    }
}
