package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class IntakeWait extends SequentialCommandGroup {
    // Don't need this anymore, probably should get rid of this
    // private boolean f = false;
    private IntakeSubsystem intake;
    private IntakeSubsystem.SweepingState sweepingState;

    public IntakeWait(IntakeSubsystem intake) {
        addCommands(
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.REPELLING),
                new WaitCommand(500),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.INTAKING),
                new WaitCommand(500),
                new IntakeCommand(intake, IntakeSubsystem.SweepingState.STOPPED)
        );
        addRequirements(intake);
    }
}
