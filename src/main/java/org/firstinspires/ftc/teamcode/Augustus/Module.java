package org.firstinspires.ftc.teamcode.Augustus;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The module interface is the basis for all robot modules
 * each module extends the class and is required to have all of its
 * abstract functions.
 */
public interface Module {

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
