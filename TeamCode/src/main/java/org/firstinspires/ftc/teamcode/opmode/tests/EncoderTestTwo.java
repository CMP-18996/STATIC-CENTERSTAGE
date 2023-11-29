package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.common.Robot;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

@TeleOp(name="Encoder Test Two")
public class EncoderTestTwo extends LinearOpMode {
    private Robot robot;
    MotorEx encoderMotor;
    @Override
    public void runOpMode() throws InterruptedException {
        encoderMotor = hardwareMap.get(MotorEx.class, "encoderMotor");
        encoderMotor.setRunMode(Motor.RunMode.PositionControl);
        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
