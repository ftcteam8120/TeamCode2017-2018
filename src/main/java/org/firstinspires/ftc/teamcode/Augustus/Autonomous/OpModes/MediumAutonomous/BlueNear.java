package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes.MediumAutonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers.MediumAutonomous;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

//Medium Autonomous for the Blue Alliance
// starting on the Balancing Stone closest to the Relic Recovery Zone
@Autonomous(name = "MedAutoBlueNear", group = "Augustus")
public class BlueNear extends OpMode {
    private AutonomousHandler handler;
    private Robot augustus;

    /**
     * What occurs when this Class is initialized
     */
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);
        handler = new AutonomousHandler(augustus, Side.BLUE, Section.NEAR, new MediumAutonomous(1));
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
