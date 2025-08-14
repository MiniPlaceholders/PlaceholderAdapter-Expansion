package io.github.miniplaceholders.expansion.placeholderadapter;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.MiniPlaceholders;
import io.github.miniplaceholders.api.provider.ExpansionProvider;
import io.github.miniplaceholders.api.provider.LoadRequirement;
import io.github.miniplaceholders.api.types.Platform;
import io.github.miniplaceholders.expansion.placeholderadapter.velocity.VelocityPlatform;
import io.github.miniplaceholders.expansion.placeholderadapter.paper.PaperPlatform;

public final class AdapterExpansionProvider implements ExpansionProvider {
    @Override
    public Expansion provideExpansion() {
        return switch (MiniPlaceholders.platform()) {
            case PAPER -> PaperPlatform.provideExpansion();
            case VELOCITY -> VelocityPlatform.provideExpansion();
            default -> throw new UnsupportedOperationException("Unsupported Platform");
        };
    }

    @Override
    public LoadRequirement loadRequirement() {
        return LoadRequirement.allOf(
                LoadRequirement.platform(Platform.PAPER, Platform.VELOCITY),
                LoadRequirement.requiredComplement("placeholderapi", "PlaceholderAPI"),
                LoadRequirement.requiredComplement("papiproxybridge")
        );
    }
}
