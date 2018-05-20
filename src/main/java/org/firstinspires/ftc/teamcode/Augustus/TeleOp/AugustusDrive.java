package org.firstinspires.ftc.teamcode.Augustus.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.RobotAugustus;


import static org.firstinspires.ftc.teamcode.QuickMaths.normalize;

//The class controls the TeleOp of our RobotNero
@TeleOp(name = "AugustusDrive", group = "Augustus")
public class AugustusDrive extends OpMode
{
    //The robotAugustus being used in this class
    RobotAugustus augustus;

    //What occurs when AugustusDrive is initted
    @Override
    public void init() {
        augustus = new RobotAugustus();
        augustus.init(hardwareMap, ClawType.J);
    }

    //vertical displacement of analog stick for directional control
    double v;
    //horizontal displacement of analog stick for directional control
    double h;
    //The angle computed by v and h
    double angle;
    //The power which the robotAugustus turns at
    double rotate;
    //The power that the motors are set to when the robotAugustus drives
    double speed;
    //The adjustable speed which allows for a "slowed down" mode of driving
    double holoSpeed;

    //What is called while AugustusDrive is running
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
     * Stop robotAugustus when b is pressed.
     *
     * Set speed for robotAugustus based on right trigger.
     *
     * Get variables for robotAugustus direction.
     *
     * Move robotAugustus based on gamepad input.
     *
     * @param gamepad gamepad used for controlling drivetrain components.
     */
    public void updateDriver(Gamepad gamepad) {

        //control of knocker
        /*
        if(gamepad.a)
            augustus.knocker.out();
        else if(gamepad.b)
            augustus.knocker.fullIn();
        if(gamepad.x)
            augustus.knocker.left();
        else if(gamepad.y)
            augustus.knocker.right();
        */

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

        // Control of grab/release of Elevator Claw
        if (gamepad.right_trigger > 0.5) {
            augustus.elevator.claw.grab(true);
        } else if (gamepad.left_trigger > 0.5) {
            augustus.elevator.claw.release(true);
        } else {
            augustus.elevator.claw.stop();
        }

        /*
        //control of the ability to flip relic grabber over field perimeter
        if(gamepad.y)
            augustus.grabber.flipUp();
        else if(gamepad.x)
            augustus.grabber.flipDown();

        //control of the extension/retraction of the relic grabber arm
        if(gamepad.right_bumper)
            augustus.grabber.extend(.7);
        else if(gamepad.left_bumper)
            augustus.grabber.retract(.7);
        else
            augustus.grabber.extend(0);

        //control of the clamping mechanism on the Relic Grabber
        if(gamepad.a)
            augustus.grabber.grab();
        else if(gamepad.b)
            augustus.grabber.release();
        */

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

        augustus.feedback(telemetry);

        telemetry.addData("stick angle", normalize(90 - angle));

    }
}
