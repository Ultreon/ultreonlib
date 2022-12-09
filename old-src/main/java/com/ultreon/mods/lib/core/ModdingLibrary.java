package com.ultreon.mods.lib.core;

import com.mojang.brigadier.CommandDispatcher;
import com.ultreon.mods.lib.commons.collection.list.ItemStackList;
import com.ultreon.mods.lib.core.player.ModMessages;
import com.ultreon.mods.lib.core.server.command.TeleportCommand;
import com.ultreon.mods.lib.core.server.command.ViewNbtCommand;
import com.ultreon.mods.lib.core.util.FirstSpawnItems;
import com.ultreon.mods.lib.core.util.GameUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.Random;

@Mod(ModdingLibrary.MOD_ID)
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ModdingLibrary {
    public static final String MOD_ID = "ultreonlib";
    public static final String MOD_NAME = "Ultreon Core Library";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();

    @Nullable
    private static ModdingLibrary instance;

    public ModdingLibrary() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcEnqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcProcess);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(RecipeSerializer.class, this::registerRecipeSerializers);

        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);

        if (GameUtil.isDeveloperEnv()) {
            ModMessages.addMessage(new TextComponent("You are running a development build of Minecraft.").withStyle(style -> style.withColor(ChatFormatting.GOLD)));
            FirstSpawnItems.register(res("dev"), player -> ItemStackList.of(Items.DEBUG_STICK, Items.COMMAND_BLOCK, Items.CHAIN_COMMAND_BLOCK, Items.REPEATING_COMMAND_BLOCK, Items.STRUCTURE_BLOCK, Items.STRUCTURE_VOID, Items.JIGSAW, Items.NETHERITE_SWORD));
        }
    }

    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "0.0.0";
    }

    /**
     * Get whether the game is running in a development environment or not.
     *
     * @return true if the current environment is for developers.
     * @deprecated use {@link GameUtil#isDeveloperEnv()} instead.
     */
    @Deprecated
    public static boolean isDevEnv() {
        return !FMLEnvironment.production;
    }

    public static boolean isProdEnv() {
        return FMLEnvironment.production;
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Nullable
    public static ModdingLibrary getInstance() {
        return instance;
    }

    public static boolean isModDev() {
        return !FMLEnvironment.production;
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
//        gen.addProvider(new TestRecipeProvider(gen));
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    private void imcEnqueue(InterModEnqueueEvent event) {
    }

    private void imcProcess(InterModProcessEvent event) {
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        ViewNbtCommand.register(dispatcher);
        TeleportCommand.register(dispatcher);
    }

    private void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
    }
}
