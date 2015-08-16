package ua.org.s4code.intellicalc.analyser.function;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;

/**
 * Created by Serhii on 8/11/2015.
 */
public class CustomFunction extends Function {

    private String name;

    public CustomFunction(String name) {
        this.name = name;
    }

    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws Exception {
        Function func = context.getFunction(name);
        Expression result = func.count(context, arguments);

        return result;
    }
}
