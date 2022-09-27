package com.ultreon.mods.lib.core.common;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;
import java.util.function.Predicate;

@SuppressWarnings("unused")
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Ticker {
    private final Predicate<Ticker> autoReset;
    private final Consumer<Ticker> onTick;
    private int ticks;

    public Ticker(int startValue) {
        this(startValue, (ticker) -> false);
    }

    public Ticker(int startValue, @NotNull Predicate<Ticker> autoReset) {
        this(startValue, autoReset, (ticker) -> {
        });
    }

    public Ticker(int startValue, @NotNull Predicate<Ticker> autoReset, @NotNull Consumer<Ticker> onTick) {
        this.ticks = startValue;
        this.autoReset = autoReset;
        this.onTick = onTick;
    }

    public int next() {
        int current = this.ticks++;
        onTick.accept(this);
        if (autoReset.test(this)) {
            reset();
        }
        return current;
    }

    public void reset() {
        this.ticks = 0;
    }

    public int ticks() {
        return ticks;
    }
}
