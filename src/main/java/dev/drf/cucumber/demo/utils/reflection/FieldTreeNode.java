package dev.drf.cucumber.demo.utils.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FieldTreeNode {
    private final String name;
    private final Class<?> type;
    private final boolean repeatable;

    private Method getter;
    private Method setter;
    private List<FieldTreeNode> nodes;

    private Class<?> repeatableClass;

    private FieldTreeNode(String name, Class<?> type, boolean repeatable) {
        this.name = name;
        this.type = type;
        this.repeatable = repeatable;

        this.nodes = new ArrayList<>();
    }

    public static FieldTreeNode objectNode(String name, Class<?> type) {
        return new FieldTreeNode(name, type, false);
    }

    public static FieldTreeNode collectionNode(String name, Class<?> type) {
        return new FieldTreeNode(name, type, true);
    }

    public String getName() {
        return this.name;
    }

    public Class<?> getType() {
        return this.type;
    }

    public Method getGetter() {
        return this.getter;
    }

    public Method getSetter() {
        return this.setter;
    }

    void setGetter(Method getter) {
        this.getter = getter;
    }

    void setSetter(Method setter) {
        this.setter = setter;
    }

    void addChildNode(FieldTreeNode node) {
        this.nodes.add(node);
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public List<FieldTreeNode> getNodes() {
        return nodes;
    }

    public Class<?> getRepeatableClass() {
        return repeatableClass;
    }

    void setRepeatableClass(Class<?> repeatableClass) {
        this.repeatableClass = repeatableClass;
    }
}
