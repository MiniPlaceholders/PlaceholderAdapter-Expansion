package io.github.miniplaceholders.expansion.placeholderadapter.fabric;

import eu.pb4.placeholders.api.PlaceholderContext;
import eu.pb4.placeholders.api.Placeholders;
import io.github.miniplaceholders.api.Expansion;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class FabricPlatform {
    public static Expansion.Builder provideBuilder(Object platformInstance) {
        final MinecraftServer server = (MinecraftServer) platformInstance;
        final MinecraftServerAudiences serverAudiences = MinecraftServerAudiences.of(server);
        return Expansion.builder("placeholder-adapter")
                .globalPlaceholder("global", (queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    final Component papiParsed = serverAudiences.asAdventure(
                            Placeholders.parseText(net.minecraft.network.chat.Component.literal(argument), PlaceholderContext.of(server))
                    );
                    if (parseString(queue)) {
                        return Tag.preProcessParsed(miniMessage().serialize(papiParsed));
                    } else {
                        return Tag.selfClosingInserting(papiParsed);
                    }
                })
                .audiencePlaceholder(ServerPlayer.class, "player", (player, queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    final Component papiParsed = serverAudiences.asAdventure(
                            Placeholders.parseText(net.minecraft.network.chat.Component.literal(argument), PlaceholderContext.of(player))
                    );
                    if (parseString(queue)) {
                        return Tag.preProcessParsed(miniMessage().serialize(papiParsed));
                    } else {
                        return Tag.selfClosingInserting(papiParsed);
                    }
                });
    }

    private static boolean parseString(ArgumentQueue queue) {
        return queue.hasNext() && queue.pop().lowerValue().equals("string");
    }
}
