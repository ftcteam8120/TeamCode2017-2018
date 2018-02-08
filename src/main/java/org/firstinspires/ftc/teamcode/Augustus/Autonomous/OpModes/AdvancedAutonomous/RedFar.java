package org.firstinspires.ftc.teamcode.Augustus.Autonomous.OpModes.AdvancedAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Augustus.Autonomous.Handlers.AdvancedAutonomous;
import org.firstinspires.ftc.teamcode.Augustus.AutonomousHandler;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Augustus.Section;
import org.firstinspires.ftc.teamcode.Augustus.Side;

//Advanced Autonomous for the Red Alliance
// starting on the Balancing Stone furthest from the Relic Recovery Zone
@Autonomous(name = "AdvancedAutoRedFar", group = "Augustus")
public class RedFar extends OpMode {
    private AutonomousHandler handler;
    private Robot augustus;
    private VuforiaLocalizer vuforia;

    /**
     * What occurs when this Class is initialized
     */
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, ClawType.J);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AeCcTnL/////AAAAGUAPbo6kUUuwjowvOPWZ039HrVUCpXqngD/djJBaWh9OLO7sz1Tvb6tDZFox4o0tHyDOV0HMrLolDE3N2qkPIcIeCCZL8gBiKocDomdnXkTlg7xu4buajp23E9h5jlDriyYsZVreM8wk3RkWFZC21nx+N8WFBj/9N3SsBU/7fGJDzFq8I7/InurDgkQ/Zk3Uc4dGwM7SEmuJsI0oKrKfjrBOBWdFU3mVFhfokTebsb7/gZOtsEzJUP+oUpxS0lDvz5jfXVOkiVnXPLa9dyeC8ciRyKZ5WE1j+F+LFXcbEDfwObqwQ5mlSSuwASHh3OxXhJ9tT25S/WAhzzORUBs0PiShQuNsBGpKIFy0k87C3qxF";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTrackables.activate();

        handler = new AutonomousHandler(augustus, Side.RED, Section.FAR, new AdvancedAutonomous(1, relicTrackables.get(0)));
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
