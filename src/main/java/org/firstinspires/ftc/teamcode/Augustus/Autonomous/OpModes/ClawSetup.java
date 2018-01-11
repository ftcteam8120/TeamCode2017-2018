package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@Autonomous(name = "ClawSetup", group = "Augustus")
public class ClawSetup extends OpMode{

    private AutonomousHandler handler;
    private Robot augustus;

    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        handler = new AutonomousHandler(augustus);
    }

    public void loop() {
        augustus.update();
        augustus.elevator.claw.grab(true);
    }
}