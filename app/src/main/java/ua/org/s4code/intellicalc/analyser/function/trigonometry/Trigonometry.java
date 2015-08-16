package ua.org.s4code.intellicalc.analyser.function.trigonometry;

import ua.org.s4code.intellicalc.analyser.function.Function;

/**
 * Created by Serhii on 8/6/2015.
 *
 * Representing all trigonometry functions.
 */
public abstract class Trigonometry extends Function {

    public enum AngleGrade {
        DEGREES, RADIANS
    }

    protected AngleGrade grade = AngleGrade.RADIANS;

    /**
     * Point which units used to represent angle.
     */
    public void setGrade(AngleGrade grade) {
        this.grade = grade;
    }

    protected boolean inverse = false;

    /**
     * If true then class represents inverse function.
     */
    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    protected static double toRadians(double degrees) {
        return Math.PI * degrees / 180.0;
    }
}
