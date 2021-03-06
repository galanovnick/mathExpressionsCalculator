package calculator.impl;

import calculator.MathExpressionsCalculator;
import calculator.CalculationException;
import calculator.impl.abstractstatemachine.AbstractCharacterExpressionResolver;
import calculator.impl.context.InputMathExpressionContext;
import calculator.impl.context.OutputMathExpressionContext;
import calculator.impl.parser.*;
import calculator.impl.abstractstatemachine.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static calculator.impl.abstractstatemachine.State.*;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MathExpressionsCalculatorImpl
        extends AbstractCharacterExpressionResolver<State>
        implements MathExpressionsCalculator {

    private final static Logger log = LoggerFactory.getLogger(MathExpressionsCalculator.class);

    private final ExpressionParsersContainer<State> registeredParsers
            = new ExpressionParsersContainer<>(registerParsers());

    public MathExpressionsCalculatorImpl() {
        super(registerTransitions());
    }

    public double evaluate(String mathExpression) throws CalculationException {

        checkNotNull(mathExpression, "Expected not null math expression");
        checkArgument(mathExpression.length() != 0, "Expected not empty math expression");

        InputMathExpressionContext inputContext =
                new InputMathExpressionContext(mathExpression, registeredParsers);

        OutputMathExpressionContext outputContext =
                new OutputMathExpressionContext();

        run(inputContext, outputContext, START, FINISH);

        return outputContext.getResult();
    }

    @Override
    public void deadlock(int deadlockPosition) throws CalculationException {
        if (log.isWarnEnabled()) {
            log.warn("Input expression is invalid. Symbol at "
                    + (deadlockPosition + 1) + " position unresolved.");
        }


        throw new CalculationException("Cannot resolve symbol at "
                + (deadlockPosition + 1) + " position",
                deadlockPosition + 1);
    }

    private static Map<State, EnumSet<State>> registerTransitions() {
        return new HashMap<State, EnumSet<State>>() {{

            put(START, EnumSet.of(NUMBER, FUNCTION));
            put(NUMBER, EnumSet.of(ARGUMENTS_SEPARATOR, BINARY_OPERATOR, CLOSE_BRACKET, FINISH));
            put(FUNCTION, EnumSet.of(NUMBER, FUNCTION));
            put(CLOSE_BRACKET, EnumSet.of(CLOSE_BRACKET, BINARY_OPERATOR, ARGUMENTS_SEPARATOR, FINISH));
            put(BINARY_OPERATOR, EnumSet.of(NUMBER, FUNCTION));
            put(ARGUMENTS_SEPARATOR, EnumSet.of(NUMBER, FUNCTION));
        }};
    }

    private static HashMap<State, ExpressionParser> registerParsers() {
        return new HashMap<State, ExpressionParser>() {{

            put(NUMBER, new NumberParser());
            put(BINARY_OPERATOR, new BinaryOperatorParser());
            put(FUNCTION, new FunctionParser());
            put(ARGUMENTS_SEPARATOR, new ArgumentsSeparatorParser());
            put(CLOSE_BRACKET, new CloseBracketParser());
            put(FINISH, new FinishParser());
        }};
    }
}
