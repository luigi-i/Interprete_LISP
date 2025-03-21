package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Creacion del entorno
        Entorno globalEnv = new Entorno(null);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("LISP> ");
            String input = scanner.nextLine().trim();

            // Salir si el usuario escribe "salir"
            if (input.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del intérprete LISP...");
                break;
            }

            try {

                ArrayList<String> tokens = Lexer.Tokenizer(input);
                System.out.println("Tokens: " + tokens);

                Node estructura = Parser.parse(tokens);
                System.out.println("Estructura: " + estructura);

                Node resultado = Evaluador.eval(estructura, globalEnv);
                System.out.println("Resultado: " + resultado);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

    }
}