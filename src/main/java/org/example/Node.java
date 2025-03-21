package org.example;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    public abstract String toString();
}

class AtomNode extends Node {
    private String valor;

    public AtomNode(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}

class ListNode extends Node {
    private List<Node> elementos; // Sin final

    public ListNode() {
        this.elementos = new ArrayList<>();
    }

    public void add(Node node) {
        elementos.add(node);
    }

    public List<Node> getElementos() {
        return elementos;
    }

    @Override
    public String toString() {
        return elementos.toString();
    }
}