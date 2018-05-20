package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//This class controls Augustus's Knocker movement
public class Knocker implements Module {

    //Servo which moves the Knocker laterally
    private Servo servoX;
    //Servo which moves the Knocker vertically
    private Servo servoY;
    //Sensor used to detect which color Jewel is in front of the Knocker
    public ColorRangeSensor colorRangeSensor;

    /**
     * Constructor
     */
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
        //center();
    }

    /**
     * Pull the knocker in/out
     */
    public void in() {
        servoY.setPosition(0.0);
    }
    public void out() {
        servoY.setPosition(0.50);
    }

    /**
     * Knocker is pulled completely in
     */
    public void fullIn() { servoY.setPosition(0.30); }

    /**
     * Pull knocker in
     */
    public void stop() {
        this.in();
    }

    /**
     * Move the Knocker to the right laterally
     */
    public void right()
    {
        servoX.setPosition(.58);
    }

    /**
     * Move the Knocker to the left laterally
     */
    public void left()
    {
        servoX.setPosition(.98);
    }

    /**
     * Move the Knocker to the middle laterally
     */
    public void center()
    {
        servoX.setPosition(.77);
    }

    /**
     * A placeholder function so this class can implement the module interface
     */
    public void update() {
        // Nothing to do here
    }

    /**
     * Print various telemetry data to the screen
     *
     * @param telemetry the data which is printed to the screen
     */
    public void feedback(Telemetry telemetry) {
        telemetry.addData("kX", servoX.getPosition());
        telemetry.addData("kY", servoY.getPosition());
        this.colorRangeSensor.feedback(telemetry);
    }
}
