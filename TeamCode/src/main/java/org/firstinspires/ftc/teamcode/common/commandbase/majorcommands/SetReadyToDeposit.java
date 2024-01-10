package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositExpansionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;


public class SetReadyToDeposit extends SequentialCommandGroup {
    public SetReadyToDeposit(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, LiftSubsystem.LiftHeight LiftHeight) {
       addCommands(
               new DepositExpansionCommand(depositSubsystem, DepositSubsystem.ExpandedState.EXPANDED_STATE),
               new LiftCommand(liftSubsystem, LiftHeight),
               new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH)
       );
    }
}
