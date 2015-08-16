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
public class Log extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments) throws Exception {
        Expression result = null;

        switch (arguments.size()) {
            case 0:
                throw new Exception("There are lack of operands.");
            case 1:
                if (Function.isValues(arguments)) {
                    double res = Math.log(((ValueType) arguments.get(0)).getValue(context));

                    result = new Literal(res);
                } else {
                    throw new Exception("Type of arguments is not permitted.");
                }
                break;
            case 2:
                if (Function.isValues(arguments)) {
                    double base = ((ValueType) arguments.get(0)).getValue(context);
                    double num = ((ValueType) arguments.get(1)).getValue(context);
                    double res = Math.log(base) / Math.log(num);

                    result = new Literal(res);
                } else {
                    throw new Exception("Type of arguments is not permitted.");
                }
                break;
            default:
                throw new Exception("Too many parameters!");
        }

        return result;
    }
}
