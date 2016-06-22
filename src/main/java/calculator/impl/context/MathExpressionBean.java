package calculator.impl.context;

import calculator.impl.tokens.BinaryOperator;
import calculator.impl.tokens.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;

public class MathExpressionBean {

    private static final Logger log = LoggerFactory.getLogger(MathExpressionBean.class);

    private Deque<Double> numbersStack = new ArrayDeque<>();

    private Deque<BinaryOperator> operatorsStack = new ArrayDeque<>();

    private Function function;

    private MathExpressionBean parent;

    public MathExpressionBean() {
        if (log.isDebugEnabled()) {
            log.debug("Created bean from empty function.");
        }

        this.function = args -> numbersStack.pop();
    }

    public MathExpressionBean(Function function, MathExpressionBean parent) {
        if (log.isDebugEnabled()) {
            log.debug("Created bean from \"" + function.getClass().getName() + "\" function.");
        }

        this.parent = parent;
        this.function = function;
    }

    public MathExpressionBean getParent() {
        return parent;
    }

    public double getResultValue() {
        if (log.isDebugEnabled()) {
            log.debug("Popping result...");
        }

        while (!operatorsStack.isEmpty()) {
            popTopOperator();
        }

        return function.execute(numbersStack.toArray(new Double[0]));
    }

    public void pushOperand(double operator) {
        numbersStack.push(operator);
    }

    public void pushOperator(BinaryOperator operator) {
        while (!operatorsStack.isEmpty() &&
                (operatorsStack.peek().compareTo(operator) > 0)) {
            popTopOperator();
        }
        operatorsStack.push(operator);
    }

    private void popTopOperator() {
        double rightOperand = numbersStack.pop();
        double leftOperand = numbersStack.pop();

        numbersStack.add(operatorsStack.pop().execute(leftOperand, rightOperand));
    }
}
