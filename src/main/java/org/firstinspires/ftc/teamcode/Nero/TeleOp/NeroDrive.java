package org.firstinspires.ftc.teamcode.Nero.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
import org.firstinspires.ftc.teamcode.Nero.Robot;

import static org.firstinspires.ftc.teamcode.QuickMaths.normalize;

@TeleOp(name = "NeroDrive", group="Nero")
public class NeroDrive extends OpMode {

    // construct and initialize the robot
    Robot robot;
    @Override
    public void init() {
        robot = new Robot();
        robot.init(hardwareMap);
    }

    // loop through the OpMode
    @Override
    public void loop() {
        // get controller one input and update
        updatePilot(gamepad1);
        // get controller two input and update
        updateCopilot(gamepad2);
        // run internal module updates on robot
        robot.update();
        // get telemetry readings
        feedback();
    }

    // local variables to keep track of loop operations
    double speed;
    double holoSpeed;
    double v;
    double h;
    double rotate;
    double angle;

    // update the robot based on driver controls
    public void updatePilot(Gamepad gamepad)
    {
        // Adjust "safe" perpendicular speed
        if(gamepad.right_trigger > 0)
            // High speed
            speed = 0.6;
        else
            // Low speed
            speed = 0.2;

        // Set the speed of the normal holo drive
        if (gamepad.right_bumper)
            // Quarter speed
            holoSpeed = 0.25;
        else
            // Normal speed
            holoSpeed = 1.0;

        // Get stick values
        v = -gamepad.left_stick_y;
        h = gamepad.left_stick_x;
        rotate = gamepad.right_stick_x;

        // Directional assist
        if(rotate != 0)
            robot.drive.setAllDrive(rotate * holoSpeed);
        else if(gamepad.dpad_right)
            robot.drive.setDrive(HoloDir.RIGHT, speed);
        else if(gamepad.dpad_left)
            robot.drive.setDrive(HoloDir.LEFT, speed);
        else if(gamepad.dpad_up)
            robot.drive.setDrive(HoloDir.FORWARD, speed);
        else if(gamepad.dpad_down)
            robot.drive.setDrive(HoloDir.BACKWARD, speed);
        else {
            //
            if(v == 0 && h == 0)
                robot.drive.setAllDrive(0);
            else if(v == 0)
            {
                if(h > 0)
                    robot.drive.setDrive(HoloDir.RIGHT, holoSpeed);
                else
                    robot.drive.setDrive(HoloDir.LEFT, holoSpeed);
            }
            else if(h == 0)
            {
                if(v > 0)
                    robot.drive.setDrive(HoloDir.FORWARD, holoSpeed);
                else
                    robot.drive.setDrive(HoloDir.BACKWARD, holoSpeed);
            }
            else
            {
                if(h > 0)
                {
                    if(v > 0)
                    {
                        // Quadrant 1
                        angle = (Math.atan(v / h) * 180 / Math.PI);
                    }
                    else
                    {
                        // Quadrant 4
                        angle = (Math.atan(v / h) * 180 / Math.PI) + 360;
                    }
                }
                else
                {
                    // Quadrants 2 & 3
                    angle = (Math.atan(v / h) * 180 / Math.PI) + 180;
                }
                robot.drive.setDrive(normalize(90 - angle), holoSpeed);
            }
        }
    }

    boolean aLast;
    boolean bLast;
    //
    boolean flipToggle;
    boolean grabToggle;

    // update the robot based on utility controls
    public void updateCopilot(Gamepad gamepad)
    {
        // update relic flipper according to state
        if(flipToggle)
            robot.rightUpper.grabber.flipUp();
        else
            robot.rightUpper.grabber.flipDown();

        // update relic grabber according to state
        if(grabToggle)
            robot.rightUpper.grabber.grab();
        else
            robot.rightUpper.grabber.release();

        // extend or retract relic arm based on bumpers
        if(gamepad.right_bumper)
            robot.rightUpper.grabber.extend(.7);
        else if(gamepad.left_bumper)
            robot.rightUpper.grabber.retract(.7);
        else
            robot.rightUpper.grabber.extend(0);

        // flip the flipper up or down based on y or x buttons being pressed
        if(gamepad.y)
            robot.rightUpper.flip(true);
        else if(gamepad.x)
            robot.rightUpper.flip(false);

        // set the slide motor power based on trigger values; left = down, right = up, none = hold
        if(gamepad.right_trigger > 0)
            robot.rightUpper.slide(.5);
        else if(gamepad.left_trigger > 0)
            robot.rightUpper.slide(-.5);
        else
            robot.rightUpper.slide(0);

        // do end-of-loop button toggle checks
        if(aLast && !gamepad.a)
            flipToggle = !flipToggle;
        if(bLast && !gamepad.b)
            grabToggle = !grabToggle;
        aLast = gamepad.a;
        bLast = gamepad.b;
    }

    // get telemetry readings
    public void feedback()
    {
        robot.feedback(telemetry);
    }
}
