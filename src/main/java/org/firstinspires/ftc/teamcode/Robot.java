package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface Robot {

    void init(HardwareMap map);
    void stop();
    void update();
    void feedback(Telemetry telemetry);

}
