package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;

public class TurnTable {

    private DcMotor motor;

    public int targetPos;
    private boolean isTargeting;

    public static final int ROTATION_AMOUNT = 1070;
    public static final int THRESH = 20;

    public TurnTable(DcMotor m)
    {
        motor = m;
        m.setDirection(DcMotorSimple.Direction.FORWARD);
        m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //TODO replace encoder cable
        m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void rotate(double p)
    {
        if(!isTargeting)
        {
            if(!outOfBounds())
                motor.setPower(p);
            else
            {
                if(p < 0 && getPosition() > 0)
                    motor.setPower(p);
                else if(p > 0 && getPosition() < 0)
                    motor.setPower(p);
                else
                    motor.setPower(0);
            }
        }
    }

    public int getPosition()
    {
        return motor.getCurrentPosition();
    }

    public void rotateTo(int pos)
    {
        isTargeting = true;
        targetPos = pos;
    }

    public boolean outOfBounds()
    {
        return (getPosition() < ROTATION_AMOUNT*-4 || getPosition() > ROTATION_AMOUNT*4);
    }

    public boolean isBusy()
    {
        return isTargeting;
    }

    public void update()
    {
        if(isTargeting)
        {
            double speed = Math.abs(targetPos - getPosition()) < 100 ? .1 : .2;

            if(getPosition() > targetPos)
                motor.setPower(-speed);
            else if(getPosition() < targetPos)
                motor.setPower(speed);
            else if(getPosition() > (targetPos - THRESH) && getPosition() < (targetPos + THRESH))
            {
                motor.setPower(0);
                isTargeting = false;
            }
        }
    }
}
