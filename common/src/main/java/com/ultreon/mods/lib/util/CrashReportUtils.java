package com.ultreon.mods.lib.util;

import com.ultreon.mods.lib.actionmenu.ActionMenu;
import com.ultreon.mods.lib.actionmenu.ActionMenuItem;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class CrashReportUtils {
    @ApiStatus.Internal
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int index, final int x, final int y) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item class name", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Index", () -> String.valueOf(index));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Position", () -> "(" + x + ", " + y + ")");
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    @ApiStatus.Internal
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int x, final int y) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item class name", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Position", () -> "(" + x + ", " + y + ")");
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    @ApiStatus.Internal
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int index) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item class name", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Index", () -> String.valueOf(index));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    @ApiStatus.Internal
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item class name", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    @ApiStatus.Internal
    public static void addActionMenu(CrashReport crashreport, ActionMenu menu, int menuIndex) {
        CrashReportCategory crashMenuCategory = crashreport.addCategory("Menu details");
        crashMenuCategory.setDetail("Menu class name", menu.getClass()::getName);
        crashMenuCategory.setDetail("Menu index", () -> String.valueOf(menuIndex));
    }

    @ApiStatus.Internal
    public static void addActionMenu(CrashReport crashreport, ActionMenu menu) {
        CrashReportCategory crashMenuCategory = crashreport.addCategory("Menu details");
        crashMenuCategory.setDetail("Menu class name", menu.getClass()::getName);
    }
}
