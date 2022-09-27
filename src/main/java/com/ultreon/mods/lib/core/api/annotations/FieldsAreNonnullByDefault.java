package com.ultreon.mods.lib.core.api.annotations;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Nonnull
@TypeQualifierDefault(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * @deprecated Removed
 */
@Deprecated
public @interface FieldsAreNonnullByDefault {
}