package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DoubleClaw implements Claw
{
    //Leftmost Servo
    private Servo cL;
    //Rightmost Servo
    private Servo cR;
    //Position of claw, created before the clawPos enum
    private double clawPos;
    private static final double CLAW_INCREMENT = 0.5;

    /**
     * Constructor
     */
    public DoubleClaw(Servo scL, Servo scR)
    {
        cL = scL;
        cR = scR;
        clawPos = 0.0;
    }

    /**
     * empty function which is required but not needed
     */
    public void init() {
        // Nothing to do here
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

    /**
     * empty function only created so this could implement the Claw interface
     */
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

    //empty function only created so this could implement the Claw interface
    public void feedback(Telemetry telemetry) {}
}
