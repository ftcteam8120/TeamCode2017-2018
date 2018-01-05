package org.firstinspires.ftc.teamcode.Augustus.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "KnockerTest", group = "Augustus")
public class KnockerTest extends OpMode
{
    private AutonomousHandler handler;
    private Robot augustus;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, null);
        augustus.knocker.colorRangeSensor.calibrate();
        handler = new AutonomousHandler(augustus);
    }

    @Override
    public void loop() {
        augustus.update();
        handler.knock();
        augustus.knocker.colorRangeSensor.feedback(telemetry);
    }
}