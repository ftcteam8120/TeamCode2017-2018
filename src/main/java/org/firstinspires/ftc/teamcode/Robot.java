package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    public DriveTrain drive;
    public TurnTable table;
    public Arm primary;
    public Arm secondary;

    public void init(HardwareMap map)
    {
        drive = new DriveTrain(map.dcMotor.get("right"), map.dcMotor.get("left"), true);
        table = new TurnTable(map.dcMotor.get("turn_table"));
    }

    public void update()
    {
        table.update();
    }
}
