package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;

public class RobotJerry implements Robot {

    public DriveTrain drive;
    public TurnTable table;
    public Arm primary;
    public Arm secondary;

    public void init(HardwareMap map)
    {
        drive = new DriveTrain(map.dcMotor.get("right"), map.dcMotor.get("left"), false);
        table = new TurnTable(map.dcMotor.get("turn_table"));
        primary = new Arm(map.dcMotor.get("elbow"), map.servo.get("wrist"), map.servo.get("claw"));
    }

    public void update()
    {
        table.update();
        primary.update();
    }

    public void stop()
    {
        drive.stop();
    }

    public void feedback(Telemetry telemetry){}
}
