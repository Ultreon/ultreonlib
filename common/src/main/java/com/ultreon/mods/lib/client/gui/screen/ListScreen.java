package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.widget.BaseButton;
import com.ultreon.mods.lib.util.ScissorStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * List screen. A screen that's made for only a list with entries.
 */
public class ListScreen extends PanoramaScreen {
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

    protected void addEntry(ListWidget.Entry entry) {
        this.cachedEntries.add(entry);
        if (list != null) {
            this.list.addEntry(entry);
        }
    }

    protected int listHeight() {
        return Math.max(52, this.height - 128 - 32);
    }

    protected int listBottomY() {
        return 80 + this.listHeight() - 8;
    }

    protected int left() {
        return (this.width - 238) / 2;
    }

    protected int right() {
        return (this.width - 238) / 2 + 238;
    }

    @Override
    protected void init() {
        onInit.init();

        if (this.initialized && this.list != null) {
            this.list.setSize(this.width, this.height);
        } else {
            this.list = new ListWidget(this, this.minecraft, this.width, this.height, 88, 36);
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

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, float partialTicks) {
        int i = this.left() + 3;
        super.renderBackground(gfx, partialTicks);

        renderFrame(gfx, i, 64, 236, this.listHeight() + 16, this.globalTheme.getContentTheme(), FrameType.BORDER);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        Objects.requireNonNull(this.minecraft);
        this.renderBackground(gfx, partialTicks);

        renderTitleFrame(gfx, this.left() + 3, 28, this.minecraft.font.width(this.title) + 12, 21, this.globalTheme);
        renderTitleFrame(gfx, this.right() - 8 - 12, 28, 21, 21, this.globalTheme);

        gfx.drawString(this.font, this.title, this.left() + 9, 35, this.getStyle().getTitleColor().getRgb(), false);

        if (!this.list.isEmpty()) {
            this.list.render(gfx, mouseX, mouseY, partialTicks);
        } else if (!this.searchBox.getValue().isEmpty()) {
            drawCenteredStringWithoutShadow(gfx, this.minecraft.font, SEARCH_EMPTY, this.width / 2, (78 + this.listBottomY()) / 2, this.globalTheme.getContentTheme().getInactiveTextColor().getRgb());
        }

        if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
            gfx.drawString(this.font, SEARCH_HINT, this.searchBox.getX(), this.searchBox.getY(), this.globalTheme.getContentTheme().getInactiveTextColor().getRgb(), false);
        } else {
            this.searchBox.render(gfx, mouseX, mouseY, partialTicks);
        }

        super.render(gfx, mouseX, mouseY, partialTicks);
    }

    @NotNull
    @Override
    public Vec2 getCloseButtonPos() {
        return new Vec2(this.right() - 12, 35);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.searchBox.isFocused()) {
            this.searchBox.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
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

        /**
         * @deprecated use {@link #ListWidget(ListScreen, Minecraft, int, int, int, int)} instead
         */
        @Deprecated
        public ListWidget(ListScreen screen, Minecraft minecraft, int width, int height, int top, int bottom, int itemHeight) {
            this(screen, minecraft, width, height, top, itemHeight);
        }

        public ListWidget(ListScreen screen, Minecraft minecraft, int width, int height, int top, int itemHeight) {
            super(minecraft, width, height, top, itemHeight);
            this.mc = minecraft;

            this.screen = screen;

            for (Entry cachedEntry : screen.cachedEntries) {
                addEntry(cachedEntry);
            }

            this.setRenderBackground(false);
        }

        @Override
        protected int addEntry(@NotNull ListScreen.ListWidget.Entry entry) {
            defaultEntries.add(entry);
            if (search == null) {
                super.addEntry(entry);
            }
            return this.defaultEntries.size() - 1;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width / 2 + 105; // 124 default
        }

        @Override
        public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
            double scaleFactor = this.mc.getWindow().getGuiScale();
            this.height = this.screen.listHeight() - 16;
            ScissorStack.pushScissor((int) ((double) this.getRowLeft() * scaleFactor), (int) ((double) (this.getY()) * scaleFactor), (int) ((double) (this.getScrollbarPosition() + 6 - getX()) * scaleFactor), (int) ((double) (this.height) * scaleFactor));
            super.renderWidget(gfx, mouseX, mouseY, partialTicks);
            ScissorStack.popScissor();
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

            @Override
            public void render(@NotNull GuiGraphics gfx, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
                int i = left + 7;

                Component description = this.getDescription();
                int l;
                if (Objects.equals(description, Component.empty())) {
                    gfx.fill(left, top, left + width, top + height, this.screen.globalTheme.getContentTheme().getSecondaryColor().darker().getRgb());
                    l = top + (height - 9) / 2;
                } else {
                    gfx.fill(left, top, left + width, top + height, this.screen.globalTheme.getContentTheme().getSecondaryColor().darker().getRgb());
                    l = top + (height - (9 + 9)) / 2;
                    gfx.drawString(this.mc.font, description, i, l + 12, this.screen.globalTheme.getContentTheme().getButtonStyle().getTextColor().darker().getRgb(), false);
                }

                gfx.drawString(this.mc.font, this.entryTitle, i, l, this.screen.globalTheme.getContentTheme().getButtonStyle().getTextColor().getRgb(), false);
                float ticksUntilTooltip = this.ticksTooltip;

                int btnIndex = 0;
                for (GuiEventListener guiEventListener : buttons) {
                    if (guiEventListener instanceof Button button) {
                        button.setX(left + width - 8 - (BUTTON_WIDTH + 4) * (btnIndex + 1));
                        button.setY(top + height / 2 - 10);
                        button.render(gfx, mouseX, mouseY, partialTicks);
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
