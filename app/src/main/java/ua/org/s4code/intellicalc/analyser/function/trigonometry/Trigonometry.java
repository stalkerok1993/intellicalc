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

    private AngleGrade grade = AngleGrade.RADIANS;

    /**
     * Point which units used to represent angle.
     */
    public final void setGrade(AngleGrade grade) {
        this.grade = grade;

        invalidateCache();
    }

    public final AngleGrade getGrade() {
        return grade;
    }

    private boolean inverse = false;

    /**
     * If true then class represents inverse function.
     */
    public final void setInverse(boolean inverse) {
        this.inverse = inverse;

        invalidateCache();
    }

    public final boolean isInverse() {
        return inverse;
    }

    protected static double toRadians(double degrees) {
        return Math.PI * degrees / 180.0;
    }
}
