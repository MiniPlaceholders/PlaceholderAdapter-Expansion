package io.github.miniplaceholders.expansion.placeholderapi.velocity;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.LegacyUtils;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.william278.papiproxybridge.api.PlaceholderAPI;
import org.slf4j.Logger;

import java.util.OptionalInt;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.AMPERSAND_CHAR;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.SECTION_CHAR;

@Plugin(
        id = "placeholderapi-expansion",
        name = "PlaceholderAPI Expansion",
        version = "{version}",
        authors = { "4drian3d" },
        dependencies = {
                @Dependency(id = "miniplaceholders"),
                @Dependency(id = "papiproxybridge")
        }
)
public final class VelocityPlugin {
    @Inject
    private Logger logger;
    private static final UUID NULL_UUID = UUID.randomUUID();
    private static final PlaceholderAPI instance = PlaceholderAPI.createInstance();
    private static final ConcurrentMap<Long, PlaceholderAPI> cachedInstances = Maps.newConcurrentMap();

    private PlaceholderAPI getAPI(ArgumentQueue queue) {
        if (!queue.hasNext()) return instance;
        OptionalInt optExpiry = queue.pop().asInt();

        if (optExpiry.isEmpty()) return instance;
        Long expiry = (long) optExpiry.getAsInt();

        return cachedInstances.computeIfAbsent(expiry, (key) -> {
            PlaceholderAPI api = PlaceholderAPI.createInstance();
            api.setCacheExpiry(key);
            return api;
        });
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.logger.info("Starting PlaceholderAPI Expansion for MiniPlaceholders Velocity");

        Expansion.builder("placeholderapi")
                .filter(Player.class)
                .globalPlaceholder("global", (queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    boolean isString = parseString(queue);
                    PlaceholderAPI api = getAPI(queue);
                    final String papiParsed = api
                            .formatPlaceholders(argument, NULL_UUID)
                            .completeOnTimeout(argument, 100, TimeUnit.MILLISECONDS)
                            .join()
                            .replace(SECTION_CHAR, AMPERSAND_CHAR);

                    if (isString) {
                        return Tag.preProcessParsed(miniMessage().serialize(LegacyUtils.parsePossibleLegacy(papiParsed)));
                    } else {
                        return Tag.selfClosingInserting(LegacyUtils.parsePossibleLegacy(papiParsed));
                    }
                })
                .audiencePlaceholder("player", (aud, queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    final Player player = (Player)aud;
                    boolean isString = parseString(queue);
                    PlaceholderAPI api = getAPI(queue);
                    final String papiParsed = api
                            .formatPlaceholders(argument, player.getUniqueId())
                            .completeOnTimeout(argument, 100, TimeUnit.MILLISECONDS)
                            .join()
                            .replace(SECTION_CHAR, AMPERSAND_CHAR);

                    if (isString) {
                        return Tag.preProcessParsed(miniMessage().serialize(LegacyUtils.parsePossibleLegacy(papiParsed)));
                    } else {
                        return Tag.selfClosingInserting(LegacyUtils.parsePossibleLegacy(papiParsed));
                    }
                })
                .relationalPlaceholder("relational", (audience, audienceToShow, queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    final Player player = (Player) audience;
                    final Player toShow = (Player) audienceToShow;
                    boolean isString = parseString(queue);
                    PlaceholderAPI api = getAPI(queue);
                    final String papiParsed = api
                            .formatPlaceholders(argument, player.getUniqueId(), toShow.getUniqueId())
                            .completeOnTimeout(argument, 100, TimeUnit.MILLISECONDS)
                            .join()
                            .replace(SECTION_CHAR, AMPERSAND_CHAR);

                    if (isString) {
                        return Tag.preProcessParsed(miniMessage().serialize(LegacyUtils.parsePossibleLegacy(papiParsed)));
                    } else {
                        return Tag.selfClosingInserting(LegacyUtils.parsePossibleLegacy(papiParsed));
                    }
                })
                .build()
                .register();
    }

    private boolean parseString(ArgumentQueue queue) {
        boolean isString = queue.hasNext() && queue.peek().lowerValue().equals("string");
        if (isString) queue.pop();
        return isString;
    }
}
