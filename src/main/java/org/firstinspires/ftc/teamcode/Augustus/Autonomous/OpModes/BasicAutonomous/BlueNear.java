package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes.BasicAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers.BasicAutonomous;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

@Autonomous(name = "BasicAutoBlueNear", group = "Augustus")
public class BlueNear extends OpMode {
    private AutonomousHandler handler;
    private Robot augustus;

    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        handler = new AutonomousHandler(augustus, Side.BLUE, Section.NEAR, new BasicAutonomous(1));
    }

    public void loop() {
        augustus.update();
        handler.update();
        handler.feedback(telemetry);
    }
}
