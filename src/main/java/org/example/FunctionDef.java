package org.example;

import java.util.List;

public class FunctionDef {
    private List<String> parameters;  // Lista de nombres de parametros
    private Node body;               // cuerpo de la funcion
    private Entorno closure;         // Entorno en el que se definió la funcion

    // Constructor
    public FunctionDef(List<String> parameters, Node body, Entorno closure) {
        this.parameters = parameters;
        this.body = body;
        this.closure = closure;
    }

    // Metodo para aplicar la función con argumentos
    public Node apply(List<Node> args, Entorno env) {
        // Verifica que el número de argumentos coincida con el número de parámetros
        if (args.size() != parameters.size()) {
            throw new RuntimeException("Número incorrecto de argumentos. Esperados: " + parameters.size() + ", obtenidos: " + args.size());
        }

        // Crea un nuevo entorno para la ejecución de la función, con el entorno de cierre como padre
        Entorno functionEnv = new Entorno(closure);

        // Asigna los valores de los argumentos a los parámetros en el nuevo entorno
        for (int i = 0; i < parameters.size(); i++) {
            String paramName = parameters.get(i);
            Node argValue = args.get(i);
            // Evalúa el argumento en el entorno actual antes de asignarlo
            Node evaluatedArg = Evaluador.eval(argValue, env);
            functionEnv.defineVariable(paramName, Integer.parseInt(((AtomNode) evaluatedArg).getValor()));
        }

        // Evalúa el cuerpo de la función en el nuevo entorno
        return Evaluador.eval(body, functionEnv);
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Node getBody() {
        return body;
    }

    public Entorno getClosure() {
        return closure;
    }
}