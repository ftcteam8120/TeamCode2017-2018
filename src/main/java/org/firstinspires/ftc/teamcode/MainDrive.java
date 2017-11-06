package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.MultiTarget;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@TeleOp(name = "Main Drive")
public class MainDrive extends OpMode
{
    Robot robot = new Robot();

    @Override
    public void init()
    {
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
    }
}
