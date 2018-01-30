package org.firstinspires.ftc.teamcode.Augustus.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Augustus.ClawType;
import org.firstinspires.ftc.teamcode.Augustus.Robot;

@TeleOp(name = "Debug", group = "Augustus")
public class Debug extends OpMode
{
    Robot robot;
    private ElapsedTime runtime;

    @Override
    public void init() {
        robot = new Robot();
        robot.init(hardwareMap, null);
        runtime = new ElapsedTime();
        runtime.reset();
    }

    @Override
    public void loop() {
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
}
