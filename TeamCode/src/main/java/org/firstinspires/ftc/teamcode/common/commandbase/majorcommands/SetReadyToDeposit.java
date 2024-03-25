package org.firstinspires.ftc.teamcode.common.commandbase.majorcommands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.DepositExpansionCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.FourBarCommand;
import org.firstinspires.ftc.teamcode.common.commandbase.minorcommands.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystems.DepositSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystems.LiftSubsystem;


public class SetReadyToDeposit extends SequentialCommandGroup {
    public SetReadyToDeposit(DepositSubsystem depositSubsystem, LiftSubsystem liftSubsystem, LiftSubsystem.LiftHeight LiftHeight) {
       addCommands(
               new FourBarCommand(depositSubsystem, DepositSubsystem.FourBarState.HIGH),
               new WaitCommand(500),
               new LiftCommand(liftSubsystem, LiftHeight)
       );
    }
}
