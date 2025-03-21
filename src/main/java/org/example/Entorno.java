package org.example;
import java.util.Map;
import java.util.HashMap;

public class Entorno {
    private Map<String, Integer> variables = new HashMap<>(); // Variables (nombre -> valor)
    private Map<String, FunctionDef> funciones = new HashMap<>(); // Funciones (nombre -> definición)
    private Entorno parent; // Para manejar ámbitos anidados

    public Entorno(Entorno parent) {  //constructor
        this.parent = parent;
    }

    // Define una variable en entorno
    public void defineVariable(String nombre, int value) {
        variables.put(nombre, value);
    }

    //Obriene valor de alguna variable
    public int getVariable(String nombre) {
        if (variables.containsKey(nombre)) {
            return variables.get(nombre);
        } else if (parent != null) {
            return parent.getVariable(nombre);
        } else {
            throw new RuntimeException("Variable no definida: " + nombre);
        }
    }

    // Define una función en entorno
    public void defineFunction(String nombre, FunctionDef funcion) {
        funciones.put(nombre, funcion);
    }

    // Obtiene definición de funcion
    public FunctionDef getFuncion(String nombre) {
        if (funciones.containsKey(nombre)) {
            return funciones.get(nombre);
        } else if (parent != null) {
            return parent.getFuncion(nombre);
        } else {
            throw new RuntimeException("Función no definida: " + nombre);
        }
    }
}