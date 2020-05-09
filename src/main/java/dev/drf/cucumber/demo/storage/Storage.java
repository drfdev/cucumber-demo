package dev.drf.cucumber.demo.storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class Storage {
    private Storage() {
    }

    private static final AtomicInteger step = new AtomicInteger(1);
    private static final Map<Integer, List<Object>> storage = new HashMap<>();

    static void clear() {
        storage.clear();
        step.set(1);
    }

    static void nextStep() {
        step.incrementAndGet();
    }

    public static void saveToStorage(Object value) {
        if (value instanceof List list) {
            saveToStorage(list);
        } else {
            storage.put(step.get(), Collections.singletonList(value));
        }
    }

    public static void saveToStorage(List<Object> values) {
        storage.put(step.get(), values);
    }

    public static List<Object> getValuesByStep(int stepNumber) {
        return storage.get(stepNumber);
    }

    public static <T> T getSingleValueByStep(int stepNumber) {
        List<Object> values = storage.get(stepNumber);
        if (values == null) {
            throw new AssertionError(String.format("Result by step %s is null", stepNumber));
        }
        return (T) values.get(0);
    }
}
