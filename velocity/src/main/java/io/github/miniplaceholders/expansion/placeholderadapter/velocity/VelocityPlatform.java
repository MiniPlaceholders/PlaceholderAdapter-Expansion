package io.github.miniplaceholders.expansion.placeholderadapter.velocity;

import com.velocitypowered.api.proxy.Player;
import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.LegacyStrings;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.william278.papiproxybridge.api.PlaceholderAPI;

import java.util.OptionalInt;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.AMPERSAND_CHAR;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.SECTION_CHAR;

public final class VelocityPlatform {
    private static final UUID NULL_UUID = UUID.randomUUID();
    private static final PlaceholderAPI instance = PlaceholderAPI.createInstance();
    private static final ConcurrentMap<Long, PlaceholderAPI> cachedInstances = new ConcurrentHashMap<>();

    private static PlaceholderAPI getAPI(ArgumentQueue queue) {
        if (!queue.hasNext()) return instance;
        final OptionalInt optExpiry = queue.pop().asInt();

        if (optExpiry.isEmpty()) return instance;
        final long expiry = optExpiry.getAsInt();

        return cachedInstances.computeIfAbsent(expiry, (key) -> {
            final PlaceholderAPI api = PlaceholderAPI.createInstance();
            api.setCacheExpiry(key);
            return api;
        });
    }
    
    public static Expansion.Builder provideBuilder() {
        return Expansion.builder("placeholder-adapter")
                .globalPlaceholder("global", (queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    final boolean isString = parseString(queue);
                    final PlaceholderAPI api = getAPI(queue);
                    final String papiParsed = api
                            .formatPlaceholders(argument, NULL_UUID)
                            .completeOnTimeout(argument, 100, TimeUnit.MILLISECONDS)
                            .thenApply(st -> st.replace(SECTION_CHAR, AMPERSAND_CHAR))
                            .handle((result, ex) -> ex == null ? result : "")
                            .join();

                    if (isString) {
                        return Tag.preProcessParsed(miniMessage().serialize(LegacyStrings.parsePossibleLegacy(papiParsed)));
                    } else {
                        return Tag.selfClosingInserting(LegacyStrings.parsePossibleLegacy(papiParsed));
                    }
                })
                .audiencePlaceholder(Player.class, "player", (player, queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    boolean isString = parseString(queue);
                    final PlaceholderAPI api = getAPI(queue);
                    final String papiParsed = api
                            .formatPlaceholders(argument, player.getUniqueId())
                            .completeOnTimeout(argument, 100, TimeUnit.MILLISECONDS)
                            .thenApply(st -> st.replace(SECTION_CHAR, AMPERSAND_CHAR))
                            .handle((result, ex) -> ex == null ? result : "")
                            .join();

                    if (isString) {
                        return Tag.preProcessParsed(miniMessage().serialize(LegacyStrings.parsePossibleLegacy(papiParsed)));
                    } else {
                        return Tag.selfClosingInserting(LegacyStrings.parsePossibleLegacy(papiParsed));
                    }
                })
                .relationalPlaceholder(Player.class, "relational", (player, playerToShow, queue, ctx) -> {
                    final String argument = queue.popOr("You need to provide a placeholder").value();
                    final boolean isString = parseString(queue);
                    final PlaceholderAPI api = getAPI(queue);
                    final String papiParsed = api
                            .formatPlaceholders(argument, player.getUniqueId(), playerToShow.getUniqueId())
                            .completeOnTimeout(argument, 100, TimeUnit.MILLISECONDS)
                            .thenApply(st -> st.replace(SECTION_CHAR, AMPERSAND_CHAR))
                            .handle((result, ex) -> ex == null ? result : "")
                            .join();

                    if (isString) {
                        return Tag.preProcessParsed(miniMessage().serialize(LegacyStrings.parsePossibleLegacy(papiParsed)));
                    } else {
                        return Tag.selfClosingInserting(LegacyStrings.parsePossibleLegacy(papiParsed));
                    }
                });
    }

    private static boolean parseString(ArgumentQueue queue) {
        //noinspection DataFlowIssue
        final boolean isString = queue.hasNext() && queue.peek().lowerValue().equals("string");
        if (isString) queue.pop();
        return isString;
    }
}
