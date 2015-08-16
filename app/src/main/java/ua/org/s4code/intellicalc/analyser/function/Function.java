package ua.org.s4code.intellicalc.analyser.function;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
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
import ua.org.s4code.intellicalc.analyser.value.ValueType;
import ua.org.s4code.intellicalc.analyser.value.Vector;

/**
 * Created by Serhii on 8/6/2015.
 */
public abstract class Function extends Expression {

    protected ArrayList<Expression> successors = new ArrayList<>();

    public void addOperand(Expression child) {
        successors.add(child);
    }

    /** Factory method */
    public static Function create(String name) {
        Function func = null;

        switch (name) {
            case "+":
                func = new Add();
                break;
            case "/":
                func = new Div();
                break;
            case "!":
                func = new Factorial();
                break;
            case "%":
                func = new Mod();
                break;
            case "*":
                func = new Mul();
                break;
            case "-":
                func = new Sub();
                break;
            case "log":
                func = new Log();
                break;
            case "^":
                // fall through
            case "pow":
                func = new Pow();
                break;
            case "sqr":
                func = new Sqr();
                break;
            case "sqrt":
                func = new Sqrt();
                break;
            case "sin":
                func = new Sinus();
                break;
            case "asin":
                Sinus asin = new Sinus();
                asin.setInverse(true);
                func = asin;
                break;
            case "cos":
                func = new Cosinus();
                break;
            case "acos":
                Cosinus acos = new Cosinus();
                acos.setInverse(true);
                func = acos;
                break;
            case "tg":
                // fall through
            case "tan":
                func = new Tangent();
                break;
            case "atg":
                // fall through
            case "atan":
                Tangent atg = new Tangent();
                atg.setInverse(true);
                func = atg;
                break;
            case "ctg":
                func = new Cotangent();
                break;
            case "actg":
                Cotangent actg = new Cotangent();
                actg.setInverse(true);
                func = actg;
                break;
            default: // user defined function
                func = new CustomFunction(name);
        }

        return func;
    }

    @Override
    public Expression result(ExprContainer context) throws Exception {
        return this.count(context, successors);
    }

    public abstract Expression count(ExprContainer context, ArrayList<Expression> arguments)
            throws  Exception;

    public static boolean isValues(ExprContainer context, ArrayList<Expression> arguments) throws Exception {
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
}
