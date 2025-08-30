package io.github.miniplaceholders.expansion.placeholderadapter;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.MiniPlaceholders;
import io.github.miniplaceholders.api.provider.ExpansionProvider;
import io.github.miniplaceholders.api.provider.LoadRequirement;
import io.github.miniplaceholders.api.provider.PlatformData;
import io.github.miniplaceholders.api.types.Platform;
import io.github.miniplaceholders.expansion.placeholderadapter.velocity.VelocityPlatform;
import io.github.miniplaceholders.expansion.placeholderadapter.paper.PaperPlatform;
import io.github.miniplaceholders.expansion.placeholderadapter.fabric.FabricPlatform;
import team.unnamed.inject.Inject;

public final class AdapterExpansionProvider implements ExpansionProvider {
    @Inject
    private PlatformData platformData;

    @Override
    public Expansion provideExpansion() {
        final Expansion.Builder builder = switch (MiniPlaceholders.platform()) {
            case PAPER -> PaperPlatform.provideBuilder();
            case VELOCITY -> VelocityPlatform.provideBuilder();
            case FABRIC -> FabricPlatform.provideBuilder(platformData.serverInstance());
            default -> throw new UnsupportedOperationException("Unsupported Platform");
        };

        return builder.author("MiniPlaceholders Contributors")
                .version("2.1.0")
                .build();
    }

    @Override
    public LoadRequirement loadRequirement() {
        return LoadRequirement.allOf(
                LoadRequirement.platform(Platform.PAPER, Platform.VELOCITY, Platform.FABRIC),
                LoadRequirement.requiredComplement(
                        // Paper
                        "placeholderapi", "PlaceholderAPI",
                        // Velocity
                        "papiproxybridge",
                        // Fabric
                        "placeholder-api"
                )
        );
    }
}
