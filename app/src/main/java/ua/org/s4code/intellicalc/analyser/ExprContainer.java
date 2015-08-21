package ua.org.s4code.intellicalc.analyser;

import java.util.ArrayList;
import java.util.HashMap;

import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.CustomFunction;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Variable;

/**
 * Container for the Expression object and variables and functions, connected with it.
 *
 * Created by Serhii on 8/4/2015.
 *
 */
public class ExprContainer implements ExprTraversalHandler {

    private String expression;

    private HashMap<String, Double> variables;

    public ExprContainer(String expression, Expression expressionRoot) {
        variables = new HashMap<>();
        functions = new HashMap<>();

        this.expression = expression;
        setRoot(expressionRoot);
    }

    public void addVariable(String name, double value) {
        variables.put(name, value);
    }

    public double getVariable(String name) throws Exception {
        Double value = variables.get(name);

        if (value == null) {
            throw new Exception(
                    String.format("There is no getVariable with name '%s' in given context!", name));
        }

        return value;
    }

    private Expression root;

    public void setRoot(Expression expressionRoot) {
        root = expressionRoot;

        extractVariableData();
    }

    public Expression getRoot() {
        return root;
    }

    private HashMap<String, Function> functions;

    public void addFunction(String name, Function function) throws Exception {
        functions.put(name, function);
    }

    public Function getFunction(String name) throws ExprException {
        Function function = functions.get(name);

        if (function == null) {
            throw new ExprException(0, 0,
                    String.format("There is no getVariable with name '%s' in given context!", name));
        }

        return function;
    }

    public Expression getResult() throws ExprException {
        return root.result(this);
    }

    @Override
    public String toString() {
        return expression;
    }

    private ArrayList<Variable> exprVariables;
    private ArrayList<CustomFunction> exprFunctions;

    private void extractVariableData() {
        if (root != null) {
            exprVariables = new ArrayList<Variable>();
            exprFunctions = new ArrayList<CustomFunction>();

            if (root instanceof Function) {
                ((Function) root).preOrderTraversal(this);
            }
            else {
                traversalHandle(root);
            }
        }
    }

    @Override
    public void traversalHandle(Expression node) {
        if (node instanceof Variable) {
            exprVariables.add((Variable) node);
        } else if (node instanceof CustomFunction) {
            exprFunctions.add((CustomFunction) node);
        }
    }

}
