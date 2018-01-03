package org.firstinspires.ftc.teamcode.Augustus;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Wrapper class for the LynxI2cColorRangeSensor
 */
public class ColorRangeSensor {

    public ColorRangeSensor(LynxI2cColorRangeSensor sensor) {
        this.sensor = sensor;
    }

    /**
     * The sensor
     */
    private LynxI2cColorRangeSensor sensor;

    // Base values for calibration
    private int baseRed = 0;
    private int baseBlue = 0;
    private int baseGreen = 0;

    /**
     * Reset all the base values
     */
    public void reset() {
        baseRed = 0;
        baseBlue = 0;
        baseGreen = 0;
    }

    /**
     * Calibrate the sensor to ambient light
     */
    public void calibrate() {
        baseRed = sensor.red();
        baseBlue = sensor.blue();
        baseGreen = sensor.green();
    }

    /**
     * Alias for the red() function on wrapped sensor
     * @return the calibrated red value
     */
    public synchronized int red() {
        return sensor.red() - baseRed;
    }

    /**
     * Alias for the green() function on wrapped sensor
     * @return the calibrated green value
     */
    public synchronized int green() {
        return sensor.green() - baseGreen;
    }

    /**
     * Alias for the blue() function on wrapped sensor
     * @return the calibrated blue value
     */
    public synchronized int blue() {
        return sensor.blue() - baseBlue;
    }

    /**
     * Alias for the getDistance(DistanceUnit unit) function on wrapped sensor
     * @return the distance value
     */
    public double getDistance(DistanceUnit unit) {
        return sensor.getDistance(unit);
    }

    /**
     * Determines if the red jewel is detected
     * @return if it is red
     */
    public boolean isRed() {
        return this.red() > this.blue();
    }

    /**
     * Determines if the blue jewel is detected
     * @return if it is blue
     */
    public boolean isBlue() {
        return !this.isRed();
    }

    /**
     * Provides telemetry data for the sensor
     */
    public void feedback(Telemetry telemetry) {
        // Print out the color sensor RGB values
        telemetry.addData("Right R", this.red());
        telemetry.addData("Right G", this.green());
        telemetry.addData("Right B", this.blue());
        // Print out the distance from the right sensor in CM
        telemetry.addData("Right Distance (cm)", this.getDistance(DistanceUnit.CM));
    }

}
