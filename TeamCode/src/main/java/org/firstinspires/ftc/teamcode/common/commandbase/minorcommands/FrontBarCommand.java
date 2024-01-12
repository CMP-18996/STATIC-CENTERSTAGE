package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class FrontBarCommand extends InstantCommand {
    public FrontBarCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.FrontBarState frontBarState) {
        super(
                () -> {
                    intakeSubsystem.updateFrontBarState(frontBarState);
                }
        );
    }
}
