package org.firstinspires.ftc.teamcode.Augustus.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
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
    double currentTime;

    boolean aLast;
    boolean aToggle;
    boolean bLast;
    boolean bToggle;

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
            speed = 0.4;
        else
            // Low speed
            speed = 0.2;

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
        else
        {
            aPow = -v - h;
            bPow = v - h;
            cPow = v + h;
            dPow = -v + h;
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
        // Check if claw is due to move and move it accordingly
        if(aToggle)
        {
            aToggle = false;
            augustus.elevator.grab(true);
        }
        else if(bToggle)
        {
            bToggle = false;
            augustus.elevator.release(true);
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

        // Check last button states for flag toggles
        if(!gamepad.a && aLast)
            aToggle = true;
        if(!gamepad.b && bLast)
            bToggle = true;

        // Get last state of buttons
        aLast = gamepad.a;
        bLast = gamepad.b;
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
    }
}
