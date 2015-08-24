package ua.org.s4code.intellicalc.analyser.function.basic;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.ValueType;

/**
 * Created by Serhii on 8/11/2015.
 */
public class Add extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws ExprException {
        Expression result = null;

        if (cachedValue == null) {
            switch (arguments.size()) {
                case 0:
                    throw new ExprException(startPos, endPos, "There are lack of operands.");
                default:
                    if (Function.isValues(context, arguments)) {
                        double acc = 0.0;
                        for (Expression argument : arguments) {
                            acc += ((ValueType) argument.result(context)).getValue(context);
                        }

                        cachedValue = new Literal(acc);
                        result = cachedValue;
                    } else {
                        throw new ExprException(startPos, endPos, "Type of arguments is not permitted.");
                    }
            }
        } else {
            result = cachedValue;
        }

        return result;
    }
}
