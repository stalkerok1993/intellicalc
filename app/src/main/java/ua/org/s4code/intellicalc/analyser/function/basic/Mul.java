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
public class Mul extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments) throws Exception {
        Expression result = null;

        switch (arguments.size()) {
            case 0:
                // fall through
            case 1:
                throw new Exception("There are lack of operands.");
            default:
                if (Function.isValues(arguments)) {
                    double acc = ((ValueType) arguments.get(0)).getValue(context);
                    for (int i = 1; i < arguments.size(); i++) {
                        acc *= ((ValueType) arguments.get(i)).getValue(context);
                    }

                    result = new Literal(acc);
                } else {
                    throw new Exception("Type of arguments is not permitted.");
                }
        }

        return result;
    }
}
