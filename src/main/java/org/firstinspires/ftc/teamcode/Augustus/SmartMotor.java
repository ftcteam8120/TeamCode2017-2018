package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

//Wrapper class for DcMotor
public class SmartMotor
{
    //Motor instance variable
    private DcMotor motor;
    //The last value marked
    private int lastValue;
    //Boolean telling whether or not to use encoders
    private boolean encoders;

    /**
     * Constructor
     */
    public SmartMotor(DcMotor m, boolean e)
    {
        motor = m;
        encoders = e;
        lastValue = 0;
    }

    /**
     * What happens when a smart motor is initialized
     *
     * @param dir the direction which the motor is set to run
     */
    public void init(DcMotorSimple.Direction dir)
    {
        motor.setDirection(dir);

        if(encoders)
        {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Set power of motor.
     *
     * @param power What the power gets set to.
     */
    public void setPower(double power)
    {
        motor.setPower(power);
    }

    /**
     * Track how much the motor has rotated.
     *
     * @return Distance traveled by  encoder.
     */
    public int getDistanceTraveled()
    {
        return Math.abs(lastValue - motor.getCurrentPosition());
    }

    /**
     * Set value to the last known motor position
     */
    public void reset()
    {
        if(encoders)
            lastValue = motor.getCurrentPosition();
    }

    /**
     * Returns the motor
     *
     * @return the motor instance variable of this class
     */
    public DcMotor getInternal()
    {
        return motor;
    }

}
