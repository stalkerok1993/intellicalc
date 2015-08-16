package ua.org.s4code.intellicalc.analyser.function.basic;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.ValueType;

/**
 * Created by Serhii on 8/11/2015.
 */
public class Factorial extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws Exception {
        Expression result = null;

        if (cachedValue == null) {
            switch (arguments.size()) {
                case 0:
                    throw new Exception("There are lack of operands.");
                case 1:
                    if (Function.isValues(context, arguments)) {
                        double num = ((ValueType) arguments.get(0).result(context))
                                .getValue(context);
                        double res = fct(((long) num));

                        result = new Literal(res);
                    } else {
                        throw new Exception("Type of arguments is not permitted.");
                    }
                    break;
                default:
                    throw new Exception("Too many operands.");
            }

            cachedValue = result;
        } else {
            result = cachedValue;
        }

        return result;
    }

    private static double fct(long op) {
        if (op < 0) {
            throw new IllegalArgumentException("op MUST be a positive integer or zero.");
        }

        long result = 1;

        for (int i = 1; i < op; result *= i++);

        return result;
    }
}
