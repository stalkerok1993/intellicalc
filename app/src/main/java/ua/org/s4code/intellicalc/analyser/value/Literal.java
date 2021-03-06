package ua.org.s4code.intellicalc.analyser.value;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;

/**
 * Created by Serhii on 8/5/2015.
 */
public class Literal extends ValueType {

    double value;

    public Literal(double value) {
        this(Double.toString(value), value);
    }

    public Literal(String expression, double value) {
        super(expression);

        this.value = value;
    }

    @Override
    public Expression result(ExprContainer context) {
        return this;
    }

    public void setValue(double value) {
        this.value = value;

        invalidateCache();
    }

    @Override
    public double getValue(ExprContainer context) {
        return getValue();
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        String result = Double.toString(value);
        return result;
    }

}
