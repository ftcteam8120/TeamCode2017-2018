package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Module;

public class Elevator implements Module {
    //boolean determining whether or not to use encoders when running motors
    private boolean encoder;
    //Controls up/down motion
    private SmartMotor y;
    // Controls in/out motion
    private SmartMotor x;
    //The claw currently attached to the robot
    public Claw claw;
    //A way of determining how the elevator is positioned laterally
    public ElevatorXPos xPos;


    /**
     * Constructor
     */
    public Elevator(boolean e, DcMotor y, DcMotor x, Claw claw)
    {
        encoder = e;
        this.y = new SmartMotor(y, true);
        this.x = new SmartMotor(x, true);
        this.claw = claw;
        xPos = ElevatorXPos.STOPPED;
    }

    /**
     * What occurs upon initialization of the Elevator
     */
    public void init() {
        y.init(DcMotorSimple.Direction.REVERSE);
        x.init(DcMotorSimple.Direction.FORWARD);

        this.stop();
    }

    /**
     * Move elevator out horizontally.
     */
    public void out(boolean manual) {
        if (manual) {
            x.setPower(0.5);
            xPos = ElevatorXPos.OVERRIDE;
        } else {
            xPos = ElevatorXPos.EXTENDED;
        }
    }

    /**
     * Move elevator in horizontally.
     */
    public void in(boolean manual) {
        if (manual) {
            x.setPower(-0.5);
            xPos = ElevatorXPos.OVERRIDE;
        } else {
            xPos = ElevatorXPos.RETRACTED;
        }
    }

    /**
     * Stop horizontal movement
     */
    public void stopHoriz() {
        x.setPower(0);
    }

    /**
     * Move elevator up.
     */
    public void up() {
        y.setPower(1);
    }

    /**
     * Move elevator down.
     */
    public void down() {
        y.setPower(-0.75);
    }

    /**
     * Stop vertical movement.
     */
    public void stopVert() {
        y.setPower(0);
    }

    public void stop() {
        this.stopHoriz();
        this.stopVert();
        claw.stop();
    }

    /**
     * Assign updated position if claw attached.
     */
    public void update() {
        switch (xPos) {
            case STOPPED:
                this.stopHoriz();
                break;
            case EXTENDED:
                if (x.getInternal().getCurrentPosition() < -6300) {
                    xPos = ElevatorXPos.STOPPED;
                } else {
                    x.setPower(0.5);
                }
                break;
            case RETRACTED:
                if (x.getInternal().getCurrentPosition() > -200) {
                    xPos = ElevatorXPos.STOPPED;
                } else {
                    x.setPower(-0.5);
                }
                break;
            case OVERRIDE:
                // The elevator x position is not being manually controlled
                // DO NOTHING
                break;
        }
        if(claw != null)
            claw.update();
    }

    /**
     * Used in telemetry to determine the lateral position of the Elevator
     *
     * @return the name as a String of the enum position
     */
    private String getElevatorXStateString() {
        switch (xPos) {
            case RETRACTED:
                return "Retracted";
            case EXTENDED:
                return "Extended";
            case OVERRIDE:
                return "Override";
            case STOPPED:
                return "Stopped";
            default:
                return "None";
        }
    }

    /**
     * Function to share how many encoder ticks the motor has traveled since initialization
     *
     * @return how far the motor has traveled
     */
    public int getXPos()
    {
        return this.x.getDistanceTraveled();
    }

    /**
     * Function to share how many encoder ticks the motor has traveled since initialization
     *
     * @return how far the motor has traveled
     */
    public int getYPos()
    {
        return this.y.getDistanceTraveled();
    }

    /**
     * Function which displays data to the Drivers Station
     *
     * @param telemetry The way which data is displayed to the Drivers Station
     */
    public void feedback(Telemetry telemetry) {
        telemetry.addData("Elevator X Pos", x.getInternal().getCurrentPosition());
        telemetry.addData("Elevator Y Pos", y.getInternal().getCurrentPosition());
        telemetry.addData("Elevator X State", this.getElevatorXStateString());
        if (claw != null) claw.feedback(telemetry);
    }
}
