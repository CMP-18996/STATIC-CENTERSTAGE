package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class XAxisCommand extends InstantCommand {
    public XAxisCommand(DepositSubsystem deposit, DepositSubsystem.LowerHorizontalState horizontalState) {
        super(
                () -> deposit.setLowerHorizontalState(horizontalState)
        );
    }

    public XAxisCommand(DepositSubsystem deposit, DepositSubsystem.UpperHorizontalState horizontalState) {
        super(
                () -> deposit.setUpperHorizontalState(horizontalState)
        );
    }
}
