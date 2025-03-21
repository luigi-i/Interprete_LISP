package org.example;

import java.util.List;

public class Evaluador {

    public static Node eval(Node expr, Entorno env) {
        if (expr instanceof AtomNode) {
            // Si es un átomo, devuelve su valor
            return evalAtom((AtomNode) expr, env);
        } else if (expr instanceof ListNode) {
            // Si es una lista, evalúa la expresión
            return evalList((ListNode) expr, env);
        } else {
            throw new RuntimeException("Tipo de nodo no reconocido: " + expr.getClass().getSimpleName());
        }
    }

    private static Node evalAtom(AtomNode atom, Entorno env) {
        String valor = atom.getValor();
        try {
            // Si es un número, devuelve el número
            int numero = Integer.parseInt(valor);
            return new AtomNode(String.valueOf(numero));
        } catch (NumberFormatException e) {
            // Si no es un número, es una variable
            return new AtomNode(String.valueOf(env.getVariable(valor)));
        }
    }

    private static Node evalList(ListNode list, Entorno env) {
        List<Node> elementos = list.getElementos();
        if (elementos.isEmpty()) {
            throw new RuntimeException("Lista vacía no puede ser evaluada.");
        }

        Node primerElemento = elementos.get(0);
        if (primerElemento instanceof AtomNode) {
            String operador = ((AtomNode) primerElemento).getValor();
            switch (operador) {
                case "+":
                case "-":
                case "*":
                case "/":
                    return evalAritmetica(operador, elementos.subList(1, elementos.size()), env);
                default:
                    // Si no es un operador, es una función definida
                    FunctionDef funcion = env.getFuncion(operador);
                    return funcion.apply(elementos.subList(1, elementos.size()), env);
            }
        } else {
            throw new RuntimeException("El primer elemento de la lista debe ser un operador o función.");
        }
    }

    private static Node evalAritmetica(String operador, List<Node> operandos, Entorno env) {
        if (operandos.isEmpty()) {
            throw new RuntimeException("No hay operandos para la operación: " + operador);
        }

        // Evaluar el primer operando
        Node primerOperando = eval(operandos.get(0), env);
        int resultado = Integer.parseInt(((AtomNode) primerOperando).getValor());

        // Iterar sobre los operandos restantes
        for (int i = 1; i < operandos.size(); i++) {
            Node operando = eval(operandos.get(i), env);
            int valor = Integer.parseInt(((AtomNode) operando).getValor());

            switch (operador) {
                case "+":
                    resultado += valor;
                    break;
                case "-":
                    resultado -= valor;
                    break;
                case "*":
                    resultado *= valor;
                    break;
                case "/":
                    if (valor == 0) {
                        throw new RuntimeException("División por cero.");
                    }
                    resultado /= valor;
                    break;
                default:
                    throw new RuntimeException("Operador no reconocido: " + operador);
            }
        }

        return new AtomNode(String.valueOf(resultado));
    }

    private Node aplicarFunc(FunctionDef funcion, List<Node> args, Entorno env) {
        // Implementar la aplicación de la función
        // evaluar los argumentos y aplicar la función en el entorno
        throw new UnsupportedOperationException("Aplicación de función no implementada.");
    }
}