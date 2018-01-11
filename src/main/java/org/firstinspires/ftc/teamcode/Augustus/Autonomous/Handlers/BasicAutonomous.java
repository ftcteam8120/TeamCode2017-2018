package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Handler;
import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

import java.util.ArrayList;

public class BasicAutonomous extends Handler {

    private double speed;

    // Autonomous constant values
    private double KNOCK_SPEED = 0.15;
    private double AUTO_SPEED = 1;
    private double ROTATE_SPEED = 0.55;

    public BasicAutonomous(double speed) {
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
        if(!handler.started) start();

        switch (state) {
            case 0:
                // Extend the knocker
                if(handler.runTime.milliseconds() >= 1500) next();
                robot.knocker.out();
                break;
            case 1:
                // Actually detect the color of the jewel
                if((side == Side.BLUE && robot.knocker.colorRangeSensor.isRed())
                        || (side == Side.RED && !robot.knocker.colorRangeSensor.isRed()))
                    next(side == Side.BLUE ? 2 : -2);
                else
                    next(side == Side.BLUE ? -2 : 2);
                break;
            case -2:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(-3);
                robot.drive.setAllDrive(side == Side.BLUE ? -.05 : .05);
                break;
            case -3:
                robot.knocker.in();
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(4);
                robot.drive.setAllDrive(side == Side.BLUE ? .05 : -.05);
                break;
            case 2:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 600)) next();
                robot.drive.setDrive(side == Side.BLUE ? HoloDir.BACKWARD : HoloDir.FORWARD, .2);
                break;
            case 3:
                robot.knocker.in();
                next();
            case 4:
                if(handler.runTime.milliseconds() >= (handler.lastTick + (states.indexOf(2) < 0 ? 2500 : 1900))) next();
                robot.drive.setDrive(side == Side.BLUE ? HoloDir.BACKWARD : HoloDir.FORWARD, .5);
                break;
            case 5:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1500)) next();
                robot.drive.setDrive(HoloDir.LEFT, .5);
                break;
            /*case 3:
                // Move back to the base position
                // Spend 500 ms doing this
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                if(robot.drive.a.getDistanceTraveled() > 0)
                    robot.drive.setAllDrive(-KNOCK_SPEED);
                else
                    robot.drive.setAllDrive(KNOCK_SPEED);
                break;
            case 4:
                if (side == Side.BLUE) {
                    // Move backwards for 3s
                    if(handler.runTime.milliseconds() >= (handler.lastTick + 3000)) next();
                    robot.drive.setDrive(HoloDir.BACKWARD, AUTO_SPEED);
                } else {
                    // Move forwards for 3s
                    if(handler.runTime.milliseconds() >= (handler.lastTick + 3000)) next();
                    robot.drive.setDrive(HoloDir.FORWARD, AUTO_SPEED);
                }
                break;
            case 5:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next();
                if (side == Side.BLUE) {
                    // Move left for 2s
                    robot.drive.setDrive(HoloDir.LEFT, AUTO_SPEED);
                } else {
                    // Move right for 2s
                    robot.drive.setDrive(HoloDir.RIGHT, AUTO_SPEED);
                }
                break;
            case 6:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                if (side == Side.BLUE) {
                    // Rotate the robot right to turn toward the cryptobox
                    robot.drive.setAllDrive(ROTATE_SPEED);
                } else {
                    // Rotate the robot left to turn toward the cryptobox
                    robot.drive.setAllDrive(-ROTATE_SPEED);
                }
                break;
            case 7:
                // Extend the elevator for 1s
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1000)) next();
                robot.elevator.out(false);
                break;
            case 8:
                // Stop the elevator from moving
                robot.elevator.stop();
                next();
                break;
            case 9:
                // Release the glyph and slowly back up for 1.5s
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1500)) next();
                robot.elevator.claw.release(false);
                robot.drive.setDrive(HoloDir.BACKWARD, 0.15);
                break;
            case 10:
                robot.stop();
                break;*/
            default:
                robot.stop();
                break;
        }
    }

    /**
     * Autonomous for the closer two balancing stones
     */
    public void Near() {
        if (!handler.started) handler.start();
        switch (state) {
            case 0:
                // Extend the knocker
                if(handler.runTime.milliseconds() >= 1500) next();
                robot.knocker.out();
                break;
            case 1:
                // Actually detect the color of the jewel
                if((side == Side.BLUE && robot.knocker.colorRangeSensor.isRed())
                        || (side == Side.RED && !robot.knocker.colorRangeSensor.isRed()))
                    next(side == Side.RED ? -2 : 2);
                else
                    next(side == Side.RED ? 2 : -2);
                break;
            case -2:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(-3);
                robot.drive.setAllDrive(side == Side.RED ? .05 : -.05);
                break;
            case -3:
                robot.knocker.in();
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2000)) next(4);
                robot.drive.setAllDrive(side == Side.RED ? -.05 : .05);
                break;
            case 2:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1000)) next(3);
                robot.drive.setDrive(side == Side.RED ? HoloDir.FORWARD : HoloDir.BACKWARD, .3);
                break;
            case 3:
                // Pull the knocker in
                robot.knocker.in();
                next();
                break;
            case 4:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 2500)) next();
                robot.drive.setDrive(side == Side.RED ? HoloDir.FORWARD : HoloDir.BACKWARD, .5);
                break;
            case 5:
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1000)) next();
                robot.drive.setDrive(HoloDir.RIGHT, .5);
                break;
            /*case 3:
                // Move back to the base position
                // Spend 0.5s doing this
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                if(robot.drive.a.getDistanceTraveled() > 0)
                    robot.drive.setAllDrive(-KNOCK_SPEED);
                else
                    robot.drive.setAllDrive(KNOCK_SPEED);
            case 4:
                // Move backwards for 3s
                if(handler.runTime.milliseconds() >= (handler.lastTick + 3000)) next();
                if(side == Side.BLUE) {
                    robot.drive.setDrive(HoloDir.BACKWARD, AUTO_SPEED);
                } else {
                    robot.drive.setDrive(HoloDir.FORWARD, AUTO_SPEED);
                }
                break;
            case 5:
                // Rotate the robot to turn toward the cryptobox
                if(handler.runTime.milliseconds() >= (handler.lastTick + 500)) next();
                robot.drive.setAllDrive(-ROTATE_SPEED);
                break;
            case 6:
                // Extend the elevator for 1s
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1000)) next();
                robot.elevator.out(false);
                break;
            case 7:
                // Stop the elevator from moving
                robot.elevator.stop();
                next();
                break;
            case 8:
                // Release the glyph and slowly back up for 1.5s
                if(handler.runTime.milliseconds() >= (handler.lastTick + 1500)) next();
                robot.elevator.claw.release(false);
                robot.drive.setDrive(HoloDir.BACKWARD, 0.15);
                break;
            case 9:
                robot.stop();
                break;*/
            default:
                robot.stop();
                break;
        }
    }

    public void update() {
        if (section == Section.FAR) {
            this.Far();
        } else {
            this.Near();;
        }
    }

    public void feedback(Telemetry telemetry) {
        robot.feedback(telemetry);
    }


}
