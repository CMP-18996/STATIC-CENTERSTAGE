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
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FrontBarCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

@Disabled
@TeleOp
public class FrontBarTest extends CommandOpMode {
    Robot robot;
    IntakeSubsystem intakeSubsystem;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(robot);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.GROUND),
                        new WaitCommand(5000),
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL1),
                        new WaitCommand(5000),
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL2),
                        new WaitCommand(5000),
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL3),
                        new WaitCommand(5000),
                        new FrontBarCommand(intakeSubsystem, IntakeSubsystem.FrontBarState.LEVEL4)
                )
        );
    }

    @Override
    public void run() {
        super.run();
        sleep(10);
    }
}
