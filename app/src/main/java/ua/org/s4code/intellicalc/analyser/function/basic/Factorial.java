package ua.org.s4code.intellicalc.analyser.function.basic;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.function.Function;

/**
 * Created by Serhii on 8/11/2015.
 */
public class Factorial extends Function {
    @Override
    public Expression count(ExprContainer context, ArrayList<Expression> arguments) throws Exception {
        return null;
    }

    public double fct(long op) {
        if (op < 0) {
            throw new IllegalArgumentException("op MUST be a positive integer or zero.");
        }

        long result = 1;

        for (int i = 1; i < op; result *= i++);

        return result;
    }
}
