package dev.drf.cucumber.demo.utils.reflection;

public class FieldTree {
    private final Class<?> objectClass;
    private final FieldTreeNode root;

    public FieldTree(Class<?> objectClass) {
        this.objectClass = objectClass;
        this.root = FieldTreeNode.objectNode("object", objectClass);
    }

    public FieldTreeNode getRoot() {
        return root;
    }
}
