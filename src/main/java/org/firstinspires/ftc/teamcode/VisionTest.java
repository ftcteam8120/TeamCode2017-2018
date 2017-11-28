package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Jerry.Constants;

@Autonomous(name = "VisionTest")
public class VisionTest extends OpMode
{
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

    @Override
    public void init()
    {
        //Initialize camera monitor
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = Constants.vuforiaKey;
        //Choose camera
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        //Load Relic Recovery marks
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();
    }


    RelicRecoveryVuMark vuMark;
    @Override
    public void loop()
    {
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        int which = -1;
        if(vuMark == RelicRecoveryVuMark.LEFT)
            which = 1;
        else if(vuMark == RelicRecoveryVuMark.CENTER)
            which = 2;
        else if(vuMark == RelicRecoveryVuMark.RIGHT)
            which = 3;
        else if(vuMark == RelicRecoveryVuMark.UNKNOWN)
            which = 0;

        if(which == -1)
            telemetry.addData("Mark", "ERROR");
        if(which == 0)
            telemetry.addData("Mark", "NONE");
        if(which == 1)
            telemetry.addData("Mark", "LEFT");
        if(which == 2)
            telemetry.addData("Mark", "MIDDLE");
        if(which == 3)
            telemetry.addData("Mark", "RIGHT");

    }
}
