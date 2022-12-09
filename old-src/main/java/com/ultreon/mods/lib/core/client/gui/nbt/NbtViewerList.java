package com.ultreon.mods.lib.core.client.gui.nbt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;

public class NbtViewerList extends ObjectSelectionList<NbtViewerEntry> {
    private final NbtViewerScreen screen;

    public NbtViewerList(NbtViewerScreen screen, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.screen = screen;
        this.screen.lines.forEach(line -> addEntry(new NbtViewerEntry(line)));
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 200;
    }

}
