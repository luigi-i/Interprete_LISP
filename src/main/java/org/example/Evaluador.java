package org.example;

import java.util.List;
import java.util.ArrayList;

public class Evaluador {

    // Método principal para evaluar una expresión
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

    // Evalúa un átomo (número o variable)
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
                case "define":
                    // Manejar la definición de funciones en tiempo real
                    return definirFuncion(elementos.subList(1, elementos.size()), env);
                case "if":
                    // Manejar la expresión if
                    return evalIf(new ListNode(elementos.subList(1, elementos.size())), env);
                case "=":
                    // Manejar la expresión =
                    return evalIgual(new ListNode(elementos.subList(1, elementos.size())), env);
                default:
                    // Si no es un operador, es una función definida
                    FunctionDef funcion = env.getFuncion(operador);
                    return aplicarFunc(funcion, elementos.subList(1, elementos.size()), env);
            }
        } else {
            throw new RuntimeException("El primer elemento de la lista debe ser un operador o función.");
        }
    }

    // Evalúa una operación aritmética
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

    // Aplica una función definida
    private static Node aplicarFunc(FunctionDef funcion, List<Node> args, Entorno env) {
        // Verifica que el número de argumentos coincida con el número de parámetros
        if (args.size() != funcion.getParameters().size()) {
            throw new RuntimeException("Número incorrecto de argumentos. Esperados: " + funcion.getParameters().size() + ", obtenidos: " + args.size());
        }

        // Crea un nuevo entorno para la ejecución de la función, con el entorno de cierre como padre
        Entorno functionEnv = new Entorno(funcion.getClosure());

        // Asigna los valores de los argumentos a los parámetros en el nuevo entorno
        for (int i = 0; i < funcion.getParameters().size(); i++) {
            String paramName = funcion.getParameters().get(i);
            Node argValue = args.get(i);
            // Evalúa el argumento en el entorno actual antes de asignarlo
            Node evaluatedArg = eval(argValue, env);
            functionEnv.defineVariable(paramName, Integer.parseInt(((AtomNode) evaluatedArg).getValor()));
        }

        // Evalúa el cuerpo de la función en el nuevo entorno
        return eval(funcion.getBody(), functionEnv);
    }

    // Define una nueva función en el entorno
    private static Node definirFuncion(List<Node> elementos, Entorno env) {
        // La estructura de define es: (define (nombre param1 param2 ...) cuerpo)
        if (elementos.size() < 2) {
            throw new RuntimeException("Expresión 'define' mal formada.");
        }

        // El primer elemento es la lista de nombre y parámetros
        Node nombreYParametros = elementos.get(0);
        if (!(nombreYParametros instanceof ListNode)) {
            throw new RuntimeException("La definición de función debe tener una lista de nombre y parámetros.");
        }

        ListNode listaNombreYParametros = (ListNode) nombreYParametros;
        List<Node> elementosNombreYParametros = listaNombreYParametros.getElementos();

        // El nombre de la función es el primer elemento de la lista
        if (elementosNombreYParametros.isEmpty()) {
            throw new RuntimeException("La definición de función debe incluir un nombre.");
        }

        Node nombreFuncionNode = elementosNombreYParametros.get(0);
        if (!(nombreFuncionNode instanceof AtomNode)) {
            throw new RuntimeException("El nombre de la función debe ser un átomo.");
        }

        String nombreFuncion = ((AtomNode) nombreFuncionNode).getValor();

        // Los parámetros son los elementos restantes de la lista
        List<String> parametros = new ArrayList<>();
        for (int i = 1; i < elementosNombreYParametros.size(); i++) {
            Node parametroNode = elementosNombreYParametros.get(i);
            if (!(parametroNode instanceof AtomNode)) {
                throw new RuntimeException("Los parámetros de la función deben ser átomos.");
            }
            parametros.add(((AtomNode) parametroNode).getValor());
        }

        // El cuerpo de la función es el segundo elemento de la expresión define
        Node cuerpoFuncion = elementos.get(1);

        // Crear la función y registrarla en el entorno
        FunctionDef funcion = new FunctionDef(parametros, cuerpoFuncion, env);
        env.defineFunction(nombreFuncion, funcion);

        // Devolver un mensaje de éxito (o simplemente devolver la función)
        return new AtomNode("Función definida: " + nombreFuncion);
    }

    private static Node evalIf(ListNode list, Entorno env) {
        List<Node> elementos = list.getElementos();
        if (elementos.size() < 3) {
            throw new RuntimeException("Expresión 'if' mal formada. Se esperan 3 elementos: condición, valor-si-verdadero, valor-si-falso.");
        }

        // Evaluar la condición
        Node condicion = eval(elementos.get(0), env);
        boolean esVerdadero = esVerdadero(condicion);

        // Devolver el valor correspondiente
        if (esVerdadero) {
            return eval(elementos.get(1), env);  // valor-si-verdadero
        } else {
            return eval(elementos.get(2), env);  // valor-si-falso
        }
    }

    // Método auxiliar para determinar si un nodo es "verdadero"
    private static boolean esVerdadero(Node nodo) {
        if (nodo instanceof AtomNode) {
            String valor = ((AtomNode) nodo).getValor();
            // En LISP, cualquier valor distinto de "false" o "0" se considera verdadero
            return !valor.equalsIgnoreCase("false") && !valor.equals("0");
        }
        return true;  // Los nodos que no son átomos se consideran verdaderos
    }

    private static Node evalIgual(ListNode list, Entorno env) {
        List<Node> elementos = list.getElementos();
        if (elementos.size() != 2) {
            throw new RuntimeException("Expresión '=' mal formada. Se esperan 2 operandos.");
        }

        // Evaluar los operandos
        Node izquierdo = eval(elementos.get(0), env);
        Node derecho = eval(elementos.get(1), env);

        // Comparar los valores
        if (izquierdo instanceof AtomNode && derecho instanceof AtomNode) {
            String valorIzquierdo = ((AtomNode) izquierdo).getValor();
            String valorDerecho = ((AtomNode) derecho).getValor();
            return new AtomNode(valorIzquierdo.equals(valorDerecho) ? "true" : "false");
        } else {
            throw new RuntimeException("Los operandos de '=' deben ser átomos.");
        }
    }
}