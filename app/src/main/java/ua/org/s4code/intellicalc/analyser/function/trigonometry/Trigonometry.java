package ua.org.s4code.intellicalc.analyser.function.trigonometry;

import ua.org.s4code.intellicalc.analyser.function.Function;

/**
 * Created by Serhii on 8/6/2015.
 *
 * Representing all trigonometry functions.
 */
public abstract class Trigonometry extends Function {

    protected Trigonometry(String expression, boolean inverse, AngleGrade grade) {
        super(expression);

        this.inverse = inverse;
        this.grade = grade;
    }

    protected Trigonometry(String expression, boolean inverse) {
        this(expression, inverse, DEFAULT_GRADE);
    }

    protected Trigonometry(String expression) {
        this(expression, false, DEFAULT_GRADE);
    }

    public enum AngleGrade {
        DEGREES, RADIANS
    }

    public static final AngleGrade DEFAULT_GRADE = AngleGrade.RADIANS;

    private AngleGrade grade = DEFAULT_GRADE;

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
    /*public final void setInverse(boolean inverse) {
        this.inverse = inverse;

        invalidateCache();
    }*/

    public final boolean isInverse() {
        return inverse;
    }

    protected static final double toRadians(double degrees) {
        return Math.PI * degrees / 180.0;
    }
}
