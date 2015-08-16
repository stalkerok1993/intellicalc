package ua.org.s4code.intellicalc.analyser.value;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;

/**
 * Created by Serhii on 8/3/2015.
 */
public class Variable extends ValueType {

    String name;

    public Variable(String name) {
        if (name == null) {
            throw new NullPointerException("Variable name can not be null.");
        }

        this.name = name;
    }

    public Expression result(ExprContainer context) {
        return this;
    }

    @Override
    public double getValue(ExprContainer context) throws ExprException {
        double res = 0.0;

        try {
            res = context.getVariable(name);
        }
        catch (Exception exception) {
            throw new ExprException(startPos, endPos, "There are no such variable in context.");
        }

        return res;
    }
}
