package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes.MediumAutonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers.MediumAutonomous;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

@Autonomous(name = "MedAutoBlueFar", group = "Augustus")
public class BlueFar extends OpMode {
    private AutonomousHandler handler;
    private Robot augustus;

    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        handler = new AutonomousHandler(augustus, Side.BLUE, Section.FAR, new MediumAutonomous());
        augustus.elevator.claw.clinch();
    }

    public void loop() {
        augustus.update();
        handler.update();
        handler.feedback(telemetry);
    }
}
