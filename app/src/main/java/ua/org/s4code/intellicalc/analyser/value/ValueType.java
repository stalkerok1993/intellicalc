package ua.org.s4code.intellicalc.analyser.value;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;

/**
 * Representing double value.
 *
 * Created by Serhii on 8/8/2015.
 */
public abstract class ValueType extends Expression {
    public abstract double getValue(ExprContainer context) throws Exception;
}
