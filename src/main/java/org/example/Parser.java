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

        public String next() {
            if (!hasNext()) {
                throw new IllegalArgumentException("Error: No hay más tokens.");
            }
            return tokens.get(indice++);
        }

        public String peek() {
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

            // procesa tokens y encuentra parentesis de cierre
            while (iterador.hasNext() && !iterador.peek().equals(")")) {
                expression.add(parseExpression(iterador));
            }

            // Consume el paréntesis de cierre
            iterador.next();
            return expression;
        } else if (token.equals(")")) {

            throw new IllegalArgumentException("Error: Paréntesis de cierre inesperado.");
        } else {

            return new AtomNode(token);
        }
    }

    
}