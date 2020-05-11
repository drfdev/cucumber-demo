package dev.drf.cucumber.demo.utils.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class ValueConverter {
    private final static Map<Class<?>, BiFunction<Class<?>, String, Object>> CONVERTERS = new HashMap<>() {{
        put(int.class, new IntegerConverter()); put(Integer.class, new IntegerConverter());
        put(long.class, new LongConverter()); put(Long.class, new LongConverter());
        put(char.class, new CharacterConverter()); put(Character.class, new CharacterConverter());
        put(byte.class, new ByteConverter()); put(Byte.class, new ByteConverter());
        put(float.class, new FloatConverter()); put(Float.class, new FloatConverter());
        put(double.class, new DoubleConverter()); put(Double.class, new DoubleConverter());
        put(short.class, new ShortConverter()); put(Short.class, new ShortConverter());
        put(String.class, new StringConverter());
        put(Enum.class, new EnumConverter());
    }};

    private ValueConverter() {
    }

    public static Object convert(Class<?> type, String value) {
        if (value == null || "null".equalsIgnoreCase(value)) {
            return null;
        }
        Class<?> clazz = type;
        if (Enum.class.isAssignableFrom(type)) {
            clazz = Enum.class;
        }
        BiFunction<Class<?>, String, Object> converter = CONVERTERS.get(clazz);
        return converter.apply(type, value);
    }

    private static class IntegerConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Integer.valueOf(value);
        }
    }

    private static class LongConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Long.valueOf(value);
        }
    }

    private static class CharacterConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            if (value.length() > 1) {
                throw new RuntimeException("CharacterConverter: value length > 1: " + value.length());
            }
            return value.charAt(0);
        }
    }

    private static class ByteConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Byte.valueOf(value);
        }
    }

    private static class FloatConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Float.valueOf(value);
        }
    }

    private static class DoubleConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Double.valueOf(value);
        }
    }

    private static class ShortConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Short.valueOf(value);
        }
    }

    private static class StringConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return value;
        }
    }

    private static class EnumConverter implements BiFunction<Class<?>, String, Object> {
        @Override
        public Object apply(Class<?> type, String value) {
            return Enum.valueOf((Class<? extends Enum>) type, value);
        }
    }
}
