package org.firstinspires.ftc.teamcode.Nero.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HoloDir;
import org.firstinspires.ftc.teamcode.Nero.RobotNero;
import org.firstinspires.ftc.teamcode.Section;
import org.firstinspires.ftc.teamcode.Handler;

import static org.firstinspires.ftc.teamcode.HoloDir.*;
import static org.firstinspires.ftc.teamcode.Side.*;

public class NoGlyphAutonomous extends Handler {

    RobotNero nero;

    String error;
    boolean happened;

    public NoGlyphAutonomous() {
        super();
    }

    /**
     * Autonomous for the further two balancing stones
     */
    public void Far()
    {
        if(!handler.started)
        {
            start();
            nero = (RobotNero)robot;
        }

        //((RobotNero)robot).knocker.center();

        switch (state) {
            case 0:
                //Drops knocker and gives time for the sensor to detect color
                nero.knocker.center();
                nero.knocker.out();
                if(pause(2000)) next();
                break;
            case 1:
                //determine which state to move to based on Side enum and whether the color
                // sensor sees a red jewel
                if((side == BLUE && nero.knocker.colorRangeSensor.isRed()) ||
                        side == RED && !nero.knocker.colorRangeSensor.isRed())
                    next(-2);
                else
                    next(2);
                break;
            case 2:
                //Knock the jewel to the left then wait 1 second
                nero.knocker.left();
                if(pause(500)) next(3);
                break;
            case -2:
                //Knock the Jewel to the right then wait 1 second
                nero.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                //retract the Knocker in .5 seconds
                nero.knocker.center();
                nero.knocker.in();
                if(pause(500)) next();
                break;
            case 4:
                //Move forward if the Side is red, backward if it is blue (for 2170 encoder ticks)
                nero.drive.setDrive(side == BLUE ? FORWARD : BACKWARD, .35);
                if(nero.drive.getAverageTravel() >= 2170) next();
                break;
            case 5:
                //Robot move left for 400 encoder ticks
                nero.drive.setDrive(LEFT, .2);
                if(nero.drive.getAverageTravel() >= 400) next();
                break;
            case 6:
                //Kick it
                nero.redUpper.kick();
                if(pause(500)) next();
                break;
            case 7:
                //Stop kicking it
                nero.redUpper.stopKick();
                next();
                break;
            default:
                //Stop the robot if the next case can not be found
                robot.stop();
                break;
        }
    }

    /**
     * Autonomous for the closer two balancing stones
     */
    public void Near() {

        if(!handler.started)
        {
            start();
            nero = (RobotNero)robot;
        }

        switch (state) {
            case 0:
                //Drops knocker and gives time for the sensor to detect color
                nero.knocker.center();
                nero.knocker.out();
                if(pause(2000)) next();
                break;
            case 1:
                //determine which state to move to based on Side enum and whether the color
                // sensor sees a red jewel
                if((side == BLUE && nero.knocker.colorRangeSensor.isRed()) ||
                        side == RED && !nero.knocker.colorRangeSensor.isRed())
                    next(-2);
                else
                    next(2);
                break;
            case 2:
                //Knock the jewel to the left then wait 1 second
                nero.knocker.left();
                if(pause(500)) next(3);
                break;
            case -2:
                //Knock the Jewel to the right then wait 1 second
                nero.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                //retract the Knocker in .5 seconds
                nero.knocker.center();
                nero.knocker.in();
                if(pause(500)) next();
                break;
            case 6:
                //Kick it
                nero.redUpper.kick();
                if(pause(500)) next();
                break;
            case 7:
                //Stop kicking it
                nero.redUpper.stopKick();
                next();
                break;
            default:
                //Stop the robot if the next case can't be found
                robot.stop();
                break;
        }
    }

    /**
     * Check the Side enum
     */
    public void update() {

        if (section == Section.FAR) {
            this.Far();
        } else {
            this.Near();;
        }
    }

    /*
    *Print out the feedback function from the robot class
    *
    * @param telemetry the imported way to print specified data to the Drivers Station
    */
    public void feedback(Telemetry telemetry) {
        nero.feedback(telemetry);
        telemetry.addData("error", error);
    }


}
