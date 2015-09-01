package ua.org.s4code.intellicalc.analyser;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.Variable;
import ua.org.s4code.intellicalc.analyser.value.Vector;

/**
 * Base class for all elements in expression tree.
 *
 * Created by Serhii on 8/3/2015.
 */
public abstract class Expression {

    /** Used for retrieving equation result with tree traversal. */
    public abstract Expression result(ExprContainer context) throws ExprException;

    protected Literal cachedValue = null;

    protected Expression parent = null;

    protected int startPos = 0;
    protected int endPos = 0;

    protected void setPosition(int start, int end) {
        startPos = start;
        endPos = end;
    }

    protected String expressionString = "n/a";

    public Expression(String expression) {
        expressionString = expression;
    }

    /**
     * Parse math expression into equation tree.
     *
     * @param expression string representation of the math equation.
     * @return container with expression tree.
     */
    public static ExprContainer parse(String expression) throws ExprException {
        ExpressionParser parser = new ExpressionParser();
        return parser.parse(expression);
    }

    @Override
    public String toString() {
        String result = "";

        if (cachedValue != null) {
            result = cachedValue.toString();
        }
        else {
            result = expressionString;
        }

        return result;
    }

    public final void invalidateCache() {
        cachedValue = null;

        if (parent != null) {
            parent.invalidateCache();
        }
    }
}
