package org.firstinspires.ftc.teamcode.Augustus.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "DirectionTest", group = "Augustus")
public class DirectionTest extends OpMode
{
    private AutonomousHandler handler;
    private Robot augustus;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap);
        handler = new AutonomousHandler(augustus);
    }

    @Override
    public void loop() {
        if(!handler.started)
            handler.start();
        augustus.update();
        handler.dismount(.5);
    }
}
