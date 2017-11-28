package org.firstinspires.ftc.teamcode.Augustus;


public class HoloDir
{
    public double a;
    public double b;
    public double c;
    public double d;
    public HoloDir(int ax, int bx, int cx, int dx){
         a=ax;
        b=bx;
        c=cx;
        d=dx;
    }

    // Constant directional values.
    public static final HoloDir STATIONARY = new HoloDir(0, 0, 0, 0);
    public static final HoloDir FORWARD = new HoloDir(-1, 1, 1, -1);
    public static final HoloDir BACKWARD = new HoloDir(1, -1, -1, 1);
    public static final HoloDir LEFT = new HoloDir(1, 1, -1, -1);
    public static final HoloDir RIGHT = new HoloDir(-1, -1, 1, 1);
    public static final HoloDir FORWARD_LEFT = new HoloDir(0, 1, 0, -1);
    public static final HoloDir BACKWARD_LEFT = new HoloDir(1, 0, -1, 0);
    public static final HoloDir FORWARD_RIGHT = new HoloDir(-1, 0, 1, 0);
    public static final HoloDir BACKWARD_RIGHT = new HoloDir(0, -1, 0, 1);

}
