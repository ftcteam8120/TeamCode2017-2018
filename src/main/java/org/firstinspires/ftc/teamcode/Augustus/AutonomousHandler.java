package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Augustus.Robot;

public class AutonomousHandler {

    Robot robot;
    boolean blue;
    int section;
    int state;
    //
    public boolean started;
    private ElapsedTime runTime;

    public AutonomousHandler(Robot robot, boolean blue, int section)
    {
        this.robot = robot;
        this.blue = blue;
        this.section = section;
        state = 0;
        runTime = new ElapsedTime();
        started = false;
    }

    public AutonomousHandler(Robot robot)
    {
        this(robot, false, 0);
    }

    public void start(){
        started = true;
        runTime.reset();
    }

    public void park(double speed)
    {

    }

    public void spin(double speed){
        robot.drive.setAllDrive(speed);
    }

    public void dismount(double speed)
    {
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
}
