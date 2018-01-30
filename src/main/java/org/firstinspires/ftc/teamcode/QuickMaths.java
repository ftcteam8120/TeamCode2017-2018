package org.firstinspires.ftc.teamcode;

public class QuickMaths {
    /**
     * Sets the angle to an angle at or below 360 degrees
     *
     * @param angle the angle which needs to be lowered/ raised to be between 0 and 360 degrees
     *
     * @return
     */
    public static double normalize(double angle)
    {
        double deg = angle;
        while(deg > 360)
            deg -= 360;
        while(deg < 0)
            deg += 360;
        return deg;
    }

    /**
     * Checks to see if the given angle is between two values
     *
     * @param val the value which is being compared to the min and max
     *
     * @param min minimum value that the value is being compared to
     *
     * @param max maximum value that the value is being compared to
     *
     * @param inclusive whether or not the comparison includes min or max
     *
     * @return
     */
    public static boolean isBetween(double val, double min, double max, boolean inclusive)
    {
        if(inclusive)
            return (val == min || val == max || isBetween(val, min, max, false));
        else
            return (val > min && val < max);
    }
}
