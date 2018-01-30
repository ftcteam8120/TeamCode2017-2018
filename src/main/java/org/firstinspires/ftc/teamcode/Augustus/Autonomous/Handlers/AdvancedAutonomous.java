package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.ClawPos;
import org.firstinspires.ftc.teamcode.Augustus.ElevatorXPos;
import org.firstinspires.ftc.teamcode.Augustus.Handler;
import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.JClaw;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

public class AdvancedAutonomous extends Handler {

    private double speed;

    public AdvancedAutonomous(double speed) {
        super();
        this.speed = speed;
    }

    /**
     * Autonomous for the further two balancing stones
     */
    public void Far()
    {
        if(!handler.started) start();

        switch (state) {
            case 0:
                robot.knocker.out();
                if(pause(1500)) next();
                break;
            case 1:
                if((side == Side.BLUE && robot.knocker.colorRangeSensor.isRed()) ||
                        side == Side.RED && !robot.knocker.colorRangeSensor.isRed())
                    next(-2);
                else
                    next(2);
                break;
            case -2:
                robot.knocker.left();
                if(pause(500)) next(3);
                break;
            case 2:
                robot.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                robot.knocker.center();
                robot.knocker.in();
                if(pause(500)) next();
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
                robot.knocker.out();
                if(pause(1500)) next();
                break;
            case 1:
                if((side == Side.BLUE && robot.knocker.colorRangeSensor.isRed()) ||
                        side == Side.RED && !robot.knocker.colorRangeSensor.isRed())
                    next(-2);
                else
                    next(2);
                break;
            case -2:
                robot.knocker.left();
                if(pause(500)) next(3);
                break;
            case 2:
                robot.knocker.right();
                if(pause(500)) next(3);
                break;
            case 3:
                robot.knocker.center();
                robot.knocker.in();
                if(pause(500)) next();
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
