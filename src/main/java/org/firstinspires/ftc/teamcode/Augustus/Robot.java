package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ColorRangeSensor;
import org.firstinspires.ftc.teamcode.DriveTrain;
import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Knocker;

public class Robot
{
    //Instance variable which controls the robots Drivetrain
    public DriveTrain drive;
    //Instance Variable which controls the robots Elevator
    public Elevator elevator;
    //Instance variable which controls the robots Knocker
    public Knocker knocker;
    //Color Sensor attached to the Knocker
    public ColorRangeSensor right;
    //Instance Variable which controls the robots Grabber
    public Grabber grabber;

    /**
     * What runs when the robot is initted
     *
     * @param map where everything is located on the robot
     * @param clawType which variation of the Claw module is being used
     */
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
                map.dcMotor.get("x"),
                clawType == ClawType.DOUBLE ?
                        new DoubleClaw(map.servo.get("cL"), map.servo.get("cR"))
                        :
                        clawType == ClawType.SINGLE ?
                            new SingleClaw(map.servo.get("cM"))
                            :
                            clawType == ClawType.J ?
                                    new JClaw(map.dcMotor.get("cJ"))
                                    :
                                    null
        );

        grabber = new Grabber(map.dcMotor.get("extender"), map.servo.get("relic_grabber"), map.servo.get("relic_flipper"));
        knocker = new Knocker(map.servo.get("kX"), map.servo.get("kY"), right);

        // Initialize modules
        drive.init();
        elevator.init();
        grabber.init();
        knocker.init();

        stop();
    }

    /**
     * Initialize Color Sensors
     *
     * @param map Hardware map for robot.
     */
    public void initSensors(HardwareMap map) {
        // Load the right color/range sensor from the hardware map
        LynxI2cColorRangeSensor right_color_range = map.get(LynxI2cColorRangeSensor.class, "right_color_range");
        right = new ColorRangeSensor(right_color_range);
        right.calibrate();
    }

    /**
     * Run internal checks on modules
     */
    public void update() {
        if(elevator != null)
            elevator.update();
    }

    /**
     * Stop all components of the robot.
     */
    public void stop() {
        drive.stop();
        if(elevator != null)
            elevator.stop();
    }

    /**
     * Function which prints information onto the Drivers stations phone
     *
     * @param telemetry what is printed onto the screen
     */
    public void feedback(Telemetry telemetry) {
        this.drive.feedback(telemetry);
        this.knocker.feedback(telemetry);
        this.elevator.feedback(telemetry);
        this.knocker.feedback(telemetry);
    }
}
