package dev.drf.cucumber.demo.steps;

import dev.drf.cucumber.demo.common.Pokemon;
import dev.drf.cucumber.demo.storage.Storage;
import dev.drf.cucumber.demo.utils.PokemonBuilderUtil;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;

import java.util.Map;

import static org.junit.Assert.*;

public class DefinedSteps {

    @Дано("^Создать покемона с перечисленными параметрами$")
    public void createPokemon(Map<String, String> values) throws Exception {
        Pokemon pokemon = PokemonBuilderUtil.buildFrom(values);
        Storage.saveToStorage(pokemon);
    }

    @Тогда("^Проверить что покемон на шаге (\\d+) создан$")
    public void checkPokemonNonNull(int stepNum) {
        Pokemon pokemon = Storage.getSingleValueByStep(stepNum);
        assertNotNull("Покемон должен быть создан!", pokemon);
    }
}
