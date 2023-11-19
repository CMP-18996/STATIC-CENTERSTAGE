package org.firstinspires.ftc.teamcode.common.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem.LowerHorizontalState;

public class LowerHorizontalMoveCommand extends CommandBase {
    LowerHorizontalState desiredState;
    double distanceTraveled = 0;
    public LowerHorizontalMoveCommand(DepositSubsystem deposit, LowerHorizontalState horizontalState) {
        desiredState = horizontalState;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
