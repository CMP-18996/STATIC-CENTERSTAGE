package org.firstinspires.ftc.teamcode.common.commandbase.minorcommands;

import android.icu.text.CaseMap;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem.LowerHorizontalState;


public class LowerHorizontalMoveCommand extends InstantCommand {
    public LowerHorizontalMoveCommand(DepositSubsystem deposit, LowerHorizontalState horizontalState) {
        super(
                () -> deposit.setLowerHorizontalState(horizontalState)
        );
    }
}