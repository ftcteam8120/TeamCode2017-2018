package org.firstinspires.ftc.teamcode.Nero;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Module;

public class LeftUpper implements Module {

    private DcMotor leftImpeller;
    private Servo leftKicker;

    // construct the object
    public LeftUpper(DcMotor imp, Servo kick)
    {
        leftImpeller = imp;
        leftKicker = kick;
    }

    // initialize all actuators
    @Override
    public void init() {
        leftKicker.setPosition(0);
    }

    // shut down the module
    @Override
    public void stop() {
        leftImpeller.setPower(0);
    }

    // run internal module updates
    @Override
    public void update() {
        // not yet implemented
    }

    // get telemetry readings
    @Override
    public void feedback(Telemetry telemetry) {

    }
}
