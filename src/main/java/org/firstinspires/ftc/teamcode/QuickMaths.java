package org.firstinspires.ftc.teamcode;

public class QuickMaths {

    public static double normalize(double angle)
    {
        double deg = angle;
        while(deg > 360)
            deg -= 360;
        while(deg < 0)
            deg += 360;
        return deg;
    }

    public static boolean isBetween(double val, double min, double max, boolean inclusive)
    {
        if(inclusive)
            return (val == min || val == max || isBetween(val, min, max, false));
        else
            return (val > min && val < max);
    }
}
