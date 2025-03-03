package org.example;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        String input = "(+ 22 (* count 8))";

        ArrayList<String> lista = Lexer.Tokenizer(input);

        System.out.println(lista);


    }
}