package ua.org.s4code.intellicalc.analyser.value;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;

/**
 * Created by Serhii on 8/8/2015.
 */
public class Vector extends Expression {

    public Vector() {}

    @Override
    public Expression result(ExprContainer context) {
        return this;
    }

    public Expression result(ExprContainer context, int index) {
        return successors.get(index);
    }

    public int length() {
        return successors.size();
    }
}
