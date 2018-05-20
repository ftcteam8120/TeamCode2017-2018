package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static org.firstinspires.ftc.teamcode.QuickMaths.*;

public class DriveTrain implements Module {
    //boolean which tells whether or not to use encoders when running motors
    public boolean encoders;
    //Motors on base of our robot which are each attached to a wheel
    public SmartMotor a;
    public SmartMotor b;
    public SmartMotor c;
    public SmartMotor d;

    /**
     * Constructor
     */
    public DriveTrain(boolean e, DcMotor aM, DcMotor bM, DcMotor cM, DcMotor dM)
    {
        a = new SmartMotor(aM, e);
        b = new SmartMotor(bM, e);
        c = new SmartMotor(cM, e);
        d = new SmartMotor(dM, e);
        encoders = e;
    }

    /**
     * What occurs when this class is initialized
     */
    public void init()
    {
        a.init(DcMotorSimple.Direction.REVERSE);
        b.init(DcMotorSimple.Direction.REVERSE);
        c.init(DcMotorSimple.Direction.REVERSE);
        d.init(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Stop motors and reset encoders.
     */
    public void stop()
    {
        setAllDrive(0);
        a.reset();
        b.reset();
        c.reset();
        d.reset();
    }

    /**
     * Set all motors to the same power.
     *
     * @param pow the power which all motors are set
     */
    public void setAllDrive(double pow)
    {
        a.setPower(pow);
        b.setPower(pow);
        c.setPower(pow);
        d.setPower(pow);
    }

    /**
     * Set each motor to a different power.
     *
     * @param cPow power to which motor c is set.
     * @param dPow power to which motor d is set.
     * @param aPow power to which motor a is set.
     * @param bPow power to which motor b is set.
     */
    public void setDrive(double cPow, double dPow, double aPow, double bPow)
    {
        a.setPower(aPow);
        b.setPower(bPow);
        c.setPower(cPow);
        d.setPower(dPow);
    }

    /**
     *Tells which direction to drive and how quickly
     *
     * @param dir a holodir constant
     * @param pow the power which all motors are set
     */
    public void setDrive(HoloDir dir, double pow)
    {
        setDrive(dir.c*pow,dir.d*pow,dir.a*pow,dir.b*pow);
    }

    /**
     * What angle to move at when the robot drives
     *
     * @param angle the angle of the stick
     * @param pow the power which all motors are set
     */
    public void setDrive(double angle, double pow)
    {
        if(isBetween(angle, 22.5, 67.5, true))
            setDrive(HoloDir.FORWARD_RIGHT, pow);
        else if(isBetween(angle, 67.5, 112.5, true))
            setDrive(HoloDir.RIGHT, pow);
        else if(isBetween(angle, 112.5, 157.5, true))
            setDrive(HoloDir.BACKWARD_RIGHT, pow);
        else if(isBetween(angle, 157.5, 202.5, true))
            setDrive(HoloDir.BACKWARD, pow);
        else if(isBetween(angle, 202.5, 247.5, true))
            setDrive(HoloDir.BACKWARD_LEFT, pow);
        else if(isBetween(angle, 247.5, 292.5, true))
            setDrive(HoloDir.LEFT, pow);
        else if(isBetween(angle, 292.5, 337.5, true))
            setDrive(HoloDir.FORWARD_LEFT, pow);
        else
            setDrive(HoloDir.FORWARD, pow);
    }

    /**
     * Function required by the Modules interface
     */
    public void update() {
        // Not yet necessary
    }

    /**
     * Function to determine the average value of the four drivetrain motor encoders
     *
     * @return returns the average value of all the Drivetrain encoders
     */
    public int getAverageTravel()
    {
        return (Math.abs(a.getDistanceTraveled()) +
                Math.abs(b.getDistanceTraveled()) +
                Math.abs(c.getDistanceTraveled()) +
                Math.abs(d.getDistanceTraveled()))/4;
    }

    /**
     * Displays feedback to the Driver station
     *
     * @param telemetry the data, some of which will be displayed on screen
     */
    public void feedback(Telemetry telemetry) {
        telemetry.addData("Drive Encoders", this.encoders);
        telemetry.addData("Values", "a:" + a.getDistanceTraveled() + "; b:" + b.getDistanceTraveled() + "; c:" + c.getDistanceTraveled() + "; d:" + d.getDistanceTraveled());
    }



}
