package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot
{
    public DriveTrain drive;

    public void init(HardwareMap map)
    {
        drive = new DriveTrain(true,
            map.dcMotor.get("a"),
            map.dcMotor.get("b"),
            map.dcMotor.get("c"),
            map.dcMotor.get("d")
        );
        drive.init();

        stop();
    }

    public void update()
    {

    }

    public void stop()
    {
        drive.stop();
    }
}
