package org.firstinspires.ftc.teamcode.Augustus;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SingleClaw implements Claw {
    //Servo which controls the enitre Claw
    private Servo c;
    //What position the claw is in
    private double clawPos;
    //How far the Claw increments
    private static final double CLAW_INCREMENT = 0.1;

    /**
     * Constructor
     */
    public SingleClaw(Servo sc) {
        c = sc;
        clawPos = 0.2;
    }

    /**
     * What happens when the SingleClaw is initialized
     */
    public void init() {
        // Nothing to do here
    }

    /**
     * Close Single Claw (to grab glyph).
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
            clawPos = 0.2;
        else
        {
            if(clawPos > 0.2)
                clawPos -= CLAW_INCREMENT;
        }
    }

    /**
     * Open Single claw.
     *
     * When not in override mode the position of the claw is incremented to release further. When in
     * override mode the claw goes to a fixed release position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void release(boolean override)
    {
        if(override)
            clawPos = 0.5;
        else
        {
            if(clawPos < 0.5)
                clawPos += CLAW_INCREMENT;
        }
    }

    /**
     * Empty function required to make SingleClaw implement the Claw interface
     */
    @Override
    public void stop() {}

    /**
     * Assign updated position of Single Claw.
     */
    @Override
    public void update()
    {
        c.setPosition(clawPos);
    }

    /**
     * Empty function required to make SingleClaw implement the Claw interface
     */
    @Override
    public void feedback(Telemetry telemetry) {}
}
