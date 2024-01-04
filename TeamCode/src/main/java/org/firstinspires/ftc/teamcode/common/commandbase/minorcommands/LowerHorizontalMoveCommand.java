package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import android.icu.text.CaseMap;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem.LowerHorizontalState;

/* Old code
public class LowerHorizontalMoveCommand extends CommandBase {
    LowerHorizontalState desiredState;
    DepositSubsystem deposit;
    double distanceTraveled = 0;
    public LowerHorizontalMoveCommand(DepositSubsystem deposit, LowerHorizontalState horizontalState) {
        desiredState = horizontalState;
        this.deposit = deposit;
    }

    @Override
    public void initialize() {
        deposit.setLowerHorizontalState(desiredState);
    }

    @Override
    public boolean isFinished() {
        return !deposit.horizontalFinishedMoving();
    }
}
*/

public class LowerHorizontalMoveCommand extends InstantCommand {
    LowerHorizontalState desiredState;
    DepositSubsystem depositSubsystem;

    public LowerHorizontalMoveCommand(DepositSubsystem deposit, LowerHorizontalState horizontalState) {
        deposit.setLowerHorizontalState(horizontalState);
    }
}