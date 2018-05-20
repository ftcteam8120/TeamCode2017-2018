package org.firstinspires.ftc.teamcode.Nero;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Module;

// RIGHT SIDE
public class BlueUpper implements Module {

    private DcMotor rightImpeller;
    private DcMotor slider;
    private Servo flipper;

    public Grabber grabber;

    // construct the object
    public BlueUpper(DcMotor imp, DcMotor sli, Servo f, Grabber g)
    {
        rightImpeller = imp;
        slider = sli;
        flipper = f;
        grabber = g;
    }

    // initialize all actuators and sub-modules
    @Override
    public void init() {

        flip(false);

        rightImpeller.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(grabber != null)
            grabber.init();
    }

    // Impeller controls
    public void impel(boolean boost)
    {
        rightImpeller.setPower(boost ? 1 : .5);
    }
    public void outpel(boolean boost)
    {
        rightImpeller.setPower(boost ? -1 : -.5);
    }
    public void dontpel()
    {
        rightImpeller.setPower(0);
    }

    // flip the flipper (if up up, if not up down)
    public void flip(boolean up)
    {
        flipper.setPosition(up ? 0 : .8);
    }

    public void level() { flipper.setPosition(.65); }

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

        if(grabber != null)
            grabber.stop();
    }

    // update the sub-modules
    @Override
    public void update() {
        if(grabber != null)
            grabber.update();
    }

    // get telemetry readings
    @Override
    public void feedback(Telemetry telemetry) {
        if(grabber != null)
            grabber.feedback(telemetry);
    }
}
