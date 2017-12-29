package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DriveTrain {

    public DcMotor left;
    public DcMotor right;

    private boolean encoders;

    public static final int TICKS_PER_FOOT = 9999;
    public static final int TICKS_PER_QUARTER_TURN = 8888;
    //TODO fill, perfect with gyro

    public DriveTrain(DcMotor r, DcMotor l, boolean e)
    {
        left = l;
        right = r;
        encoders = e;
        left.setDirection(DcMotorSimple.Direction.FORWARD);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        stop();
    }

    public void stop()
    {
        left.setPower(0);
        right.setPower(0);
        if(encoders)
            resetEncoders();
        else
        {
            left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void resetEncoders()
    {
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void turn(double speed, boolean clockwise)
    {
        if(clockwise)
        {
            left.setPower(speed);
            right.setPower(-speed);
        }
        else
        {
            left.setPower(-speed);
            right.setPower(speed);
        }
    }

    public void straight(boolean forward, double speed)
    {
        left.setPower(forward ? speed : -speed);
        right.setPower(forward ? speed : -speed);
    }

    public static int feetToTicks(double feet)
    {
        return (int)(feet * TICKS_PER_FOOT);
    }
}
