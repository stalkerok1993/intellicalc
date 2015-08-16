package ua.org.s4code.intellicalc.analyser.value;

import java.util.ArrayList;

import ua.org.s4code.intellicalc.analyser.ExprContainer;
import ua.org.s4code.intellicalc.analyser.Expression;

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

    public Expression result(ExprContainer context, int index) {
        return members.get(index);
    }

    public int getLength() {
        return members.size();
    }

    private ArrayList<Expression> members;

    public void addMember(Expression member) {
        members.add(member);
    }

    public void setMember(int index, Expression member) {
        members.set(index, member);
    }

}
