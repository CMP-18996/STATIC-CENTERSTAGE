package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;

public class IntakeWaitTest extends LinearOpMode {
    public void runOpMode() {
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        DcMotor rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        IntakeWait intakeWait = new IntakeWait(leftFront, rightFront, leftRear, rightRear);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                intakeWait.schedule();
            }
        }
    }
}
