package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutonomousHandler {

    /**
     * The robot object
     */
    Robot robot;
    /**
     * If the robot is on the blue side or not
     */
    Side side;
    /**
     * Which balancing stone the robot is on
     */
    Section section;
    /**
     * The current robot state
     */
    int state;
    //
    /**
     * If the auto is started
     */
    private boolean started;
    /**
     * The elapsed time
     */
    private ElapsedTime runTime;
    /**
     * The last tick time
     */
    private double lastTick;

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

    public void start(){
        started = true;
        state = 0;
        runTime.reset();
    }

    public void next()
    {
        state++;
        robot.stop();
        lastTick = runTime.milliseconds();
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
                robot.drive.setDrive(((side == Side.BLUE) ? HoloDir.RIGHT : HoloDir.LEFT), speed);
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

    public void knock()
    {
        if(!started) start();
        switch(state)
        {
            case 0:
                robot.knocker.out();
                break;
        }
    }

    // Autonomous constant values
    final double KNOCK_POWER = 0.4;
    final double AUTO_POWER = 1;
    final double ROTATE_POWER = 0.55;

    /**
     * Autonomous for the further two balancing stones
     */
    public void Auto1SectionOne()
    {
        if(!started) start();

        switch (state)
        {
            case 0:
                // Extend the knocker
                robot.knocker.out();
                next();
                break;
            case 1:
                // Actually detect the color of the jewel and knock
                // Spend 0.5s knocking the jewel off the platform
                if(runTime.milliseconds() >= (lastTick + 500)) next();
                if((side == Side.BLUE && robot.knocker.colorRangeSensor.isRed())
                        || (side == Side.RED && !robot.knocker.colorRangeSensor.isRed()))
                    robot.drive.setAllDrive(KNOCK_POWER);
                else
                    robot.drive.setAllDrive(-KNOCK_POWER);
                break;
            case 2:
                // Move back to the base position
                // Spend 500 ms doing this
                if(runTime.milliseconds() >= (lastTick + 500)) next();
                if(robot.drive.a.getDistanceTraveled() > 0)
                    robot.drive.setAllDrive(-KNOCK_POWER);
                else
                    robot.drive.setAllDrive(KNOCK_POWER);
                break;
            case 3:
                // Pull the knocker in
                robot.knocker.in();
                next();
                break;
            case 4:
                if (side == Side.BLUE) {
                    // Move backwards for 3s
                    if(runTime.milliseconds() >= (lastTick + 3000)) next();
                    robot.drive.setDrive(HoloDir.BACKWARD, AUTO_POWER);
                } else {
                    // Move forwards for 3s
                    if(runTime.milliseconds() >= (lastTick + 3000)) next();
                    robot.drive.setDrive(HoloDir.FORWARD, AUTO_POWER);
                }
                break;
            case 5:
                if(runTime.milliseconds() >= (lastTick + 2000)) next();
                if (side == Side.BLUE) {
                    // Move left for 2s
                    robot.drive.setDrive(HoloDir.LEFT, AUTO_POWER);
                } else {
                    // Move right for 2s
                    robot.drive.setDrive(HoloDir.RIGHT, AUTO_POWER);
                }
                break;
            case 6:
                if(runTime.milliseconds() >= (lastTick + 500)) next();
                if (side == Side.BLUE) {
                    // Rotate the robot right to turn toward the cryptobox
                    robot.drive.setAllDrive(ROTATE_POWER);
                } else {
                    // Rotate the robot left to turn toward the cryptobox
                    robot.drive.setAllDrive(-ROTATE_POWER);
                }
                break;
            case 7:
                // Extend the elevator for 1s
                if(runTime.milliseconds() >= (lastTick + 1000)) next();
                robot.elevator.out();
                break;
            case 8:
                // Stop the elevator from moving
                robot.elevator.stop();
                next();
                break;
            case 9:
                // Release the glyph and slowly back up for 1.5s
                if(runTime.milliseconds() >= (lastTick + 1500)) next();
                robot.elevator.claw.release(false);
                robot.drive.setDrive(HoloDir.BACKWARD, 0.15);
                break;
            case 10:
                robot.stop();
                break;
        }
    }

    /**
     * Autonomous for the closer two balancing stones
     */
    public void Auto1SectionTwo() {
        if (!started) start();
        switch (state) {
            case 0:
                robot.knocker.out();
                next();
                break;
            case 1:
                // Actually detect the color of the jewel and knock
                // Spend 0.5s knocking the jewel off the platform
                if(runTime.milliseconds() >= (lastTick + 500)) next();
                if((side == Side.BLUE && robot.knocker.colorRangeSensor.isRed())
                        || (side == Side.RED && !robot.knocker.colorRangeSensor.isRed()))
                    robot.drive.setAllDrive(KNOCK_POWER);
                else
                    robot.drive.setAllDrive(-KNOCK_POWER);
                break;
            case 2:
                // Move back to the base position
                // Spend 0.5s doing this
                if(runTime.milliseconds() >= (lastTick + 500)) next();
                if(robot.drive.a.getDistanceTraveled() > 0)
                    robot.drive.setAllDrive(-KNOCK_POWER);
                else
                    robot.drive.setAllDrive(KNOCK_POWER);
                break;
            case 3:
                // Pull the knocker in
                robot.knocker.in();
                next();
                break;
            case 4:
                // Move backwards for 3s
                if(runTime.milliseconds() >= (lastTick + 3000)) next();
                if(side == Side.BLUE) {
                    robot.drive.setDrive(HoloDir.BACKWARD, AUTO_POWER);
                } else {
                    robot.drive.setDrive(HoloDir.FORWARD, AUTO_POWER);
                }
                break;
            case 5:
                // Rotate the robot to turn toward the cryptobox
                if(runTime.milliseconds() >= (lastTick + 500)) next();
                robot.drive.setAllDrive(-ROTATE_POWER);
                break;
            case 6:
                // Extend the elevator for 1s
                if(runTime.milliseconds() >= (lastTick + 1000)) next();
                robot.elevator.out();
                break;
            case 7:
                // Stop the elevator from moving
                robot.elevator.stop();
                next();
                break;
            case 8:
                // Release the glyph and slowly back up for 1.5s
                if(runTime.milliseconds() >= (lastTick + 1500)) next();
                robot.elevator.claw.release(false);
                robot.drive.setDrive(HoloDir.BACKWARD, 0.15);
                break;
            case 9:
                robot.stop();
                break;
        }
    }

}