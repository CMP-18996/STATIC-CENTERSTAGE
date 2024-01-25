package org.firstinspires.ftc.teamcode.tests.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
public class TransferTest1 extends LinearOpMode {

    public static double GRABBER_OPEN_VALUE = 0.75;
    public static  double GRABBER_CLOSED_VALUE = 0.25;

    public static double armDown = 0.08;
    public static double armUp = 0.7;

    public static double intakeMult = 1;


//    public static boolean grabber1 = false;
//    public static boolean grabber2 = false;

    public static double armVal = 0.5;
    public static double wristNorm = 0.48;
    public static double wristSweep = .5;

    @Override
    public void runOpMode() throws InterruptedException {

        Servo gripper1 = hardwareMap.get(Servo.class, "leftGrabber");
        Servo gripper2 = hardwareMap.get(Servo.class, "rightGrabber");

        Servo wrist = hardwareMap.get(Servo.class, "depositRotator");
        Servo arm = hardwareMap.get(Servo.class, "fourBar");

        Servo lid = hardwareMap.get(Servo.class, "cover");
        DcMotorEx intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while(opModeIsActive()) {

            gripper1.setPosition((gamepad1.a) ? GRABBER_CLOSED_VALUE : GRABBER_OPEN_VALUE);
            gripper2.setPosition((gamepad1.a) ? GRABBER_CLOSED_VALUE : GRABBER_OPEN_VALUE);

            if(gamepad1.right_bumper) {
                arm.setPosition(armDown);
            }
            if(gamepad1.left_bumper) {
                arm.setPosition(armUp);
            }
            if(gamepad1.dpad_up) {
                wrist.setPosition(wristSweep);
            } else {
                wrist.setPosition(wristNorm);
            }

            intakeMotor.setPower(intakeMult*gamepad1.left_stick_y);
            if(gamepad1.left_stick_y > 0) {
                lid.setPosition(.7775);
            } else {
                lid.setPosition(.0675);
            }


        }

    }
}
