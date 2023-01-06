/*
 * Copyright (c) 2022. - Qboi SMP Development Team
 * Do NOT redistribute, or copy in any way, and do NOT modify in any way.
 * It is not allowed to hack into the code, use cheats against the code and/or compiled form.
 * And it is not allowed to decompile, modify or/and patch parts of code or classes or in full form.
 * Sharing this file isn't allowed either, and is hereby strictly forbidden.
 * Sharing decompiled code on social media or an online platform will cause in a report on that account.
 *
 * ONLY the owner can bypass these rules.
 */

package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.widget.BaseButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * List screen. A screen that's made for only a list with entries.
 */
public final class ListScreen extends PanoramaScreen {
    private IListFilter listFilter = (query, id, title, description) -> {
        var found = true;
        for (var part : query.split(" ")) {
            found &= title.toLowerCase(Locale.ROOT).contains(part.toLowerCase(Locale.ROOT));
        }
        return found;
    };
    private ListWidget list;
    private EditBox searchBox;
    private String searchTerms = "";
    private boolean initialized;

    private static final Component SEARCH_HINT = (Component.translatable("gui.ultreonlib.search_hint")).withStyle(ChatFormatting.ITALIC);
    private static final Component SEARCH_EMPTY = (Component.translatable("gui.ultreonlib.search_empty"));
    private static final ResourceLocation DARK_TEXTURE = UltreonLib.res("textures/gui/list/dark.png");
    private static final ResourceLocation LIGHT_TEXTURE = UltreonLib.res("textures/gui/list/light.png");
    private static final ResourceLocation NORMAL_TEXTURE = UltreonLib.res("textures/gui/list/normal.png");

    private IListEntryClick onListEntryClick = (list, entry) -> {

    };

    private IInitHandler onInit = () -> {

    };

    final List<ListWidget.Entry> cachedEntries = new ArrayList<>();

    /**
     * List screen constructor.
     *
     * @param title screen title.
     */
    public ListScreen(Component title) {
        super(title);
    }

    public void setOnListEntryClick(IListEntryClick handler) {
        this.onListEntryClick = handler;
    }

    public void setOnInit(IInitHandler handler) {
        this.onInit = handler;
    }

    public void setListFilter(IListFilter handler) {
        this.listFilter = handler;
    }

    public void addEntry(String title, String description, String id, BaseButton... buttons) {
        this.addEntry(new ListWidget.Entry(this, title, description, id, buttons));
    }

    public void addEntry(String title, String id, BaseButton... buttons) {
        this.addEntry(title, "", id, buttons);
    }

    private void addEntry(ListWidget.Entry entry) {
        this.cachedEntries.add(entry);
        if (list != null) {
            this.list.addEntry(entry);
        }
    }

    private int getListHeight() {
        return Math.max(52, this.height - 128 - 16);
    }

    private int func0() {
        return this.getListHeight() / 16;
    }

    private int func1() {
        return 80 + this.func0() * 16 - 8;
    }

    private int left() {
        return (this.width - 238) / 2;
    }

    private int right() {
        return (this.width - 238) / 2 + 238;
    }

    public void tick() {
        super.tick();
        this.searchBox.tick();
    }

    protected void init() {
        onInit.init();

        if (this.initialized && this.list != null) {
            this.list.updateSize(this.width, this.height, 88, this.func1());
        } else {
            this.list = new ListWidget(this, this.minecraft, this.width, this.height, 88, this.func1(), 36);
        }

        String s = this.searchBox != null ? this.searchBox.getValue() : "";
        this.searchBox = new EditBox(this.font, this.left() + 28, 78, 196, 16, SEARCH_HINT);
        this.searchBox.setMaxLength(30);
        this.searchBox.setBordered(false);
        this.searchBox.setVisible(true);
        this.searchBox.setTextColor(16777215);
        this.searchBox.setValue(s);
        this.searchBox.setResponder(this::search);
        this.addRenderableWidget(this.searchBox);
        this.addRenderableWidget(this.list);
        this.initialized = true;
    }

    public void removed() {

    }

    @Override
    public void renderBackground(@NotNull PoseStack pose, float partialTicks) {
        int i = this.left() + 3;
        super.renderBackground(pose, partialTicks);

        RenderSystem.setShaderTexture(0, getTexture());
        this.blit(pose, i, 64, 1, 1, 236, 8);
        int j = this.func0();

        for (int k = 0; k < j; ++k) {
            this.blit(pose, i, 72 + 16 * k, 1, 10, 236, 16);
        }

        this.blit(pose, i, 72 + 16 * j, 1, 27, 236, 8);
        this.blit(pose, i + 10, 76, 243, 1, 12, 12);
    }

    private ResourceLocation getTexture() {
        return switch (getTheme()) {
            case DARK -> DARK_TEXTURE;
            case MIX, LIGHT -> LIGHT_TEXTURE;
            default -> NORMAL_TEXTURE;
        };
    }

    public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        Objects.requireNonNull(this.minecraft);
        this.renderBackground(pose, partialTicks);

        renderTitleFrame(pose, this.left() + 3, 35 - 7, this.minecraft.font.width(this.title), 7, getTheme());
        renderTitleFrame(pose, this.right() - 8 - 12, 35 - 7, 7, 7, getTheme());

        font.draw(pose, this.title, this.left() + 9, 35, switch (getTheme()) {
            case DARK, MIX -> 0xffffffff;
            case LIGHT -> 0xff202020;
            case NORMAL -> 0xff000000;
        });

        if (!this.list.isEmpty()) {
            this.list.render(pose, mouseX, mouseY, partialTicks);
        } else if (!this.searchBox.getValue().isEmpty()) {
            drawCenteredStringWithoutShadow(pose, this.minecraft.font, SEARCH_EMPTY, this.width / 2, (78 + this.func1()) / 2, switch (getTheme()) {
                case DARK -> 0xffffffff;
                case LIGHT, MIX -> 0xff202020;
                case NORMAL -> 0xff000000;
            });
        }

        if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
            font.draw(pose, SEARCH_HINT, this.searchBox.getX(), this.searchBox.getY(), switch (getTheme()) {
                case DARK -> 0xffffffff;
                case LIGHT, MIX -> 0xff202020;
                case NORMAL -> 0xff000000;
            });
        } else {
            this.searchBox.render(pose, mouseX, mouseY, partialTicks);
        }

        super.render(pose, mouseX, mouseY, partialTicks);
    }

    @NotNull
    @Override
    public Vec2 getCloseButtonPos() {
        return new Vec2(this.right() - 12, 35);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.searchBox.isFocused()) {
            this.searchBox.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean isPauseScreen() {
        return false;
    }

    private void search(String text) {
        text = text.toLowerCase(Locale.ROOT);
        if (!text.equals(this.searchTerms)) {
            this.list.search(text);
            this.searchTerms = text;
        }

    }

    @FunctionalInterface
    public interface IListEntryClick {
        void call(ListWidget list, ListWidget.Entry entry);
    }

    @FunctionalInterface
    public interface IInitHandler {
        void init();
    }

    @FunctionalInterface
    public interface IListFilter {
        boolean filter(String query, String id, String title, String description);
    }

    @SuppressWarnings("DuplicatedCode")
    public static class ListWidget extends ContainerObjectSelectionList<ListWidget.Entry> {
        private final Minecraft mc;
        private final ListScreen screen;
        private final List<Entry> defaultEntries = new ArrayList<>();
        private String search;

        public ListWidget(ListScreen screen, Minecraft minecraft, int width, int height, int top, int bottom, int itemHeight) {
            super(minecraft, width, height, top, bottom, itemHeight);
            this.mc = minecraft;

            this.screen = screen;

            for (Entry cachedEntry : screen.cachedEntries) {
                addEntry(cachedEntry);
            }

            this.setRenderBackground(false);
            this.setRenderTopAndBottom(false);
        }

        @Override
        protected int addEntry(@NotNull ListScreen.ListWidget.Entry entry) {
            defaultEntries.add(entry);
            if (search == null) {
                super.addEntry(entry);
            }
            return this.defaultEntries.size() - 1;
        }

        protected int getScrollbarPosition() {
            return this.width / 2 + 105; // 124 default
        }

        public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
            double scaleFactor = this.mc.getWindow().getGuiScale();
            RenderSystem.enableScissor((int) ((double) this.getRowLeft() * scaleFactor), (int) ((double) (this.height - this.y1) * scaleFactor), (int) ((double) (this.getScrollbarPosition() + 6) * scaleFactor), (int) ((double) (this.height - (this.height - this.y1) - this.y0 - 4) * scaleFactor));
            super.render(pose, mouseX, mouseY, partialTicks);
            RenderSystem.disableScissor();
        }

        public void search(String text) {
            this.search = Objects.equals(text, "") ? null : text;

            this.clearEntries();

            for (Entry entry : this.defaultEntries) {
                if (screen.listFilter.filter(text, entry.getId(), entry.getTitle(), entry.getDescription().getString())) {
                    super.addEntry(entry);
                }
            }
        }

        public boolean isEmpty() {
            return children().isEmpty();
        }

        public void loadCache() {

        }

        @Environment(EnvType.CLIENT)
        public static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
            private static final int BUTTON_WIDTH = 60;
            private final Minecraft mc;
            private final List<GuiEventListener> buttons;
            private final String entryTitle;
            private final ListScreen screen;
            private float ticksTooltip;

            public static final int DARK_TITLE_COLOR = FastColor.ARGB32.color(255, 255, 255, 255);
            public static final int LIGHT_TITLE_COLOR = FastColor.ARGB32.color(255, 16, 16, 16);
            public static final int VANILLA_TITLE_COLOR = FastColor.ARGB32.color(255, 0, 0, 0);
            public static final int DARK_DESCRIPTION_COLOR = FastColor.ARGB32.color(140, 255, 255, 255);
            public static final int LIGHT_DESCRIPTION_COLOR = FastColor.ARGB32.color(140, 16, 16, 16);
            public static final int VANILLA_DESCRIPTION_COLOR = FastColor.ARGB32.color(140, 0, 0, 0);
            public static final int DARK_EMPTY_COLOR = FastColor.ARGB32.color(255, 74, 74, 74);
            public static final int DARK_NON_EMPTY_COLOR = FastColor.ARGB32.color(255, 48, 48, 48);
            public static final int LIGHT_EMPTY_COLOR = FastColor.ARGB32.color(255, 192, 192, 192);
            public static final int LIGHT_NON_EMPTY_COLOR = FastColor.ARGB32.color(255, 160, 160, 160);
            public static final int VANILLA_EMPTY_COLOR = FastColor.ARGB32.color(255, 128, 128, 128);
            public static final int VANILLA_NON_EMPTY_COLOR = FastColor.ARGB32.color(255, 96, 96, 96);

            private final Component description;
            private final String id;

            public Entry(ListScreen screen, String title, String description, String id, BaseButton... buttons) {
                this(Minecraft.getInstance(), screen, title, description, id, buttons);
            }

            public Entry(Minecraft minecraft, ListScreen screen, String title, String description, String id, BaseButton... buttons) {
                this.mc = minecraft;
                this.entryTitle = title;
                this.description = Component.literal(description);
                this.id = id;
                this.screen = screen;

                this.buttons = Arrays.asList(buttons);
            }

            public void render(@NotNull PoseStack pose, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
                int i = left + 7;

                Component description = this.getDescription();
                int l;
                if (Objects.equals(description, Component.empty())) {
                    fill(pose, left, top, left + width, top + height, switch (screen.getTheme()) {
                        case DARK -> DARK_EMPTY_COLOR;
                        case LIGHT, MIX -> LIGHT_EMPTY_COLOR;
                        default -> VANILLA_EMPTY_COLOR;
                    });
                    l = top + (height - 9) / 2;
                } else {
                    fill(pose, left, top, left + width, top + height, switch (screen.getTheme()) {
                        case DARK -> DARK_NON_EMPTY_COLOR;
                        case LIGHT, MIX -> LIGHT_NON_EMPTY_COLOR;
                        default -> VANILLA_NON_EMPTY_COLOR;
                    });
                    l = top + (height - (9 + 9)) / 2;
                    this.mc.font.draw(pose, description, (float) i, (float) (l + 12), switch (screen.getTheme()) {
                        case DARK -> DARK_DESCRIPTION_COLOR;
                        case LIGHT, MIX -> LIGHT_DESCRIPTION_COLOR;
                        default -> VANILLA_DESCRIPTION_COLOR;
                    });
                }

                this.mc.font.draw(pose, this.entryTitle, (float) i, (float) l, switch (screen.getTheme()) {
                    case DARK -> DARK_TITLE_COLOR;
                    case LIGHT, MIX -> LIGHT_TITLE_COLOR;
                    default -> VANILLA_TITLE_COLOR;
                });
                float ticksUntilTooltip = this.ticksTooltip;

                int btnIndex = 0;
                for (GuiEventListener guiEventListener : buttons) {
                    if (guiEventListener instanceof Button button) {
                        button.setX(left + width - 8 - (BUTTON_WIDTH + 4) * (btnIndex + 1));
                        button.setY(top + height / 2 - 10);
                        button.render(pose, mouseX, mouseY, partialTicks);
                        btnIndex++;
                    }
                }

                if (ticksUntilTooltip == this.ticksTooltip) {
                    this.ticksTooltip = 0.0F;
                }
            }

            public String getTitle() {
                return this.entryTitle;
            }

            public Component getDescription() {
                return description;
            }

            public String getId() {
                return id;
            }

            public ListScreen getScreen() {
                return screen;
            }

            @Override
            public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
                boolean wasClicked = super.mouseClicked(p_94695_, p_94696_, p_94697_);
                if (!wasClicked) {
                    screen.onListEntryClick.call(screen.list, this);
                }
                return wasClicked;
            }

            @NotNull
            @Override
            public List<? extends NarratableEntry> narratables() {
                return Collections.emptyList();
            }

            @Override
            public @NotNull List<? extends GuiEventListener> children() {
                return buttons;
            }
        }
    }
}
