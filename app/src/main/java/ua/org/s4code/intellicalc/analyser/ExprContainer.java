package ua.org.s4code.intellicalc.analyser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
    private HashMap<String, Function> functions;

    public ExprContainer(String expression, Expression expressionRoot) {
        variables = new HashMap<>();
        functions = new HashMap<>();

        this.expression = expression;
        setRoot(expressionRoot);
    }

    public void addVariable(String name, double value) {
        variables.put(name, value);

        for(Variable var : exprVariables) {
            var.invalidateCache();
        }
    }

    public double getVariable(Variable var) throws ExprException {
        String name = var.getName();
        Double value = variables.get(name);

        if (value == null) {
            throw new ExprException(var.startPos, var.endPos,
                    String.format("There is no getVariable with name '%s' in given context!",
                            name));
        }

        return value;
    }

    private Expression root;

    public void setRoot(Expression expressionRoot) {
        root = expressionRoot;

        extractVariableData();
    }

    private Expression getRoot() {
        return root;
    }

    public void addFunction(String name, Function function) throws Exception {
        functions.put(name, function);

        for (CustomFunction func : exprFunctions) {
            func.invalidateCache();
        }
    }

    public Function getFunction(CustomFunction func) throws ExprException {
        String name = func.getName();
        Function function = functions.get(name);

        if (function == null) {
            throw new ExprException(func.startPos, func.endPos,
                    String.format("There is no function with name '%s' in given context!", name));
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

    public void getUniqueContextValues(List<IContextValue> contextValues) {
        contextValues.clear();

        ArrayList<String> existingValues = new ArrayList<>();
        for (IContextValue var : exprVariables) {
            if (!existingValues.contains(var.getName())) {
                contextValues.add(var);
                existingValues.add(var.getName());
            }
        }
        for (IContextValue func : exprFunctions) {
            if (!existingValues.contains(func.getName())) {
                contextValues.add(func);
                existingValues.add(func.getName());
            }
        }
    }

    public List<IContextValue> getUniqueContextValues() {
        ArrayList<IContextValue> contextValues = new ArrayList<>();
        getUniqueContextValues(contextValues);

        return contextValues;
    }

    private void extractVariableData() {
        if (root != null) {
            exprVariables = new ArrayList<>();
            exprFunctions = new ArrayList<>();

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
            Variable var = (Variable) node;
            exprVariables.add(var);
        } else if (node instanceof CustomFunction) {
            CustomFunction func = (CustomFunction) node;
            exprFunctions.add(func);
        }
    }

}
