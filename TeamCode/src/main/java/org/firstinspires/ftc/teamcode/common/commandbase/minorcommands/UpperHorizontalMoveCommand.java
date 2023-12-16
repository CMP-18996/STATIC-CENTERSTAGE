package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;

public class UpperHorizontalMoveCommand extends CommandBase {
    DepositSubsystem.UpperHorizontalState desiredState;
    DepositSubsystem deposit;
    double distanceTraveled = 0;
    public UpperHorizontalMoveCommand(DepositSubsystem deposit, DepositSubsystem.UpperHorizontalState horizontalState) {
        desiredState = horizontalState;
        this.deposit = deposit;
    }

    @Override
    public void initialize() {
        deposit.setUpperHorizontalState(desiredState);
    }

    @Override
    public boolean isFinished() {
        return !deposit.horizontalFinishedMoving();
    }
}
