package io.github.miniplaceholders.expansion.placeholderadapter.paper;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.utils.LegacyStrings;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.AMPERSAND_CHAR;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.SECTION_CHAR;

@SuppressWarnings("unused")
public final class PaperPlatform {

    public static Expansion.Builder provideBuilder() {
        return Expansion.builder("placeholder-adapter")
            .globalPlaceholder("global", (queue, ctx) -> {
                final String argument = queue.popOr("You need to provide a placeholder").value();
                final String papiParsed = PlaceholderAPI.setPlaceholders(
                        null,
                        argument
                ).replace(SECTION_CHAR, AMPERSAND_CHAR);

                if (parseString(queue)) {
                    return Tag.preProcessParsed(miniMessage().serialize(LegacyStrings.parsePossibleLegacy(papiParsed)));
                } else {
                    return Tag.selfClosingInserting(LegacyStrings.parsePossibleLegacy(papiParsed));
                }
            })
            .audiencePlaceholder(Player.class, "player", (player, queue, ctx) -> {
                final String argument = queue.popOr("You need to provide a placeholder").value();
                final String papiParsed = PlaceholderAPI.setPlaceholders(
                        player,
                        argument
                ).replace(SECTION_CHAR, AMPERSAND_CHAR);

                if (parseString(queue)) {
                    return Tag.preProcessParsed(miniMessage().serialize(LegacyStrings.parsePossibleLegacy(papiParsed)));
                } else {
                    return Tag.selfClosingInserting(LegacyStrings.parsePossibleLegacy(papiParsed));
                }
            })
            .relationalPlaceholder(Player.class, "relational", (player, toShow, queue, ctx) -> {
                final String argument = queue.popOr("You need to provide a placeholder").value();
                final String papiParsed = PlaceholderAPI.setRelationalPlaceholders(
                        player,
                        toShow,
                        argument
                ).replace(SECTION_CHAR, AMPERSAND_CHAR);

                if (parseString(queue)) {
                    return Tag.preProcessParsed(miniMessage().serialize(LegacyStrings.parsePossibleLegacy(papiParsed)));
                } else {
                    return Tag.selfClosingInserting(LegacyStrings.parsePossibleLegacy(papiParsed));
                }
            });
    }

    private static boolean parseString(ArgumentQueue queue) {
        return queue.hasNext() && queue.pop().lowerValue().equals("string");
    }
}
