package org.firstinspires.ftc.teamcode.Augustus.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Augustus.ClawPos;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.JClaw;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "AugustusDrive", group = "Augustus")
public class AugustusDrive extends OpMode
{
    Robot augustus;
    private ElapsedTime runtime;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        runtime = new ElapsedTime();
        runtime.reset();
    }

    double v;
    double h;
    //
    double aPow;
    double bPow;
    double cPow;
    double dPow;
    //
    double rotate;
    //
    double speed;
    //
    double holoSpeed;
    //
    double currentTime;

    @Override
    public void loop() {
        // Check for driver controls and update accordingly
        updateDriver(gamepad1);
        // Check for other controls and update accordingly
        updateUtility(gamepad2);

        // Run internal checks and adjustments
        augustus.update();

        // Update telemetry
        feedback();
    }

    /**
     * Control output of drivetrain.
     *
     * Stop robot when b is pressed.
     *
     * Set speed for robot based on right trigger.
     *
     * Get variables for robot direction.
     *
     * Move robot based on gamepad input.
     *
     * @param gamepad gamepad used for controlling drivetrain components.
     */
    public void updateDriver(Gamepad gamepad)
    {
        // Reset encoders
        if(gamepad.b)
            augustus.stop();

        // Adjust "safe" perpendicular speed
        if(gamepad.right_trigger > 0)
            // High speed
            speed = 0.6;
        else {
            // Low speed
            speed = 0.2;
        }

        // Set the speed of the normal holomonic drive
        if (gamepad.right_bumper) {
            // Half speed
            holoSpeed = 0.5;
        } else {
            // Normal speed
            holoSpeed = 1.0;
        }

        v = -gamepad.left_stick_y;
        h = gamepad.left_stick_x;
        rotate = gamepad.right_stick_x;

        // Directional assist
        if(rotate != 0)
            augustus.drive.setAllDrive(rotate);
        else if(gamepad.dpad_right)
            augustus.drive.setDrive(HoloDir.RIGHT, speed);
        else if(gamepad.dpad_left)
            augustus.drive.setDrive(HoloDir.LEFT, speed);
        else if(gamepad.dpad_up)
            augustus.drive.setDrive(HoloDir.FORWARD, speed);
        else if(gamepad.dpad_down)
            augustus.drive.setDrive(HoloDir.BACKWARD, speed);
        // Custom direction math
        else {
            aPow = (-v - h) * holoSpeed;
            bPow = (v - h) * holoSpeed;;
            cPow = (v + h) * holoSpeed;;
            dPow = (-v + h) * holoSpeed;
            augustus.drive.setDrive(aPow, bPow, cPow, dPow);
        }
    }


    /**
     * Control output of all non-drivetrain components.
     *
     * When a is pressed grabber closes. When b is pressed grabber opens.
     *
     * Left stick moves elevator up and down.
     *
     * Right stick moves elevator in and out while stick is held plus half a second.
     *
     * @param gamepad gamepad used for controlling non drivetrain components.
     */
    public void updateUtility(Gamepad gamepad)
    {
        // Override the claw position manually
        if (gamepad.left_bumper) {
            augustus.elevator.claw.release(false);
        } else if (gamepad.right_bumper) {
            augustus.elevator.claw.grab(false);
        }

        // Move the claw to predefined positions
        if (gamepad.left_trigger > 0.5) {
            augustus.elevator.claw.release(true);
        } else if (gamepad.right_trigger > 0.5) {
            augustus.elevator.claw.grab(true);
        } else {
            augustus.elevator.claw.stop();
        }

        // Loading button
        if (gamepad.a) {
            // When a is clicked, move the claw to the load position to load the claw arms
            if (augustus.elevator.claw instanceof JClaw) {
                ((JClaw)augustus.elevator.claw).setPosition(ClawPos.LOAD);
            }
        }

        // Knock
        if(gamepad.x)
            augustus.knocker.out();
        else if(gamepad.y)
            augustus.knocker.in();

        // Check if elevator motor should be on
        if(gamepad.left_stick_y > 0.5)
            augustus.elevator.up();
        else if(gamepad.left_stick_y < -0.5)
            augustus.elevator.down();
        else
            augustus.elevator.stop();

        // Activate rails
        if(gamepad.right_stick_y > 0.5) {
            currentTime = runtime.milliseconds();
            augustus.elevator.out();
        }
        else if (gamepad.right_stick_y < -0.5) {
            currentTime = runtime.milliseconds();
            augustus.elevator.in();
        }

        // Check time since rail activation, stop if necessary
        if(runtime.milliseconds() > currentTime + 500)
            augustus.elevator.stopPosition();
    }

    /**
     * Print various telemetry data to the screen
     */
    public void feedback()
    {
        telemetry.addData("knocker", augustus.knocker.pos());
        telemetry.addData("encoders", augustus.drive.encoders);
        if(augustus.right != null)
            telemetry.addData("color", "b:" + augustus.right.blue() + "; r:" + augustus.right.red());
        augustus.elevator.claw.feedback(telemetry);
    }
}
