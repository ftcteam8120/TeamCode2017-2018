package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.teamcode.Augustus.RobotAugustus;
import org.firstinspires.ftc.teamcode.Handler;
import org.firstinspires.ftc.teamcode.Section;
import org.firstinspires.ftc.teamcode.Side;

import static org.firstinspires.ftc.teamcode.HoloDir.*;
import static org.firstinspires.ftc.teamcode.Side.*;

//Autonomous class which allows the robot to knock jewel, park and complete a one Glyph run
public class AdvancedAutonomous extends Handler {

    //the speed to which the motors are set to
    private double speed;
    //The pictograph scanned by the robot
    private RelicRecoveryVuMark pictograph;
    //The available pictographs
    private VuforiaTrackable template;

    RobotAugustus augustus;

    public AdvancedAutonomous(double speed, VuforiaTrackable relicTemplate) {
        super();
        this.speed = speed;
        template = relicTemplate;
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
                //Drop knocker and wait 1.5 seconds
                augustus.knocker.center();
                augustus.knocker.out();
                if(pause(1500)) next();
                break;
            case 1:
                //Determines which path to take in order to knock correct Jewel
                if((side == Side.BLUE && augustus.knocker.colorRangeSensor.isRed()) ||
                        side == RED && !augustus.knocker.colorRangeSensor.isRed())
                    next(-2);
                else
                    next(2);
                break;
            case -2:
                //Move knocker to the left and wait .5 seconds
                augustus.knocker.left();
                if(pause(500)) next(3);
                break;
            case 2:
                //move Knocker to the right and wait .5 seconds
                augustus.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                //retract Knocker and determine which path to take based on Side enum
                augustus.knocker.center();
                augustus.knocker.in();
                if(pause(500)) next(side == BLUE ? 30 : 4);
                break;
            case 30:
                //Turn slowly for 694 encoder ticks
                augustus.drive.setAllDrive(-.2);
                if(augustus.drive.getAverageTravel() >= 694) next();
                break;
            case 31:
                //Look for pictograph
                pictograph = RelicRecoveryVuMark.from(template);
                next();
                break;
            case 32:
                //Turn slowly for 1.8 seconds
                augustus.drive.setAllDrive(-.2);
                if(augustus.drive.getAverageTravel() >= 1800) next(4);
                break;
            case 4:
                //Drive forward for 1120 encoder ticks, then depending on what pictograph was
                // scanned continue
                augustus.drive.setDrive(FORWARD, 0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 1120) next((pictograph == null ||
                        pictograph == RelicRecoveryVuMark.UNKNOWN) ? 5 : 6);
                break;
            case 5:
                //Figure out which pictograph it was
                pictograph = RelicRecoveryVuMark.from(template);
                next();
                break;
            case 6:
                //Move to the left/right depending on Side enum
                augustus.drive.setDrive(side == RED ? LEFT : RIGHT, 0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 875) next();
                break;
            case 7:
                //Drive forward for 375 encoder ticks then advance state depending on the pictograph
                augustus.drive.setDrive(FORWARD, 0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 375)
                {
                    if(pictograph == RelicRecoveryVuMark.LEFT) next(-8);
                    else if(pictograph == RelicRecoveryVuMark.RIGHT) next(8);
                    else next(9);
                }
                break;
            case -8:
                //Move to the left for 495 encoder ticks
                augustus.drive.setDrive(LEFT, 0.1);
                if(augustus.drive.getAverageTravel() >= 495) next(9);
                break;
            case 8:
                //move to the right for 495 encoder ticks
                augustus.drive.setDrive(RIGHT, 0.1);
                if(augustus.drive.getAverageTravel() >= 495) next(9);
                break;
            case 9:
                //extend the elevator until the lateral position is at 4500 encoder ticks
                augustus.elevator.out(true);
                if(augustus.elevator.getXPos() >= 4500) next();
                break;
            case 10:
                //release the glyph then pause for .45 seconds
                augustus.elevator.claw.release(true);
                if(pause(450)) next();
                break;
            case 11:
                //Drive backwards for 300 encoder ticks
                augustus.drive.setDrive(BACKWARD, 0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 300) next();
                break;
            case 12:
                //Turn for 1390 encoder ticks
                augustus.drive.setAllDrive(0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 1390) next();
                break;
            case 13:
                //augustus move left for 900 encoder ticks
                //Slam glyph into Cryptobox
                augustus.drive.setDrive(LEFT, 1.0);
                if(augustus.drive.getAverageTravel() >= 900) next();
                break;
            case 14:
                //augustus move right for 300 encoder ticks
                augustus.drive.setDrive(RIGHT, 0.2);
                if(augustus.drive.getAverageTravel() >= 300) next();
                break;
            default:
                //Stop the augustus
                augustus.stop();
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
                //Drop knocker and wait 1.5 seconds
                augustus.knocker.center();
                augustus.knocker.out();
                if(pause(1500)) next();
                break;
            case 1:
                //determine which state to move to based on Side enum and whether the color
                // sensor sees a red jewel
                if((side == Side.BLUE && augustus.knocker.colorRangeSensor.isRed()) ||
                        side == RED && !augustus.knocker.colorRangeSensor.isRed())
                    next(-2);
                else
                    next(2);
                break;
            case -2:
                //Knock the jewel to the left then wait .5 seconds
                augustus.knocker.left();
                if(pause(500)) next(3);
                break;
            case 2:
                //Knock the Jewel to the right then wait .5 seconds
                augustus.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                //Bring the knocker back in and wait .5 seconds
                augustus.knocker.center();
                augustus.knocker.in();
                if(pause(500)) next();
                break;
            case 4:
                //Drive forward/backwards for 1120 encoder ticks and then change states based on
                //Side enum
                augustus.drive.setDrive(side == RED ? FORWARD : BACKWARD, 0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 1120) next(side == RED ? 5 : 6);
                break;
            case 5:
                //Scan pictograph
                pictograph = RelicRecoveryVuMark.from(template);
                next();
                break;
            case 6:
                //Drive forward/backward for 1050 encoder ticks based on Side enum
                //determine which state to advance to based on Side enum
                augustus.drive.setDrive(side == RED ? FORWARD : BACKWARD, 0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 1050) next(side == BLUE ? 60 : 7);
                break;
            case 60:
                //Drive backwards for 1050 encoder ticks
                augustus.drive.setDrive(BACKWARD, 0.5);
                if(augustus.drive.getAverageTravel() >= 1050) next();
                break;
            case 61:
                //determine which pictograph was scanned and pause for .5 seconds
                pictograph = RelicRecoveryVuMark.from(template);
                if(pause(500)) next();
                break;
            case 62:
                //drive foreward for 900 encoder ticks
                augustus.drive.setDrive(FORWARD, 0.5);
                if(augustus.drive.getAverageTravel() >= 900) next(7);
                break;
            case 7:
                //Turn for 1390 encoder ticks then determine which case to switch to based on
                //pictograph
                augustus.drive.setAllDrive(0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 1390)
                {
                    if(pictograph == RelicRecoveryVuMark.LEFT) next(-8);
                    else if(pictograph == RelicRecoveryVuMark.RIGHT) next(8);
                    else next(9);
                }
                break;
            case -8:
                //Move left for 495 encoder ticks
                augustus.drive.setDrive(LEFT, 0.1);
                if(augustus.drive.getAverageTravel() >= 495) next(9);
                break;
            case 8:
                //move right for 495 encoder ticks
                augustus.drive.setDrive(RIGHT, 0.1);
                if(augustus.drive.getAverageTravel() >= 495) next(9);
                break;
            case 9:
                //have the elevator move out for 4500 encoder ticks
                augustus.elevator.out(true);
                if(augustus.elevator.getXPos() >= 4500) next();
                break;
            case 10:
                //release the claw for 450 encoder ticks
                augustus.elevator.claw.release(true);
                if(pause(450)) next();
                break;
            case 11:
                //Drive backwards for 300 encoder ticks
                augustus.drive.setDrive(BACKWARD, 0.3);
                if(augustus.drive.getAverageTravel() >= 300) next();
                break;
            case 12:
                //Turn for 1410 encoder ticks
                augustus.drive.setAllDrive(0.2 * speed);
                if(augustus.drive.getAverageTravel() >= 1410) next();
                break;
            case 13:
                //move left for 900 encoder ticks to slam the Gyph into the Cryptobox
                augustus.drive.setDrive(LEFT, 1.0);
                if(augustus.drive.getAverageTravel() >= 900) next();
                break;
            case 14:
                //Move right for 300 encoder ticks
                augustus.drive.setDrive(RIGHT, 0.2);
                if(augustus.drive.getAverageTravel() >= 300) next();
                break;
            default:
                //Stop the augustus if the case machine ever tries to reach a case that doesn't exist
                augustus.stop();
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
