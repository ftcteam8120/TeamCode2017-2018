package org.firstinspires.ftc.teamcode.Augustus;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DoubleClaw implements Claw
{
    private Servo cL;
    private Servo cR;

    private double clawPos;
    private static final double CLAW_INCREMENT = 0.5;

    public DoubleClaw(Servo scL, Servo scR)
    {
        cL = scL;
        cR = scR;
        clawPos = 0.0;
    }

    /**
     * Close Double Claw.
     *
     * When not in override mode the position of the claw is incremented to grab further. When in
     * override mode the claw goes to a fixed grab position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void grab(boolean override)
    {
        if(override)
            clawPos = 0.0;
        else
        {
            if(clawPos > 0.0)
                clawPos -= CLAW_INCREMENT;
        }
    }

    /**
     * Open Double Claw.
     *
     * When not in override mode the position of the claw is incremented to open further. When in
     * override mode the claw goes to a fixed release position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void release(boolean override)
    {
        if(override)
            clawPos = 1.0;
        else
        {
            if(clawPos < 1.0)
                clawPos += CLAW_INCREMENT;
        }
    }

    @Override
    public void stop() {}

    /**
     * Assign updated position for Double Claw.
     */
    @Override
    public void update()
    {
        cL.setPosition(1.0 - clawPos);
        cR.setPosition(clawPos);
    }

    public void feedback(Telemetry telemetry) {}
}
