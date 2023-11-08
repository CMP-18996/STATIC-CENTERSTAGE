package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.concurrent.TimeUnit;

public class IntakeWait extends CommandBase {
    private boolean f = false;
    DcMotor leftFront, rightFront, leftRear, rightRear;

    public IntakeWait(DcMotor leftFront, DcMotor rightFront, DcMotor leftRear, DcMotor rightRear) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftRear = leftRear;
        this.rightRear = rightRear;
    }

    @Override
    public void initialize() { }

    @Override
    public void execute() {
        Timing.Timer timer1 = new Timing.Timer(500, TimeUnit.MILLISECONDS);
        timer1.start();

        while (!timer1.isTimerOn()) {
            leftFront.setPower(-0.25);
            rightFront.setPower(-0.25);
            leftRear.setPower(-0.25);
            rightRear.setPower(-0.25);
        }

        Timing.Timer timer2 = new Timing.Timer(500, TimeUnit.MILLISECONDS);
        timer2.start();

        while (!timer2.isTimerOn()) {
            leftFront.setPower(0.25);
            rightFront.setPower(0.25);
            leftRear.setPower(0.25);
            rightRear.setPower(0.25);
        }
        f = true;
    }

    @Override
    public boolean isFinished() {
        return f;
    }
}
