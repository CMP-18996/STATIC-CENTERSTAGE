package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImplOnSimple;

import org.firstinspires.ftc.teamcode.common.Drivers.HT16K33;
import org.firstinspires.ftc.teamcode.common.GlobalVariables;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.TouchpadSubsystem;

import java.util.HashMap;

@TeleOp(name="Lift Test Two")
public class LiftTestTwo extends CommandOpMode {
    Robot robot;
    LiftSubsystem liftSubsystem;
    @Override
    public void initialize() {
        GlobalVariables.opMode = GlobalVariables.OpMode.TELEOP;
        robot = new Robot(hardwareMap);
        liftSubsystem = new LiftSubsystem(robot);

        // Change to sequential command group
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                    new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTSEVEN),
                    new WaitCommand(1000),
                    new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.BASE),
                    new WaitCommand(1000),
                    new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTFIVE),
                    new WaitCommand(1000),
                    new LiftCommand(liftSubsystem, LiftSubsystem.LiftHeight.HEIGHTONE),
                    new WaitCommand(1000)
                )
        );
    }

    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.addData("LiftOne", robot.liftOne.getCurrentPosition());
        telemetry.addData("LiftTwo", robot.liftTwo.getCurrentPosition());
        telemetry.addData("Lift One Target", robot.liftOne.getDistance());
        telemetry.update();
    }
}