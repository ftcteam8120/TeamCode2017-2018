package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot
{
    public DriveTrain drive;
    public Elevator elevator;
    public Knocker knocker;

    public ColorSensor right;
    public ColorSensor left;

    public void init(HardwareMap map, ClawType clawType)
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
                clawType == ClawType.DOUBLE ?
                        new DoubleClaw(map.servo.get("cL"), map.servo.get("cR"))
                        :
                        clawType == ClawType.SINGLE ?
                            new SingleClaw(map.servo.get("cM"))
                            :
                            clawType == ClawType.J ?
                                    new JClaw(map.servo.get("cJ"))
                                    :
                                    null
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
        right = map.colorSensor.get("color");
        right.enableLed(false);
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
