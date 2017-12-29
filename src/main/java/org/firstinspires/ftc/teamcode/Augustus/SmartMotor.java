package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SmartMotor
{
    private DcMotor motor;
    private int lastValue;
    private boolean encoders;

    public SmartMotor(DcMotor m, boolean e)
    {
        motor = m;
        encoders = e;
        lastValue = 0;
    }

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
     * Set va
     */
    public void reset()
    {
        if(encoders)
            lastValue = motor.getCurrentPosition();
    }

}
