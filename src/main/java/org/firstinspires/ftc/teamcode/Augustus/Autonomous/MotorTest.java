package org.firstinspires.ftc.teamcode.Augustus.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "MotorTest", group = "Augustus")
public class MotorTest extends OpMode
{
    private AutonomousHandler handler;
    private Robot augustus;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, 0);
        handler = new AutonomousHandler(augustus);
    }

    @Override
    public void loop() {
        augustus.update();
        handler.motorTest(.5);
    }
}
