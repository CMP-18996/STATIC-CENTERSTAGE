package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class UpperHorizontalMoveCommand extends InstantCommand {
    public UpperHorizontalMoveCommand(DepositSubsystem deposit, DepositSubsystem.UpperHorizontalState horizontalState) {
        super(
                () -> deposit.setUpperHorizontalState(horizontalState)
        );
    }
}