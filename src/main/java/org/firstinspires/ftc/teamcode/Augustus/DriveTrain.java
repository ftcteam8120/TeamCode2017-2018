package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DriveTrain
{
    public boolean encoders;

    public SmartMotor a;
    public SmartMotor b;
    public SmartMotor c;
    public SmartMotor d;

    public DriveTrain(boolean e, DcMotor aM, DcMotor bM, DcMotor cM, DcMotor dM)
    {
        a = new SmartMotor(aM, e);
        b = new SmartMotor(bM, e);
        c = new SmartMotor(cM, e);
        d = new SmartMotor(dM, e);
        encoders = e;
    }

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
     * @param pow       the power which all motors are set
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
     * @param aPow power to which motor a is set.
     * @param bPow power to which motor b is set.
     * @param cPow power to which motor c is set.
     * @param dPow power to which motor d is set.
     */
    public void setDrive(double aPow, double bPow, double cPow, double dPow)
    {
        a.setPower(aPow);
        b.setPower(bPow);
        c.setPower(cPow);
        d.setPower(dPow);
    }

    /**
     *
     * @param dir a holodir constant
     * @param pow the power which all motors are set
     */
    public void setDrive(HoloDir dir, double pow)
    {
        setDrive(dir.a*pow,dir.b*pow,dir.c*pow,dir.d*pow);
    }


}
