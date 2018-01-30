package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Augustus.SmartMotor;

//Relic Grabber
public class Grabber{
    //motor for extension of relic arm
    private SmartMotor out;
    //servo to clamp down grabbing mechanism
    private Servo claw;
    private Servo elbow;

    private double servoPos;


    public Grabber(DcMotor o2, Servo c, Servo e){
        out= new SmartMotor(o2, true);
        claw=c;
        elbow=e;
    }

    public void init(){
        out.init(DcMotorSimple.Direction.FORWARD);
        servoPos = 0;
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

    /**
     * Turns Servo so Grabber can extend over the wall
     */
    public void flipUp()
    {
        elbow.setPosition(0);
    }

    /**
     * Turns Servo so the relic can be placed
     */
    public void flipDown()
    {
        elbow.setPosition(1);
    }

    /**
     * Closes claw to grab Relic
     */
    public void grab()
    {
        if(servoPos > 0)
            servoPos--;
    }

    /**
     * Opens claw to let go of Relic
     */
    public void release()
    {
        if(servoPos < 2)
            servoPos++;
    }

    /**
     * Sets claw to half closed compared to its current position
     */
    public void update()
    {
        claw.setPosition(servoPos / 2.0);
    }

}
