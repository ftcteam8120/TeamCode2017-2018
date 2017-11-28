package org.firstinspires.ftc.teamcode.Augustus;


import com.qualcomm.robotcore.hardware.DcMotor;

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
        a.init();
        b.init();
        c.init();
        d.init();
    }

    public void stop()
    {
        setAllDrive(0);
        a.reset();
        b.reset();
        c.reset();
        d.reset();
    }

    public void setAllDrive(double pow)
    {
        a.setPower(pow);
        b.setPower(pow);
        c.setPower(pow);
        d.setPower(pow);
    }

    public void setDrive(double aPow, double bPow, double cPow, double dPow)
    {
        a.setPower(aPow);
        b.setPower(bPow);
        c.setPower(cPow);
        d.setPower(dPow);
    }

    public void setDrive(HoloDir dir, double pow)
    {
        setDrive(dir.a*pow,dir.b*pow,dir.c*pow,dir.d*pow);
    }


}
