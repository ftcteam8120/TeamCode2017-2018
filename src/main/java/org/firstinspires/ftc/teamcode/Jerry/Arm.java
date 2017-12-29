package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    public DcMotor elbow;
    public Servo wrist;
    public Servo claw;

    private double wristTarget;
    private int pivotPosE;
    private double pivotPosW;
    private double clawTarget;

    private boolean synced;

    public Arm(DcMotor e, Servo w, Servo c)
    {
        elbow = e;
        elbow.setDirection(DcMotorSimple.Direction.FORWARD);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wrist = w;
        claw = c;
        synced = false;
        clawTarget = 1.0;
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
        if(clawTarget < 1)
            clawTarget += 0.2;
    }

    public void grab()
    {
        if(clawTarget > 0)
            clawTarget -= 0.2;

    }

    public void sync()
    {
        pivotPosE = elbow.getCurrentPosition();
        pivotPosW = wrist.getPosition();
        synced = true;
    }

    public void wristUp()
    {
        if(wristTarget < 1)
            wristTarget += .01;
        synced = false;
    }

    public void wristDown()
    {
        if(wristTarget > 0)
            wristTarget -= .01;
        synced = false;
    }

    public void up()
    {
        wrist.setPosition(1);
    }

    public void down()
    {
        wrist.setPosition(0);
    }

    public void update()
    {
        if(synced)
            wristTarget = pivotPosW - ((elbow.getCurrentPosition() - pivotPosE) / 5000.0);
        wrist.setPosition(wristTarget);

        claw.setPosition(clawTarget);
    }
}
