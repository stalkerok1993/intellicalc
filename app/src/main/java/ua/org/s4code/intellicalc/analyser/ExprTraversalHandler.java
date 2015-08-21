package ua.org.s4code.intellicalc.analyser;

import ua.org.s4code.intellicalc.analyser.Expression;

/**
 * Created by Serhii on 8/21/2015.
 */
public interface ExprTraversalHandler {
    public void traversalHandle(Expression node);
}
