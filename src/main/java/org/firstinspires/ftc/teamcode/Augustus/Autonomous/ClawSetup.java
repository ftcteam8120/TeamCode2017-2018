package org.firstinspires.ftc.teamcode.Augustus.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "ClawSetup", group = "Augustus")
public class ClawSetup extends OpMode{

    private AutonomousHandler handler;
    private Robot augustus;

    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, null);
        handler = new AutonomousHandler(augustus);
    }

    public void loop() {
        augustus.update();
        augustus.elevator.claw.grab(true);
    }
}