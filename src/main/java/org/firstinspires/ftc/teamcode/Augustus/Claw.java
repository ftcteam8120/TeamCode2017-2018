package org.firstinspires.ftc.teamcode.Augustus;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface Claw
{
    /**
     * Abstract Grab method
     *
     * @param override Is override mode enabled?
     */
    void grab(boolean override);

    /**
     * Abstract release method
     *
     * @param override Is override mode enabled?
     */
    void release(boolean override);

    void stop();

    /**
     * Abstract update method
     */
    void update();

    void feedback(Telemetry telemetry);
}