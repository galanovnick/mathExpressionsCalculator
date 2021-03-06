package calculator;

import calculator.impl.MathExpressionsCalculatorImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryOperatorsTest {

    MathExpressionsCalculator calculator = new MathExpressionsCalculatorImpl();

    @Test
    public void testPlusOperator() throws CalculationException {
        assertEquals("Plus operator has been calculated incorrect",
                4, calculator.evaluate("2+2"), 0.0001);
    }

    @Test
    public void testMinusOperator() throws CalculationException {
        assertEquals("Plus operator has been calculated incorrect",
                2, calculator.evaluate("4-2"), 0.0001);
    }

    @Test
    public void testMultiplyOperator() throws CalculationException {
        assertEquals("Plus operator has been calculated incorrect",
                -6, calculator.evaluate("-2*3"), 0.0001);
    }

    @Test
    public void testDivideOperator() throws CalculationException {
        assertEquals("Plus operator has been calculated incorrect",
                9, calculator.evaluate("36/4"), 0.0001);
    }

    @Test
    public void testPowerOperator() throws CalculationException {
        assertEquals("Plus operator has been calculated incorrect",
                256, calculator.evaluate("2^8"), 0.0001);
    }

    @Test
    public void testMultiplyPriority() throws CalculationException {
        assertEquals("Multiply priority has been resolved incorrect",
                6, calculator.evaluate("2+2*2"), 0.0001);
    }

    @Test
    public void testDividePriority() throws CalculationException {
        assertEquals("Divide priority has been resolved incorrect",
                4, calculator.evaluate("2+4/2"), 0.0001);
    }

    @Test
    public void testPowerPriority() throws CalculationException {
        assertEquals("Power priority has been resolved incorrect",
                18, calculator.evaluate("2*3^2"), 0.0001);
    }
}
