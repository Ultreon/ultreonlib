package com.ultreon.mods.lib.client.theme;

import com.ultreon.libs.commons.v0.Color;

public class Style {
    protected Color textColor;
    protected Color inactiveTextColor;
    protected Color titleColor;
    protected Color headerColor;
    protected Color subTitleColor;
    protected Color borderColor;
    protected Color inactiveBorderColor;
    protected Color accentColor;
    protected Color secondaryColor;

    public Style() {
    }

    public Style(Style style) {
        this.textColor = style.textColor;
        this.inactiveTextColor = style.inactiveTextColor;
        this.titleColor = style.titleColor;
        this.headerColor = style.headerColor;
        this.subTitleColor = style.subTitleColor;
        this.borderColor = style.borderColor;
        this.inactiveBorderColor = style.inactiveBorderColor;
        this.accentColor = style.accentColor;
        this.secondaryColor = style.secondaryColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getInactiveTextColor() {
        return inactiveTextColor;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public Color getHeaderColor() {
        return headerColor;
    }

    public Color getSubTitleColor() {
        return subTitleColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getInactiveBorderColor() {
        return inactiveBorderColor;
    }

    public Color getAccentColor() {
        return accentColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }
}
