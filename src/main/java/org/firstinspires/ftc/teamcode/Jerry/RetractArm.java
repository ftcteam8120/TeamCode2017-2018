package org.firstinspires.ftc.teamcode.Jerry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name="Retract", group="Jerry")
public class RetractArm extends OpMode
{
    RobotJerry robotJerry;

    @Override
    public void init()
    {
        robotJerry = new RobotJerry();
        robotJerry.init(hardwareMap);
    }

    @Override
    public void loop() {
        robotJerry.primary.elbow.setPower(-.3);
    }
}
