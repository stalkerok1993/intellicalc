package ua.org.s4code.intellicalc.analyser.function.basic;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.ValueType;

/**
 * Created by Serhii on 8/11/2015.
 */
public class Sub extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws Exception {
        Expression result = null;

        if (cachedValue == null) {
            switch (arguments.size()) {
                case 0:
                    throw new Exception("There are lack of operands.");
                default:
                    if (Function.isValues(context, arguments)) {
                        double acc = 0.0;
                        for (Expression argument : arguments) {
                            acc -= ((ValueType) argument.result(context)).getValue(context);
                        }

                        result = new Literal(acc);
                    } else {
                        throw new Exception("Type of arguments is not permitted.");
                    }
            }

            cachedValue = result;
        } else {
            result = cachedValue;
        }

        return result;
    }
}
