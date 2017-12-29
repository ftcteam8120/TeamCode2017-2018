package org.firstinspires.ftc.teamcode.Augustus.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Augustus.Robot;

@TeleOp(name = "ColorReader", group = "Augustus")
public class ColorReader extends OpMode
{
    Robot augustus;
    private ElapsedTime runtime;

    @Override
    public void init() {
        augustus = new Robot();
        augustus.init(hardwareMap, 0);
        runtime = new ElapsedTime();
        runtime.reset();
    }

    @Override
    public void loop() {
        telemetry.addData("left_back", "r:" + augustus.left_back.red() + " b:" + augustus.left_back.blue());
        telemetry.addData("left_front", "r:" + augustus.left_front.red() + " b:" + augustus.left_front.blue());
        telemetry.addData("right_back", "r:" + augustus.right_back.red() + " b:" + augustus.right_back.blue());
        telemetry.addData("right_front", "r:" + augustus.right_front.red() + " b:" + augustus.right_front.blue());
    }
}
