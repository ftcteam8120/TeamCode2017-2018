package org.firstinspires.ftc.teamcode.Augustus;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public abstract class Handler {

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
     * The current robot state and list of previous states
     */
    public int state;
    public ArrayList<Integer> states;

    public void init(AutonomousHandler handler) {
        this.handler = handler;
        this.robot = handler.robot;
        this.side = handler.side;
        this.section = handler.section;
        states = new ArrayList<Integer>();
    }

    public void start() {
        this.state = 0;
        this.handler.start();
    }

    public void next() {
        states.add(state);
        state++;
        robot.stop();
        handler.lastTick = handler.runTime.milliseconds();
    }

    public void next(int nextState) {
        next();
        state = nextState;
    }

    public AutonomousHandler handler;

    abstract public void update();

    abstract public void feedback(Telemetry telemetry);
}
