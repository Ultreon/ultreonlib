package com.ultreon.mods.lib.dev;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.ultreon.mods.lib.dev.network.DevNetwork;
import com.ultreon.mods.lib.dev.network.TestBiDirectionalPacket;
import com.ultreon.mods.lib.dev.network.TestToClientPacket;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.UuidArgument;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

@ApiStatus.Internal
public final class DevCommands {
    private DevCommands() {
        throw new UnsupportedOperationException();
    }

    static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
        dispatcher.register(Commands.literal("ultreonlib")
                .then(registerDevCommand())
        );
    }

    private static LiteralArgumentBuilder<CommandSourceStack> registerDevCommand() {
        return Commands.literal("dev")
                .then(Commands.literal("send-packet")
                        .then(Commands.literal("bi-directional")
                                .then(Commands.argument("uuid", UuidArgument.uuid())
                                        .executes((source) -> {
                                            DevNetwork.get().sendToClient(new TestBiDirectionalPacket(UuidArgument.getUuid(source, "uuid")), source.getSource().getPlayerOrException());
                                            return 1;
                                        })
                                ).executes((source) -> {
                                    DevNetwork.get().sendToClient(new TestBiDirectionalPacket(UUID.randomUUID()), source.getSource().getPlayerOrException());
                                    return 1;
                                })
                        ).then(Commands.literal("to-client").executes((source) -> {
                            DevNetwork.get().sendToClient(new TestToClientPacket(UUID.randomUUID()), source.getSource().getPlayerOrException());
                            return 1;
                        }))
                );
    }
}
