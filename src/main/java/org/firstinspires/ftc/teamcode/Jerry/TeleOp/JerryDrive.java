package org.firstinspires.ftc.teamcode.Jerry.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Jerry.RobotJerry;

@TeleOp(name = "JerryDrive", group = "Jerry")
public class JerryDrive extends OpMode
{
    RobotJerry robotJerry;

    @Override
    public void init()
    {
        robotJerry = new RobotJerry();
        robotJerry.init(hardwareMap);
    }

    boolean resetLast;
    boolean resetToggle;
    boolean syncLast;
    boolean syncToggle;

    @Override
    public void loop()
    {
        updateDriver(gamepad1);
        updateUtility(gamepad2);

        robotJerry.update();
        feedback();
    }

    public void updateDriver(Gamepad gamepad)
    {
        //Check drive train controls and update
        robotJerry.drive.left.setPower(gamepad.left_stick_y);
        robotJerry.drive.right.setPower(gamepad.right_stick_y);
    }

    public void updateUtility(Gamepad gamepad)
    {
        robotJerry.primary.moveElbow(-gamepad.left_stick_y);

        if(gamepad.a)
            robotJerry.primary.grab();
        if(gamepad.b)
            robotJerry.primary.release();
        
        if(gamepad.right_stick_y > 0.5)
            robotJerry.primary.wristUp();
        else if(gamepad.right_stick_y < -0.5)
            robotJerry.primary.wristDown();

        if(gamepad.left_bumper)
            robotJerry.primary.down();
        else if(gamepad.right_bumper)
            robotJerry.primary.up();

        //Check if recall is active and execute
        if(resetToggle)
        {
            resetToggle = false;
            if(!robotJerry.table.isBusy())
                robotJerry.table.rotateTo(0);
        }

        if(syncToggle)
        {
            syncToggle = false;
            robotJerry.primary.sync();
        }

        //Check turntable controls and update
        if(gamepad.left_trigger > 0)
            robotJerry.table.rotate(gamepad.left_trigger / 2);
        else if(gamepad.right_trigger > 0)
            robotJerry.table.rotate(-gamepad.right_trigger / 2);
        else
            robotJerry.table.rotate(0);

        //Check toggle for recall
        if(!gamepad.y && resetLast)
            resetToggle = true;
        resetLast = gamepad.y;

        //Check toggle for sync
        if(!gamepad.x && syncLast)
            syncToggle = true;
        syncLast = gamepad.x;
    }

    public void feedback()
    {
        telemetry.addData("turn", robotJerry.table.getPosition() + " : " + robotJerry.table.targetPos);
        telemetry.addData("targeting", robotJerry.table.isBusy());
        telemetry.addData("elbow", robotJerry.primary.elbow.getCurrentPosition());
        telemetry.addData("wrist", robotJerry.primary.wrist.getPosition());
    }
}
