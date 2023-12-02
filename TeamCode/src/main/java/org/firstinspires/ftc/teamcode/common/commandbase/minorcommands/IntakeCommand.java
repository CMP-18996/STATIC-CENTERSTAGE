package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class IntakeCommand extends InstantCommand {
    public IntakeCommand(IntakeSubsystem intake, IntakeSubsystem.SweepingState state) {
        super(
                () -> intake.updateSweepingState(state)
        );
    }
}
