package org.firstinspires.ftc.teamcode.opmode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.common.Robot;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.CoverCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@Disabled
@TeleOp
public class CoverTest extends CommandOpMode {
    Robot robot;
    IntakeSubsystem intakeSubsystem;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                        new WaitCommand(3000),
                        new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                        new WaitCommand(3000),
                        new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.OPEN),
                        new WaitCommand(3000),
                        new CoverCommand(intakeSubsystem, IntakeSubsystem.CoverState.CLOSED),
                        new WaitCommand(3000)
                )
        );
    }

    @Override
    public void run() {
        super.run();
    }
}
