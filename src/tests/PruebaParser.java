public class PruebaParser {
    public void testParseExpression() {
        String input = "(+ 2 (* 3 4))";
        Node ast = Parser.parse(input);
        
        assertNotNull(ast, "El árbol sintáctico no debe ser nulo.");
        assertTrue(ast instanceof ListNode, "La raíz debe ser una lista.");
    }
}
