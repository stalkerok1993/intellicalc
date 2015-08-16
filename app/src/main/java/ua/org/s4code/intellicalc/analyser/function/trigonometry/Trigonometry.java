package ua.org.s4code.intellicalc.analyser.function.trigonometry;

import ua.org.s4code.intellicalc.analyser.function.Function;

/**
 * Created by Serhii on 8/6/2015.
 */
public abstract class Trigonometry extends Function {

    public enum AngleGrade {
        DEGREES, RADIANS
    }

    private AngleGrade grade = AngleGrade.RADIANS;

    public void setGrade(AngleGrade grade) {
        this.grade = grade;
    }

    private boolean inverse = false;

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

}
