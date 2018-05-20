package org.firstinspires.ftc.teamcode.Nero.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HoloDir;
import org.firstinspires.ftc.teamcode.Nero.RobotNero;

import static org.firstinspires.ftc.teamcode.HoloDir.*;
import static org.firstinspires.ftc.teamcode.QuickMaths.normalize;

//Class which controlls the teleOp of the robotNero
@TeleOp(name = "NeroDrive", group="Nero")
public class NeroDrive extends OpMode {
    /**
     * construct and initialize the robotNero
     */
    RobotNero robotNero;
    ElapsedTime runtime;

    @Override
    public void init() {
        robotNero = new RobotNero();
        robotNero.init(hardwareMap);
        runtime = new ElapsedTime();
    }

    @Override
    public void start()
    {
        runtime.reset();
    }

    /**
     * loop through the OpMode
     */
    @Override
    public void loop() {
        // get controller one input and update
        updatePilot(gamepad1);
        // get controller two input and update
        updateCopilot(gamepad2);
        // run internal module updates on robotNero
        robotNero.knocker.in();
        if(runtime.seconds() >= 3)
            robotNero.knocker.left();
        robotNero.update();
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

    boolean slow;
    boolean boost;
    boolean aDriverLast;

    boolean auto;
    double autoStartTime;

    /**
     * update the robotNero based on driver controls
     */

    public void updatePilot(Gamepad gamepad)
    {



        ////// THIS WHOLE SECTION CONTROLS THE DRIVE TRAIN
        // Set the speed of the normal holo drive depending on the toggle
        if(slow)
            // Quarter speed
            holoSpeed = 0.4;
        else
            // Normal speed
            holoSpeed = 1.0;

        speed = 0.2;

        if(gamepad1.b)
            robotNero.drive.stop();

        // Get stick values
        v = -gamepad.left_stick_y;
        h = gamepad.left_stick_x;
        rotate = gamepad.right_stick_x;

        if(rotate != 0)
            robotNero.drive.setAllDrive(rotate * holoSpeed);
        else if(gamepad.dpad_down)
            robotNero.drive.setDrive(BACKWARD, speed);
        else if(gamepad.dpad_up)
            robotNero.drive.setDrive(FORWARD, speed);
        else if(gamepad.dpad_left)
            robotNero.drive.setDrive(LEFT, speed);
        else if(gamepad.dpad_right)
            robotNero.drive.setDrive(RIGHT, speed);
        else {
            //
            if(v == 0 && h == 0)
                robotNero.drive.setAllDrive(0);
            else if(v == 0)
            {
                if(h > 0)
                    robotNero.drive.setDrive(RIGHT, holoSpeed);
                else
                    robotNero.drive.setDrive(HoloDir.LEFT, holoSpeed);
            }
            else if(h == 0)
            {
                if(v > 0)
                    robotNero.drive.setDrive(HoloDir.FORWARD, holoSpeed);
                else
                    robotNero.drive.setDrive(HoloDir.BACKWARD, holoSpeed);
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
                robotNero.drive.setDrive(normalize(90 - angle), holoSpeed);
            }
        }

        //////

        // Override automatic impelling
        if(gamepad.left_bumper || gamepad.right_bumper || (gamepad.left_trigger > 0) || (gamepad.right_trigger > 0))
        {

            if(gamepad.right_bumper)
                robotNero.blueUpper.outpel(boost);
            else if(gamepad.right_trigger > 0)
                robotNero.blueUpper.impel(boost);
            else
                robotNero.blueUpper.dontpel();


            if(gamepad.left_bumper)
                robotNero.redUpper.outpel(boost);
            else if(gamepad.left_trigger > 0)
                robotNero.redUpper.impel(boost);
            else
                robotNero.redUpper.dontpel();
        }

        if(gamepad.y)
        {
            robotNero.blueUpper.outpel(boost);
            robotNero.redUpper.outpel(boost);
        }
        else if(gamepad.x)
        {
            robotNero.blueUpper.impel(boost);
            robotNero.redUpper.impel(boost);
        }

        if(gamepad.b)
        {
            robotNero.blueUpper.dontpel();
            robotNero.redUpper.dontpel();
        }

        // Kick the impellers out when held down
        if (gamepad.start) {
            robotNero.redUpper.kick();
        } else {
            robotNero.redUpper.stopKick();
        }

        // Toggle slow mode if a button state is mismatched
        if(!gamepad.a && aDriverLast)
            slow = !slow;
        aDriverLast = gamepad.a;

        if(gamepad.dpad_down)
        {
            auto = true;
            autoStartTime = runtime.milliseconds();
        }
    }

    boolean aLast;
    boolean bLast;
    //
    boolean flipToggle;
    boolean grabToggle;

    // update the robotNero based on utility controls
    public void updateCopilot(Gamepad gamepad)
    {
        if(robotNero.blueUpper.grabber != null)
        {
            // update relic flipper according to state
            if(flipToggle)
                robotNero.blueUpper.grabber.flipUp();
            else
                robotNero.blueUpper.grabber.flipDown();

            // update relic grabber according to state
            if(grabToggle)
                robotNero.blueUpper.grabber.grab();
            else
                robotNero.blueUpper.grabber.release();

            // extend or retract relic arm based on bumpers
            if(gamepad.right_bumper)
                robotNero.blueUpper.grabber.extend(.7);
            else if(gamepad.left_bumper)
                robotNero.blueUpper.grabber.retract(.7);
            else
                robotNero.blueUpper.grabber.extend(0);
        }

        // flip the flipper up or down based on y or x buttons being pressed
        if(gamepad.y)
            robotNero.blueUpper.flip(false);
        else if (gamepad.dpad_up)
            robotNero.blueUpper.level();
        else if(gamepad.x)
            robotNero.blueUpper.flip(true);

        // set the slide motor power based on trigger values; left = down, right = up, none = hold
        if(gamepad.right_trigger > 0)
            robotNero.blueUpper.slide(.5);
        else if(gamepad.left_trigger > 0)
            robotNero.blueUpper.slide(-.5);
        else
            robotNero.blueUpper.slide(0);

        // do end-of-loop button toggle checks
        if(aLast && !gamepad.a)
            flipToggle = !flipToggle;
        if(bLast && !gamepad.b)
            grabToggle = !grabToggle;
        aLast = gamepad.a;
        bLast = gamepad.b;
    }

    /**
     *  get telemetry readings
      */

    public void feedback()
    {
        telemetry.addData("Impel Speed:", boost ? "Boost" : "Normal");
        robotNero.feedback(telemetry);
    }

    /**
     * @return calculated time since automatin start
     */
    public double timeSinceAuto()
    {
        return runtime.milliseconds() - autoStartTime;
    }
}
