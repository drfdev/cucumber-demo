package dev.drf.cucumber.demo.storage;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;

public class Hooks {
    @Before
    public void prepare() {
        Storage.clear();
    }

    @After
    public void clear() {
        Storage.clear();
    }

    @AfterStep
    public void prepareToNextStep() {
        Storage.nextStep();
    }
}
