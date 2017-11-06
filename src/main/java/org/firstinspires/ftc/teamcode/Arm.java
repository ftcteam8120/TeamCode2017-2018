package org.firstinspires.ftc.teamcode;

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
        e.setDirection(DcMotorSimple.Direction.REVERSE);
        e.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        e.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        wrist = w;
        claw = c;
    }

    public void moveElbow(double power)
    {
        elbow.setPower(power);
    }

    public void grab()
    {
        claw.setPosition(180);
    }

    public void release()
    {
        claw.setPosition(0);
    }
}
