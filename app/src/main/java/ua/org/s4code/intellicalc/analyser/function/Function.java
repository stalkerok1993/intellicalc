package ua.org.s4code.intellicalc.analyser.function;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.ExprTraversalHandler;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.basic.Add;
import ua.org.s4code.intellicalc.analyser.function.basic.Div;
import ua.org.s4code.intellicalc.analyser.function.basic.Factorial;
import ua.org.s4code.intellicalc.analyser.function.basic.Mod;
import ua.org.s4code.intellicalc.analyser.function.basic.Mul;
import ua.org.s4code.intellicalc.analyser.function.basic.Sub;
import ua.org.s4code.intellicalc.analyser.function.power.Log;
import ua.org.s4code.intellicalc.analyser.function.power.Pow;
import ua.org.s4code.intellicalc.analyser.function.power.Sqr;
import ua.org.s4code.intellicalc.analyser.function.power.Sqrt;
import ua.org.s4code.intellicalc.analyser.function.trigonometry.Cosinus;
import ua.org.s4code.intellicalc.analyser.function.trigonometry.Cotangent;
import ua.org.s4code.intellicalc.analyser.function.trigonometry.Sinus;
import ua.org.s4code.intellicalc.analyser.function.trigonometry.Tangent;
import ua.org.s4code.intellicalc.analyser.function.trigonometry.Trigonometry;
import ua.org.s4code.intellicalc.analyser.value.ValueType;
import ua.org.s4code.intellicalc.analyser.value.Vector;

/**
 * Factory method for all child classes.
 * Base class for all nodes of expression tree, that can contain successors.
 *
 * Created by Serhii on 8/6/2015.
 */
public abstract class Function extends Expression {

    public abstract Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws  ExprException;

    protected ArrayList<Expression> successors = new ArrayList<>();

    public void addOperand(Expression child) {
        successors.add(child);

        invalidateCache();
    }

    public Function(String expression) {
        super(expression);
    }

    /** Factory method */
    public static Function create(String expression, String name, Trigonometry.AngleGrade grade) {
        Function func = null;

        switch (name) {
            case "+":
                func = new Add(expression);
                break;
            case "/":
                func = new Div(expression);
                break;
            case "!":
                func = new Factorial(expression);
                break;
            case "%":
                func = new Mod(expression);
                break;
            case "*":
                func = new Mul(expression);
                break;
            case "-":
                func = new Sub(expression);
                break;
            case "log":
                func = new Log(expression);
                break;
            case "^":
                // fall through
            case "pow":
                func = new Pow(expression);
                break;
            case "sqr":
                func = new Sqr(expression);
                break;
            case "sqrt":
                func = new Sqrt(expression);
                break;
            case "sin":
                func = new Sinus(expression);
                break;
            case "asin":
                Sinus asin = new Sinus(expression, true, grade);
                func = asin;
                break;
            case "cos":
                func = new Cosinus(expression);
                break;
            case "acos":
                Cosinus acos = new Cosinus(expression, true, grade);
                func = acos;
                break;
            case "tg":
                // fall through
            case "tan":
                func = new Tangent(expression);
                break;
            case "atg":
                // fall through
            case "atan":
                Tangent atg = new Tangent(expression, true, grade);
                func = atg;
                break;
            case "ctg":
                func = new Cotangent(expression);
                break;
            case "actg":
                Cotangent actg = new Cotangent(expression, true, grade);
                func = actg;
                break;
            default: // user defined function
                func = new CustomFunction(name, expression);
        }

        return func;
    }

    @Override
    public Expression result(ExprContainer context) throws ExprException {
        return this.count(context, successors);
    }

    public static boolean isValues(ExprContainer context, ArrayList<Expression> arguments)
            throws ExprException {
        boolean isValues = true;

        for (Expression argument : arguments) {
            if ( !(argument.result(context) instanceof ValueType) ) {
                isValues = false;
                break;
            }
        }

        return isValues;
    }

    public static boolean isVectors(ExprContainer context, ArrayList<Expression> arguments) throws Exception {
        boolean isVectors = true;

        for (Expression argument : arguments) {
            if ( !(argument.result(context) instanceof Vector) ) {
                isVectors = false;
                break;
            }
        }

        return isVectors;
    }

    @Override
    public String toString() {
        String result = expressionString;
        return result;
    }

    public final void preOrderTraversal(ExprTraversalHandler handler) {
        handler.traversalHandle(this);

        for (Expression successor : successors) {
            if (successor instanceof Function) {
                ((Function) successor).preOrderTraversal(handler);
            } else {
                handler.traversalHandle(successor);
            }
        }
    }

}
