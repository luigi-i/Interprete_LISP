package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //Funciones de regalo:
    //Factorial:
    // (define (factorial n) (if (= n 0) 1 (* n (factorial (- n 1)))))
    //Fibonacci:
    // (define (fibonacci n) (if (= n 0) 0 (if (= n 1) 1 (+ (fibonacci (- n 1)) (fibonacci (- n 2))))))
    //MALAN
    // (define (MALAN M N) (if  (= N 0) 1  (* M (MALAN M (- N 1)))))



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