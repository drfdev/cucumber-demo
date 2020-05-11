package dev.drf.cucumber.demo.utils;

import dev.drf.cucumber.demo.common.Pokemon;

import java.util.Map;

public final class PokemonBuilderUtil {
    private PokemonBuilderUtil() {
    }

    public static Pokemon buildFrom(Map<String, String> values) throws Exception {
        ObjectBuilder<Pokemon> objectBuilder = ObjectBuilder.of(Pokemon.class);
        return objectBuilder.build(values);
    }
}
