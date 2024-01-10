package tech.it.mentor;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {


    @Test
    public void testValidExpression() {
        Calculator calculator = new Calculator();

        assertEquals(new BigDecimal(5), calculator.calc("2+3"));
        assertEquals(new BigDecimal("1819.99"), calculator.calc("1110+2*345+10+10-10/100/10"));
        assertEquals(new BigDecimal("7.00"), calculator.calc("7/1"));
        assertEquals(new BigDecimal(0), calculator.calc("5*0"));
        assertEquals(new BigDecimal(7), calculator.calc("2+3*2-1"));
    }

    @Test
    public void testInvalidExpression() {
        Calculator calculator = new Calculator();

        assertThrows(InvalidExpressionException.class, () -> calculator.calc("+2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("/7d2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("abs"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("+3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2++3+"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2+3/"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("3%"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("3+"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("3%"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc(""));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2$3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2++3"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2+3+"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("2++3+4"));

        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1110+690+10+10-0.10/0"));

        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1-+2"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1*2/"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1+2-"));
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1/0")); // Division by zero
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1.5.2")); // Multiple decimal points
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1.5")); //  decimal points is not supported
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1E2")); // Scientific notation
        assertThrows(InvalidExpressionException.class, () -> calculator.calc("1^2")); // Unsupported operator
    }

}