package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.Servo;

public class Knocker
{
    private Servo servo;
    public ColorRangeSensor colorRangeSensor;


    public Knocker(Servo s, ColorRangeSensor crs)
    {
        servo = s;
        colorRangeSensor = crs;
    }

    /**
     * Initialize the Knocker
     */
    public void init()
    {
        // Reset to the in position
        in();
    }

    /**
     * Set the knocker to the out position
     */
    public void out()
    {
        servo.setPosition(0.67);
    }

    /**
     * Pull the knocker in
     */
    public void in()
    {
        servo.setPosition(0);
    }

    /**
     * Return the current position of the knocker
     * @return the current knocker position
     */
    public double pos()
    {
        return servo.getPosition();
    }
}
