package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    public DcMotor elbow;
    public Servo wrist;
    public Servo claw;

    public Arm(DcMotor e, Servo w, Servo c)
    {
        elbow = e;
        elbow.setDirection(DcMotorSimple.Direction.FORWARD);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wrist = w;
        claw = c;
    }

    public void moveElbow(double power)
    {
        if (power > 0)
        {
            elbow.setPower(elbow.getCurrentPosition() < Constants.ELBOW_MAX ? power : 0);
        }
        else
        {
            elbow.setPower(elbow.getCurrentPosition() > 0 ? power : 0);
        }
    }

    public void release()
    {
        claw.setPosition(180);
    }

    public void grab()
    {
        claw.setPosition(0);
    }

    public void moveWrist(double dir)
    {
        if(dir > 0 && wrist.getPosition() < 180)
            wrist.setPosition(wrist.getPosition() + 1);
        else if(dir < 0 && wrist.getPosition() > 0)
            wrist.setPosition(wrist.getPosition() - 1);
    }
}
