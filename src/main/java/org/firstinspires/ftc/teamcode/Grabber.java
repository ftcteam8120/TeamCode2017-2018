package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//Relic Grabber
public class Grabber implements Module{
    //motor for extension of relic arm
    private SmartMotor out;
    //servo to clamp down grabbing mechanism
    private Servo claw;
    //Servo to rotate Relic above the perimeter wall
    private Servo elbow;

    /**
     * Constructor
     */
    public Grabber(DcMotor o2, Servo c, Servo e){
        out= new SmartMotor(o2, true);
        claw=c;
        elbow=e;
    }

    /**
     * To prepare the position of grabber for motion
     */
    public void init(){
        out.init(DcMotorSimple.Direction.FORWARD);
        release();
        flipDown();
        stop();

    }

    /**
     * Activiates motor to move outward
     *
     * @param pow the power to which the motor is set
     */
    public void extend(double pow){
        out.setPower(pow);
    }

    /**
     * Activiates motor to move inward
     *
     * @param pow the power to which the motor is set
     */
    public void retract(double pow){
        out.setPower(-pow);
    }

    /**
     * Deactiviates motor
     */
    public void stop()
    {
        out.setPower(0);
    }

    @Override
    public void update() {
        // Not yet implemented
    }

    @Override
    public void feedback(Telemetry telemetry) {
        // Not yet implemented
    }

    /**
     * Turns Servo so Grabber can extend over the wall
     */
    public void flipUp()
    {
        // RIGHT LIMIT
        elbow.setPosition(1);
    }

    /**
     * Turns Servo so the relic can be placed
     */
    public void flipDown()
    {
        // LEFT LIMIT
        elbow.setPosition(0);
    }

    /**
     * Closes claw to grab Relic
     */
    public void grab()
    {
        claw.setPosition(0);
    }

    /**
     * Opens claw to let go of Relic
     */
    public void release()
    {
        claw.setPosition(1);
    }

}
