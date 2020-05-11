package dev.drf.cucumber.demo.utils;

import dev.drf.cucumber.demo.utils.convert.ValueConverter;
import dev.drf.cucumber.demo.utils.reflection.FieldTree;
import dev.drf.cucumber.demo.utils.reflection.FieldTreeNode;
import dev.drf.cucumber.demo.utils.reflection.ReflectionUtil;

import java.util.*;

import static java.util.Objects.requireNonNull;

public class ObjectBuilder<T> {
    private final Class<T> clazz;
    private FieldTree fieldTree;

    private Map<String, Object> cache = new HashMap<>();

    private ObjectBuilder(Class<T> clazz) {
        this.clazz = requireNonNull(clazz);
    }

    public static <T> ObjectBuilder<T> of(Class<T> clazz) {
        ObjectBuilder<T> objectBuilder = new ObjectBuilder<>(clazz);
        objectBuilder.initFieldTree();
        return objectBuilder;
    }

    public T build(Map<String, String> values) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();
        values.forEach((key, value) -> {
            String[] path = key.split("\\.");
            setValueByPath(instance, path, value);
        });
        return instance;
    }

    private void initFieldTree() {
        this.fieldTree = ReflectionUtil.loadFieldTree(this.clazz);
    }

    private void setValueByPath(T instance, String[] path, String value) {
        FieldTreeNode root = fieldTree.getRoot();
        Object obj = instance;
        FieldTreeNode node = root;

        for (int i = 0; i < path.length; i++) {
            final String pathi = path[i];

            String p = path[i];
            boolean last = (i == path.length - 1);

            if (pathi.contains("[")) {
                p = pathi.substring(0, pathi.indexOf("["));
            }

            final String pathFieldName = p;
            node = node.getNodes().stream()
                    .filter(item -> item.getName().equals(pathFieldName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Wrong field name: " + pathFieldName));

            try {
                if (last) {
                    Object[] val = calculateValue(node.getType(), value);
                    if (node.isRepeatable()) {
                        // TODO List<String> or List<Enum>
                    } else {
                        node.getSetter().invoke(obj, val);
                    }
                } else {
                    Object val = node.getGetter().invoke(obj);
                    if (node.isRepeatable()) {
                        Iterable<?> collection = (Iterable<?>) val;
                        if (collection == null) {
                            collection = newCollectionInstance(node.getRepeatableClass());
                            node.getSetter().invoke(obj, collection);
                        }

                        String subPath = buildSubPath(path, i);
                        val = cache.get(subPath);

                        if (val == null) {
                            val = node.getType().getDeclaredConstructor().newInstance();
                            addToCollection(collection, val);
                            cache.put(subPath, val);
                        }
                    } else {
                        if (val == null) {
                            val = node.getType().getDeclaredConstructor().newInstance();
                            node.getSetter().invoke(obj, val);
                        }
                    }
                    obj = val;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private Object[] calculateValue(Class<?> type, String value) {
        Object val = ValueConverter.convert(type, value);
        Object[] vals = new Object[1];
        vals[0] = val;
        return vals;
    }

    private Iterable<?> newCollectionInstance(Class<?> clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            return new ArrayList<>();
        } else if (Set.class.isAssignableFrom(clazz)) {
            return new HashSet<>();
        }
        throw new RuntimeException("Unknown collection: " + clazz);
    }

    private void addToCollection(Iterable<?> collection, Object val) {
        if (collection instanceof List) {
            ((List) collection).add(val);
        } else if (collection instanceof Set) {
            ((Set) collection).add(val);
        }
    }

    private String buildSubPath(String[] path, int index) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= index; i++) {
            builder.append(path[i]);
        }
        return builder.toString();
    }
}
