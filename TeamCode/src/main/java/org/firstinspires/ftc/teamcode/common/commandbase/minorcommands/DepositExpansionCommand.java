package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class DepositExpansionCommand extends InstantCommand {
    public DepositExpansionCommand(DepositSubsystem deposit, DepositSubsystem.ExpandedState expandedState) {
        super(
                () -> deposit.setExpandedState(expandedState)
        );
    }
}
