package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.RobotAugustus;

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

    /**
     * What occurs when Handler is initialized
     *
     * @param handler the way of setting up what the handler is set to
     */
    public void init(AutonomousHandler handler) {
        this.handler = handler;
        this.robot = handler.robot;
        this.side = handler.side;
        this.section = handler.section;
        states = new ArrayList<Integer>();
    }

    /**
     * Starts the case machine
     */
    public void start() {
        this.state = 0;
        this.handler.start();
    }

    /**
     * Moves the case machine to the next case chronologically
     */
    public void next() {
        states.add(state);
        state++;
        robot.stop();
        handler.lastTick = handler.runTime.milliseconds();
    }

    /**
     * Moves the case machine to the specified case
     *
     * @param nextState case which the case machine is moved to
     */
    public void next(int nextState) {
        next();
        state = nextState;
    }

    /**
     * Causes a pause of a set amount to occur in autonomous code
     *
     * @param ms The number of milliseconds the pause goes on for
     *
     * @return Returns true only when the allotted amount of milliseconds have passed
     */
    public boolean pause(int ms){
        return handler.runTime.milliseconds() >= handler.lastTick + ms;
    }

    /**
     * Recursive call of a handler in order to use handlers inside classes
     */
    public AutonomousHandler handler;

    /**
     * Abstract class required of all child classes
     */
    abstract public void update();

    /**
     * Abstract class required of all child classes
     */
    abstract public void feedback(Telemetry telemetry);
}
