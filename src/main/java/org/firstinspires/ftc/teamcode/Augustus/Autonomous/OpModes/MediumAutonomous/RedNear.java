package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes.MediumAutonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers.MediumAutonomous;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

@Autonomous(name = "MedAutoRedNear", group = "Augustus")
public class RedNear extends OpMode {
    private AutonomousHandler handler;
    private Robot augustus;

    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        handler = new AutonomousHandler(augustus, Side.RED, Section.NEAR, new MediumAutonomous(1));
    }

    public void loop() {
        augustus.update();
        handler.update();
        handler.feedback(telemetry);
    }
}
