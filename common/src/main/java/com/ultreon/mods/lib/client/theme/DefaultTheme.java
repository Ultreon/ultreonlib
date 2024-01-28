package com.ultreon.mods.lib.client.theme;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.commons.Color;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class DefaultTheme extends Theme {
    public static final Theme VANILLA = new DefaultTheme(0xffffff, 0xa0a0a0, 0xffffff, 0x808080, () -> createWidgetSprites(new ResourceLocation("widget/button"))).withBorderColor(0xffffff).withTitleColor(0x111111).withHeaderColor(0x333333).withTextureLocation(UltreonLib.res("vanilla"));
    public static final Theme LIGHT = new DefaultTheme(0x808080, 0x949494, 0x19b2ff, 0xb7b7b7, buttonStyle -> buttonStyle.textColor = Color.white, () -> createWidgetSprites(UltreonLib.res("widget/button/light"))).withBorderColor(0xffffff).withInactiveBorderColor(0x868686).withSubTitleColor(0xb0b0b0).withHeaderColor(0x333333).withTextureLocation(UltreonLib.res("light"));
    public static final Theme DARK = new DefaultTheme(0xffffff, 0xA0A0A0, 0xffa54c, 0x313131, true, () -> createWidgetSprites(UltreonLib.res("widget/button/dark"))).withSubTitleColor(0x808080).withTextureLocation(UltreonLib.res("dark"));

    private final Supplier<WidgetSprites> buttonSpritesFactory;

    private DefaultTheme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, Supplier<WidgetSprites> buttonSpritesFactory) {
        super(textColor, inactiveTextColor, accentColor, secondaryColor);
        this.buttonSpritesFactory = buttonSpritesFactory;

        this.init();
    }

    private DefaultTheme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, boolean dark, Supplier<WidgetSprites> buttonSpritesFactory) {
        super(textColor, inactiveTextColor, accentColor, secondaryColor, dark);
        this.buttonSpritesFactory = buttonSpritesFactory;

        this.init();
    }

    private DefaultTheme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, Consumer<Style> buttonStyle, Supplier<WidgetSprites> buttonSpritesFactory) {
        super(textColor, inactiveTextColor, accentColor, secondaryColor, buttonStyle);
        this.buttonSpritesFactory = buttonSpritesFactory;

        this.init();
    }

    private DefaultTheme(int textColor, int inactiveTextColor, int accentColor, int secondaryColor, Consumer<Style> buttonStyle, boolean dark, Supplier<WidgetSprites> buttonSpritesFactory) {
        super(textColor, inactiveTextColor, accentColor, secondaryColor, buttonStyle, dark);
        this.buttonSpritesFactory = buttonSpritesFactory;

        this.init();
    }

    @Override
    public void init() {
        this.buttonSprites = Suppliers.memoize(buttonSpritesFactory);
    }
}
