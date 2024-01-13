package org.firstinspires.ftc.teamcode.opmode.tests;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.awt.font.NumericShaper;

@Disabled
@TeleOp
@Config
public class OuttakeTest extends LinearOpMode {


    public static double F = 0.05;
    public static double P = 0.005;

    public static double maxDesc = 0.5;

    public static double servoAngle = 0.5;

    public static int target = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx liftL = hardwareMap.get(DcMotorEx.class, "liftL");
        DcMotorEx liftR = hardwareMap.get(DcMotorEx.class, "liftR");

        Servo servo = hardwareMap.get(Servo.class, "testServo");

        liftL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        liftL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftL.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while(opModeIsActive()) {

            servo.setPosition(servoAngle);

            int error = target - liftL.getCurrentPosition();
            double power = Range.clip(P * error + F, -maxDesc, 1);
            liftL.setPower(power);
            liftR.setPower(power);

            telemetry.addData("left", liftL.getCurrentPosition());
            telemetry.addData("right", liftR.getCurrentPosition());
            telemetry.addData("error", error);
            telemetry.update();


        }

    }
}