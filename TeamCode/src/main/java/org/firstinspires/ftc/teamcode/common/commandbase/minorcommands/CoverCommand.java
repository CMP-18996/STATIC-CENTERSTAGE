package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.IntakeSubsystem;

public class CoverCommand extends InstantCommand {
    public CoverCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.CoverState coverState) {
        super(
                () -> intakeSubsystem.updateCoverState(coverState)
        );
    }
}
