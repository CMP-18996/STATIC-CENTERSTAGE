package org.firstinspires.ftc.teamcode.common.commandbase.auto;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class DepositRotatorCommand extends InstantCommand {
    public DepositRotatorCommand(DepositSubsystem deposit, DepositSubsystem.DepositRotationState depositRotationState) {
        super(
                () -> deposit.setDepositRotationState(depositRotationState)
        );
    }
}
