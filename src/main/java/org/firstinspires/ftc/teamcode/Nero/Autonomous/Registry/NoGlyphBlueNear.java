package org.firstinspires.ftc.teamcode.Nero.Autonomous.Registry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Nero.Autonomous.Handlers.NoGlyphAutonomous;
import org.firstinspires.ftc.teamcode.Nero.RobotNero;
import org.firstinspires.ftc.teamcode.Robot;

import static org.firstinspires.ftc.teamcode.Section.*;
import static org.firstinspires.ftc.teamcode.Side.*;

@Autonomous(name = "NeroAutoBlueNear0", group = "Nero")
public class NoGlyphBlueNear extends OpMode
{
    private AutonomousHandler handler;
    private Robot nero;

    /**
     * What occurs when this Class is initialized
     */
    public void init() {
        nero = new RobotNero();
        nero.init(hardwareMap);
        handler = new AutonomousHandler(nero, BLUE, NEAR, new NoGlyphAutonomous());
    }

    /**
     * What occurs when this class is played
     */
    public void loop() {
        nero.update();
        handler.update();
        handler.feedback(telemetry);
    }
}
