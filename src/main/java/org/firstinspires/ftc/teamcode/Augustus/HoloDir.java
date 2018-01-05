package org.firstinspires.ftc.teamcode.Augustus;


public class HoloDir
{
    public double a;
    public double b;
    public double c;
    public double d;
    public HoloDir(double ax, double bx, double cx, double dx){
        a=ax;
        b=bx;
        c=cx;
        d=dx;
    }

    // Constant directional values.
    public static final HoloDir STATIONARY = new HoloDir(0, 0, 0, 0);
    public static final HoloDir BACKWARD = new HoloDir(0.75, -0.75, -0.75, 0.75);
    public static final HoloDir FORWARD = new HoloDir(-0.75, 0.75, 0.75, -0.75);
    public static final HoloDir LEFT = new HoloDir(0.75, 0.75, -0.75, -0.75);
    public static final HoloDir RIGHT = new HoloDir(-0.75, -0.75, 0.75, 0.75);
    public static final HoloDir FORWARD_LEFT = new HoloDir(0, 0.75, 0, -0.75);
    public static final HoloDir BACKWARD_LEFT = new HoloDir(0.75, 0, -0.75, 0);
    public static final HoloDir FORWARD_RIGHT = new HoloDir(-0.75, 0, 0.75, 0);
    public static final HoloDir BACKWARD_RIGHT = new HoloDir(0, -0.75, 0, 0.75);
    // Rotation directional value
    //public static final HoloDir ROTATE_RIGHT = new HoloDir(0.75, 0.75, 0.75, 0.75);
    //public static final HoloDir ROTATE_LEFT = new HoloDir(-0.75, -0.75, -0.75, -0.75);

}
