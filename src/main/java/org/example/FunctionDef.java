package org.example;

import java.util.List;

public class FunctionDef {
    private List<String> parameters;  // Lista de nombres de parametros
    private Node body;               // cuerpo de la funcion
    private Entorno closure;         // Entorno en el que se definió la funcion

    // Constructor
    public FunctionDef(List<String> parameters, Node body, Entorno closure) {
        this.parameters = parameters;
        this.body = body;
        this.closure = closure;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Node getBody() {
        return body;
    }

    public Entorno getClosure() {
        return closure;
    }
}