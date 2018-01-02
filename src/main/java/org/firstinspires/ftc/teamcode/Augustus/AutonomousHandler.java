package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Augustus.Robot;

public class AutonomousHandler {

    Robot robot;
    boolean blue;
    int section;
    int state;
    //
    private boolean started;
    private ElapsedTime runTime;
    private double lastTick;

    public AutonomousHandler(Robot robot, boolean blue, int section)
    {
        this.robot = robot;
        this.blue = blue;
        //relic side = 1; other side = 2;
        this.section = section;
        state = -999;
        runTime = new ElapsedTime();
        started = false;
        lastTick = 0;
    }

    public AutonomousHandler(Robot robot)
    {
        this(robot, false, 0);
    }

    public void start(){
        started = true;
        state = 0;
        runTime.reset();
    }

    public void next()
    {
        state++;
        robot.stop();
    }



    //  7:^]
    //  ROUTINES
    //  7:^]


    /**
     * Move robot to park in front of Cryptobox.
     *
     * Case 0: move forward for 1800 encoder tick.
     *
     * Case 1: move right if blue, left if red.
     *
     * Case 2: Stop robot.
     *
     * @param speed the speed at which the robot moves in all cases.
     */
    public void parkOnly(double speed)
    {
        if(!started) start();
        switch(state)
        {
            case 0:
                if(robot.drive.a.getDistanceTraveled() > 1800) next();
                robot.drive.setDrive(HoloDir.FORWARD, speed);
                break;

            case 1:
                if(robot.drive.a.getDistanceTraveled() > 800) next();
                robot.drive.setDrive((blue ? HoloDir.RIGHT : HoloDir.LEFT), speed);
                break;

            case 2:
                robot.stop();
                break;
        }
    }

    public void full(double speed)
    {
        if(!started) start();
        switch(state)
        {
            case 0:
                robot.knocker.out();
                lastTick = runTime.milliseconds();
                state++;
                break;

            case 1:
                if(runTime.milliseconds() >= (lastTick + 500)) state++;
                break;


        }
    }

    /**
     * Spins robot.
     *
     * @param speed sets all motors to the same speed.
     */
    public void spin(double speed){
        robot.drive.setAllDrive(speed);
    }

    /**
     * Move off balancing stone and test movement in all directions.
     *
     * Case 0: move foreward for 3 seconds
     *
     * Case 1: move right for 3 seconds.
     *
     * Case 2: move backwards for 3 seconds.
     *
     * Case 3: move left for 3 seconds.
     *
     * Default: stops robot.
     *
     * @param speed Speed that the motors are set to when the robot moves.
     */
    public void dismount(double speed)
    {
        if(!started) start();
        switch(state)
        {
            case 0:
                if(runTime.seconds() >= 3)  state++;
                robot.drive.setDrive(HoloDir.FORWARD, speed);
                break;
            case 1:
                if(runTime.seconds() >= 6)  state++;
                robot.drive.setDrive(HoloDir.RIGHT, speed);
                break;
            case 2:
                if(runTime.seconds() >= 9)  state++;
                robot.drive.setDrive(HoloDir.BACKWARD, speed);
                break;
            case 3:
                if(runTime.seconds() >= 12)  state++;
                robot.drive.setDrive(HoloDir.LEFT, speed);
                break;
            default:
                robot.stop();
                break;

        }
    }

    /**
     * Run each motor for one second to test them.
     *
     * Case 0: runs motor a for 1 second.
     *
     * Case 1: runs motor b for 1 second.
     *
     * Case 2: runs motor c for 1 second.
     *
     * Case 3: runs motor d for 1 second.
     *
     * Case 4: stops robot.
     *
     * @param speed the speed at whihc all motors are run at.
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
}
