package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Knocker implements Module {
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
    public void init() {
        // Reset to the in position
        in();
    }

    /**
     * Set the knocker to the out position
     */
    public void out() {
        servo.setPosition(0.67);
    }

    /**
     * Pull the knocker in
     */
    public void in() {
        servo.setPosition(0.01);
    }

    public void stop() {
        this.in();
    }

    /**
     * Return the current position of the knocker
     * @return the current knocker position
     */
    public void update() {
        // Nothing to do here
    }
    public double pos() {
        return servo.getPosition();
    }

    public void feedback(Telemetry telemetry) {
        telemetry.addData("Knocker", this.pos());
        this.colorRangeSensor.feedback(telemetry);
    }
}
