package org.firstinspires.ftc.teamcode.Augustus.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Augustus.HoloDir;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@TeleOp(name = "AugustusDrive", group = "Augustus")
public class AugustusDrive extends OpMode
{
    Robot augustus;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap);
    }

    double v;
    double h;
    //
    double aPow;
    double bPow;
    double cPow;
    double dPow;
    //
    double rotate;
    //
    double speed;

    @Override
    public void loop() {
        updateDriver();

        augustus.update();
        feedback();
    }

    public void updateDriver()
    {
        if(gamepad1.b)
            augustus.stop();

        if(gamepad1.right_trigger > 0)
            speed = .4;
        else
            speed = .2;

        v = -gamepad1.left_stick_y;
        h = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if(rotate != 0)
            augustus.drive.setAllDrive(rotate);
        else if(gamepad1.dpad_right)
            augustus.drive.setDrive(HoloDir.RIGHT, speed);
        else if(gamepad1.dpad_left)
            augustus.drive.setDrive(HoloDir.LEFT, speed);
        else if(gamepad1.dpad_up)
            augustus.drive.setDrive(HoloDir.FORWARD, speed);
        else if(gamepad1.dpad_down)
            augustus.drive.setDrive(HoloDir.BACKWARD, speed);
        else
        {
            aPow = -v - h;
            bPow = v - h;
            cPow = v + h;
            dPow = -v + h;
            augustus.drive.setDrive(aPow, bPow, cPow, dPow);
        }
    }

    public void feedback()
    {
        telemetry.addData("breathing", "yes");
        telemetry.addData("encoders", augustus.drive.encoders);
        telemetry.addData("distance",
                augustus.drive.a.getDistanceTraveled() + ", " +
                augustus.drive.b.getDistanceTraveled() + ", " +
                augustus.drive.c.getDistanceTraveled() + ", " +
                augustus.drive.d.getDistanceTraveled()
        );
    }
}
