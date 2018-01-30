package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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

    /**
     * Run each motor for one second to test them in the proper A B C D order.
     *
     * Case 0: runs motor A for 1 second.
     *
     * Case 1: runs motor B for 1 second.
     *
     * Case 2: runs motor C for 1 second.
     *
     * Case 3: runs motor D for 1 second.
     *
     * Case 4: stops robot.
     *
     * @param speed the speed at which all motors are run at.
     */
    public void motorTest(double speed)
    {
        if(!started) start();
        switch(state)
        {
            case 0:
                if(runTime.seconds() > 1) state++;
                robot.drive.a.setPower(speed);
                break;
            case 1:
                if(runTime.seconds() > 2) state++;
                robot.drive.b.setPower(speed);
                break;
            case 2:
                if(runTime.seconds() > 3) state++;
                robot.drive.c.setPower(speed);
                break;
            case 3:
                if(runTime.seconds() > 4) state++;
                robot.drive.d.setPower(speed);
                break;
            case 4:
                robot.stop();
                break;
        }
    }

    public void update() {
        if (handler != null) handler.update();
    }

    public void feedback(Telemetry telemetry) {
        if (handler != null) handler.feedback(telemetry);
    }

}