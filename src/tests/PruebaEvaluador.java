public class PruebaEvaluador {
    @Test
    public void testEvaluarOperacionBasica() {
        String input = "(+ 2 3)";
        Node ast = Parser.parse(input);
        Object resultado = Evaluador.evaluar(ast, new Entorno());

        assertEquals(5, resultado, "La evaluación de la suma es incorrecta.");
    }
}
