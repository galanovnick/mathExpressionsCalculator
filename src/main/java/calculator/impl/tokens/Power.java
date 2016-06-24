package calculator.impl.tokens;

import calculator.impl.tokens.priorityenum.Priority;

import static calculator.impl.tokens.priorityenum.Priority.HIGH;

public class Power extends AbstractBinaryOperator {

    private final Priority priority = HIGH;

    @Override
    public Double execute(Double leftOperand, Double rightOperand) {
        return Math.pow(leftOperand, rightOperand);
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
}
