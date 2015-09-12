package ua.org.s4code.intellicalc.analyser.value;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.IContextValue;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;

/**
 * Created by Serhii on 8/3/2015.
 */
public class Variable extends ValueType implements IContextValue {

    String name;

    public Variable(String name) {
        super(name);

        if (name == null) {
            throw new NullPointerException("Variable name can not be null.");
        }

        this.name = name;
    }

    public Expression result(ExprContainer context) throws ExprException {
        return new Literal(this.getValue(context));
    }

    public String getName() {
        return name;
    }

    @Override
    public double getValue(ExprContainer context) throws ExprException {
        double res = 0.0;

        try {
            if (cachedValue == null) {
                res = context.getVariable(this);
                cachedValue = new Literal(res);
            } else {
                res = cachedValue.getValue();
            }
        } catch (ExprException ex) {
            ExprException newEx = new ExprException(startPos, endPos, ex.getMessage());
            newEx.initCause(ex);
            throw newEx;
        }

        return res;
    }

    @Override
    public String toString() {
        String result = "";

        if (cachedValue != null) {
            result = cachedValue.toString();
        } else {
            result = expressionString;
        }

        return result;
    }
}
