package com.ultreon.mods.lib.client.gui.screen;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.commons.collection.map.OrderedMap;
import com.ultreon.mods.lib.client.gui.widget.BaseButton;
import com.ultreon.mods.lib.client.gui.widget.TabContainer;
import com.ultreon.mods.lib.client.gui.widget.TabNavigator;
import com.ultreon.mods.lib.client.gui.widget.ThemedButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LeftSideTabScreen extends PanoramaScreen {
    public static final int BORDER = 5;
    public static final int PADDING = 3;
    public static final int TAB_WIDTH = 60;
    private final TabNavigator navigator = new TabNavigator();
    private final Map<TabContainer, BaseButton> tabButtons = new OrderedMap<>();

    public LeftSideTabScreen(Component title) {
        super(title);

        navigator.setOnCloseTab(this::onCloseTab);
    }

    private void onCloseTab(TabContainer container) {
        BaseButton button = tabButtons.remove(container);

        removeWidget(button);
    }

    private int left() {
        return (width - (width())) / 2;
    }

    private int top() {
        return (height - (height())) / 2;
    }

    private int width() {
        return 300 + BORDER_SIZE * 2;
    }

    private int height() {
        return 168 + BORDER_SIZE * 2;
    }

    private int innerLeft() {
        return left() + BORDER;
    }

    private int innerTop() {
        return top() + BORDER;
    }

    private int containerLeft() {
        return innerLeft() + TAB_WIDTH + PADDING + BORDER_SIZE * 2;
    }

    private int containerTop() {
        return innerTop();
    }

    private int containerWidth() {
        return innerWidth() - TAB_WIDTH - PADDING - BORDER_SIZE * 2;
    }

    private int containerHeight() {
        return innerHeight();
    }

    private int tabLeft() {
        return innerLeft();
    }

    private int tabTop() {
        return innerTop();
    }

    private int tabWidth() {
        return TAB_WIDTH;
    }

    private int tabHeight() {
        return innerHeight();
    }

    private int innerWidth() {
        return width() - BORDER * 2;
    }

    private int innerHeight() {
        return height() - BORDER * 2;
    }

    @CanIgnoreReturnValue
    public TabContainer addTab(Component title) {
        TabContainer tabContainer = new TabContainer(navigator, containerLeft(), containerTop(), containerWidth(), containerHeight(), title);
        navigator.add(tabContainer);

        ThemedButton themedButton = new ThemedButton(title, button -> navigator.setForeground(navigator.getIndex(tabContainer)), ThemedButton.Type.of(getTheme()));
        tabButtons.put(tabContainer, themedButton);

        return tabContainer;
    }

    @CanIgnoreReturnValue
    public TabContainer addTab(Component title, int index) {
        TabContainer tabContainer = new TabContainer(navigator, containerLeft(), containerTop(), containerWidth(), containerHeight(), title);
        navigator.add(tabContainer, index);

        ThemedButton themedButton = new ThemedButton(title, button -> navigator.setForeground(navigator.getIndex(tabContainer)), ThemedButton.Type.of(getTheme()));
        tabButtons.put(tabContainer, themedButton);

        return tabContainer;
    }

    @Override
    protected void init() {
        super.init();

        for (BaseButton button : tabButtons.values()) {
            addWidget(button);
        }
    }

    @CanIgnoreReturnValue
    public boolean closeTab(TabContainer container) {
        return navigator.close(container);
    }
    
    @Override
    public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        renderPanorama(pose, partialTicks);

        // Main frame.
        renderFrame(pose, left(), top(), width(), height(), getTheme(), 0);

        // Inner frames.
        renderFrame(pose, tabLeft(), tabTop(), tabWidth(), tabHeight(), getTheme(), 3);
        renderFrame(pose, containerLeft(), containerTop(), containerWidth(), containerHeight(), getTheme(), 3);

        pose.pushPose();
        {
            pose.translate(0, 0, -320);
            assert minecraft != null;
            double i = minecraft.getWindow().getGuiScale();
            RenderSystem.enableScissor((int) ((tabLeft() + 1) * i), (int) ((tabTop() + 1) * i), (int) ((tabWidth() - 2 + BORDER_SIZE * 2) * i), (int) ((tabHeight() - 2 + BORDER_SIZE * 2) * i));

            final int x = tabLeft();
            final int tabHeight = 20;
            int y = tabTop() + 1;
            TabContainer foreground = navigator.getForeground();

            for (Map.Entry<TabContainer, BaseButton> entry : tabButtons.entrySet()) {
                TabContainer container = entry.getKey();
                BaseButton button = entry.getValue();

                button.x = x;
                button.y = y;
                button.setWidth(tabWidth() - 2);
                button.active = !(Objects.equals(container, foreground));

                button.render(pose, mouseX, mouseY, partialTicks);

                y += tabHeight;
            }

            RenderSystem.disableScissor();

            if (foreground != null) {
                RenderSystem.enableScissor((int) ((containerLeft() + 1) * i), (int) ((containerTop() + 1) * i), (int) ((containerLeft() + containerWidth() + 1) * i), (int) ((containerTop() + containerHeight() + 1) * i));

                foreground.x = containerLeft() + 1;
                foreground.y = containerTop() + 1;
                foreground.setWidth(containerWidth() - 2);
                foreground.setHeight(containerHeight() - 2);

                int mx = mouseX;
                int my = mouseY;
                if (isPointBetween(mx, my, containerLeft() + 1, containerTop() + 1, containerWidth() - 2, containerHeight() - 2))
                    mx = my = Integer.MAX_VALUE;

                foreground.render(pose, mx, my, partialTicks);

                RenderSystem.disableScissor();
            }
        }
        pose.popPose();

        // Widgets.
        super.render(pose, mouseX, mouseY, partialTicks);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return super.children();
    }

    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return null;
    }
}
