package com.ultreon.mods.lib.client.gui.v2;

import com.google.common.collect.Lists;

final class McKernel extends McApplication {
    private boolean verbose;

    public McKernel() {
        super("com.ultreon:mc-kernel");
    }

    @Override
    public void create() {
        super.create();

        var argv = Lists.newArrayList(this.getArgv());
        this.verbose = argv.remove("-v");
    }

    public boolean isVerbose() {
        return verbose;
    }
}
