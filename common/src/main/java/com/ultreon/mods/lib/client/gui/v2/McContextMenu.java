package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class McContextMenu extends McComponent {
    private final List<McMenuItem> items = Lists.newArrayList();

    public McContextMenu(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {

    }

    public McMenuItem add(Component name, McMenuItem.Callback callback) {
        return add(new McMenuItem(name, callback));
    }

    public McMenuItem add(McMenuItem item) {
        this.items.add(item);
        return item;
    }

    public void remove(McMenuItem item) {
        this.items.remove(item);
    }
}
