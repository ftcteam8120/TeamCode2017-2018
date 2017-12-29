package org.firstinspires.ftc.teamcode.Augustus;

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

    /**
     * Abstract update method
     */
    void update();
}
