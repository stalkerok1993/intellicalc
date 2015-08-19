package ua.org.s4code.intellicalc.analyser.value;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;
import ua.org.s4code.intellicalc.analyser.exception.ExprException;

/**
 * Created by Serhii on 8/8/2015.
 */
public class Vector extends Expression {

    public Vector() {
        members = new ArrayList<>();
    }

    @Override
    public Expression result(ExprContainer context) {
        return this;
    }

    public Expression result(ExprContainer context, int index) throws ExprException {
        return members.get(index).result(context);
    }

    public int getLength() {
        return members.size();
    }

    private ArrayList<Expression> members;

    public void addMember(Expression member) {
        members.add(member);
    }

    public Expression getMember(int index) {
        return members.get(index);
    }

    public void setMember(int index, Expression member) {
        members.set(index, member);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("(");

        int count = 0;
        for (Expression member : members) {
            result.append(member.toString());

            if (++count < members.size()) {
                result.append(", ");
            } else {
                result.append(")");
            }
        }

        return result.toString();
    }
}
