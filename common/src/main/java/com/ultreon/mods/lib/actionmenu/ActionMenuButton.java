package com.ultreon.mods.lib.actionmenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.TooltipFactory;
import com.ultreon.mods.lib.client.gui.widget.TransparentButton;
import com.ultreon.mods.lib.commons.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("unused")
public class ActionMenuButton extends TransparentButton<ActionMenuButton> implements ActionMenuIndexable {
    @NotNull
    private static final ResourceLocation ICONS = UltreonLib.res("textures/gui/action_menu.png");
    @NotNull
    private final ActionMenuItem item;
    @NotNull
    private final ActionMenuScreen screen;
    private int menuIndex;

    public ActionMenuButton(@NotNull ActionMenuScreen screen, @NotNull ActionMenuItem item, int x, int y, int width, int height) {
        super(item.getText(), (btn) -> item.activate());
        this.screen = screen;
        this.item = item;
    }

    public ActionMenuButton(@NotNull ActionMenuScreen screen, @NotNull ActionMenuItem item, int x, int y, int width, int height, TooltipFactory<ActionMenuButton> onTooltip) {
        super(item.getText(), (btn) -> item.activate(), onTooltip);
        this.screen = screen;
        this.item = item;
    }

    @Override
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        var client = Minecraft.getInstance();
        var font = client.font;

        Color background;
        background = selectOptionColor(Color.rgba(0, 0, 0, (int) Math.min(Math.max(127 - (51.2 * (menuIndex - 1)), 0), 127)), Color.rgba(0, 0, 0, (int) Math.min(Math.max(127 - (51.2 * (menuIndex)), 0), 127)));

        renderer.fill(getX(), getY(), getX() + width, getY() + height, background);

        Color textColor = this.findTextColor();

        if (isHovered && menuIndex == 0 && active) {
            renderer.textCenter(this.getMessage(), (this.getX() + this.width / 2) + 1, (this.getY() + (this.height - 8) / 2) + 1, textColor);
        } else {
            renderer.textCenter(this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, textColor);
        }

        if (item instanceof SubmenuItem) {
            RenderSystem.setShaderTexture(0, ICONS);

            renderer.pushPose();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f / (menuIndex + 1));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            if (active) {
                if (isHovered && menuIndex == 0) {
                    renderer.blit(ICONS, getX() + width - 6, getY() + height / 2 - 4, 6, 9, 12, 0, 6, 9, 64, 64);
                } else {
                    renderer.blit(ICONS, getX() + width - 6, getY() + height / 2 - 4, 6, 9, 6, 0, 6, 9, 64, 64);
                }
            } else {
                renderer.blit(ICONS, getX() + width - 6, getY() + height / 2 - 4, 6, 9, 0, 0, 6, 9, 64, 64);
            }

            renderer.popPose();

            if (client.screen instanceof ActionMenuScreen currentScreen) {
                if (isHovered && active) {
                    if (currentScreen.getMenuIndex() >= screen.getMenuIndex()) {
                        screen.setButtonRectangle(new Rectangle(getX(), getY(), width + 1, height));
                        screen.setActiveItem(this);
                        client.setScreen(new ActionMenuScreen(screen, ((SubmenuItem) item).getHandler().getMenu(), screen.getMenuIndex() + 1, item.getText()));
                    }
                } else if (isHovered) {
                    if (client.screen != screen) {
                        screen.scheduleDisplay(screen);
                    }
                }
            }
        } else {
            if (isHovered) {
                if (client.screen != screen) {
                    screen.scheduleDisplay(screen);
                }
            }
        }
    }

    @NotNull
    private Color findTextColor() {
        Color hoverColor;
        Color normalColor;
        Color disabledColor;
        if (screen.getActiveItem() == this) {
            hoverColor = Color.rgba(255, 255, 0, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1));
            normalColor = Color.rgba(255, 255, 255, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1));
            disabledColor = Color.rgba(160, 160, 160, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1));
        } else {
            hoverColor = Color.rgba(255, 255, 0, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1));
            normalColor = Color.rgba(255, 255, 255, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1));
            disabledColor = Color.rgba(160, 160, 160, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1));
        }

        return selectColor(hoverColor, normalColor, disabledColor);
    }

    private Color selectColor(Color hovered, Color normal, Color disabled) {
        return this.active ? selectActiveColor(hovered, normal) : disabled;
    }

    private Color selectActiveColor(Color hovered, Color normal) {
        if (menuIndex != 0) return selectOptionColor(isHovered ? hovered : normal, normal);
        else return isHovered ? hovered : normal;
    }

    private Color selectOptionColor(Color hovered, Color normal) {
        return screen.getActiveItem() == this ? hovered : normal;
    }

    @Override
    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

    @NotNull
    public ActionMenuItem getItem() {
        return item;
    }

    @NotNull
    public ActionMenuScreen getScreen() {
        return screen;
    }

    public int getMenuIndex() {
        return menuIndex;
    }
}
