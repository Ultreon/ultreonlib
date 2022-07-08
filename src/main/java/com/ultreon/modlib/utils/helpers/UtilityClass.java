package com.ultreon.modlib.utils.helpers;

import com.ultreon.modlib.utils.ExceptionUtil;

public abstract class UtilityClass {
    protected UtilityClass() {
        throw ExceptionUtil.utilityConstructor();
    }
}
