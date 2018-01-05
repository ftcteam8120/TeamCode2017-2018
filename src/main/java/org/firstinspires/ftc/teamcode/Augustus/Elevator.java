package org.firstinspires.ftc.teamcode.Augustus;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;

public class Elevator
{
    private boolean encoder;

    private DcMotor y;
    private CRServo xL;
    private CRServo xR;
    public Claw claw;

    // x moves horizontally
    // y moves vertically
    public Elevator(boolean e, DcMotor m, CRServo sxL, CRServo sxR, Claw c)
    {
        encoder = e;
        y = m;
        xL = sxL;
        xR = sxR;
        claw = c;
    }

    public void init()
    {
        y.setDirection(DcMotorSimple.Direction.REVERSE);
        if(encoder)
        {
            y.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            y.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else
            y.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        stopPosition();
    }

    /**
     * Move elevator out horizontally.
     */
    public void out()
    {
        xL.setPower(-1);
        xR.setPower(1);
    }

    /**
     * Move elevator in horizontally.
     */
    public void in()
    {
        xL.setPower(1);
        xR.setPower(-1);
    }

    /**
     * Stop horizontal movement
     */
    public void stopPosition() {
        xL.setPower(0);
        xR.setPower(0);
    }

    /**
     * Release claw.
     *
     * When not in override mode the position of the claw is incremented to release further. When in
     * override mode the claw goes to a fixed release position.
     *
     * @param override Is override mode enabled?
     */
    public void release(boolean override)
    {
        if(claw != null)
            claw.release(override);
    }

    /**
     * Close claw.
     *
     * When not in override mode the position of the claw is incremented to grab further. When in
     * override mode the claw goes to a fixed grab position.
     *
     * @param override Is override mode enabled?
     */
    public void grab(boolean override)
    {
        if(claw != null)
            claw.grab(override);
    }

    /**
     * Move elevator up.
     */
    public void up()
    {
        y.setPower(1);
    }

    /**
     * Move elevator down.
     */
    public void down()
    {
        y.setPower(-.5);
    }

    /**
     * Stop vertical movement.
     */
    public void stop()
    {
        y.setPower(0);
    }

    /**
     * Assign updated position if claw attached.
     */
    public void update()
    {
        if(claw != null)
            claw.update();
    }
}
