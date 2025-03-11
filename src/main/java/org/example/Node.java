package org.example;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {
        public abstract String toString();
}

class AtomNode extends Node {
    private final String value;

    public AtomNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

class ListNode extends Node {
    private final List<Node> elements = new ArrayList<>();

    public void add(Node node) {
        elements.add(node);
    }

    public List<Node> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}