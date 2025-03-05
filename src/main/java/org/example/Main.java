package org.example;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        String input = "(+ 22 (* count 8))";

        ArrayList<String> lista = Lexer.Tokenizer(input); // solo tokenizer
        System.out.println(lista);
//
//        ArrayList<ArrayList<String>> listaparseada = Lexer.Parser(input); //parser (no es para esta entrega)
//        System.out.println(listaparseada);



    }
}