package org.firstinspires.ftc.teamcode.Augustus.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;
import org.firstinspires.ftc.teamcode.Constants;

@TeleOp(name = "Debug", group = "Augustus")
public class Debug extends OpMode
{
    Robot robot;
    private ElapsedTime runtime;
    private VuforiaTrackable template;

    @Override
    public void init() {
        //robot = new Robot();
        //robot.init(hardwareMap, ClawType.J);
        runtime = new ElapsedTime();
        runtime.reset();

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()));
        parameters.vuforiaLicenseKey = Constants.vuforiaLicenseKey;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        template = trackables.get(0);
        trackables.activate();

    }

    String which;

    @Override
    public void loop() {

        if(robot != null)
        {
            if(gamepad1.x)
                robot.knocker.out();
            else if(gamepad1.y)
                robot.knocker.in();

            if(gamepad1.a)
                robot.knocker.right();
            else if(gamepad1.b)
                robot.knocker.left();

            robot.feedback(telemetry);
        }

        RelicRecoveryVuMark pictograph = RelicRecoveryVuMark.from(template);
        switch(pictograph)
        {
            case LEFT:
                which = "LEFT";
                break;
            case RIGHT:
                which = "RIGHT";
                break;
            case CENTER:
                which = "CENTER";
                break;
            case UNKNOWN:
                which = "UNKNOWN";
                break;
        }

        telemetry.addData("pic", which);
    }
}
