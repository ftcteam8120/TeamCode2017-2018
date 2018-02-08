package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes.BasicAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers.BasicAutonomous;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

//Basic Autonomous for Red Alliance
//Starting on the closest Balancing Stone to the Relic Recovery Zone
@Autonomous(name = "BasicAutoRedNear", group = "Augustus")
public class RedNear extends OpMode {
    private AutonomousHandler handler;
    private Robot augustus;

    /**
     * What occurs when this Class is initialized
     */
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        handler = new AutonomousHandler(augustus, Side.RED, Section.NEAR, new BasicAutonomous(1));
    }

    /**
     * What occurs when this class is played
     */
    public void loop() {
        augustus.update();
        handler.update();
        handler.feedback(telemetry);
    }
}
