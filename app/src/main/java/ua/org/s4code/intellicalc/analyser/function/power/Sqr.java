package ua.org.s4code.intellicalc.analyser.function.power;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.ValueType;

/**
 * Created by Serhii on 8/6/2015.
 */
public class Sqr extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws ExprException {
        Expression result = null;

        if (cachedValue == null) {
            switch (arguments.size()) {
                case 0:
                    throw new ExprException(startPos, endPos, "There are lack of operands.");
                case 1:
                    if (Function.isValues(context, arguments)) {
                        double num = ((ValueType) arguments.get(0).result(context))
                                .getValue(context);
                        double res = num * num;

                        result = new Literal(res);
                    } else {
                        throw new ExprException(startPos, endPos, "Type of arguments is not permitted.");
                    }
                    break;
                default:
                    throw new ExprException(startPos, endPos, "Too many parameters!");
            }

            cachedValue = result;
        } else {
            result = cachedValue;
        }

        return result;
    }
}
