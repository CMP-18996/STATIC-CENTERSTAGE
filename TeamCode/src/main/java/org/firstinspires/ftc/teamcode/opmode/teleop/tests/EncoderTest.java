package org.firstinspires.ftc.teamcode.opmode.teleop.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.IntakeWait;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


public class EncoderTest extends LinearOpMode {
    Robot robot;
    DcMotorEx encoder;

    @Override
    public void runOpMode() throws InterruptedException {
        encoder = hardwareMap.get(DcMotorEx.class, "encoderMotor");

        while (opModeIsActive()) {
            System.out.println("system errors can fuck off");
        }
    }
}
