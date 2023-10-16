package com.ultreon.mods.lib.client.gui.v2;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.libs.commons.v0.Color;
import com.ultreon.libs.commons.v0.util.ExceptionUtils;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.screen.test.device.TestEmptyApplication;
import com.ultreon.mods.lib.client.gui.v2.util.IntSize;
import com.ultreon.mods.lib.input.GameKeyboard;
import com.ultreon.mods.lib.input.GameKeyboard.Modifier;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

final class McOperatingSystem extends McWindowManager implements IMcOperatingSystem {
    private static McOperatingSystem instance;
    private final Map<ApplicationId, McApplicationFactory<?>> applications = new HashMap<>();
    private final Map<Class<?>, ApplicationId> applicationTypes = new HashMap<>();
    private final McPermissionManager permissionManager = new McPermissionManager();
    private final List<McApplication> openApps = new CopyOnWriteArrayList<>();
    private DeviceScreen screen;
    private McDesktopWindow desktop;
    private McTaskbarWindow taskbar;
    private McDesktopApplication desktopApp;
    private final Modifier metaKey = Modifier.CTRL;
    private long pid = 0L;
    final McKernel kernel = new McKernel();
    private final OsLogger logger = new OsLoggerImpl();
    private final Deque<McKeyboardHook> keyboardHooks = new ArrayDeque<>();
    private BSOD bsod;
    private long shutdownTime;

    public McOperatingSystem(DeviceScreen screen, int width, int height, ArrayList<McWindow> windows, McDesktopApplication desktopApp) {
        this(screen, 0, 0, width, height, windows, desktopApp);
    }

    public McOperatingSystem(DeviceScreen screen, int x, int y, int width, int height, ArrayList<McWindow> windows, McDesktopApplication desktopApp) {
        super(x, y, width, height, windows);

        instance = this;

        try {
            // Register apps and spawn kernel
            this._spawn(this.kernel, new String[]{});
            this.registerApp(this.kernel.getId(), () -> this.kernel);
            this.registerApp(McDesktopApplication.id(), () -> desktopApp);
            this.registerApp(TestEmptyApplication.id(), TestEmptyApplication::new);

            // Setup permissions
            this.permissionManager.grantPermission(McDesktopApplication.id(), McPermission.SHUTDOWN);

            this.screen = screen;
            this.desktopApp = desktopApp;
            this._spawn(desktopApp, new String[0]);

            this.desktop = desktopApp.getDesktop();
            this.taskbar = desktopApp.getTaskbar();
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
        }
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    final <T extends McApplication> void registerApp(ApplicationId id, McApplicationFactory<T> factory, T... typeGetter) {
        Class<T> componentType = (Class<T>) typeGetter.getClass().getComponentType();
        this.applicationTypes.put(componentType, id);
        this.applications.put(id, factory);
        this.permissionManager.registerPerms(id);
    }

    public static McOperatingSystem get() {
        return instance;
    }

    McDesktopApplication getDesktop() {
        return desktopApp;
    }

    private boolean _spawn(McApplication application, String[] argv) {
        this.openApps.add(application);
        try {
            this.permissionManager.onAppSpawned(application);
            application._main(this, this, argv, this.pid++);
            return true;
        } catch (Exception e) {
            UltreonLib.LOGGER.info("Application crashed: ");
            this.destroyApplication(application);
            return false;
        }
    }

    private McApplication resolveApplication(ApplicationId id) throws AppNotFoundException {
        McApplicationFactory<?> factory = this.applications.get(id);
        if (factory == null) throw new AppNotFoundException(id);
        return factory.create();
    }

    boolean spawn(McApplication executor, ApplicationId id, String... argv) throws AccessDeniedException, AppNotFoundException {
        this.permissionManager.checkPermission(executor, McPermission.SPAWN_APPLICATION);
        this.permissionManager.checkPermission(executor, new McSpawnApplicationPermission(id));

        return this._spawn(this.resolveApplication(id), argv);
    }

    @Override
    public void shutdown(McApplication executor) throws AccessDeniedException {
        this.permissionManager.checkPermission(executor, McPermission.SHUTDOWN);
        this.shutdownAll();
    }

    @Override
    public IntSize getScreenSize() {
        return new IntSize(this.width, this.height);
    }

    private void shutdownAll() {
        this.shutdown();

        this.screen.onShutdown();
        instance = null;
    }

    private void shutdown() {
        try {
            this.desktopApp.quit();
        } catch (Exception e) {
            this.getLogger().error("Application crash:", e);
            this.destroyApplication(this.desktopApp);
        }

        this.createWindow(new McShutdownWindow(kernel, 0, 0, width, height, "Shutting down..."));

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            this.openApps.forEach(application -> {
                try {
                    application.quit();
                } catch (Exception e) {
                    this.getLogger().error("Application crash:", e);
                    this.destroyApplication(application);
                }
            });
        });

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            future.cancel(true);
        }
        this.openApps.clear();
        this.windows.clear();
    }

    void destroyApplication(McApplication application) {
        this.permissionManager.onAppDestroyed(application);
        this.openApps.remove(application);
        try {
            for (McWindow window : application.windows) {
                this.destroyWindow(window);
            }
            ApplicationId id = application.getId();
            _spawn(applications.get(id).create(), application.getArgv());
        } catch (Exception e) {
            this.logger.error("Failed to normally close windows", e);
        }
    }

    public void loadWallpaper(File file) {
        this.desktop.loadWallpaper(file);
    }

    public void loadWallpaper(Path path) {
        this.desktop.loadWallpaper(path);
    }

    public void setColorBackground(Color color) {
        this.desktop.setBackgroundColor(color);
    }

    @Override
    public final void setX(int i) {

    }

    @Override
    public final void setY(int i) {

    }

    @Override
    public final int getX() {
        return 0;
    }

    @Override
    public final int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return screen.width;
    }

    @Override
    public int getHeight() {
        return screen.height;
    }

    public void setWindowActiveColor(int windowActiveColor) {
        this.windowActiveColor = windowActiveColor;
    }

    public void setWindowInactiveColor(int windowInactiveColor) {
        this.windowInactiveColor = windowInactiveColor;
    }

    @Override
    public void raiseHardError(McApplication executor, Throwable throwable) throws AccessDeniedException {
        this.permissionManager.checkPermission(executor, McPermission.HARD_ERROR);
        this._raiseHardError(throwable);
    }

    private void _raiseHardError(Throwable throwable) {
        this.openApps.clear();
        this.windows.clear();

        this.shutdownTime = System.currentTimeMillis() + 10000;
        this.bsod = new BSOD(throwable);
        UltreonLib.LOGGER.error("System hard error:", throwable);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.bsod != null) return false;

        try {
            Iterator<McKeyboardHook> iterator = this.keyboardHooks.iterator();
            if (iterator.hasNext()) {
                McKeyboardHook current = iterator.next();
                current = current.keyPressed(keyCode, scanCode, modifiers, current);
                if (current == null) return true;
                while (iterator.hasNext()) {
                    current = current.keyPressed(keyCode, scanCode, modifiers, iterator.next());
                    if (current == null) return true;
                }
            }

            McWindow activeWindow = getActiveWindow();
            try {
                if ((keyCode == InputConstants.KEY_Q && GameKeyboard.isKeyDown(metaKey)) || (keyCode == InputConstants.KEY_F4 && GameKeyboard.isAltDown())) {
                    activeWindow.close();
                    return true;
                }
                if (GameKeyboard.isKeyDown(metaKey)) {
                    switch (keyCode) {
                        case InputConstants.KEY_UP -> {
                            if (activeWindow.isMinimized()) activeWindow.restore();
                            else activeWindow.maximize();
                            return true;
                        }
                        case InputConstants.KEY_DOWN -> {
                            if (activeWindow.isMaximized()) activeWindow.restore();
                            else activeWindow.minimize();
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                this.getLogger().error("Application crash:", e);
                this.destroyApplication(activeWindow.application);
                return false;
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return true;
        }
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (this.bsod != null) return false;

        try {
            Iterator<McKeyboardHook> iterator = this.keyboardHooks.iterator();
            if (iterator.hasNext()) {
                McKeyboardHook current = iterator.next();
                while (iterator.hasNext()) {
                    current = current.keyReleased(keyCode, scanCode, modifiers, iterator.next());
                    if (current == null) return true;
                }
            }

            return super.keyReleased(keyCode, scanCode, modifiers);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return true;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        if (this.bsod != null) {
            long millisRemaining = this.shutdownTime - System.currentTimeMillis();
            if (millisRemaining < 0) {
                this.shutdownAll();
            }

            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            gfx.fill(0, 0, getWidth(), getHeight(), 0xff0000ff);
            gfx.pose().pushPose();
            gfx.pose().scale(2, 2, 1);
            gfx.drawString(this.font, ":(", 3, 3, 0xffffffff, false);
            gfx.pose().popPose();
            gfx.drawString(this.font, ChatFormatting.BOLD + "Your Minecraft ran into a problem and needs to restart.", 6, 25, 0xffffffff, false);
            gfx.drawString(this.font, "Shutting down in: " + (Mth.ceil(millisRemaining / 1000f)), 20, 10, 0xb0ffffff, false);
            AtomicInteger i = new AtomicInteger(0);
            ExceptionUtils.getStackTrace(this.bsod.throwable()).lines().forEachOrdered(s -> {
                gfx.drawString(this.font, s.replaceAll("\t", "      "), 6, 40 + (i.addAndGet(this.font.lineHeight)), 0xb0ffffff, false);
            });

            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();
            return;
        }

        try {
            this.openApps.forEach(McApplication::update);

            super.render(gfx, mouseX, mouseY, partialTicks);
        } catch (Throwable throwable) {
            ScissorStack.clearScissorStack();
            this._raiseHardError(throwable);
        }
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        try {
            super.renderWidget(gfx, mouseX, mouseY, partialTicks);
        } catch (Throwable throwable) {
            ScissorStack.clearScissorStack();
            this._raiseHardError(throwable);
        }
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (this.bsod != null) return false;

        try {
            Iterator<McKeyboardHook> iterator = this.keyboardHooks.iterator();
            if (iterator.hasNext()) {
                McKeyboardHook current = iterator.next();
                while (iterator.hasNext()) {
                    current = current.charTyped(codePoint, modifiers, iterator.next());
                    if (current == null) return true;
                }
            }

            return super.charTyped(codePoint, modifiers);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return true;
        }
    }

    public OsLogger getLogger() {
        return logger;
    }

    @Override
    public void addKeyboardHook(McKeyboardHook hook) {
        Preconditions.checkNotNull(hook, "hook");
        this.keyboardHooks.addFirst(hook);
    }

    @Override
    public void removeKeyboardHook(McKeyboardHook hook) {
        Preconditions.checkNotNull(hook, "hook");
        this.keyboardHooks.remove(hook);
    }

    @Override
    public Modifier getMetaKey() {
        return metaKey;
    }

    @Override
    public void setActiveWindow(McWindow activeWindow) {
        try {
            super.setActiveWindow(activeWindow);
        } catch (Exception e) {
            this.getLogger().error("Application crash:", e);
            this.destroyApplication(activeWindow.application);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        if (this.bsod != null) return false;

        try {
            return super.mouseScrolled(mouseX, mouseY, amountX, amountY);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return false;
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.bsod != null) return false;

        try {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return false;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.bsod != null) return false;

        try {
            return super.mouseClicked(mouseX, mouseY, button);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return false;
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.bsod != null) return false;

        try {
            return super.mouseReleased(mouseX, mouseY, button);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return false;
        }
    }

    @Override
    public boolean preMouseClicked(double mouseX, double mouseY, int button) {
        if (this.bsod != null) return false;

        try {
            return super.preMouseClicked(mouseX, mouseY, button);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
            return false;
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (this.bsod != null) return;

        try {
            super.mouseMoved(mouseX, mouseY);
        } catch (Throwable throwable) {
            this._raiseHardError(throwable);
        }
    }

    public boolean isApplicationRegistered(McApplication application) {
        try {
            return Objects.equals(applicationTypes.get(application.getClass()), application.getId());
        } catch (Exception e) {
            return false;
        }
    }
}
