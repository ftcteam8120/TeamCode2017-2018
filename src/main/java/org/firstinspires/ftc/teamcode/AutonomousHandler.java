package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.RobotAugustus;

public class AutonomousHandler {

    /**
     * The robot object
     */
    public Robot robot;
    /**
     * If the robot is on the blue side or not
     */
    public Side side;
    /**
     * Which balancing stone the robot is on
     */
    public Section section;
    /**
     * The current robot state
     */
    public int state;
    //
    /**
     * If the auto is started
     */
    public boolean started;
    /**
     * The elapsed time
     */
    public ElapsedTime runTime;
    /**
     * The last tick time
     */
    public double lastTick;

    private Handler handler;

    public AutonomousHandler(Robot robot, Side side, Section section)
    {
        this.robot = robot;
        this.side = side;
        this.section = section;
        state = -999;
        runTime = new ElapsedTime();
        started = false;
        lastTick = 0;
    }

    public AutonomousHandler(Robot robot) {
        this(robot, null, null);
    }

    public AutonomousHandler(Robot robot, Side side, Section section, Handler handler) {
        this(robot, side, section);
        this.handler = handler;
        this.handler.init(this);
    }

    public void start() {
        started = true;
        state = 0;
        runTime.reset();
    }

    public void next() {
        state++;
        robot.stop();
        lastTick = runTime.milliseconds();
    }

    public void update() {
        if (handler != null) handler.update();
    }

    public void feedback(Telemetry telemetry) {
        if (handler != null) handler.feedback(telemetry);
    }

}