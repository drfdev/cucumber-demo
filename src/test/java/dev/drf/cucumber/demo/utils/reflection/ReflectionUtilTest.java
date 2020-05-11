package dev.drf.cucumber.demo.utils.reflection;

import dev.drf.cucumber.demo.common.Pokemon;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ReflectionUtilTest {

    @Test
    public void reflection() {
        FieldTree fieldTree = ReflectionUtil.loadFieldTree(Pokemon.class);

        fieldTree.toString();
    }
}
