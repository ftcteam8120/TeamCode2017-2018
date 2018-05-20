package org.firstinspires.ftc.teamcode.Nero.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.teamcode.Augustus.RobotAugustus;
import org.firstinspires.ftc.teamcode.Handler;
import org.firstinspires.ftc.teamcode.Nero.RobotNero;
import org.firstinspires.ftc.teamcode.Section;
import org.firstinspires.ftc.teamcode.Side;

import static org.firstinspires.ftc.teamcode.HoloDir.BACKWARD;
import static org.firstinspires.ftc.teamcode.HoloDir.FORWARD;
import static org.firstinspires.ftc.teamcode.HoloDir.LEFT;
import static org.firstinspires.ftc.teamcode.HoloDir.RIGHT;
import static org.firstinspires.ftc.teamcode.Side.BLUE;
import static org.firstinspires.ftc.teamcode.Side.RED;

//Autonomous class which allows the robot to knock jewel, park and complete a one Glyph run
public class ThreeGlyphAutonomous extends Handler {

    //The pictograph scanned by the robot
    private RelicRecoveryVuMark pictograph;
    //The available pictographs
    private VuforiaTrackable template;

    RobotNero nero;

    int travelDistance;

    public ThreeGlyphAutonomous(VuforiaTrackable relicTemplate) {
        super();
        template = relicTemplate;
    }

    /**
     * Autonomous for the further two balancing stones
     */
    public void Far()
    {
        if(!handler.started)
        {
            nero = (RobotNero)robot;
            start();
        }

        //constantly scanning for a pictograph
        if(pictograph == null || pictograph == RelicRecoveryVuMark.UNKNOWN)
            pictograph = RelicRecoveryVuMark.from(template);

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
                //Bring the knocker back in and wait 1 second
                nero.knocker.center();
                nero.knocker.in();
                if(pause(500)) next(side == RED ? 4 : 30);
                break;
            case 30:
                nero.drive.setDrive(RIGHT, 0.3);
                if(nero.drive.getAverageTravel() >= 1300) next();
                break;
            case 31:
                nero.drive.setDrive(FORWARD, 0.3);
                if(nero.drive.getAverageTravel() >= 1950) next();
                break;
            case 32:
                nero.drive.setDrive(LEFT, 0.3);
                if(nero.drive.getAverageTravel() >= 1300) next(5);
                break;
            case 4:
                //If your on the blue side move forward for ... encoder ticks
                //else if your on red go backwards that same amount
                nero.drive.setDrive(BACKWARD, 0.3);
                if(nero.drive.getAverageTravel() >= 1950) next();
                break;
            default:
                //Stop the robot
                nero.stop();
                break;
        }
    }

    /**
     * Autonomous for the closer two balancing stones
     */
    public void Near() {

        if(!handler.started)
        {
            nero = (RobotNero)robot;
            start();
        }

        //constantly scanning for a pictograph
        if(pictograph == null || pictograph == RelicRecoveryVuMark.UNKNOWN)
            pictograph = RelicRecoveryVuMark.from(template);

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
                //Bring the knocker back in and wait 1 second
                nero.knocker.center();
                nero.knocker.in();
                if(pause(500)) next(side == RED ? 4 : 30);
                break;
            case 30:
                nero.drive.setDrive(RIGHT, 0.4);
                if(nero.drive.getAverageTravel() >= 1300) next();
                break;
            case 31:
                nero.drive.setDrive(FORWARD, 0.6);
                if(nero.drive.getAverageTravel() >= 1950) next();
                break;
            case 32:
                nero.drive.setDrive(LEFT, 0.6);
                if(nero.drive.getAverageTravel() >= 1400) next(5);
                break;
            case 4:
                //If your on the blue side move forward for ... encoder ticks
                //else if your on red go backwards that same amount
                nero.drive.setDrive(BACKWARD, 0.3);
                if(nero.drive.getAverageTravel() >= 1950) next();
                break;
            case 5:
                //Turn clockwise for 1330 encoder ticks
                nero.drive.setAllDrive(0.6);
                if(nero.drive.getAverageTravel() >= 1200)
                {
                    if(pictograph == RelicRecoveryVuMark.CENTER) next(50);
                    else if(pictograph == RelicRecoveryVuMark.LEFT) next(51);
                    else next();
                }
                break;
            case 50:
                nero.drive.setDrive(side == RED ? RIGHT : LEFT, 0.2);
                if(nero.drive.getAverageTravel() >= 480) next(6);
                break;
            case 51:
                nero.drive.setDrive(side == RED ? RIGHT : LEFT, 0.2);
                if(nero.drive.getAverageTravel() >= 960) next(6);
                break;
            case 6:
                //Go backwards 225 encoder ticks, lined up back against the Cryptobox
                nero.drive.setDrive(BACKWARD, 0.4);
                if(nero.drive.getAverageTravel() >= 225) next();
                break;
            case 7:
                //Use 1.5 seconds to flip the glyph into place
                nero.blueUpper.flip(true);
                if(pause(1000)) next();
                break;
            case 8:
                //Reset Glyph Flipper, drive forward off the Glyph
                nero.blueUpper.flip(false);
                nero.redUpper.kick();
                nero.drive.setDrive(FORWARD, 0.4);
                if(nero.drive.getAverageTravel() >= 200) next();
                break;
            case 9:
                nero.redUpper.stopKick();
                nero.redUpper.impel(false);
                nero.blueUpper.impel(false);
                nero.drive.setDrive(FORWARD, 0.4);
                if(nero.distance.getDistance(DistanceUnit.CM) < 10 || nero.drive.getAverageTravel() >= 1650){
                    travelDistance = nero.drive.getAverageTravel() / 2;
                    next();
                }
                break;
            case 10:
                nero.drive.setDrive(BACKWARD, 0.6);
                if(nero.drive.getAverageTravel() >= travelDistance) next();
                break;
            case 11:
                if(side == RED)
                    nero.drive.setDrive(pictograph == RelicRecoveryVuMark.LEFT ? LEFT : RIGHT, 0.4);
                else
                    nero.drive.setDrive(pictograph == RelicRecoveryVuMark.LEFT ? RIGHT : LEFT, 0.4);
                if(nero.drive.getAverageTravel() >= 480) next();
                break;
            case 12:
                nero.drive.setAllDrive(0.6);
                if(nero.drive.getAverageTravel() >= 2500) next();
                break;
            case 13:
                nero.drive.setDrive(FORWARD, 0.6);
                if(nero.drive.getAverageTravel() >= 250) next();
                break;
            case 14:
                nero.redUpper.outpel(false);
                nero.blueUpper.outpel(false);
                if(pause(500)) next();
                break;
            case 15:
                nero.drive.setDrive(BACKWARD, 0.6);
                if(nero.drive.getAverageTravel() >= 200) next();
                break;
            case 16:
                nero.redUpper.dontpel();
                nero.blueUpper.dontpel();
                nero.drive.setAllDrive(-0.6);
                if(nero.drive.getAverageTravel() >= 1240) next();
                break;
            case 17:
                nero.drive.setDrive(RIGHT, 1);
                if(pause(1500)) next();
                break;
            case 18:
                nero.drive.setDrive(LEFT, 0.2);
                if(nero.drive.getAverageTravel() >= 200) next();
                break;
            default:
                //Stop the robot if the case machine ever tries to reach a case that doesn't exist
                nero.stop();
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
