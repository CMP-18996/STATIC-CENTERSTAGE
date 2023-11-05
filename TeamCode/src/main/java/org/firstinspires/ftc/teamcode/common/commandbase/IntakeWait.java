package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.acmerobotics.roadrunner.*;
import com.arcrobotics.ftclib.util.Timing;
//import org.firstinspires.ftc.teamcode.common.drive.MecanumDrive;
//import org.firstinspires.ftc.teamcode.common.drive.Drive;
//import org.firstinspires.ftc.teamcode.common.Robot;

public class IntakeWait extends CommandBase {

//    private MecanumDrive drive;
//    Idk if I even need this
//    private int motorPower;

    private boolean f;
    DcMotor leftFront, rightFront, leftRear, rightRear;

    public IntakeWait(DcMotor leftFront, DcMotor rightFront, DcMotor leftRear, DcMotor rightRear) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.leftRear = leftRear;
        this.rightRear = rightRear;
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {

        leftFront.setPower(-0.25);
        rightFront.setPower(-0.25);
        leftRear.setPower(-0.25);
        rightRear.setPower(-0.25);
    }

    @Override
    public boolean isFinished() {
        return f;
    }
}
