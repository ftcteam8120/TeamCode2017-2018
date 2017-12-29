package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name="Retract", group="Jerry")
public class RetractArm extends OpMode
{
    Robot robot;

    @Override
    public void init()
    {
        robot = new Robot();
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        robot.primary.elbow.setPower(-.3);
    }
}
