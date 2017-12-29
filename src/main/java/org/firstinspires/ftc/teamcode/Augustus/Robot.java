package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot
{
    public DriveTrain drive;
    public Elevator elevator;
    public Knocker knocker;

    public ColorSensor left_back;
    public ColorSensor left_front;
    public ColorSensor right_back;
    public ColorSensor right_front;

    public void init(HardwareMap map, int clawType)
    {
        // Map modules
        drive = new DriveTrain(true,
            map.dcMotor.get("a"),
            map.dcMotor.get("b"),
            map.dcMotor.get("c"),
            map.dcMotor.get("d")
        );
        elevator = new Elevator(true,
                map.dcMotor.get("y"),
                map.crservo.get("xL"),
                map.crservo.get("xR"),
                clawType == 2 ?
                        new DoubleClaw(map.servo.get("cL"), map.servo.get("cR"))
                        :
                        clawType == 1 ?
                            new SingleClaw(map.servo.get("cM")) : null
        );
        knocker = new Knocker(map.servo.get("k0"));

        // Initialize modules
        drive.init();
        elevator.init();
        knocker.init();

        // Initialize sensors
        //initSensors(map);

        stop();
    }

    /**
     * Initialize Color Sensors
     *
     * @param map Hardware map for robot.
     */
    public void initSensors(HardwareMap map)
    {
        left_back = map.colorSensor.get("lb");
        left_front = map.colorSensor.get("lf");
        right_back = map.colorSensor.get("rb");
        right_front = map.colorSensor.get("rf");
    }

    /**
     * Run internal checks on modules
     */
    public void update()
    {
        elevator.update();
    }

    /**
     * Stop all components of the robot.
     */
    public void stop()
    {
        drive.stop();
        elevator.stop();
    }
}
