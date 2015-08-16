package ua.org.s4code.intellicalc.analyser.exception;

import ua.org.s4code.intellicalc.analyser.Expression;

/**
 * Created by Serhii on 8/16/2015.
 */
public class ExprException extends Exception {

    protected int exprStart;
    protected int exprEnd;

    public ExprException(int start, int end, String message) {
        super(message);

        exprStart = start;
        exprEnd = end;
    }

    public int selectionStart() {
        return exprStart;
    }

    public int selectionEnd() {
        return exprEnd;
    }

}
