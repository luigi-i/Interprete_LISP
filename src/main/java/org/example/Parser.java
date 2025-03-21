package org.example;

import java.util.List;

public class Parser {

    // Clase que define el iterador para recorrer los tokens
    private static class IteradorToken {
        private List<String> tokens;
        private int indice = 0;

        public IteradorToken(List<String> tokens) {
            this.tokens = tokens;
        }

        public boolean hasNext() {
            return indice < tokens.size();
        }

        public String next() {//avanza en la lista, en la practica consume el node que estaba ahi
            if (!hasNext()) {
                throw new IllegalArgumentException("Error: No hay más tokens.");
            }
            return tokens.get(indice++);
        }

        public String peek() {//Verifica el siguiente elemento sin avanzar
            if (!hasNext()) {
                throw new IllegalArgumentException("Error: No hay más tokens.");
            }
            return tokens.get(indice);
        }
    }

    public static Node parse(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No hay ninguna expresion, ingrese una");
        }

        // Crea iterador 
        IteradorToken iterador = new IteradorToken(tokens);
        Node resultado = parseExpression(iterador);

        // Verifica si sobraron tokens
        if (iterador.hasNext()) {
            throw new IllegalArgumentException("Error: Expresión mal formada. Tokens adicionales: " + iterador.peek());
        }

        return resultado;
    }

    private static Node parseExpression(IteradorToken iterador) {
        if (!iterador.hasNext()) {
            throw new IllegalArgumentException("Error: Expresión incompleta.");
        }

        String token = iterador.next();

        if (token.equals("(")) {
            // Inicia una nueva lista
            ListNode expression = new ListNode();

            // Procesa tokens y los guarda en la NodeList actual hasta encontrar el paréntesis de cierre
            while (iterador.hasNext() && !iterador.peek().equals(")")) {
                expression.add(parseExpression(iterador));
            }

            // Consume el paréntesis de cierre y cierra la lista
            if (iterador.hasNext() && iterador.peek().equals(")")) {
                iterador.next();  // Consume ")"
            } else { //Si no se encontraron parentesis de cierre
                throw new IllegalArgumentException("Error: Falta paréntesis de cierre.");
            }

            return expression;
        } else if (token.equals(")")) { //Si se encuentra un parentesis de cierre al principio
            throw new IllegalArgumentException("Error: Paréntesis de cierre inesperado, las expresiones empiezan en '(' .");
        } else {
            // la expresion solo tenia un atomNode
            return new AtomNode(token);
        }
    }


}