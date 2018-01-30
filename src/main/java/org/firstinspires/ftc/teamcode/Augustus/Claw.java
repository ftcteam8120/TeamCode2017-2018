package org.firstinspires.ftc.teamcode.Augustus;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface Claw {
    /**
     * Abstract Grab method
     *
     * @param override Is override mode enabled?
     */
    void grab(boolean override);

    void clinch();

    /**
     * Abstract release method
     *
     * @param override Is override mode enabled?
     */
    void release(boolean override);

    /**
     * All modules must have a default init function to initialize them properly
     */
    void init();

    /**
     * All modules must have a stop function for emergencies
     */
    void stop();

    /**
     * All modules must have a update function to be called inside the OpMode loop
     */
    void update();

    /**
     * All modules must be able to provide feedback via the telemetry data
     * @param telemetry
     */
    void feedback(Telemetry telemetry);
}