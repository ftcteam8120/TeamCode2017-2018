package org.firstinspires.ftc.teamcode.Augustus.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Augustus.ClawPos;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.JClaw;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

import static org.firstinspires.ftc.teamcode.QuickMaths.*;

import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.QuickMaths.normalize;

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
    double angle;
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
    public void updateDriver(Gamepad gamepad) {
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

        // Set the speed of the normal holo drive
        if (gamepad.right_bumper) {
            // Quarter speed
            holoSpeed = 0.25;
        } else {
            // Normal speed
            holoSpeed = 1.0;
        }

        v = -gamepad.left_stick_y;
        h = gamepad.left_stick_x;
        rotate = gamepad.right_stick_x;

        // Directional assist
        if(rotate != 0)
            augustus.drive.setAllDrive(rotate * holoSpeed);
        else if(gamepad.dpad_right)
            augustus.drive.setDrive(HoloDir.RIGHT, speed);
        else if(gamepad.dpad_left)
            augustus.drive.setDrive(HoloDir.LEFT, speed);
        else if(gamepad.dpad_up)
            augustus.drive.setDrive(HoloDir.FORWARD, speed);
        else if(gamepad.dpad_down)
            augustus.drive.setDrive(HoloDir.BACKWARD, speed);
        else {
            //
            if(v == 0 && h == 0)
                augustus.drive.setAllDrive(0);
            else if(v == 0)
            {
                if(h > 0)
                    augustus.drive.setDrive(HoloDir.RIGHT, holoSpeed);
                else
                    augustus.drive.setDrive(HoloDir.LEFT, holoSpeed);
            }
            else if(h == 0)
            {
                if(v > 0)
                    augustus.drive.setDrive(HoloDir.FORWARD, holoSpeed);
                else
                    augustus.drive.setDrive(HoloDir.BACKWARD, holoSpeed);
            }
            else
            {
                if(h > 0)
                {
                    if(v > 0)
                    {
                        //Quadrant 1
                        angle = (Math.atan(v / h) * 180 / Math.PI);
                    }
                    else
                    {
                        //Quadrant 4
                        angle = (Math.atan(v / h) * 180 / Math.PI) + 360;
                    }
                }
                else
                {
                    //Quadrants 2 & 3
                    angle = (Math.atan(v / h) * 180 / Math.PI) + 180;
                }
                augustus.drive.setDrive(normalize(90 - angle), holoSpeed);
            }
            //


            /* Old fluid movement code
            aPow = (-v - h) * holoSpeed;
            bPow = (v - h) * holoSpeed;;
            cPow = (v + h) * holoSpeed;;
            dPow = (-v + h) * holoSpeed;
            augustus.drive.setDrive(aPow, bPow, cPow, dPow);
            */
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
    public void updateUtility(Gamepad gamepad) {

        if (gamepad.left_trigger > 0.5) {
            augustus.elevator.claw.release(true);
        } else if (gamepad.right_trigger > 0.5) {
            augustus.elevator.claw.grab(true);
        } else {
            augustus.elevator.claw.stop();
        }

        // Knock
        if(gamepad.x)
            augustus.knocker.out();
        else if(gamepad.y)
            augustus.knocker.in();

        // Check if elevator motor should be on
        // Negative is down and positive is up
        if(gamepad.left_stick_y > 0.5)
            augustus.elevator.down();
        else if(gamepad.left_stick_y < -0.5)
            augustus.elevator.up();
        else
            augustus.elevator.stopVert();

        if (gamepad.dpad_up) {
            augustus.elevator.out(false);
        } else if (gamepad.dpad_down) {
            augustus.elevator.in(false);
        }

        // Activate rails
        // Negative is up and positive is down
        if(gamepad.right_stick_y > 0.5) {
            augustus.elevator.in(true);
        }
        else if (gamepad.right_stick_y < -0.5) {
            augustus.elevator.out(true);
        } else {
            augustus.elevator.stopHoriz();
        }
    }

    /**
     * Print various telemetry data to the screen
     */
    public void feedback() {
        //augustus.feedback(telemetry);
        telemetry.addData("stick angle", normalize(90 - angle));
    }
}
