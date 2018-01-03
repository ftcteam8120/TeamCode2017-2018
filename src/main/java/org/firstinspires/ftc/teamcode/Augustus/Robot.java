package org.firstinspires.ftc.teamcode.Augustus;

//import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;

public class Robot
{
    public DriveTrain drive;
    public Elevator elevator;
    public Knocker knocker;

    public ColorRangeSensor right;
    // public ColorRangeSensor left;

    public void init(HardwareMap map, ClawType clawType)
    {
        // Initialize sensors
        initSensors(map);

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
        knocker = new Knocker(map.servo.get("k0"), right);

        // Initialize modules
        drive.init();
        elevator.init();
        knocker.init();

        stop();
    }

    /**
     * Initialize Color Sensors
     *
     * @param map Hardware map for robot.
     */
    public void initSensors(HardwareMap map)
    {
        // Load the right color/range sensor from the hardware map
        LynxI2cColorRangeSensor right_color_range = map.get(LynxI2cColorRangeSensor.class, "right_color_range");
        right = new ColorRangeSensor(right_color_range);
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
