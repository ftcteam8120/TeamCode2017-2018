package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SmartMotor
{
    private DcMotor motor;
    private int traveled;
    private boolean encoders;

    public SmartMotor(DcMotor m, boolean e)
    {
        motor = m;
        encoders = e;
        traveled = 0;
    }

    public void init()
    {
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        if(encoders)
        {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double power)
    {
        motor.setPower(power);
    }

    public int getDistanceTraveled()
    {
        return Math.abs(motor.getCurrentPosition()) - traveled;
    }

    public void reset()
    {
        if(encoders)
            traveled += getDistanceTraveled();
    }

}
