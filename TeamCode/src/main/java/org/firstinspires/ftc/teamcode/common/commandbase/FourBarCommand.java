package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class FourBarCommand extends InstantCommand {
    public FourBarCommand(DepositSubsystem deposit, DepositSubsystem.FourBarState fourBarState) {
        super(
                () -> deposit.setFourBarState(fourBarState)
        );
    }
}
