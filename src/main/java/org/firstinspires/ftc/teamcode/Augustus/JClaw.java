package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Augustus.Claw;

public class JClaw implements Claw
{
    private Servo servo;
    //position of servo
    private double p;
    private static final double CLAW_INCREMENT = 0.1;
    private static final double MIN = 0.1;
    private static final double MAX = 0.95;

    public JClaw(Servo s)
    {
        servo = s;
        grab(true);
    }

    /**
     * Close JClaw (to grab glyph).
     *
     * When not in override mode the position of the claw is incremented to grab further. When in
     * override mode the claw goes to a fixed grab position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void release(boolean override)
    {
        if(override)
            p = MIN;
        else
        {
            if(p > MIN)
                p -= CLAW_INCREMENT;
        }
    }

    /**
     * Open JClaw.
     *
     * When not in override mode the position of the claw is incremented to release further. When in
     * override mode the claw goes to a fixed release position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void grab(boolean override)
    {
        if(override)
            p = MAX;
        else
        {
            if(p < MAX)
                p += CLAW_INCREMENT;
        }
    }

    /**
     * Assign updated position of JClaw.
     */
    @Override
    public void update()
    {
        servo.setPosition(p);
    }
}
