package com.ultreon.mods.lib.actionmenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.widget.TransparentButton;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@SuppressWarnings("unused")
public class ActionMenuButton extends TransparentButton implements IActionMenuIndexable {
    @NotNull
    private static final ResourceLocation ICONS = UltreonLib.res("textures/gui/action_menu.png");
    @NotNull
    private final ActionMenuItem item;
    @NotNull
    private final ActionMenuScreen screen;
    private int menuIndex;

    public ActionMenuButton(@NotNull ActionMenuScreen screen, @NotNull ActionMenuItem item, int x, int y, int width, int height) {
        super(x, y, width, height, item.getText(), (btn) -> item.activate());
        this.screen = screen;
        this.item = item;
    }

    public ActionMenuButton(@NotNull ActionMenuScreen screen, @NotNull ActionMenuItem item, int x, int y, int width, int height, OnTooltip onTooltip) {
        super(x, y, width, height, item.getText(), (btn) -> item.activate(), onTooltip);
        this.screen = screen;
        this.item = item;
    }

    @Override
    public void renderButton(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        var client = Minecraft.getInstance();
        var font = client.font;

        int background;
        if (screen.getActiveItem() == this) {
            background = new Color(0, 0, 0, (int) Math.min(Math.max(127 - (51.2 * (menuIndex - 1)), 0), 127)).getRGB();
        } else {
            background = new Color(0, 0, 0, (int) Math.min(Math.max(127 - (51.2 * (menuIndex)), 0), 127)).getRGB();
        }

        fill(pose, x, y, x + width, y + height, background);

        int hoverColor;
        int normalColor;
        int disabledColor;
        if (screen.getActiveItem() == this) {
            hoverColor = new Color(255, 255, 0, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1)).getRGB();
            normalColor = new Color(255, 255, 255, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1)).getRGB();
            disabledColor = new Color(160, 160, 160, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1)).getRGB();
        } else {
            hoverColor = new Color(255, 255, 0, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1)).getRGB();
            normalColor = new Color(255, 255, 255, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1)).getRGB();
            disabledColor = new Color(160, 160, 160, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1)).getRGB();
        }

        int textColor;
        if (this.active) {
            if (menuIndex != 0) {
                if (screen.getActiveItem() == this) {
                    if (isHovered) {
                        textColor = hoverColor;
                    } else {
                        textColor = normalColor;
                    }
                } else {
                    textColor = normalColor;
                }
            } else {
                if (isHovered) {
                    textColor = hoverColor;
                } else {
                    textColor = normalColor;
                }
            }
        } else {
            textColor = disabledColor;
        }

        if (isHovered && menuIndex == 0 && active) {
            drawCenteredString(pose, font, this.getMessage(), (this.x + this.width / 2) + 1, (this.y + (this.height - 8) / 2) + 1, textColor);
        } else {
            drawCenteredString(pose, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);
        }

        if (item instanceof SubmenuItem) {
            RenderSystem.setShaderTexture(0, ICONS);

            pose.pushPose();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f / (menuIndex + 1));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            if (active) {
                if (isHovered && menuIndex == 0) {
                    blit(pose, x + width - 6, y + height / 2 - 4, 6, 9, 12, 0, 6, 9, 64, 64);
                } else {
                    blit(pose, x + width - 6, y + height / 2 - 4, 6, 9, 6, 0, 6, 9, 64, 64);
                }
            } else {
                blit(pose, x + width - 6, y + height / 2 - 4, 6, 9, 0, 0, 6, 9, 64, 64);
            }
            pose.popPose();

            if (client.screen instanceof ActionMenuScreen currentScreen) {
                if (isHovered && active) {
                    if (currentScreen.getMenuIndex() >= screen.getMenuIndex()) {
                        screen.setButtonRectangle(new Rectangle(x, y, width + 1, height));
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

        if (this.isHovered) {
            this.renderToolTip(pose, mouseX, mouseY);
        }
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
