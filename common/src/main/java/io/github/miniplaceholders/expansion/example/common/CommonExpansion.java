package io.github.miniplaceholders.expansion.example.common;

import io.github.miniplaceholders.api.Expansion;

public final class CommonExpansion {
    public static void register() {
        Expansion.builder("example")
                .build()
                .register();
    }
}
