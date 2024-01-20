package org.firstinspires.ftc.teamcode.opmode.tests;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IdentifyColorCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@TeleOp(name="Color Sensor Test Three")
public class ColorSensorTestThree extends CommandOpMode {
    Robot robot;
    IntakeSubsystem intakeSubsystem;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);
        super.schedule(
                new IntakeCommand(intakeSubsystem, IntakeSubsystem.SweepingState.INTAKING)
        );
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        intakeSubsystem.identifyColor();
        telemetry.addData("Color Detected in Slot One:", intakeSubsystem.slotOne.toString());
        telemetry.addData("Color Detected in Slot Two:", intakeSubsystem.slotTwo.toString());
        telemetry.update();
    }
}
