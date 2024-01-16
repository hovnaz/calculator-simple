package tech.it.mentor;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

public class CalculatorTest {

    @Test
    public void testValidExpression() {
        Calculator calculator = new Calculator();

        assertEquals(5, calculator.calculate("2+3"));
        assertEquals(5, calculator.calculate("10/2"));
        assertEquals(-1, calculator.calculate("2-3"));
        assertEquals(12, calculator.calculate("2*6"));

    }

    @Test
    public void testInvalidExpression() {
        Calculator calculator = new Calculator();
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2-30"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("210-3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2-3.3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("210*0"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("210/3"));

        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("+2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("+2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2+003"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1110+2*345+10+10-10/100/10+0000346.00545"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("/7d2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("abs"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("+3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2++3+"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2+3/"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("3%"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("3+"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("3%"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate(""));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2$3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2+3+"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("2++3+4"));

        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1110+690+10+10-0.10/0"));

        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1-+2"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1*2/"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1+2-"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1/0")); // Division by zero
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1.5.2")); // Multiple decimal points
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1.5")); //  decimal points is not supported
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1E2")); // Scientific notation
        assertThrows(InvalidExpressionException.class, () -> calculator.calculate("1^2")); // Unsupported operator
    }
}
