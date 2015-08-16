package ua.org.s4code.intellicalc.analyser.function.power;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.ValueType;

/**
 * Created by Serhii on 8/11/2015.
 */
public class Pow extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws Exception {
        Expression result = null;

        if (cachedValue == null) {
            switch (arguments.size()) {
                case 0:
                    // fall through
                case 1:
                    throw new Exception("There are lack of operands.");
                case 2:
                    if (Function.isValues(context, arguments)) {
                        double num = ((ValueType) arguments.get(0).result(context))
                                .getValue(context);
                        double pow = ((ValueType) arguments.get(1).result(context))
                                .getValue(context);
                        double res = Math.pow(num, pow);

                        result = new Literal(res);
                    } else {
                        throw new Exception("Type of arguments is not permitted.");
                    }
                    break;
                default:
                    throw new Exception("Too many parameters!");
            }

            cachedValue = result;
        } else {
            result = cachedValue;
        }

        return result;
    }
}
