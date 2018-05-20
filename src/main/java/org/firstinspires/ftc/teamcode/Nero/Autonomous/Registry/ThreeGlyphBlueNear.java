package org.firstinspires.ftc.teamcode.Nero.Autonomous.Registry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Nero.Autonomous.Handlers.ThreeGlyphAutonomous;
import org.firstinspires.ftc.teamcode.Nero.RobotNero;
import org.firstinspires.ftc.teamcode.Robot;

import static org.firstinspires.ftc.teamcode.Section.NEAR;
import static org.firstinspires.ftc.teamcode.Side.BLUE;

@Autonomous(name = "NeroAutoBlueNear3", group = "Nero")
public class ThreeGlyphBlueNear extends OpMode
{
    private AutonomousHandler handler;
    private Robot nero;
    private VuforiaLocalizer vuforia;

    /**
     * What occurs when this Class is initialized
     */
    public void init() {
        nero = new RobotNero();
        nero.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AeCcTnL/////AAAAGUAPbo6kUUuwjowvOPWZ039HrVUCpXqngD/djJBaWh9OLO7sz1Tvb6tDZFox4o0tHyDOV0HMrLolDE3N2qkPIcIeCCZL8gBiKocDomdnXkTlg7xu4buajp23E9h5jlDriyYsZVreM8wk3RkWFZC21nx+N8WFBj/9N3SsBU/7fGJDzFq8I7/InurDgkQ/Zk3Uc4dGwM7SEmuJsI0oKrKfjrBOBWdFU3mVFhfokTebsb7/gZOtsEzJUP+oUpxS0lDvz5jfXVOkiVnXPLa9dyeC8ciRyKZ5WE1j+F+LFXcbEDfwObqwQ5mlSSuwASHh3OxXhJ9tT25S/WAhzzORUBs0PiShQuNsBGpKIFy0k87C3qxF";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTrackables.activate();

        handler = new AutonomousHandler(nero, BLUE, NEAR, new ThreeGlyphAutonomous(relicTrackables.get(0)));
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
