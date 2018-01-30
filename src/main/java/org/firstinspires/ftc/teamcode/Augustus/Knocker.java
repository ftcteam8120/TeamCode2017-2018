package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Knocker implements Module {
    private Servo servoX;
    private Servo servoY;
    public ColorRangeSensor colorRangeSensor;


    public Knocker(Servo sX, Servo sY, ColorRangeSensor crs)
    {
        servoX = sX;
        servoY = sY;
        colorRangeSensor = crs;
    }

    /**
     * Initialize the Knocker
     */
    public void init() {
        in();
        center();
    }

    /**
     * Set the knocker to the out position
     */
    public void out() {
        servoY.setPosition(0.9);
    }

    /**
     * Pull the knocker in
     */
    public void in() {
        servoY.setPosition(0.35);
    }

    public void stop() {
        this.in();
    }

    public void right()
    {
        servoX.setPosition(0.4);
    }

    public void left()
    {
        servoX.setPosition(.8);
    }

    public void center()
    {
        servoX.setPosition(0.6);
    }

    public void update() {
        // Nothing to do here
    }

    public void feedback(Telemetry telemetry) {
        telemetry.addData("kX", servoX.getPosition());
        telemetry.addData("kY", servoY.getPosition());
        this.colorRangeSensor.feedback(telemetry);
    }
}
