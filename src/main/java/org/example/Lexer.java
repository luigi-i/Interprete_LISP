package org.example;
import java.util.ArrayList;

public class Lexer {

    public static boolean Verificar(String input){//Verifica que haya el mismo numero de
        //parentesis que abren y que cierran
        int pa = 0;
        int pc = 0;//contador abre y cierra

        for (int i = 0; i < input.length(); i++){
            if (input.charAt(i) == '('){ pa++;}
            else if (input.charAt(i) == ')'){ pc++;}//agrega 1 por cada ( ) que encuentre
        }

        if (pa == pc){
            System.out.println("Expresion correcta");
            return true;} //si son iguales retorna true
        else{
            System.out.println("Expresion incorrecta");
            return false;}
    }

    public static ArrayList<String> Tokenizer(String input){
        if(!input.isEmpty() && Verificar(input)){
            ArrayList<String> lista = new ArrayList<>();

            //si encuentra un numero o una letra, se va a la siguiente posicion del String
            //y repite hasta que ya no encuentre numeros o letras, entonces avanza el contador i
            //segun el numero de caracteres que se haya avanzado en una pasada


            int i = 0; //contador de caracteres del String
            boolean check = false;//se vuelve true para indicar en cual de los multiples whiles hay
            //que realizar operaciones, al terminar y guardar el valor en una posicion de la lista
            //regresa a false
            String add = "";//aqui se va almacenando todas las letras o numeros consecutivos para agregarse al final
            while (i < input.length()){//mientras no se exceda la longitud del String


                while (Character.isDigit(input.charAt(i) )){//Si i es un digito
                    add += input.charAt(i);
                    check = true;
                    i++; //revisa i+1
                }
                if (check){lista.add(add); check = false; add = "";
                    ;}//todos los sistem.out solo son para verificar si algo falla ver en que paso fue

                while (Character.isAlphabetic(input.charAt(i) ) && i < input.length()-1){//revisa si i es letra
                    add += input.charAt(i);
                    check = true;
                    i++;
                }
                if (check){lista.add(add); check = false; add = "";
                    ;}

                if ( input.charAt(i) == ' '  ){ i++; //se salta la posicion si hay un espacio
                    System.out.println(lista);}

                if(input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/' || input.charAt(i) == '='){
                    lista.add(Character.toString(input.charAt(i)));
                    i++; //agrega un signo a la lista si lo encuentra
                }

                if (input.charAt(i) == ')' || input.charAt(i) == '('){//si es ( o ) solo los agrega a la lista
                    lista.add(Character.toString(input.charAt(i)));
                    i++;

                }
            }


            return lista;
        }
        return null;
    }

}
