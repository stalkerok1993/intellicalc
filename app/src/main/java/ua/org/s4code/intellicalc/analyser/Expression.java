package ua.org.s4code.intellicalc.analyser;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.exception.ExprException;
import ua.org.s4code.intellicalc.analyser.function.Function;
import ua.org.s4code.intellicalc.analyser.value.Literal;
import ua.org.s4code.intellicalc.analyser.value.Variable;
import ua.org.s4code.intellicalc.analyser.value.Vector;

/**
 * Base class for all elements in expression tree.
 *
 * Created by Serhii on 8/3/2015.
 */
public abstract class Expression {

    protected Expression cachedValue = null;

    /** Used for retrieving equation result with tree traversal. */
    public abstract Expression result(ExprContainer context) throws ExprException;

    /**
     * Parse math expression into equation tree.
     *
     * @param expression string representation of the math equation.
     * @return container with expression tree.
     */
    public static ExprContainer parse(String expression) throws ExprException {
        ExprContainer context = new ExprContainer(expression, Expression.localParse(expression, 0));

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

    private static Expression localParse(String expression, int position) throws ExprException {
        String tempExpr = expression.trim();
        int positionStart = position;
        position += expression.indexOf(tempExpr);
        int positionEnd = position;

        if (tempExpr.length() == 0) {
            if (expression.length() > 0) {
                positionEnd = position + expression.length() + 1;
            }
            if (positionStart > 0) {
                positionStart--;
            }
            throw new ExprException(positionStart, positionEnd, "There is an expression part.");
        }

        OperatorData opData = findOperator(tempExpr, position);

        Expression node = null;

        if (opData.inBrackets) {
            // brackets (vector or just expression in brackets)
            node = searchInBrackets(tempExpr, position);
        } else if (opData.operator != null) {
            if (node == null) { // TODO: set limits of expression in object with setStartPos & setEndPos
                // binary operator found
                String leftOperand = tempExpr.substring(0, opData.place);
                Expression leftOperandExpr = Expression.localParse(leftOperand, position);

                int rightOpPlace = opData.place + opData.operator.length();
                String rightOperand = tempExpr.substring(rightOpPlace);
                Expression rightOperandExpr = Expression.localParse(rightOperand, position + rightOpPlace);

                Function func = Function.create(opData.operator);
                func.addOperand(leftOperandExpr);
                func.addOperand(rightOperandExpr);

                node = func;
            }
        } else {
            if (node == null) {
                // search for unary operators
                node = searchUnary(tempExpr, position);
            }

            if (node == null && startsAsName(tempExpr)) {
                // variable or function
                if (isClosingBracket(tempExpr.charAt(tempExpr.length() - 1))) {
                    // function
                    int parametersStart = tempExpr.indexOf('(');
                    if (parametersStart >= 0) {
                        String functionName = tempExpr.substring(0, parametersStart);
                        String parameters = tempExpr.substring(parametersStart,
                                tempExpr.length());

                        Function func = Function.create(functionName);
                        Expression operands = Expression.localParse(parameters,
                                position + parametersStart);
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
                        throw new ExprException(position, position + tempExpr.length(),
                                "There are no opened bracket for parameters!");
                    }
                } else {
                    // variable
                    node = new Variable(tempExpr);
                }
            }

            if (node == null && isLiteral(tempExpr)) {
                // literal
                double num = 0.0;
                try {
                    num = Double.parseDouble(tempExpr);
                }
                catch (NumberFormatException exception) {
                    throw new ExprException(position, position + tempExpr.length(),
                            "This seams to be a number, but it isn't.");
                }

                node = new Literal(num);
            }
        }

        if (node != null) {
            node.setStartPos(position);
            node.setEndPos(position + tempExpr.length());
        }
        else {
            throw new ExprException(position, position + tempExpr.length(),
                    "There is some unexpected logic.");
        }

        return node;
    }

    /*
     * for the next functions string parameters MUST be trimmed
     */

    private static OperatorData findOperator(String expression, int position) throws ExprException {
        OperatorData opData = new OperatorData();

        // used to mark position in text where exception is
        int lastBracketPos = -1;
        int firstBracketPos = -1;

        ArrayList<Integer> bracketStack = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);

            // do not search in brackets
            for (int t = 0; t < bracketPairs.length; t++) {
                for (int m = 0; m < bracketPairs[t].length; m++) {
                    if (bracketPairs[t][m] == current) {
                        if (m == 0) { // opened bracket
                            if (bracketStack.size() == 0) {
                                firstBracketPos = i;
                            }
                            bracketStack.add(t);
                            lastBracketPos = i;
                        } else { // closed bracket
                            int last = bracketStack.size() - 1;
                            if (last < 0) {
                                throw new ExprException(position + i, position + i + 1,
                                        "There is the closed bracket without a pair.");
                            }

                            if (bracketStack.get(last) == t) { // closed bracket of a matched type
                                if (lastBracketPos + 1 == i) {
                                    throw new ExprException(position + lastBracketPos,
                                            position + i + 1, "There is the empty brackets.");
                                }

                                bracketStack.remove(last);
                            } else {
                                throw new ExprException(position + lastBracketPos, position + i + 1,
                                        "There is the bracket that do not match previous bracket.");
                            }
                        }
                    }
                }
            }

            if (bracketStack.size() == 0) {
                if (expression.length() <= 2) {
                    // when there is no place for brackets
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
            int bracketPos = position + firstBracketPos;
            // there is no possibility (in general) to find the bracket
            throw new ExprException(bracketPos, bracketPos + 1,
                    "There is the bracket without a pair.");
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

    private static boolean startsAsName(String expression) {
        char firstChar = expression.charAt(0);
        boolean result = firstChar >= 'A' && firstChar <= 'z' || firstChar == '_';
        return result;
    }

    private static Function searchUnary(String expression, int position) throws ExprException {
        Function node = null;

        for (int i = 0; i < prefixOperators.length; i++) { // bigger priority for prefix
            if (expression.indexOf(prefixOperators[i]) == 0) {
                String unaryOp = prefixOperators[i];

                node = Function.create(unaryOp);
                // suffix operator has bigger priority!
                node.addOperand(Expression.localParse(expression.substring(unaryOp.length()),
                        position + unaryOp.length()));
                break;
            }
        }
        if (node == null) {
            for (int i = 0; i < suffixOperators.length; i++) {
                if (expression.lastIndexOf(suffixOperators[i]) == (expression.length() - 1)) {
                    String unaryOp = suffixOperators[i];

                    node = Function.create(unaryOp);
                    int operatorEnd = expression.length() - unaryOp.length();
                    node.addOperand(Expression.localParse(
                            expression.substring(0, expression.length() - unaryOp.length()),
                            position + operatorEnd));
                    break;
                }
            }
        }

        return node;
    }

    private static Expression searchInBrackets(String expression, int position) throws ExprException {
        Expression node = null;
        int localPosition = position + 1;

        if (expression.indexOf(",") != -1) { // there are separators
            String[] vectorParts = removeBrackets(expression).split(",");

            Vector vector = new Vector();
            for (int i = 0; i < vectorParts.length; i++) {
                vector.addMember(Expression.localParse(vectorParts[i], localPosition));
                localPosition += vectorParts[i].length() + 1;
            }

            node = vector;
        }
        else {
            node = Expression.localParse(removeBrackets(expression), localPosition);
        }

        return node;
    }

    private static String removeBrackets(String expression) {
        return  expression.substring(1, expression.length() - 1);
    }


    protected int startPos = 0;
    protected int endPos = 0;

    public void setStartPos(int start) {
        startPos = start;
    }

    public void setEndPos(int end) {
        endPos = end;
    }
}
