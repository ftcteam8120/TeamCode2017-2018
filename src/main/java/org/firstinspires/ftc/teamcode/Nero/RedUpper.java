package org.firstinspires.ftc.teamcode.Nero;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Module;

// LEFT SIDE
public class RedUpper implements Module {

    private DcMotor leftImpeller;
    private CRServo kicker;

    // construct the object
    public RedUpper(DcMotor imp, CRServo kick)
    {
        leftImpeller = imp;
        kicker = kick;
    }

    /**
     * Turn on left impeller, to bring glyphs in
     */
    public void impel(boolean boost)
    {
        leftImpeller.setPower(boost ? -1 : -.5);
    }

    /**
     * Turn on left impeller, to push Glyphs out
     */
    public void outpel(boolean boost)
    {
        leftImpeller.setPower(boost ? 1 : .5);
    }

    /**
     * Turns off left impeller
     */
    public void dontpel()
    {
        leftImpeller.setPower(0);
    }

    // initialize all actuators
    @Override
    public void init() {
        if(kicker != null)
            kicker.setDirection(CRServo.Direction.FORWARD);
    }

    // Kick out the impellers
    public void kick() {
        kicker.setPower(1);
    }

    // Stop kicking the impellers
    public void stopKick() {
        kicker.setPower(-0);
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
