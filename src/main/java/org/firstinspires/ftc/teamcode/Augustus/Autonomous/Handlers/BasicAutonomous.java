package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.Handler;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

public class BasicAutonomous extends Handler {

    private double WHEEL_SPEED;

    /*
     * Constructor
     */
    public BasicAutonomous(double speed) {
        super();
        WHEEL_SPEED = speed;
    }

    /**
     * Autonomous for the further two balancing stones
     */
    public void Far()
    {
        if(!handler.started) start();

        switch (state) {
            case 0:
                //Drop knocker and wait .5 seconds
                robot.knocker.out();
                if(pause(500)) next();
                break;
            case 1:
                //Determines which case to go to next based on
                // values from the color sensor and the Side enum
                if((robot.knocker.colorRangeSensor.isRed() && side == Side.BLUE) ||
                        (!robot.knocker.colorRangeSensor.isRed() && side == Side.RED))
                    next(-2);
                else
                    next(2);
                break;
            case -2:
                //hit the jewel to the left in .5 seconds
                robot.knocker.left();
                if(pause(500)) next(3);
                break;
            case 2:
                //hit the Jewel on the right in .5 seconds
                robot.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                //retract the Knocker in .5 seconds
                robot.knocker.center();
                robot.knocker.in();
                if(pause(500)) next();
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
        if (!handler.started) handler.start();

        switch (state) {
            case 0:
                //Drop the Knocker in .5 seconds
                robot.knocker.out();
                if(pause(500)) next();
                break;
            case 1:
                //Determines which case to go to next based on
                // values from the color sensor and the Side enum
                if((robot.knocker.colorRangeSensor.isRed() && side == Side.BLUE) ||
                        (!robot.knocker.colorRangeSensor.isRed() && side == Side.RED))
                    next(-2);
                else
                    next(2);
                break;
            case -2:
                //Hit the Jewel on the left in .5 seconds
                robot.knocker.left();
                if(pause(500)) next(3);
                break;
            case 2:
                //hit the Jewel on the right in .5 seconds
                robot.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                //retract the Knocker in .5 seconds
                robot.knocker.center();
                robot.knocker.in();
                if(pause(500)) next();
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
        robot.feedback(telemetry);
    }


}
