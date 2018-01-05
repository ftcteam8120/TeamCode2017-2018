package org.firstinspires.ftc.teamcode.Augustus;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Augustus.Claw;

public class JClaw implements Claw
{
    private DcMotor motor;
    //position of servo
    private ClawPos p = ClawPos.STOPPED;

    //encoder threshold
    private static final int THRESH = 50;

    public JClaw(DcMotor m)
    {
        motor = m;
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Close JClaw (to grab glyph).
     *
     * When in override mode the position of the claw is manually controlled. When not in
     * override mode the claw goes to a fixed grab position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void grab(boolean override) {
        if (override) {
            this.motor.setPower(1);
            p = ClawPos.OVERRIDE;
        } else {
            p = ClawPos.CLOSED;
        }
    }

    /**
     * Open JClaw.
     *
     * When in override mode the position of the claw is manually controlled. When not in
     * override mode the claw goes to a fixed grab position.
     *
     * @param override Is override mode enabled?
     */
    @Override
    public void release(boolean override) {
        if (override) {
            this.motor.setPower(-1);
            p = ClawPos.OVERRIDE;
        } else {
            p = ClawPos.OPEN;
        }
    }

    /**
     * Stop the claw for an emergency or other reason
     */
    @Override
    public void stop() {
        this.motor.setPower(0);
    }

    /**
     * Assign updated position of JClaw.
     */
    @Override
    public void update() {
        switch (p) {
            case CLOSED:
                if (motor.getCurrentPosition() > 0) {
                    this.p = ClawPos.STOPPED;
                } else {
                    this.motor.setPower(1);
                }
                break;
            case OPEN:
                if (motor.getCurrentPosition() < -7300) {
                    this.p = ClawPos.STOPPED;
                } else {
                    this.motor.setPower(-1);
                }
                break;
            case OVERRIDE:
                // The claw position is not being manually controlled
                // DO NOTHING
                break;
            case LOAD:
                // Move the claw to m
                if (motor.getCurrentPosition() < -8200) {
                    this.p = ClawPos.STOPPED;
                } else {
                    this.motor.setPower(-1);
                }
                break;
            case STOPPED:
                // The claw is currently stopped and not moving at all
                this.motor.setPower(0);
                break;
        }
    }

    public void setPosition(ClawPos pos) {
        p = pos;
    }

    private String getClawStateString() {
        switch (p) {
            case CLOSED:
                return "Closed";
            case OPEN:
                return "Open";
            case OVERRIDE:
                return "Override";
            case STOPPED:
                return "Stopped";
            case LOAD:
                return "Load";
            default:
                return "None";
        }
    }

    public void feedback(Telemetry telemetry) {
        telemetry.addData("Claw:", motor.getCurrentPosition());
        telemetry.addData("Claw State:", this.getClawStateString());
    }
}
