package ua.org.s4code.intellicalc.analyser.value;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;

/**
 * Representing double value.
 *
 * Created by Serhii on 8/8/2015.
 */
public abstract class ValueType extends Expression {

    public ValueType(String expression) {
        super(expression);
    }

    public abstract double getValue(ExprContainer context) throws ExprException;

}
