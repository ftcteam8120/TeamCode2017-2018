package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.ClawPos;
import org.firstinspires.ftc.teamcode.Augustus.ElevatorXPos;
import org.firstinspires.ftc.teamcode.Augustus.RobotAugustus;
import org.firstinspires.ftc.teamcode.Handler;
import org.firstinspires.ftc.teamcode.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.JClaw;
import org.firstinspires.ftc.teamcode.Section;
import org.firstinspires.ftc.teamcode.Side;

public class MediumAutonomous extends Handler {

    private double speed;

    // Autonomous constant values
    private double KNOCK_SPEED = 0.15;
    private double AUTO_SPEED = 1;
    private double ROTATE_SPEED = 0.55;

    RobotAugustus augustus;

    public MediumAutonomous(double speed) {
        super();
        this.speed = speed;
        KNOCK_SPEED *= speed;
        AUTO_SPEED *= speed;
        ROTATE_SPEED *= speed;
    }

    /**
     * Autonomous for the further two balancing stones
     */
    public void Far()
    {
        if(!handler.started){
            start();
            augustus = (RobotAugustus)robot;
        }

        switch (state) {
            case 0:
                // Extend the knocker
                if(handler.runTime.milliseconds() >= 1500) next();
                augustus.knocker.out();
                break;
            case 1:
                // Detect the color of the jewel and identify what case to go to next based on
                // Side enum and what color is seen
                if((side == Side.BLUE && augustus.knocker.colorRangeSensor.isRed())
                        || (side == Side.RED && !augustus.knocker.colorRangeSensor.isRed()))
                    next(side == Side.BLUE ? 2 : -2);
                else
                    next(side == Side.BLUE ? -2 : 2);
                break;
            case -2:
                //turn for 2 seconds, direction determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(-3);
                augustus.drive.setAllDrive(side == Side.BLUE ? -.05 : .05);
                break;
            case -3:
                //retract Knocker and turn for 2 seconds, direction determined by Side enum
                augustus.knocker.in();
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(4);
                augustus.drive.setAllDrive(side == Side.BLUE ? .05 : -.05);
                break;
            case 2:
                //Drive forward/backward, direction determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick + 600)) next();
                augustus.drive.setDrive(side == Side.BLUE ? HoloDir.BACKWARD : HoloDir.FORWARD, .2);
                break;
            case 3:
                //retract Knocker
                augustus.knocker.in();
                next();
            case 4:
                //RobotNero goes forward/backward for an amount of time determined by
                // which side of the robot the knocked Jewel was on
                // direction determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick +
                                            (states.indexOf(2) < 0 ? 2500 : 1900))) next();
                augustus.drive.setDrive(side == Side.BLUE ? HoloDir.BACKWARD : HoloDir.FORWARD, .5);
                break;
            case 5:
                //Move left for 1.5 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1500)) next();
                augustus.drive.setDrive(HoloDir.LEFT, .5);
                break;
            case 6:
                //Move forward/backward for .625 seconds based on Side enum
                //next case determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick + 625))
                    next(side == Side.BLUE ? -7 : 7);
                augustus.drive.setDrive(side == Side.RED ? HoloDir.BACKWARD : HoloDir.FORWARD, .45);
                break;
            case -7:
                //turn for 1.96 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1960)) next(7);
                augustus.drive.setAllDrive(0.5);
                break;
            case 7:
                //Slide elevator out until the position is at the Stopped position
                if(augustus.elevator.xPos == ElevatorXPos.STOPPED) next();
                augustus.elevator.out(false);
                break;
            case 8:
                //2 second pause
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next();
                break;
            case 9:
                //Release the claw until its in the stopped claw position
                if(((JClaw)augustus.elevator.claw).p == ClawPos.STOPPED) next();
                augustus.elevator.claw.release(false);
                break;
            case 10:
                //2.5 second pause
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2500)) next();
                break;
            case 11:
                //move elevator up for .5 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                augustus.elevator.up();
                break;
            case 12:
                //turn for .98 seconds and stop vertical movement of the Elevator
                if(handler.runTime.milliseconds() >= (handler.lastTick + 980)) next();
                augustus.elevator.stopVert();
                augustus.drive.setAllDrive(0.5);
                break;
            case 13:
                //Move left for 1.6 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1600)) next();
                augustus.drive.setDrive(HoloDir.LEFT, 0.8);
                break;
            case 14:
                //move right for .5 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                augustus.drive.setDrive(HoloDir.RIGHT, 0.5);
                break;
            case 15:
                //move elevator down for .5 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                augustus.elevator.down();
                break;
            case 16:
                //Stop vertical movement of elevator
                augustus.elevator.stopVert();
                next();
                break;
            default:
                //stop robot when there are no more chronological cases
                robot.stop();
                break;
        }
    }

    /**
     * Autonomous for the closer two balancing stones
     */
    public void Near() {

        if(!handler.started){
            start();
            augustus = (RobotAugustus)robot;
        }

        switch (state) {
            case 0:
                // Extend the knocker
                if(handler.runTime.milliseconds() >= 1500) next();
                augustus.knocker.out();
                break;
            case 1:
                // Detect the color of the Jewel and determine which case to move to
                // based on what the color sensor sees and the Side enum
                if((side == Side.BLUE && augustus.knocker.colorRangeSensor.isRed())
                        || (side == Side.RED && !augustus.knocker.colorRangeSensor.isRed()))
                    next(side == Side.RED ? -2 : 2);
                else
                    next(side == Side.RED ? 2 : -2);
                break;
            case -2:
                //Turn for 2 seconds, direction determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(-3);
                augustus.drive.setAllDrive(side == Side.RED ? .05 : -.05);
                break;
            case -3:
                //retract knocker and turn for 2 seconds, direction determined by Side enum
                augustus.knocker.in();
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(4);
                augustus.drive.setAllDrive(side == Side.RED ? -.05 : .05);
                break;
            case 2:
                //Turn for 1 second, direction determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1000)) next(3);
                augustus.drive.setAllDrive(side == Side.RED ? -0.05 : 0.05);
                break;
            case 3:
                //Pull the knocker in and turn, direction determined by Side enum
                augustus.knocker.in();
                augustus.drive.setAllDrive(side == Side.RED ? 0.05 : -0.05);
                next();
                break;
            case 4:
                //Drive forward/backwards for 2.8 sections, direction determined by Side enum
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2800)) next();
                augustus.drive.setDrive(side == Side.RED ? HoloDir.FORWARD : HoloDir.BACKWARD, .5);
                break;
            case 5:
                //turn for .98 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 980)) next();
                augustus.drive.setAllDrive(0.5);
                break;
            case 6:
                //move elevator out until it is in the STOPPED position
                if(augustus.elevator.xPos == ElevatorXPos.STOPPED) next();
                augustus.elevator.out(false);
                break;
            case 7:
                //Pause for 2 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next();
                break;
            case 8:
                //open Claw until it is in the STOPPED position
                if(((JClaw)augustus.elevator.claw).p == ClawPos.STOPPED) next();
                augustus.elevator.claw.release(false);
                break;
            case 9:
                //2.5 second pause
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2500)) next();
                break;
            case 10:
                //Drive backwards for 1 second
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1000)) next();
                augustus.drive.setDrive(HoloDir.BACKWARD, 0.5);
                break;
            case 11:
                //turn for 1.08 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1080)) next();
                augustus.drive.setAllDrive(0.5);
                break;
            case 12:
                //Move left for 1.6 seconds
                //slam Glyph into Cryptobox
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1600)) next();
                augustus.drive.setDrive(HoloDir.LEFT, 0.8);
                break;
            case 13:
                //Move right for .5 seconds
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                augustus.drive.setDrive(HoloDir.RIGHT, 0.5);
                break;
            default:
                //Stop robot if the next case can not be found
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
        robot.feedback(telemetry);
    }


}
