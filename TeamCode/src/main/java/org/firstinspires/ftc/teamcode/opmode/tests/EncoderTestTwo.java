package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.Robot;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

@TeleOp(name="Encoder Test Two")
public class EncoderTestTwo extends LinearOpMode {
    private Robot robot;
    private DcMotorEx encoderMotor;
    private int targetLocation;
    @Override
    public void runOpMode() throws InterruptedException {
        encoderMotor = hardwareMap.get(DcMotorEx.class, "encoderMotor");
        targetLocation = 290;
//        encoderMotor.e;
        waitForStart();

        while (opModeIsActive()) {
            encoderMotor.setTargetPosition(targetLocation);
            encoderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            encoderMotor.setPower(0.4);
        }
    }
}
