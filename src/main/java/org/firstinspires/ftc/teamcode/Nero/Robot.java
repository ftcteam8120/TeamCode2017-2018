package org.firstinspires.ftc.teamcode.Nero;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ColorRangeSensor;
import org.firstinspires.ftc.teamcode.DriveTrain;
import org.firstinspires.ftc.teamcode.Grabber;
import org.firstinspires.ftc.teamcode.Knocker;

//Nero Robot class
public class Robot {

    // module declarations
    public LeftUpper leftUpper;
    public RightUpper rightUpper;

    public DriveTrain drive;
    public Knocker knocker;

    // sensor declarations
    private ColorRangeSensor color;

    public void init(HardwareMap map)
    {
        // initialize sensors
        initSensors(map);

        // map all actuators to modules
        drive = new DriveTrain(false, map.dcMotor.get("a"), map.dcMotor.get("b"), map.dcMotor.get("c"), map.dcMotor.get("d"));
        knocker = new Knocker(map.servo.get("kX"), map.servo.get("kY"), color);
        leftUpper = new LeftUpper(map.dcMotor.get("leftImp"), map.servo.get("leftKick"));
        rightUpper = new RightUpper(map.dcMotor.get("rightImp"), map.servo.get("rightKick"), map.dcMotor.get("slider"), map.servo.get("flipper"),
                new Grabber(map.dcMotor.get("extender"), map.servo.get("relic_grabber"), map.servo.get("relic_flipper")));

        // initialize all modules
        drive.init();
        knocker.init();
        leftUpper.init();
        rightUpper.init();

        // stop
        stop();
    }

    /**
     * Initialize Color Sensors
     *
     * @param map Hardware map for robot.
     */
    public void initSensors(HardwareMap map) {
        // Load the color/range sensor from the hardware map
        LynxI2cColorRangeSensor color_range = map.get(LynxI2cColorRangeSensor.class, "color_range");
        color = new ColorRangeSensor(color_range);
        color.calibrate();
    }

    // run internal module update functions
    public void update()
    {
        // not yet implemented
    }

    // stop the robot
    public void stop()
    {
        drive.stop();
    }

    // get telemetry readings
    public void feedback(Telemetry telemetry)
    {
        drive.feedback(telemetry);
    }
}
