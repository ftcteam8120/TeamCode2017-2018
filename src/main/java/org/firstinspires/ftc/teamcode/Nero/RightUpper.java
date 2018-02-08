package org.firstinspires.ftc.teamcode.Nero;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Module;

public class RightUpper implements Module {

    private DcMotor rightImpeller;
    private Servo rightKicker;
    private DcMotor slider;
    private Servo flipper;

    public Grabber grabber;

    // construct the object
    public RightUpper(DcMotor imp, Servo kick, DcMotor sli, Servo f, Grabber g)
    {
        rightImpeller = imp;
        rightKicker = kick;
        slider = sli;
        flipper = f;
        grabber = g;
    }

    // initialize all actuators and sub-modules
    @Override
    public void init() {
        rightKicker.setPosition(0);
        flipper.setPosition(0);

        rightImpeller.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        grabber.init();
    }

    // flip the flipper (if up up, if not up down)
    public void flip(boolean up)
    {
        flipper.setPosition(up ? 1 : 0);
    }

    // set the slide motor power
    public void slide(double power)
    {
        slider.setPower(power);
    }

    // shut down the module
    @Override
    public void stop() {
        rightImpeller.setPower(0);
        slider.setPower(0);

        grabber.stop();
    }

    // update the sub-modules
    @Override
    public void update() {
        grabber.update();
    }

    // get telemetry readings
    @Override
    public void feedback(Telemetry telemetry) {
        grabber.feedback(telemetry);
    }
}
