package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.Servo;

public class Knocker
{
    private Servo servo;

    public Knocker(Servo s)
    {
        servo = s;
    }

    public void init()
    {
        in();
    }

    public void out()
    {
        servo.setPosition(0.70);
    }

    public void in()
    {
        servo.setPosition(0.04);
    }

    public double pos()
    {
        return servo.getPosition();
    }
}
