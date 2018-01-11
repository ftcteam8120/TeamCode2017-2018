package org.firstinspires.ftc.teamcode.Augustus;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Elevator implements Module {
    private boolean encoder;

    private DcMotor y;
    private DcMotor x;
    public Claw claw;
    public ElevatorXPos xPos;

    // x moves horizontally
    // y moves vertically
    public Elevator(boolean e, DcMotor y, DcMotor x, Claw claw)
    {
        encoder = e;
        this.y = y;
        this.x = x;
        this.claw = claw;
        xPos = ElevatorXPos.STOPPED;
    }

    public void init() {
        y.setDirection(DcMotorSimple.Direction.REVERSE);
        x.setDirection(DcMotorSimple.Direction.FORWARD);
        if(encoder)
        {
            y.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            y.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            x.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            x.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        else {
            y.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            x.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        this.stopHoriz();
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
        y.setPower(-0.5);
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
                if (x.getCurrentPosition() < -6300) {
                    xPos = ElevatorXPos.STOPPED;
                } else {
                    x.setPower(0.5);
                }
                break;
            case RETRACTED:
                if (x.getCurrentPosition() > -200) {
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

    public void feedback(Telemetry telemetry) {
        telemetry.addData("Elevator X Pos", this.x.getCurrentPosition());
        telemetry.addData("Elevator Y Pos", this.y.getCurrentPosition());
        telemetry.addData("Elevator X State", this.getElevatorXStateString());
        if (claw != null) claw.feedback(telemetry);
    }
}
