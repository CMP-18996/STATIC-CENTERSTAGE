package org.firstinspires.ftc.teamcode.opmode.tests.arm;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@Disabled
@TeleOp(name="Intake Test Two")
public class IntakeTestTwo extends LinearOpMode {
    private final double BASE_POWER = 0.3;
    DcMotorEx intakeMotor;
    @Override
    public void runOpMode() throws InterruptedException {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        waitForStart();
        while (opModeIsActive()) {
            intakeMotor.setPower(-(BASE_POWER + 0.7 * gamepad1.left_stick_y));
        }
    }
}
