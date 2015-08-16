package ua.org.s4code.intellicalc.analyser.function.trigonometry;

import ua.org.s4code.intellicalc.analyser.function.Function;

/**
 * Created by Serhii on 8/6/2015.
 */
public abstract class Trigonometry extends Function {

    private static final double PI = 3.14;

    public enum AngleGrade {
        DEGREES, RADIANS
    }

    protected AngleGrade grade = AngleGrade.RADIANS;

    public void setGrade(AngleGrade grade) {
        this.grade = grade;
    }

    protected boolean inverse = false;

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    protected static double toRadians(double degrees) {
        return PI * degrees / 180.0;
    }
}
