package org.firstinspires.ftc.teamcode.Jerry.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Jerry.Robot;

@TeleOp(name = "JerryDrive", group = "Jerry")
public class JerryDrive extends OpMode
{
    Robot robot;

    @Override
    public void init()
    {
        robot = new Robot();
        robot.init(hardwareMap);
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

        robot.update();
        feedback();
    }

    public void updateDriver(Gamepad gamepad)
    {
        //Check drive train controls and update
        robot.drive.left.setPower(gamepad.left_stick_y);
        robot.drive.right.setPower(gamepad.right_stick_y);
    }

    public void updateUtility(Gamepad gamepad)
    {
        robot.primary.moveElbow(-gamepad.left_stick_y);

        if(gamepad.a)
            robot.primary.grab();
        if(gamepad.b)
            robot.primary.release();
        
        if(gamepad.right_stick_y > 0.5)
            robot.primary.wristUp();
        else if(gamepad.right_stick_y < -0.5)
            robot.primary.wristDown();

        if(gamepad.left_bumper)
            robot.primary.down();
        else if(gamepad.right_bumper)
            robot.primary.up();

        //Check if recall is active and execute
        if(resetToggle)
        {
            resetToggle = false;
            if(!robot.table.isBusy())
                robot.table.rotateTo(0);
        }

        if(syncToggle)
        {
            syncToggle = false;
            robot.primary.sync();
        }

        //Check turntable controls and update
        if(gamepad.left_trigger > 0)
            robot.table.rotate(gamepad.left_trigger / 2);
        else if(gamepad.right_trigger > 0)
            robot.table.rotate(-gamepad.right_trigger / 2);
        else
            robot.table.rotate(0);

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
        telemetry.addData("turn", robot.table.getPosition() + " : " + robot.table.targetPos);
        telemetry.addData("targeting", robot.table.isBusy());
        telemetry.addData("elbow", robot.primary.elbow.getCurrentPosition());
        telemetry.addData("wrist", robot.primary.wrist.getPosition());
    }
}
