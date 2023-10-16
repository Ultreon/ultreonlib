package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.libs.commons.v0.size.IntSize;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class McMessageDialog extends McDialogWindow {
    private McLabel description;
    private Icon icon;

    private McMessageDialog(@NotNull McApplication window, int width, int height, String title) {
        super(window, width, height, title);
    }

    private McMessageDialog(@NotNull McApplication window, int width, int height, Component title) {
        super(window, width, height, title);

        this.description = this.add(new McLabel(24, 4, 0, this.font.lineHeight, ""));
        this.setMaximizable(false);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(gfx, mouseX, mouseY, partialTicks);

        final Icon icon = this.icon;
        if (icon != null)
            icon.render(gfx, 4, 4, 16, 16);

        this.description.render(gfx, mouseX, mouseY, partialTicks);
    }

    @Override
    protected @Nullable IntSize getForceSize() {
        int descLength = this.font.width(this.description.getMessage());
        this.description.setWidth(descLength);
        var lines = this.description.getMessage().getString().replaceAll("\r\n", "\n").replaceAll("\r", "\n").split("\n");
        int textHeight = (this.font.lineHeight + 1) * lines.length + 8;
        if (textHeight < 24) {
            this.description.setY(4 + (textHeight - 9) / 2);
        } else {
            this.description.setY(0);
        }
        return new IntSize(descLength + 28, Math.max(textHeight, 24));
    }

    public Component getDescription() {
        return description.getMessage();
    }

    public void setDescription(Component description) {
        this.description.setMessage(description);
    }

    public static McMessageDialog create(McApplication application, Icon icon, Component title, Component description) {
        McMessageDialog dialog = new McMessageDialog(application, 1, 1, title);
        dialog.setDescription(description);
        dialog.icon = icon;
        return dialog;
    }

    @Override
    public boolean close() {
        return this.parent.closeDialog(this);
    }

    @Override
    boolean _isValid() {
        return this.parent.getDialog() == this;
    }

    public enum Icons implements Icon {
        INFO, WARNING, ERROR, QUESTION;

        private final ResourceLocation resource;

        Icons() {
            this.resource = UltreonLib.res("textures/gui/device/messagebox/icon_" + name().toLowerCase(Locale.ROOT) + ".png");
        }

        Icons(ResourceLocation resource) {
            this.resource = resource;
        }

        @Override
        public ResourceLocation resource() {
            return resource;
        }

        @Override
        public int width() {
            return 16;
        }

        @Override
        public int height() {
            return 16;
        }

        @Override
        public int vHeight() {
            return 16;
        }

        @Override
        public int uWidth() {
            return 16;
        }

        @Override
        public int v() {
            return 0;
        }

        @Override
        public int u() {
            return 0;
        }

        @Override
        public int texWidth() {
            return 16;
        }

        @Override
        public int texHeight() {
            return 16;
        }
    }
}
