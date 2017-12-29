package org.firstinspires.ftc.teamcode.Augustus.Autonomous.Park;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "Park_Blue_2", group = "Augustus")
public class Blue2 extends OpMode
{
    private AutonomousHandler handler;
    private Robot augustus;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, 0);
        handler = new AutonomousHandler(augustus, true, 2);
    }

    @Override
    public void loop() {
        augustus.update();
        handler.park(.2);
    }
}
