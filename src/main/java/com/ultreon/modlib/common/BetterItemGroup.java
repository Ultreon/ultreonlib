package com.ultreon.modlib.common;

import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.ReportedException;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BetterItemGroup extends CreativeModeTab {
    private final ItemStack icon;
    private final ResourceLocation rl;
    private final String labelName;

    public BetterItemGroup(ResourceLocation rl, ItemStack icon) {
        super(rl.getNamespace() + "_" + rl.getPath().replaceAll("[/.]", "_"));
        this.icon = icon;
        this.rl = rl;
        this.labelName = rl.getPath().replaceAll("[/.]", "_");
    }

    public BetterItemGroup(ResourceLocation rl, ItemLike itemProvider) {
        super(rl.getNamespace() + "_" + rl.getPath().replaceAll("[/.]", "_"));
        this.icon = new ItemStack(itemProvider);
        this.rl = rl;
        this.labelName = rl.getPath().replaceAll("[/.]", "_");
    }

    public ResourceLocation getLocation() {
        return this.rl;
    }

    public String getLabelName() {
        return this.labelName;
    }

    public String getModID() {
        return this.rl.getNamespace();
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return this.icon;
    }

    @SuppressWarnings("unchecked")
    protected <T extends Enum<T>> void addEnumValuesStack(NonNullList<ItemStack> items, Class<T> enum_, Function<T, ItemStack> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.stream().map(function).filter(Objects::nonNull).forEach(items::add);
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.forThrowable(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.setDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Enum<T>> void addEnumValuesStackCollection(NonNullList<ItemStack> items, Class<T> enum_, Function<T, Collection<ItemStack>> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.stream().map(function).filter(Objects::nonNull).forEach(items::addAll);
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.forThrowable(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.setDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Enum<T>> void addEnumValuesCollection(NonNullList<ItemStack> items, Class<T> enum_, Function<T, Collection<ItemLike>> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.forEach(t -> function.apply(t).stream().filter(Objects::nonNull).forEach((item) -> items.add(new ItemStack(item))));
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.forThrowable(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.setDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Enum<T>> void addEnumValues(NonNullList<ItemStack> items, Class<T> enum_, Function<T, ItemLike> function) {
        try {
            Method method = enum_.getDeclaredMethod("values");
            Collection<T> values = (Collection<T>) method.invoke(null);

            values.stream().map(t -> function.apply(t) != null ? new ItemStack(function.apply(t)) : null).filter(Objects::nonNull).forEach(items::add);
        } catch (Throwable t) {

            CrashReport crashreport = CrashReport.forThrowable(t, "Enum has no static values() method.");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Enum being looped in QForgeMod Item Group.");
            crashreportcategory.setDetail("Enum Class Name", enum_::toString);
            throw new ReportedException(crashreport);
        }
    }
}
