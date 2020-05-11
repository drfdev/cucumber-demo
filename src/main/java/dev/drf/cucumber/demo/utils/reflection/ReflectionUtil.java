package dev.drf.cucumber.demo.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

public final class ReflectionUtil {
    private static final Set<Class<?>> simpleTypes = Set.of(
            int.class, long.class, char.class, byte.class, float.class, double.class, short.class,
            Integer.class, Long.class, Character.class, Byte.class, Float.class, Double.class, Short.class,
            String.class
    );

    private ReflectionUtil() {
    }

    public static FieldTree loadFieldTree(Class<?> clazz) {
        FieldTree fieldTree = new FieldTree(clazz);

        FieldTreeNode root = fieldTree.getRoot();
        buildTree(root);

        return fieldTree;
    }

    private static void buildTree(FieldTreeNode node) {
        Class<?> type = node.getType();
        Field[] fields = type.getDeclaredFields();
        Method[] methods = type.getDeclaredMethods();

        for (Field f : fields) {
            String name = f.getName();
            Class<?> fieldType = f.getType();

            FieldTreeNode subNode;
            boolean isCollection = Iterable.class.isAssignableFrom(fieldType);
            if (isCollection) {
                Class<?> repeatableClass = fieldType;
                fieldType = (Class<?>) ((ParameterizedType) f.getGenericType())
                        .getActualTypeArguments()[0];
                subNode = FieldTreeNode.collectionNode(name, fieldType);
                subNode.setRepeatableClass(repeatableClass);
            } else {
                subNode = FieldTreeNode.objectNode(name, fieldType);
            }

            String getter = getterName(name);
            String setter = setterName(name);

            subNode.setGetter(searchMethod(methods, getter));
            subNode.setSetter(searchMethod(methods, setter));

            node.addChildNode(subNode);

            if (!isSimpleType(fieldType) && !isEnum(fieldType)) {
                buildTree(subNode);
            }
        }
    }

    private static String getterName(String name) {
        return "get" + Character.toUpperCase(name.charAt(0))
                + name.substring(1);
    }

    private static String setterName(String name) {
        return "set" + Character.toUpperCase(name.charAt(0))
                + name.substring(1);
    }

    private static Method searchMethod(Method[] array, String name) {
        for (Method m : array) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    private static boolean isSimpleType(Class<?> clazz) {
        return simpleTypes.contains(clazz);
    }

    private static boolean isEnum(Class<?> clazz) {
        return Enum.class.isAssignableFrom(clazz);
    }
}
