package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

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
}
