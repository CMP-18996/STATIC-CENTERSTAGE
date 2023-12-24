package org.firstinspires.ftc.teamcode.opmode.tests.arm;

import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.Robot;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import java.util.concurrent.TimeUnit;

@TeleOp(name="Encoder Test",group="test")
public class EncoderTest extends LinearOpMode {
    Robot robot;
    DcMotorEx encoderMotor;
    Encoder encoder;
//    Timing.Timer timer = new Timing.Timer(1000, TimeUnit.MILLISECONDS);

    @Override
    public void runOpMode() throws InterruptedException {
        encoderMotor = hardwareMap.get(DcMotorEx.class, "encoderMotor");
//        PIDFCoefficients pidCoefficients = new PIDFCoefficients(0.5 , 0.5, 0, 0);
        encoderMotor.setMotorEnable();
        encoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();

//        timer.start();
        // 145.1 pulses per revolution
        while (opModeIsActive()) {
            encoderMotor.setTargetPosition(145);
            encoderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            telemetry.addData("Position: ", encoderMotor.getCurrentPosition());
            encoderMotor.setPower(0.5);
            sleep(200);
            encoderMotor.setTargetPosition(-145);
            encoderMotor.setPower(0.5);
            sleep(200);
            break;
        }
    }
}
