package org.firstinspires.ftc.teamcode.opmode.tests.arm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.common.Robot;

@TeleOp(name="Hang Test")
public class HangTest extends LinearOpMode {
    Robot robot;
    DcMotorEx liftL, liftR;
    @Override
    public void runOpMode() throws InterruptedException {
        liftL = hardwareMap.get(DcMotorEx.class, "liftL");
        liftR = hardwareMap.get(DcMotorEx.class, "liftR");
        liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        liftL.setMotorEnable();
        liftR.setMotorEnable();
        liftL.setPower(1);
        liftR.setPower(-1);
        sleep(500);
        liftL.setPower(0);
        liftR.setPower(0);

        while (opModeIsActive()) {}
    }
}
