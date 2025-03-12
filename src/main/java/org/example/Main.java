package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        // Expresión LISP de ejemplo
        String input = "(+ 22 (* count 8))";
        //1. Lexer
        ArrayList<String> tokens = Lexer.Tokenizer(input); // solo tokenizer
        System.out.println(tokens);//muestra resultado

        // 2. Parser
        Node estructura = Parser.parse(tokens); //Parsea tokens
        System.out.println("Estructura: " + estructura); //muestra resultado

    }
}