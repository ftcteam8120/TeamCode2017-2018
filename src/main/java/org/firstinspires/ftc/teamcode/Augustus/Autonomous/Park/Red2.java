package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Park;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "Park_Red_2", group = "Augustus")
public class Red2 extends OpMode
{
    private AutonomousHandler handler;
    private Robot augustus;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, null);
        handler = new AutonomousHandler(augustus, false, 2);
    }

    @Override
    public void loop() {
        augustus.update();
        handler.parkOnly(.2);
    }
}
