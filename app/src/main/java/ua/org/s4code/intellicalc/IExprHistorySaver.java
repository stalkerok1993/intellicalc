package ua.org.s4code.intellicalc;

import ua.org.s4code.intellicalc.analyser.ExprContainer;

/**
 * Created by Serhii on 9/7/2015.
 */
public interface IExprHistorySaver {
    public void toHistory(ExprContainer expression);
}
