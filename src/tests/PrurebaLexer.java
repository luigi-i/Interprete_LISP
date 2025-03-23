import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class PrurebaLexer {
    @Test
    public void testTokenizeSimpleExpression() {
        String input = "(+ 2 (* 3 4))";
        List<String> tokens = Lexer.Tokenizer(input);
        List<String> expected = List.of("(", "+", "2", "(", "*", "3", "4", ")", ")");

        assertEquals(expected, tokens, "El lexer no tokenizó correctamente la expresión.");
    }
}
