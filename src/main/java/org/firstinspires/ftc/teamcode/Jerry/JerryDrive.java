package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    @Override
    public void loop()
    {
        updateDriver();
        updateUtility();

        robot.update();
        feedback();
    }

    public void updateDriver()
    {
        //Check drive train controls and update
        robot.drive.left.setPower(gamepad1.left_stick_y);
        robot.drive.right.setPower(gamepad1.right_stick_y);
    }

    public void updateUtility()
    {
        robot.primary.moveElbow(-gamepad2.left_stick_y);
        robot.primary.moveWrist(gamepad2.right_stick_y);

        if(gamepad2.a)
            robot.primary.grab();
        else if(gamepad2.b)
            robot.primary.release();

        //Check if recall is active and execute
        if(resetToggle)
        {
            resetToggle = false;
            if(!robot.table.isBusy())
                robot.table.rotateTo(0);
        }

        //Check turntable controls and update
        if(gamepad2.left_trigger > 0)
            robot.table.rotate(gamepad2.left_trigger / 2);
        else if(gamepad2.right_trigger > 0)
            robot.table.rotate(-gamepad2.right_trigger / 2);
        else
            robot.table.rotate(0);

        //Check toggle for recall
        if(!gamepad2.y && resetLast)
            resetToggle = true;
        resetLast = gamepad2.y;
    }

    public void feedback()
    {
        telemetry.addData("turn", robot.table.getPosition() + " : " + robot.table.targetPos);
        telemetry.addData("targeting", robot.table.isBusy());
        telemetry.addData("elbow", robot.primary.elbow.getCurrentPosition());
    }
}
