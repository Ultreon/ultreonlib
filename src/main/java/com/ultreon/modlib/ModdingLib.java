package com.ultreon.modlib;

import com.mojang.brigadier.CommandDispatcher;
import com.ultreon.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.modlib.silentlib.advancements.LibTriggers;
import com.ultreon.modlib.silentlib.crafting.recipe.DamageItemRecipe;
import com.ultreon.modlib.silentlib.data.TestRecipeProvider;
import com.ultreon.modlib.silentlib.item.ILeftClickItem;
import com.ultreon.modlib.silentlib.network.internal.UltreonModLibNetwork;
import com.ultreon.modlib.silentlib.server.command.internal.DisplayNBTCommand;
import com.ultreon.modlib.silentlib.server.command.internal.TeleportCommand;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.UnknownNullability;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.Random;

@Mod(ModdingLib.MOD_ID)
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ModdingLib {
    public static final String MOD_ID = "umodlib";
    public static final String MOD_NAME = "QModLib";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();

    @UnknownNullability
    private static ModdingLib instance = null;

    public ModdingLib() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcEnqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcProcess);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(RecipeSerializer.class, this::registerRecipeSerializers);

        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);

        UltreonModLibNetwork.init();
        LibTriggers.init();
        ILeftClickItem.EventHandler.init();
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        gen.addProvider(new TestRecipeProvider(gen));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void imcEnqueue(InterModEnqueueEvent event) {
    }

    private void imcProcess(InterModProcessEvent event) {
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        DisplayNBTCommand.register(dispatcher);
        TeleportCommand.register(dispatcher);
    }

    private void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        event.getRegistry().register(DamageItemRecipe.SERIALIZER.setRegistryName(ModdingLib.res("damage_item")));
    }

    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "0.0.0";
    }

    public static boolean isModDev() {
        return !FMLEnvironment.production;
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @UnknownNullability
    public static ModdingLib get() {
        return instance;
    }

    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Nullable
    public static ModdingLib getInstance() {
        return instance;
    }
}
