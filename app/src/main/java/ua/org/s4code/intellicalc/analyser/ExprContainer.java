package ua.org.s4code.intellicalc.analyser;

import java.util.HashMap;

import ua.org.s4code.intellicalc.analyser.function.Function;

/**
 * Created by Serhii on 8/4/2015.
 */
public class ExprContainer {

    private HashMap<String, Double> variables;

    public ExprContainer() {
        variables = new HashMap<>();
        functions = new HashMap<>();
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

    public void setRoot(Expression root) {
        this.root = root;
    }

    public Expression getRoot() {
        return root;
    }

    private HashMap<String, Function> functions;

    public void addFunction(String name, Function function) throws Exception {
        functions.put(name, function);
    }

    public Function getFunction(String name) throws Exception {
        Function function = functions.get(name);

        if (function == null) {
            throw new Exception(
                    String.format("There is no getVariable with name '%s' in given context!", name));
        }

        return function;
    }

    public Expression getResult() throws Exception {
        return root.result(this);
    }
}
