package ua.org.s4code.intellicalc.analyser.function;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.IContextValue;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;

/**
 * Representing user-defined function.
 *
 * Created by Serhii on 8/11/2015.
 */
public class CustomFunction extends Function implements IContextValue {

    private String name;

    public CustomFunction(String expression, String name) {
        super(expression);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws ExprException {
        try {
            Function func = context.getFunction(this);
            return func.count(context, arguments);
        } catch (ExprException ex) {
            ExprException newEx = new ExprException(startPos, endPos, ex.getMessage());
            newEx.initCause(ex);
            throw newEx;
        }
    }
}
