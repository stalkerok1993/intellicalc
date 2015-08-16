package ua.org.s4code.intellicalc.analyser;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.Variable;
import ua.org.s4code.intellicalc.analyser.value.Vector;

/**
 * Created by Serhii on 8/3/2015.
 */
public abstract class Expression {

    protected Expression cachedValue = null;

    public abstract Expression result(ExprContainer context) throws Exception;

    /**
     * Parse math expression into equation tree.
     *
     * @param expression string representation of the math equation.
     * @return root element of the expression tree.
     */
    public static ExprContainer parse(String expression) throws  Exception {
        ExprContainer context = new ExprContainer();

        context.setRoot(Expression.localParse(expression));

        return context;
    }

    // binary operations, placed by priority (less index - bigger priority)
    private static String[][] operators = new String[][] {
            { "^" },
            { "*", "/", "%" },
            { "+", "-" }
    };

    private static String[] prefixOperators = new String[] { "+", "-" };

    private static String[] suffixOperators = new String[] { "!" };

    private static char[][] bracketPairs = new char[][] { // only 2 items in each element!
            { '[', ']' },
            { '{', '}' },
            { '(', ')' }
    };

    private static class OperatorData {
        public int priority;
        public String operator;
        public boolean inBrackets;
        public int place;

        public OperatorData () {
            priority = -1;
            operator = null;
            inBrackets = true;
            place = -1;
        }
    }

    private static Expression localParse(String expression) throws Exception {
        String tempExpr = expression.trim();

        if (tempExpr.length() == 0) {
            throw new Exception("Empty expression found! " +
                    "Maybe there are an empty bracket pain \"()\"?");
        }

        OperatorData opData = findOperator(tempExpr);

        Expression node = null;

        if (opData.inBrackets) {
            // brackets (vector or just expression in brackets)
            node = searchInBrackets(tempExpr);
        } else if (opData.operator != null) {
            if (node == null) {
                // binary operator found
                String leftOperand = tempExpr.substring(0, opData.place);
                String rightOperand = tempExpr.substring(opData.place + opData.operator.length());

                Function func = Function.create(opData.operator);
                func.addOperand(Expression.localParse(leftOperand));
                func.addOperand(Expression.localParse(rightOperand));

                node = func;
            }
        } else {
            if (node == null) {
                // search for unary operators
                node = searchUnary(tempExpr);
            }

            if (node == null && isName(tempExpr)) {
                // variable or function
                if (isClosingBracket(tempExpr.charAt(tempExpr.length() - 1))) {
                    // function
                    int parametersStart = tempExpr.indexOf('(');
                    if (parametersStart >= 0) {
                        String functionName = tempExpr.substring(0, parametersStart);
                        String parameters = tempExpr.substring(parametersStart,
                                tempExpr.length());

                        Function func = Function.create(functionName);
                        Expression operands = Expression.localParse(parameters);
                        if (operands instanceof Vector) {
                            for (int i = 0; i < ((Vector) operands).getLength(); i++) {
                                Expression operand = ((Vector) operands).getMember(i);
                                func.addOperand(operand);
                            }
                        } else {
                            func.addOperand(operands);
                        }

                        node = func;
                    } else {
                        throw new Exception("There are no opened bracket for parameters!");
                    }
                } else {
                    // variable
                    node = new Variable(tempExpr);
                }
            }

            if (node == null && isLiteral(tempExpr)) {
                // literal
                node = new Literal(Double.parseDouble(tempExpr));
            }
        }

        return node;
    }

    /*
     * for the next functions string parameters MUST be trimmed
     */

    private static OperatorData findOperator(String expression) throws Exception {
        OperatorData opData = new OperatorData();

        ArrayList<Integer> bracketStack = new ArrayList<>();
        int lastBracketPos = -1;
        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);

            // do not search in brackets
            for (int t = 0; t < bracketPairs.length; t++) {
                for (int m = 0; m < bracketPairs[t].length; m++) {
                    if (bracketPairs[t][m] == current) {
                        if (m == 0) { // opened bracket
                            bracketStack.add(t);
                            lastBracketPos = i;
                        } else { // closed bracket
                            int last = bracketStack.size() - 1;
                            if (last < 0) {
                                throw new Exception("There are the closed bracket " +
                                        "without a pair");
                            }

                            if (bracketStack.get(last) == t) { // closed bracket of the matched type
                                if (lastBracketPos + 1 == i) {
                                    throw new Exception("There are empty brackets.");
                                }

                                bracketStack.remove(last);
                            } else {
                                throw new Exception("There are bracket that do not " +
                                        "match previous bracket.");
                            }
                        }
                    }
                }
            }

            if (bracketStack.size() == 0) {
                if (expression.length() <= 2) {
                    // there is no place for brackets
                    opData.inBrackets = false;
                }

                if (i > 0 && i < expression.length() - 1) {
                    opData.inBrackets = false; // not inside the brackets

                    // search for binary operators
                    for (int p = 0; p < operators.length; p++) {
                        for (int n = 0; n < operators[p].length; n++) {
                            int opLength = operators[p][n].length();
                            if (i + opLength <= expression.length() &&
                                    operators[p][n].equals(expression.substring(i, i + opLength)) &&
                                    opData.priority < p) {
                                opData.priority = p;
                                opData.operator = operators[p][n];
                                opData.place = i;
                            }
                        }
                    }
                }
            }
            // else start next iteration to find bracket pair
        }

        if (bracketStack.size() != 0) {
            throw new Exception("There are bracket without a pair.");
        }

        return opData;
    }

    private static boolean isClosingBracket(char ch) {
        for (int i = 0; i < bracketPairs.length; i++) {
            for (int j = 1; j < bracketPairs[i].length; j++) {
                if (ch == bracketPairs[i][j]) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isLiteral(String expression) {
        char firstChar = expression.charAt(0);
        boolean result = firstChar >= '0' && firstChar <= '9' || firstChar == '.';
        return result;
    }

    private static boolean isName(String expression) {
        char firstChar = expression.charAt(0);
        boolean result = firstChar >= 'A' && firstChar <= 'z' || firstChar == '_';
        return result;
    }

    private static Function searchUnary(String expression) throws Exception {
        Function node = null;

        for (int i = 0; i < prefixOperators.length; i++) { // bigger priority for prefix
            if (expression.indexOf(prefixOperators[i]) == 0) {
                String unaryOp = prefixOperators[i];

                node = Function.create(unaryOp);
                node.addOperand(Expression.localParse(expression.substring(unaryOp.length())));
                break;
            }
        }
        if (node == null) {
            for (int i = 0; i < suffixOperators.length; i++) {
                if (expression.lastIndexOf(suffixOperators[i]) == (expression.length() - 1)) {
                    String unaryOp = suffixOperators[i];

                    node = Function.create(unaryOp);
                    node.addOperand(Expression.localParse(
                            expression.substring(0, expression.length() - unaryOp.length())));
                    break;
                }
            }
        }

        return node;
    }

    private static Expression searchInBrackets(String expression) throws Exception {
        Expression node = null;

        if (expression.indexOf(",") != -1) { // there are separators
            String[] vectorParts = removeBrackets(expression).split(",");

            Vector vector = new Vector();
            for (int i = 0; i < vectorParts.length; i++) {
                vector.addMember(Expression.localParse(vectorParts[i]));
            }

            node = vector;
        }
        else {
            node = Expression.localParse(removeBrackets(expression));
        }

        return node;
    }

    private static String removeBrackets(String expression) {
        return  expression.substring(1, expression.length() - 1);
    }
}
